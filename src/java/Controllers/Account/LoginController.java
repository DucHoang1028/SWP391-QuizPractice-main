package Controllers.Account;

import DAOs.UserDAO;
import Models.User;
import Utils.EncryptionUtils;
import Utils.ValidationUtils;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.json.JSONException;
import org.json.JSONObject;

public class LoginController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        String action = request.getParameter("action");
        if (action != null) {
            if (action.equals("logout")) {
                session.setAttribute("currentUser", null);
                session.invalidate();
                response.sendRedirect("home");
                return;
            } else {

            }
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            HttpSession session = request.getSession();

            String email = request.getParameter("email").trim();
            String password = request.getParameter("password").trim();
            /// if validate not full fill break all
            if (!Validate(email, password)) {
                try {
                    ResponseJSON(request, response,
                            false,
                            "Your login information is not valid");
                } catch (JSONException ex) {
                    Logger.getLogger(LoginController.class.getName())
                            .log(Level.SEVERE, null, ex);
                }
                return;
            }

            User currentUser = VerifyLogin(email, password);
            /// check if get user by email and hash not found 
            if (currentUser != null) {
                // account exist

                if (currentUser.isLocked()) {

                    /// check if account is not available
                    try {
                        ResponseJSON(request, response, false,
                                "Your account is locked! "
                                + "Please contact admin.");
                    } catch (JSONException ex) {
                        Logger.getLogger(LoginController.class.getName())
                                .log(Level.SEVERE, null, ex);
                    }

                } else if (IsLockedUser(currentUser)) {
                    /// check if locked temp becasue of too many attemped
                    try {
                        ResponseJSON(request, response,
                                false,
                                "Your account is locked temporarily until "
                                + currentUser.getLocked_until().getHour() + ":"
                                + currentUser.getLocked_until().getMinute() + ":"
                                + currentUser.getLocked_until().getSecond());
                    } catch (JSONException ex) {
                        Logger.getLogger(LoginController.class.getName())
                                .log(Level.SEVERE, null, ex);
                    }

                } else if (IsDeactievedUser(currentUser)) {
                    try {
                        ResponseJSON(request, response,
                                false,
                                "Your account is inactive. Please check your inbox ");

                    } catch (JSONException ex) {
                        Logger.getLogger(LoginController.class.getName())
                                .log(Level.SEVERE, null, ex);
                    }
                } else {
                    boolean isRemember = "true".equals(request.getParameter("remember"));
                    Cookie emailCookie = new Cookie("email", email);
                    Cookie passwordCookie = new Cookie("pass", password);
                    Cookie rememberCookie = new Cookie("remember", String.valueOf(isRemember));

                    if (isRemember) {
                        emailCookie.setMaxAge(60 * 60 * 24 * 30);
                        passwordCookie.setMaxAge(60 * 60 * 24 * 3);
                        rememberCookie.setMaxAge(60 * 60 * 24 * 3);
                    } else {
                        emailCookie.setMaxAge(0);
                        passwordCookie.setMaxAge(0);
                        rememberCookie.setMaxAge(0);

                    }
                    session.setAttribute(email, 0);
                    response.addCookie(emailCookie);
                    response.addCookie(passwordCookie);
                    response.addCookie(rememberCookie);
                    session.setAttribute("currentUser", currentUser);
                    String url = "";
                    if (currentUser.getRole() == 1) {
                        url = "home";
                    } else {
                        url = "dashboard";
                    }
                    try {
                        ResponseJSON(request, response,
                                true, "Login successfully", url);
                    } catch (JSONException ex) {
                        Logger.getLogger(LoginController.class.getName())
                                .log(Level.SEVERE, null, ex);
                    }
                }
            } else {

                // no match or account pass is wrong
                Integer count = (Integer) session.getAttribute(email);
                if (count == null) {
                    count = 0;
                }
                count++;
                session.setAttribute(email, count);
                if (count == 5) {
                    LockUserTemp(email);
                    try {
                        ResponseJSON(request, response, false,
                                "You input password wrong over 5 times. "
                                + "Locked temporary");
                    } catch (JSONException ex) {
                        Logger.getLogger(LoginController.class.getName()).
                                log(Level.SEVERE, null, ex);
                    }
                    session.setAttribute(email, 0);

                } else {
                    try {
                        ResponseJSON(request, response,
                                false,
                                "Your login information is not valid");
                    } catch (JSONException ex) {
                        Logger.getLogger(LoginController.class.getName())
                                .log(Level.SEVERE, null, ex);
                    }
                }

            }
        } catch (SQLException ex) {
            Logger.getLogger(LoginController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public boolean Validate(String email, String password) {
        // check null
        if (email == null || password == null) {
            return false;
        }

        // check email validate format from regexp
        ValidationUtils validation = new ValidationUtils();
        if (!validation.isValidEmail(email)) {
            return false;
        }
        return true;
    }

    public User VerifyLogin(String email, String password) throws SQLException {
        EncryptionUtils encrypt = new EncryptionUtils();
        UserDAO dao = new UserDAO();
        return dao.GetUserLogin(email, encrypt.EncodeMd5(password));
    }

    public boolean IsLockedUser(User user) {
        /// verify if it behind now it is locked
        if (user.getLocked_until() == null) {
            return false;
        }

        LocalDateTime now = LocalDateTime.now();
        LocalDateTime until = user.getLocked_until();
        return now.isBefore(until);
    }

    public boolean IsDeactievedUser(User user) {
        /// verify if it behind now it is locked
        return user.getStatus() == 0;
    }

    public void LockUserTemp(String email) {
        LocalDateTime now = LocalDateTime.now();
        /// Can be change duration lock in the future
        LocalDateTime until = now.plusMinutes(15);

        UserDAO dao = new UserDAO();
        dao.UpdateLockTemp(email, until);
    }

    public void ResponseJSON(HttpServletRequest request,
            HttpServletResponse response,
            boolean success, String message ) throws IOException, JSONException {
        response.setHeader("Access-Control-Allow-Origin", "*"); // Adjust this to your needs
        response.setHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS, DELETE");
        response.setHeader("Access-Control-Max-Age", "3600");
        response.setHeader("Access-Control-Allow-Headers", "Content-Type, Access-Control-Allow-Headers, Authorization, X-Requested-With");

        response.setContentType("application/json");
        PrintWriter out = response.getWriter();

        JSONObject jsonResponse = new JSONObject();
        jsonResponse.put("success", success);
        jsonResponse.put("message", message);
        jsonResponse.put("url", "");
        out.print(jsonResponse);
        out.flush();
    }

    public void ResponseJSON(HttpServletRequest request,
            HttpServletResponse response,
            boolean success, String message, String url) throws IOException, JSONException {
        response.setHeader("Access-Control-Allow-Origin", "*"); // Adjust this to your needs
        response.setHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS, DELETE");
        response.setHeader("Access-Control-Max-Age", "3600");
        response.setHeader("Access-Control-Allow-Headers", "Content-Type, Access-Control-Allow-Headers, Authorization, X-Requested-With");

        response.setContentType("application/json");
        PrintWriter out = response.getWriter();

        JSONObject jsonResponse = new JSONObject();
        jsonResponse.put("success", success);
        jsonResponse.put("message", message);
        jsonResponse.put("url", url);
        out.print(jsonResponse);
        out.flush();
    }
}
