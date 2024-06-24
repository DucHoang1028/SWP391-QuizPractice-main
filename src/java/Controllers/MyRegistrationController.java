package Controllers;


import DAOs.RegistrationsDAO;

import Models.Registrations;
import Models.User;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet(name = "MyRegistrationController", urlPatterns = {"/my-registration"})
public class MyRegistrationController extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        User currentUser = (User) request.getSession().getAttribute("currentUser");
        if (currentUser == null) {
            response.sendRedirect("login.jsp");
            return;
        }
        
        RegistrationsDAO registrationsDAO = new RegistrationsDAO();
        List<Registrations> registrations = registrationsDAO.getUserRegistrations(currentUser.getUser_id());
        request.setAttribute("registrations", registrations);
        
        
        request.getRequestDispatcher("Views/Subject/MyRegistration.jsp").forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getParameter("action");
        if ("cancel".equals(action)) {
            int registrationId = Integer.parseInt(request.getParameter("registrationId"));
            RegistrationsDAO registrationsDAO = new RegistrationsDAO();
            registrationsDAO.cancelRegistration(registrationId);
        }
        
        // Redirect back to the registrations page
        response.sendRedirect("MyRegistrationController");
    }
}
