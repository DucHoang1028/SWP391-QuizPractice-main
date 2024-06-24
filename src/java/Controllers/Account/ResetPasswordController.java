package Controllers.Account;

import DAOs.UserDAO;
import Models.User;
import Utils.EmailUtils;
import Utils.EncryptionUtils;
import jakarta.servlet.ServletContext;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.InputStream;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ResetPasswordController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getParameter("action");

        if (action == null || action.equals("sendemail")) {
            request.getRequestDispatcher("Views/Login/ResetPasswordView.jsp").forward(request, response);
        } else if (action.equals("verify")) {
            String token = request.getParameter("token");
            try {
                /// Check valid link
                boolean verifyValidLink = VerifyToken(token);
                if (verifyValidLink) {
                    //dispatch change password
                    request.setAttribute("token", token);
                    request.getRequestDispatcher("Views/Login/ChangeResetPassView.jsp").forward(request, response);
                } else {
                    // dispatch fail screen
                    response.sendRedirect("/app/reset-password?action=fail");

                }
            } catch (Exception ex) {
                response.sendRedirect("/app/reset-password?action=fail");
            }
        } else if ("success".equals(action)) {
            // dispatch view show success state
            request.setAttribute("message", "Your password reset link has been sent successfully");
            request.getRequestDispatcher("Views/Login/MessageSuccess.jsp").forward(request, response);

        } else if (action.equals("fail")) {
            request.setAttribute("message", "Your link session is expired");
            request.getRequestDispatcher("Views/Login/MessageFail.jsp").forward(request, response);
        } else {
            /// for default case
            request.getRequestDispatcher("Views/Login/ResetPasswordView.jsp").forward(request, response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            String action = request.getParameter("action");
            if (action.equals("sendemail")) {
                String email = request.getParameter("email");
                /// verify mail exist 
                UserDAO dao = new UserDAO();
                User forgotUser = dao.GetUserByEmail(email);
                if (forgotUser == null) {
                    request.setAttribute("action", "This email address is not exist in system!");
                    request.getRequestDispatcher("Views/Login/ResetPasswordView.jsp").forward(request, response);

                } else {
                    ///send email afeter that go to success page
                    SendResetEmail(email);
                    response.sendRedirect("/app/reset-password?action=success");
                }
            } else if (action.equals("changePassword")) {
                // Get token and verify before write to database
                String token = request.getParameter("token");
                if (token == null || VerifyToken(token) == false) {
                    response.sendRedirect("/app/reset-password?action=fail");
                } else {
                    /// verify match passwrod twice
                    String newPass = request.getParameter("newPass");
                    String Confirmation = request.getParameter("confirmation");
                    if (!newPass.equals(Confirmation)) {
                        request.setAttribute("token", token);
                        request.setAttribute("fail", "Your password is not match witch confirmation");
                        request.getRequestDispatcher("Views/Login/ChangeResetPassView.jsp").forward(request, response);
                    } else {
                        ChangePassword(token, newPass);
                        request.setAttribute("message", "Your password has been changed success");
                        request.getRequestDispatcher("Views/Login/MessageSuccess.jsp").forward(request, response);
                    }
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(ResetPasswordController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            response.sendRedirect("/app/reset-password?action=fail");

        }

    }

    private void SendResetEmail(String email) throws Exception {
        //Get email function send through tls 
        EmailUtils emailUtils = new EmailUtils();
         ServletContext context = getServletContext();

        InputStream inputStream = context.getResourceAsStream("/WEB-INF/System.properties");

        Properties properties = new Properties();
        properties.load(inputStream);

        ////Get from properties system file
        String durationString = properties.getProperty("reset.token.expire");
        int duration = Integer.parseInt(durationString);
        String token = GenerateResetToken(email, duration);
        String subject = "[QPS PASSWORD RESET] Your reset password link in QPS is ready!";
        //set link with 2 param : action and token 
        String content = "Dear client,\n Your reset password link is: http://localhost:8080/app/reset-password?action=verify&token="
                + java.net.URLEncoder.encode(token, "UTF-8")
                + ". Your link will be available for next "+durationString+" minutes.\n Group 4";
        emailUtils.SendResetEmail(email, subject, content);
    }

    private String GenerateResetToken(String email, long duration) throws Exception {
        /// Token =   Email + expire time
        ///Note: Need fix to can be configed in system file later !!!!
        LocalDateTime timeNow = LocalDateTime.now();
        String expireDate = timeNow.plusMinutes(duration).toString();

        String tokenMessage = email + "&" + expireDate;
        EncryptionUtils encrypt = new EncryptionUtils();

        return encrypt.EncodeBase64(tokenMessage);
    }

    private boolean VerifyToken(String token) throws Exception {
        EncryptionUtils encrypt = new EncryptionUtils();
        String verifyInformation = encrypt.DecodeBase64(token);

        //// check expire date with time now
        LocalDateTime expireDate = LocalDateTime.parse(verifyInformation.split("\\&")[1]);
        LocalDateTime now = LocalDateTime.now();
        if (!now.isBefore(expireDate)) {
            return false;
        }
        //// Check email address valid
        String email = verifyInformation.split("\\&")[0];
        UserDAO dao = new UserDAO();
        User validEmail = dao.GetUserByEmail(email);
        if (validEmail == null) {
            return false;
        }

        return true;
    }

    private String GetEmailFromToken(String token) throws Exception {
        /// decrypt token and get email
        EncryptionUtils encrypt = new EncryptionUtils();
        String verifyInformation = encrypt.DecodeBase64(token);

        String email = verifyInformation.split("\\&")[0];
        return email;
    }

    private void ChangePassword(String token, String password) throws Exception {
        String email = GetEmailFromToken(token);

        /// Encrypt md5 for comparing to write in db
        EncryptionUtils encrypt = new EncryptionUtils();
        String password_hash = encrypt.EncodeMd5(password);

        //Update in DAO
        UserDAO dao = new UserDAO();
        dao.UpdatePassword(email, password_hash);
    }
}
