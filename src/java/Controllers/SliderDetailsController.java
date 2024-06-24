package Controllers;

import DAOs.SliderDAO;
import Models.Slider;
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
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

@MultipartConfig
public class SliderDetailsController extends HttpServlet {

    private static final String UPLOAD_DIR = "Assets/Images/Slider/";

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String rawSliderId = request.getParameter("slider_id");
        if (!ValidationUtils.isValidInteger(rawSliderId, 1, Integer.MAX_VALUE)) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid slider ID");
            return;
        }
        int sliderId = Integer.parseInt(rawSliderId);
        SliderDAO sliderDAO = new SliderDAO();
        Slider slider = null;
       
        try {
           slider = sliderDAO.getSliderById(sliderId);
            if (slider == null) {
                response.sendError(HttpServletResponse.SC_NOT_FOUND, "Slider not found");
                return;
            }
            // Set attributes for the request
            request.setAttribute("slider", slider);
            // Forward to the JSP page for rendering
            request.getRequestDispatcher("Views/Marketing/SliderDetails.jsp").forward(request, response);
        } catch (SQLException ex) {
            Logger.getLogger(SliderDetailsController.class.getName()).log(Level.SEVERE, null, ex);
        }
        }
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("application/json");
        String mes;
        HttpSession session = request.getSession();
        Slider currentSlider = (Slider) session.getAttribute("currentSlider");
        String currentImage = currentSlider.getImage();
        String applicationPath = request.getServletContext().getRealPath("");
        String uploadFilePath = Paths.get(applicationPath, UPLOAD_DIR).toString();
        File uploadDirectory = new File(uploadFilePath);
        if (!uploadDirectory.exists()) {
            uploadDirectory.mkdirs();
        }
        Part filePart = request.getPart("file");
        String imagePath;
        if (filePart != null && filePart.getSize() > 0) {
            String fileName = "slider" + currentSlider.getSlider_id() + ".jpg";
            File oldFile = new File(Paths.get(uploadFilePath, fileName).toString());
            if (oldFile.exists()) {
                oldFile.delete();
            }
            filePart.write(Paths.get(uploadFilePath, fileName).toString());
            imagePath = Paths.get(UPLOAD_DIR, fileName).toString();
        } else {
            imagePath = currentImage;
        }
        String title = request.getParameter("title");
        String backLink = request.getParameter("back_link");
        int status = Integer.parseInt(request.getParameter("status"));
        String note = request.getParameter("note");
        String validateErrorMessage = validateUpdateInformation(title, backLink, note);
        if (validateErrorMessage != null) {
            mes = validateErrorMessage;
        } else {
            updateSliderDetails(currentSlider.getSlider_id(), title, imagePath, backLink, status, note);
            mes = "Updated successfully.";
            currentSlider.setTitle(title);
            currentSlider.setImage(imagePath);
            currentSlider.setBack_link(backLink);
            currentSlider.setStatus(status);
            currentSlider.setNote(note);
        }
        session.setAttribute("currentSlider", currentSlider);
        response.setContentType("text/plain");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write(mes);
    }

    public void updateSliderDetails(int sliderId, String title, String imagePath, String backLink, int status, String note) {
        SliderDAO dao = new SliderDAO();
        try {
            dao.updateSlider(new Slider(sliderId, title, imagePath, backLink, status, note));
        } catch (SQLException ex) {
            Logger.getLogger(SliderDetailsController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public String validateUpdateInformation(String title, String backLink, String note) {
        if (title == null || title.trim().isEmpty()) {
            return "Title is required.";
        }
        if (backLink == null || backLink.trim().isEmpty()) {
            return "Back link is required.";
        }
        if (note == null || note.trim().isEmpty()) {
            return "Note is required.";
        }
        return null;
    }
}
