package Controllers;

import DAOs.RegistrationsDAO;
import DAOs.SubjectDAO;
import DAOs.UserDAO;
import Models.PricePackage;
import Models.Registrations;
import Models.User;
import Utils.ValidationUtils;
import com.google.gson.Gson;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class SubjectRegisterController extends HttpServlet {

    private static final long serialVersionUID = 1L;

    private final SubjectDAO subjectDAO = new SubjectDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String rawSubjectId = request.getParameter("subject_id");
        if (!ValidationUtils.isValidInteger(rawSubjectId, 1, Integer.MAX_VALUE)) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid subject ID");
            return;
        }

        int subjectId = Integer.parseInt(rawSubjectId);
        try {
            List<PricePackage> pricePackages = subjectDAO.getPricePackagesBySubjectId(subjectId);
            String json = new Gson().toJson(pricePackages);

            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");

            PrintWriter out = response.getWriter();
            out.write(json);
            out.flush();
        } catch (SQLException ex) {
            
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "An error occurred while fetching price packages.");
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
         // Retrieve form data
        int subjectId = Integer.parseInt(request.getParameter("subjectId"));
        int packageId = Integer.parseInt(request.getParameter("packageId"));
        String fullName = request.getParameter("fullName");
        String email = request.getParameter("email");
        String mobile = request.getParameter("mobile");
        int gender = Integer.parseInt(request.getParameter("gender"));
        String action = request.getParameter("action");

        User currentUser = (User) request.getSession().getAttribute("currentUser");

        // Check if user is logged in or a guest
        if (currentUser == null) {
            // Guest user
            User guestUser = new User();
            guestUser.setFull_name(fullName);
            guestUser.setEmail(email);
            guestUser.setMobile(mobile);
            guestUser.setGender(gender);
            guestUser.setStatus(User.STATUS_ACTIVE);

            // Store guest user in database
            UserDAO userDAO = new UserDAO();          
            currentUser = guestUser;
        // Create a new registration
        Registrations registrations = new Registrations();
        registrations.setUser(currentUser);
        registrations.setRegistrationTime(LocalDateTime.now());
        registrations.setStatus(Registrations.STATUS_SUBMITTED);

        // Fetch the selected price package
        SubjectDAO subjectDAO = new SubjectDAO();
        try {
            List<PricePackage> pricePackages = subjectDAO.getPricePackagesBySubjectId(subjectId);
            for (PricePackage pricePackage : pricePackages) {
                if (pricePackage.getPackage_id() == packageId) {
                    registrations.setPricePackage(pricePackage);
                    registrations.setTotalCost(pricePackage.getSale_price());
                    break;
                }
            }
            registrations.setSubject(subjectDAO.getSubjectById(subjectId));
        } catch (SQLException e) {
            e.printStackTrace();
        }

        // Store registration 
        RegistrationsDAO registrationsDAO = new RegistrationsDAO();
            try {
                registrationsDAO.createRegistration(registrations);
            } catch (SQLException ex) {
                Logger.getLogger(SubjectRegisterController.class.getName()).log(Level.SEVERE, null, ex);
            }

        // Redirect to confirmation screen
        response.sendRedirect("Views/Subject/SuccessRegister.jsp");
    }
}
}

    

