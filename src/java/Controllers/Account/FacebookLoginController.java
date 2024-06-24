package Controllers.Account;

import DAOs.UserDAO;
import Models.User;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.sql.SQLException;
import org.json.JSONException;
import org.json.JSONObject;

public class FacebookLoginController extends HttpServlet {

    private static final String CLIENT_SECRET = "3fef27a3f24cc4798d97ad985ea307f6";
    private static final String CLIENT_ID = "990104009492966";
    private static final String REDIRECT_URI = "http://localhost:8080/app/login-facebook";
    private static final String STATE = "sQPS-G4"; // Use a more secure method

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, JSONException, SQLException {
        String code = request.getParameter("code");
        String state = request.getParameter("state");

        if (!"sQPS-G4".equals(state)) {
            response.sendRedirect("home?action=login&message=Login failed");

        }

        String accessTokenUrl = "https://graph.facebook.com/v12.0/oauth/access_token?"
                + "&client_id=" + CLIENT_ID
                + "&client_secret=" + CLIENT_SECRET
                + "&redirect_uri=" + REDIRECT_URI
                + "&code=" + code;

        URL url = new URL(accessTokenUrl);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");

        BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        String inputLine;
        StringBuffer response2 = new StringBuffer();

        while ((inputLine = in.readLine()) != null) {
            response2.append(inputLine);
        }
        in.close();

        JSONObject accessTokenResponse = new JSONObject(response2.toString());
        String accessToken = accessTokenResponse.getString("access_token");

        // Retrieve user information using access token
        String userInfoUrl = "https://graph.facebook.com/me?fields=id,name,email,picture&access_token=" + accessToken;

        URL userInfoEndpoint = new URL(userInfoUrl);
        HttpURLConnection userInfoConnection = (HttpURLConnection) userInfoEndpoint.openConnection();
        userInfoConnection.setRequestMethod("GET");

        BufferedReader userInfoIn = new BufferedReader(new InputStreamReader(userInfoConnection.getInputStream()));
        StringBuffer userInfoResponse = new StringBuffer();
        String userInfoLine;
        while ((userInfoLine = userInfoIn.readLine()) != null) {
            userInfoResponse.append(userInfoLine);
        }
        userInfoIn.close();

        JSONObject userInfo = new JSONObject(userInfoResponse.toString());

        HttpSession session = request.getSession();
        session.setAttribute("userId", userInfo.getString("id"));
//        session.setAttribute("name", userInfo.getString("name"));
//        session.setAttribute("email", userInfo.getString("email"));
//        session.setAttribute("picture", userInfo.getJSONObject("picture").getJSONObject("data").getString("url"));
        UserDAO dao = new UserDAO();
        User currentUser = dao.GetUserLoginFacebook(userInfo.getString("id"));
        if (currentUser != null) {
            session.setAttribute("currentUser", currentUser);
            response.sendRedirect("home");
        } else {
            response.sendRedirect("home?action=login&message=your account is not asociate with facebook");
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            processRequest(request, response);
        } catch (JSONException ex) {
            response.sendRedirect("home?action=login&message=Login facebook failed");
        } catch (SQLException ex) {
            response.sendRedirect("home?action=login&message=Login facebook failed");
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            processRequest(request, response);
        } catch (JSONException ex) {
            response.sendRedirect("home?action=login&message=Login facebook failed");
        } catch (SQLException ex) {
            response.sendRedirect("home?action=login&message=Login facebook failed");
        }
    }

}
