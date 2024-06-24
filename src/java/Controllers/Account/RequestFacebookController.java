package Controllers.Account;

import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class RequestFacebookController extends HttpServlet {

    private static final String CLIENT_ID = "990104009492966";
    private static final String REDIRECT_URI = "http://localhost:8080/app/login-facebook";
    private static final String STATE = "sQPS-G4"; // Use a more secure method

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String authorizationUrl = "https://www.facebook.com/v20.0/dialog/oauth?" +
                                  "client_id=" + CLIENT_ID +
                                  "&redirect_uri=" + REDIRECT_URI +
                                  "&state=" + STATE +
                                  "&scope=email";

        response.sendRedirect(authorizationUrl);
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
