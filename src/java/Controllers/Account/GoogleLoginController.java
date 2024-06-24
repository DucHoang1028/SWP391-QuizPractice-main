package Controllers.Account;

import DAOs.UserDAO;
import Models.User;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeTokenRequest;
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
import com.google.api.client.googleapis.auth.oauth2.GoogleTokenResponse;
import com.google.api.client.http.javanet.NetHttpTransport;
import java.io.IOException;
import java.io.InputStreamReader;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.gson.GsonFactory;
import jakarta.servlet.ServletContext;
import jakarta.servlet.http.HttpSession;
import java.sql.SQLException;
import java.util.Base64;
import org.json.JSONException;
import org.json.JSONObject;

public class GoogleLoginController extends HttpServlet {

    private static final String CLIENT_SECRET_FILE = "/WEB-INF/client_secret.json";
    private static final NetHttpTransport HTTP_TRANSPORT = new NetHttpTransport();
    private static final String REDIRECT_URI = "http://localhost:8080/app/login-google";
    private static final JsonFactory JSON_FACTORY = GsonFactory.getDefaultInstance();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String code = request.getParameter("code");

        if (code != null) {
            ServletContext context = getServletContext();

            InputStreamReader inputStream = new InputStreamReader(context.getResourceAsStream(CLIENT_SECRET_FILE));

            GoogleClientSecrets clientSecrets = GoogleClientSecrets
                    .load(JSON_FACTORY,
                            inputStream);

            GoogleTokenResponse tokenResponse = new GoogleAuthorizationCodeTokenRequest(
                    HTTP_TRANSPORT,
                    JSON_FACTORY,
                    "https://oauth2.googleapis.com/token",
                    clientSecrets.getDetails().getClientId(),
                    clientSecrets.getDetails().getClientSecret(),
                    code,
                    REDIRECT_URI
            ).execute();
            String idTokenString = tokenResponse.getIdToken();
            String[] parts = idTokenString.split("\\.");
            String payload = new String(Base64.getUrlDecoder().decode(parts[1]));
            JSONObject idToken = null;
            try {
                idToken = new JSONObject(payload);

                String userId = idToken.getString("sub");
                String email = idToken.getString("email");
                boolean emailVerified = idToken.getBoolean("email_verified");
                String name = idToken.getString("name");
                String pictureUrl = idToken.getString("picture");

                UserDAO dao = new UserDAO();
                User currentUser = dao.GetUserLoginGoogle(userId);
                if (currentUser != null) {
                    HttpSession session = request.getSession();
                    session.setAttribute("currentUser", currentUser);
                    response.sendRedirect("home");
                } else
                {
                    ///If login success but not in system
                     response.sendRedirect("home?action=login&message=Your account is not associated with google8");
                }

            } catch (JSONException ex) {
                response.sendRedirect("home?action=login&message=Login with google failed");
            } catch (SQLException ex) {
                response.sendRedirect("home?action=login&message=Login with google failed");
            }

        } else {
            response.sendRedirect("home?action=login&message=Login with google failed");
        }

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
    }

}
