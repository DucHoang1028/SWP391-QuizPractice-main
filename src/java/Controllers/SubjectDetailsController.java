package Controllers;

import DAOs.SubjectDAO;
import DAOs.SubjectCategoryDAO;
import DAOs.SliderDAO;
import Models.SubjectCategory;
import Models.Subject;
import Models.Slider;
import Utils.ValidationUtils;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

@WebServlet(name = "SubjectDetailsController", urlPatterns = {"/subject-details"})
public class SubjectDetailsController extends HttpServlet {

    private final SubjectDAO subjectDAO = new SubjectDAO();
    private final SubjectCategoryDAO categoryDAO = new SubjectCategoryDAO();
    private final SliderDAO sliderDAO = new SliderDAO();
    

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
             String rawSubjectId = request.getParameter("id");
        if (!ValidationUtils.isValidInteger(rawSubjectId, 1, Integer.MAX_VALUE)) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid subject ID");
            return;
        }
        int subjectId = Integer.parseInt(rawSubjectId);
        try {
            // Fetch the subject details
            Subject subject = subjectDAO.getSubjectById(subjectId);
            if (subject == null) {
                response.sendError(HttpServletResponse.SC_NOT_FOUND, "Subject not found");
                return;
            }
             // Get categories and featured subjects for the sidebar
            List<SubjectCategory> categories = GetAllCategories();
            List<Subject> featuredSubjects = GetFeaturedSubjectsList();
            List<Slider> sliders = sliderDAO.GetActiveSliders();

            // Set attributes for the request
            request.setAttribute("subject", subject);
            request.setAttribute("categories", categories);
            request.setAttribute("featured_subjects", featuredSubjects);
            request.setAttribute("sliders", sliders);

            
            // Forward to the JSP page for rendering
            request.getRequestDispatcher("Views/Subject/SubjectDetails.jsp").forward(request, response);
         
        }catch (SQLException ex) {
            Logger.getLogger(SubjectDetailsController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
  
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response);
    }
    
    private List<Subject> GetFeaturedSubjectsList() {
        SubjectDAO dao = new SubjectDAO();
        List<Subject> result = new ArrayList<>();

        try {
            result = dao.GetRandomFeaturedSubjects(5);
        } catch (SQLException ex) {
            Logger.getLogger(SubjectListController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return result;
    }

    private List<SubjectCategory> GetAllCategories() {
        SubjectCategoryDAO dao = new SubjectCategoryDAO();
        return dao.GetAllCategories();
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }
}

