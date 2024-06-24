package Controllers;

import DAOs.RegistrationDAO;
import DAOs.UserDAO;
import Models.RegistrationDetails;
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
import java.security.SecureRandom;
import java.sql.SQLException;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

public class RegistrationDetailsController extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, SQLException {
        String idRaw = request.getParameter("registration_id");
        String action = request.getParameter("action");

        if (idRaw == null || action == null) {
            response.sendRedirect("registration-list");
        } else {
            if (action.equals("edit")) {
                RegistrationDAO dao = new RegistrationDAO();
                int id = Integer.parseInt(idRaw);
                RegistrationDetails currentRegistration = dao.GetRegistrationSpecific(id);
                if (currentRegistration != null) {
                    UserDAO userDAO = new UserDAO();
                    User current = userDAO.GetUserByEmail(currentRegistration.getUserEmaill());
                    if (current == null) {
                        response.sendRedirect("registration-list");
                    } else {
                        request.setAttribute("registration", currentRegistration);
                        request.setAttribute("customer", current);
                        request.getRequestDispatcher("./Views/Sale/RegistrationDetailsView.jsp").forward(request, response);

                    }
                } else {
                    response.sendRedirect("registration-list");
                }
            }
        }
    }

    protected void processSubmit(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, SQLException {
        String action = request.getParameter("action");

        if (action == null) {
            response.sendRedirect("registration-list");
        } else {
            if (action.equals("edit")) {
                String status = request.getParameter("status");
                String notes = request.getParameter("notes");
                String idRaw = request.getParameter("id");
                RegistrationDAO dao = new RegistrationDAO();
                int id = Integer.parseInt(idRaw);

                RegistrationDetails currentRegistration = dao.GetRegistrationSpecific(id);
                if (currentRegistration != null) {
                    if ("0".equals(status)) {
                        if (currentRegistration.getStatus() == 0) {
                            dao.UpdateRegistrationSale(0, notes, id);
                             response.sendRedirect("registration-details?registration_id=" + id + "&action=edit&message=" + "Success");

                        } else {
                             response.sendRedirect("registration-details?registration_id=" + id + "&action=edit&message=" + "Cannot set status to pending!");

                        }
                    } else if ("1".equals(status)) {
                        if (currentRegistration.getStatus() == 1) {
                            dao.UpdateRegistrationSale(1, notes, id);
                            response.sendRedirect("registration-details?registration_id=" + id + "&action=edit&message=" + "Success");

                        } else if (currentRegistration.getStatus() == 0) {
                            UserDAO userDAO = new UserDAO();
                            User current = userDAO.GetUserByEmail(currentRegistration.getUserEmaill());
                            if (current.getStatus() == 2) {
                                EncryptionUtils utils = new EncryptionUtils();
                                String newPass = generateRandomPassword();
                                try {
                                    SendAccountEmail(current.getEmail(), current.getEmail(), newPass);
                                    userDAO.UpdatePassword(idRaw, utils.EncodeMd5(newPass));
                                    userDAO.UpdateStatusInactiveToActive(current.getEmail());
                                    dao.UpdateRegistrationSale(1, notes, id);
                                } catch (Exception ex) {
                                         response.sendRedirect("registration-details?registration_id=" + id + "&action=edit&message=" + "Cannot set to Paid!");
                                }

                            } else {
                                dao.UpdateRegistrationSale(1, notes, id);
                            }

                            response.sendRedirect("registration-details?registration_id=" + id + "&action=edit&message=" + "Success");
                        } else {
                            response.sendRedirect("registration-details?registration_id=" + id + "&action=edit&message=" + "Cannot set canceled to Paid!");

                        }
                    } else if ("2".equals(status)) {
                        if (currentRegistration.getStatus() == 2 || currentRegistration.getStatus() == 0) {
                            dao.UpdateRegistrationSale(2, notes, id);
                            response.sendRedirect("registration-details?registration_id=" + id + "&action=edit&message=" + "Success");

                        } else {
                                 response.sendRedirect("registration-details?registration_id=" + id + "&action=edit&message=" + "Cannot set paid to canceled!");
                      
                        }
                    }
                } else {
                    response.sendRedirect("registration-list");
                }
             
            }
        }
    }
    private static final String CHARACTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789!@#$%^&*()-_=+";
    private static final int PASSWORD_LENGTH = 10;

    private String generateRandomPassword() {
        SecureRandom random = new SecureRandom();
        StringBuilder password = new StringBuilder(10);

        for (int i = 0; i < 10; i++) {
            int index = random.nextInt(CHARACTERS.length());
            password.append(CHARACTERS.charAt(index));
        }

        return password.toString();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            processRequest(request, response);
        } catch (SQLException ex) {
            Logger.getLogger(RegistrationDetailsController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            processSubmit(request, response);
        } catch (SQLException ex) {
            Logger.getLogger(RegistrationDetailsController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void SendAccountEmail(String email, String userName, String passWord) throws Exception {
        //Get email function send through tls 
        EmailUtils emailUtils = new EmailUtils();
        ServletContext context = getServletContext();

        ////Get from properties system file
        String subject = "[QPS Registration Notification] Your registration and your accound is ready!";
        //set link with 2 param : action and token 
        String content = "Dear client,\n Your registration is paid and accepted by sale. So now you can access to system through this account \n"
                + "Email:" + userName + "\n"
                + "Password: " + passWord
                + "\n Group 4";
        emailUtils.SendResetEmail(email, subject, content);
    }
}
