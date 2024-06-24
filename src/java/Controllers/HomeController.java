package Controllers;

import DAOs.PostDAO;
import DAOs.SliderDAO;
import DAOs.SubjectDAO;
import Models.Post;
import Models.Slider;
import Models.Subject;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 * HomeController handles requests to the home page.
 */
public class HomeController extends HttpServlet {

    private static final int HOT_POSTS_PER_PAGE = 5;
    private static final int FEATURED_SUBJECTS_PER_PAGE = 6;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            // Get page parameters
            int hotPostsPage = getPageNumber(request, "hotPostsPage");
            int featuredSubjectsPage = getPageNumber(request, "featuredSubjectsPage");

            // Fetch data for home page
            List<Slider> sliders = FetchSliders();
            List<Post> hotPosts = FetchHotPosts();
            List<Post> latestPosts = FetchLatestPosts();
            List<Subject> featuredSubjects = FetchFeaturedSubjects();

            // Paginate hot posts
            int hotPostsCount = hotPosts.size();
            int hotPostsTotalPages = (int) Math.ceil((double) hotPostsCount / HOT_POSTS_PER_PAGE);
            List<Post> paginatedHotPosts = paginateList(hotPosts, hotPostsPage, HOT_POSTS_PER_PAGE);

            // Paginate featured subjects
            int featuredSubjectsCount = featuredSubjects.size();
            int featuredSubjectsTotalPages = (int) Math.ceil((double) featuredSubjectsCount / FEATURED_SUBJECTS_PER_PAGE);
            List<Subject> paginatedFeaturedSubjects = paginateList(featuredSubjects, featuredSubjectsPage, FEATURED_SUBJECTS_PER_PAGE);

            // Set attributes for the view
            request.setAttribute("sliders", sliders);
            request.setAttribute("hotPosts", paginatedHotPosts);
            request.setAttribute("latestPosts", latestPosts);
            request.setAttribute("featuredSubjects", paginatedFeaturedSubjects);

            // Set pagination attributes
            request.setAttribute("hotPostsPage", hotPostsPage);
            request.setAttribute("hotPostsTotalPages", hotPostsTotalPages);
            request.setAttribute("featuredSubjectsPage", featuredSubjectsPage);
            request.setAttribute("featuredSubjectsTotalPages", featuredSubjectsTotalPages);

            // Forward request to the JSP page
            request.getRequestDispatcher("Views/Common/HomeView.jsp").forward(request, response);
        } catch (SQLException ex) {
            Logger.getLogger(HomeController.class.getName()).log(Level.SEVERE, "Error accessing the database", ex);
            throw new ServletException("Error accessing the database", ex);
        }
    }

    private int getPageNumber(HttpServletRequest request, String parameterName) {
        String pageParam = request.getParameter(parameterName);
        return (pageParam == null || pageParam.isEmpty()) ? 1 : Integer.parseInt(pageParam);
    }

    private <T> List<T> paginateList(List<T> list, int page, int pageSize) {
        int fromIndex = (page - 1) * pageSize;
        int toIndex = Math.min(fromIndex + pageSize, list.size());
        return list.subList(fromIndex, toIndex);
    }

    private List<Slider> FetchSliders() throws SQLException {
        SliderDAO sliderDAO = new SliderDAO();
        return sliderDAO.GetActiveSliders();
    }

    private List<Post> FetchHotPosts() throws SQLException {
        PostDAO postDAO = new PostDAO();
        return postDAO.GetHotPosts();
    }

    private List<Post> FetchLatestPosts() throws SQLException {
        PostDAO postDAO = new PostDAO();
        return postDAO.GetLatestPosts();
    }

    private List<Subject> FetchFeaturedSubjects() throws SQLException {
        SubjectDAO subjectDAO = new SubjectDAO();
        return subjectDAO.GetFeaturedSubjects();
    }
}
