/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package Controllers.Account;

import DAOs.UserDAO;
import Models.User;
import Utils.EmailUtils;
import Utils.EncryptionUtils;
import Utils.ValidationUtils;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.json.JSONObject;
import org.json.JSONException;

/**
 *
 * @author uchih
 */
public class RegisterController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getParameter("action");
        //check if the verification link is clicked
        if (action.equals("verify")) {
            String token = request.getParameter("token");
            //use try catch to catch exception when verify token
            try {
                boolean verifyValidLink = VerifyToken(token);
                String email = GetEmailFromToken(token);
                //check if the link is valid
                if (verifyValidLink) {
                    //if the token is valid, update user status in system
                    UpdateStatusAfterVerified(email);
                    request.setAttribute("message", "You have successfully verified account.");
                    request.setAttribute("butt", "Go to HomePage");
                    request.getRequestDispatcher("Views/Register/SuccessMessage.jsp").forward(request, response);
                } else {
                    // if the token is invalid, send user to error message page
                    request.setAttribute("message", "The link is not valid anymore!");
                    request.getRequestDispatcher("Views/Register/FailMessage.jsp").forward(request, response);
                }
            } catch (Exception ex) {
                request.setAttribute("message", "Some errors occured.");
                request.getRequestDispatcher("Views/Register/FailMessage.jsp").forward(request, response);
            }
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String message;
        String email = request.getParameter("email");
        User checkUser;
        //use try catch to catch exception of sql
        try {
            checkUser = VerifyEmail(email);
            //check if the user with email is exists in system or not
            if (checkUser != null) {
                message = "This email is already exist!";
            } else {
                //if the email is the new one, user's information is temporarily stored in the database
                String fullname = request.getParameter("fullname");
                String genderStr = request.getParameter("gender");
                int gender = Integer.parseInt(genderStr);
                String mobile = request.getParameter("mobile");
                String password = request.getParameter("password");
                String confirmPassword = request.getParameter("confirmPassword");

                String validateMessage = validateInformation(email, fullname, gender, mobile, password);
                //validate information inputted by user
                if (validateMessage == null) {
                    //check if user type same password and its confirmation
                    if (!password.equals(confirmPassword)) {
                        message = "Password does not match confirm password!";
                    } else {
                        StoreUserRegisterInformation(fullname, gender, email, mobile, password);
                        SendVerificationEmail(email);
                        message = "A verification link has been sent to you!";
                    }
                } else {
                    message = validateMessage;
                }
            }
            response.setContentType("text/plain");
            response.setCharacterEncoding("UTF-8");
            response.getWriter().write(message);

        } catch (SQLException ex) {
            Logger.getLogger(RegisterController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            message = "An error occured..";
            response.setContentType("text/plain");
            response.setCharacterEncoding("UTF-8");
            response.getWriter().write(message);
        }
    }

    //check if the email is exists in database or not
    public User VerifyEmail(String email) throws SQLException {
        UserDAO dao = new UserDAO();
        return dao.GetUserByEmail(email);
    }

    //send mail to user's email using email utils
    private void SendVerificationEmail(String email) throws Exception {
        //Get email function send through tls 
        EmailUtils emailUtils = new EmailUtils();
        String token = GenerateVerifyToken(email, 30);
        String subject = "[QPS VERIFICATION] Your verification link in QPS is ready!";
        //set link with 2 param : action and token 
        String content = "Dear client,\n Your verification link is: http://localhost:8080/app/register?action=verify&token="
                + java.net.URLEncoder.encode(token, "UTF-8")
                + ". Your link will be available for next 30 minutes.\n Group 4";
        emailUtils.SendResetEmail(email, subject, content);
    }

    //generate verification token
    private String GenerateVerifyToken(String email, long duration) throws Exception {
        /// Token =   Email + expire time
        ///Note: Need fix to can be configed in system file later !!!!
        LocalDateTime timeNow = LocalDateTime.now();
        String expireDate = timeNow.plusMinutes(duration).toString();
        String tokenMessage = email + "&" + expireDate;
        EncryptionUtils encrypt = new EncryptionUtils();
        return encrypt.EncodeBase64(tokenMessage);
    }

    //verify if the token is valid or not
    private boolean VerifyToken(String token) throws Exception {
        EncryptionUtils encrypt = new EncryptionUtils();
        String verifyInformation = encrypt.DecodeBase64(token);
        String email = GetEmailFromToken(token);
        UserDAO dao = new UserDAO();
        User userCheck = dao.GetUserByEmail(email);
        //check if the user is exists in database or not
        if (userCheck == null) {
            return false;
        }
        //check if user need to verify or not
        if (userCheck.getStatus() != 0) {
            return false;
        }
        // check expire date with time now
        LocalDateTime expireDate = LocalDateTime.parse(verifyInformation.split("\\&")[1]);
        LocalDateTime now = LocalDateTime.now();
        if (!now.isBefore(expireDate)) {
            return false;
        }
        return true;
    }

    //return email from token
    private String GetEmailFromToken(String token) throws Exception {
        /// decrypt token and get email
        EncryptionUtils encrypt = new EncryptionUtils();
        String verifyInformation = encrypt.DecodeBase64(token);
        String email = verifyInformation.split("\\&")[0];
        return email;
    }

    //store temporarily user's information in database
    private void StoreUserRegisterInformation(String fullname, int gender, String email, String mobile, String password) {
        /// Encrypt md5 for comparing to write in db
        EncryptionUtils encrypt = new EncryptionUtils();
        String password_hash = encrypt.EncodeMd5(password);
        UserDAO dao = new UserDAO();
        dao.RegisterTempoUser(fullname, gender, email, mobile, password_hash);
    }

    //update active status for user to database
    private void UpdateStatusAfterVerified(String email) {
        UserDAO dao = new UserDAO();
        dao.UpdateStatusInactiveToActive(email);
    }

    private String validateInformation(String email, String fullname, int gender, String mobile, String password) {
        ValidationUtils validation = new ValidationUtils();
        //check validate for email
        if (!validation.isValidEmail(email)) {
            return "Please provide property formatted email address!";
        }
        //check validate for fullname
        if (!validation.isValidFullName(fullname)) {
            return "Full name is only contains uppercase, lowercase (can add space)!";
        }
        //check validate for gender
        if (!validation.isValidGender(gender)) {
            return "Please choose a gender!";
        }
        //check validate for mobile
        if (!validation.isValidMobile(mobile)) {
            return "Please input valid mobile phone!";
        }
        //check validate for password
        if (!validation.isValidPassword(password)) {
            return "Password has at lease 8 characters, 1 uppercase, 1 lowercase, 1 digit";
        }
        return null;
    }
}
