package Controllers.Account;

import DAOs.UserDAO;
import Models.User;
import Utils.ValidationUtils;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.servlet.http.Part;
import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;

@MultipartConfig
public class UserProfileController extends HttpServlet {

    private static final String UPLOAD_DIR = "Assets/Images/User/";

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("application/json");
        String mes;
        HttpSession session = request.getSession();
        User currentUser = (User) session.getAttribute("currentUser");
        String avatar = currentUser.getAvatar();
        String applicationPath = request.getServletContext().getRealPath("");
        String uploadFilePath = Paths.get(applicationPath, UPLOAD_DIR).toString();
        File uploadDirectory = new File(uploadFilePath);
        //check if the directory is exist
        if (!uploadDirectory.exists()) {
            uploadDirectory.mkdirs();
        }
        Part filePart = request.getPart("file");
        String avatarPath;
        //check if the file has been uploaded and is not empty
        if (filePart != null && filePart.getSize() > 0) {
            String fileName = "user" + currentUser.getUser_id() + ".jpg";
            File oldFile = new File(Paths.get(uploadFilePath, fileName).toString());
            //check if the old file exists
            if (oldFile.exists()) {
                //if old file exist, delete the old file
                oldFile.delete();
            }
            filePart.write(Paths.get(uploadFilePath, fileName).toString());
            avatarPath = Paths.get(UPLOAD_DIR, fileName).toString();
        } else {
            avatarPath = avatar;
        }
        String fullName = request.getParameter("fullname");
        int gender = Integer.parseInt(request.getParameter("gender"));
        String email = request.getParameter("email");
        String mobile = request.getParameter("mobile");
        String validateErrorMessage = validateUpdateInformation(fullName, gender, mobile);
        //check validation for update information
        if (validateErrorMessage != null) {
            mes = validateErrorMessage;
        } else {
            UpdateUserProfile(fullName, gender, email, mobile, avatarPath);
            // Update the session object with new user data
            mes = "Updated successfully.";
            currentUser.setFull_name(fullName);
            currentUser.setGender(gender);
            currentUser.setEmail(email);
            currentUser.setMobile(mobile);
            currentUser.setAvatar(avatarPath);
        }
        session.setAttribute("currentUser", currentUser);
        response.setContentType("text/plain");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write(mes);
    }

    //update user information in database
    public void UpdateUserProfile(String fullName, int gender, String email, String mobile, String avatarPath) {
        UserDAO dao = new UserDAO();
        dao.UpdateUserProfile(fullName, gender, email, mobile, avatarPath);
    }

    //check validation of user's information
    public String validateUpdateInformation(String fullname, int gender, String mobile) {
        ValidationUtils validation = new ValidationUtils();
        //check if full name is valid
        if (!validation.isValidFullName(fullname)) {
            return "Full name is only contains uppercase, lowercase (can add space)!";
        }
        //check if gender is valid
        if (!validation.isValidGender(gender)) {
            return "Please choose a gender!";
        }
        //check validate for mobile
        if (!validation.isValidMobile(mobile)) {
            return "Please input valid mobile phone!";
        }
        return null;
    }
}
