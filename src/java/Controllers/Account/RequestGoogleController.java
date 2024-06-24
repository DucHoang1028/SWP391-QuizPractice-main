
package Controllers.Account;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import com.google.api.client.auth.oauth2.AuthorizationCodeFlow;
import com.google.api.client.auth.oauth2.AuthorizationCodeRequestUrl;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
import com.google.api.client.http.javanet.NetHttpTransport;
import java.io.IOException;
import java.io.InputStreamReader;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.gson.GsonFactory;
import jakarta.servlet.ServletContext;
import java.util.Arrays;


public class RequestGoogleController extends HttpServlet {

    private static final String CLIENT_SECRET_FILE = "/WEB-INF/client_secret.json";

    private static final NetHttpTransport HTTP_TRANSPORT = new NetHttpTransport();
    private static final String REDIRECT_URI = "http://localhost:8080/app/login-google";
    private static final JsonFactory JSON_FACTORY = GsonFactory.getDefaultInstance();


    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        ServletContext context = getServletContext();

        InputStreamReader inputStream = new InputStreamReader(context.getResourceAsStream(CLIENT_SECRET_FILE));

        GoogleClientSecrets clientSecrets = GoogleClientSecrets
                .load(JSON_FACTORY,
                        inputStream);
        AuthorizationCodeFlow flow = new GoogleAuthorizationCodeFlow.Builder(
                HTTP_TRANSPORT, JSON_FACTORY, clientSecrets,  Arrays.asList("profile", "email"))
                .setAccessType("offline")
                .build();
        AuthorizationCodeRequestUrl authorizationUrl = flow.newAuthorizationUrl();
        authorizationUrl.setRedirectUri(REDIRECT_URI);
        authorizationUrl.set("prompt", "select_account"); 
        response.sendRedirect(authorizationUrl.build());
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

   
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

}
