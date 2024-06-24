/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package Controllers.Account;

import DAOs.UserDAO;
import Models.User;
import Utils.EncryptionUtils;
import Utils.ValidationUtils;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author uchih
 */
public class ChangePasswordController extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        //use try catch sql exception
        try {
            String currentPassword = request.getParameter("currentPassword");
            String newPassword = request.getParameter("newPassword");
            String confirmPassword = request.getParameter("confirmPassword");
            String message;
            //get user's email from session
            HttpSession session = request.getSession();
            User user = (User) session.getAttribute("currentUser");
            String email = user.getEmail();
            String user_password_hash = GetUserPasswordByEmail(email);
            //check if user input right current password
            if (VerifyPassword(user_password_hash, currentPassword)) {
                String validateErrorChange = ValidateChangePassword(newPassword);
                //check validation for new password inputted by user
                if (validateErrorChange == null) {
                    //check if user input new password same password as current password
                    if (!currentPassword.equals(newPassword)) {
                        //check if user the new password and confirm password is the same
                        if (newPassword.equals(confirmPassword)) {
                            changePassword(newPassword, user.getEmail());
                            message = "Password changed successfully.";
                        } else {
                            message = "New password and confirm password are not equal!";
                        }
                    } else {
                        message = "You can not input old password!";
                    }
                } else {
                    message = validateErrorChange;
                }
            } else {
                message = "Current password is invalid, please enter again!";
            }
            //Send response back to client
            response.setContentType("text/plain");
            response.setCharacterEncoding("UTF-8");
            response.getWriter().write(message);
        } catch (SQLException ex) {
            Logger.getLogger(ChangePasswordController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    //check password inputted by user and password in database
    public boolean VerifyPassword(String password_hash, String currentPassword) {
        /// Encrypt md5 for comparing to password in database
        EncryptionUtils encrypt = new EncryptionUtils();
        String currentPassword_hash = encrypt.EncodeMd5(currentPassword);
        //compare two password
        if (currentPassword_hash.equals(password_hash)) {
            return true;
        }
        return false;
    }

    //update change password for user
    public void changePassword(String newPassword, String email) {
        /// Encrypt md5 for comparing to write in db
        EncryptionUtils encrypt = new EncryptionUtils();
        String password_hash = encrypt.EncodeMd5(newPassword);
        UserDAO dao = new UserDAO();
        dao.UpdatePassword(email, password_hash);
    }

    //get user's password in database by email
    public String GetUserPasswordByEmail(String email) throws SQLException {
        UserDAO dao = new UserDAO();
        User user = dao.GetUserByEmail(email);
        return user.getPassword_hash();
    }

    //validate input password
    public String ValidateChangePassword(String newPassword) {
        ValidationUtils validation = new ValidationUtils();
        //check validation for new password
        if (!validation.isValidPassword(newPassword)) {
            return "Password has at lease 8 characters, 1 uppercase, 1 lowercase, 1 digit";
        }
        return null;
    }
}
