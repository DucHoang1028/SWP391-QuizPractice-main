package Controllers;

import DAOs.PracticeDAO;
import DAOs.SubjectDAO;
import Models.Practice;
import Models.Subject;
import Models.User;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

public class PracticeListController extends HttpServlet {

    public static final int PAGE_LENGTH = 5;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("currentUser");

        if (user == null) {
            response.sendError(403);
        } else {

            int page = 1;
            String filter = "all";
            String sort = "recent";
            String search = "";

            String rawPage = request.getParameter("page");
            String rawFilter = request.getParameter("filter");
            String rawSort = request.getParameter("sort");
            String rawSearch = request.getParameter("search");

            if (rawPage != null && !rawPage.isEmpty()) {
                page = Integer.parseInt(rawPage);
            }
            if (rawFilter != null && !rawFilter.isEmpty()) {
                filter = rawFilter;
            }
            if (rawSort != null && !rawSort.isEmpty()) {
                sort = rawSort;
            }
            if (rawSearch != null && !rawSearch.isEmpty()) {
                search = rawSearch;
            }

            // get list of subjects that the user registered
            List<Subject> subjects = new SubjectDAO().GetRegisteredSubjects(user.getUser_id());

            PracticeDAO practiceDAO = new PracticeDAO();
            int resultLength = practiceDAO.GetResultLength(filter, search, user.getUser_id());
            int pagesNumber = (int) Math.ceil((double) resultLength / PAGE_LENGTH);

            // Adjust the current page if it exceeds the total number of pages
            if (page > pagesNumber) {
                page = pagesNumber;
            }

            List<Practice> practices = practiceDAO.GetPractices(PAGE_LENGTH, (page - 1) * PAGE_LENGTH, filter, sort, search, user.getUser_id());

            if (practices.isEmpty() && page != 1) {
                page = 1;
                practices = practiceDAO.GetPractices(PAGE_LENGTH, (page - 1) * PAGE_LENGTH, filter, sort, search, user.getUser_id());
            }

            request.setAttribute("page", page);
            request.setAttribute("sort", sort);
            request.setAttribute("pages_number", pagesNumber);
            request.setAttribute("result_length", resultLength);
            request.setAttribute("practices", practices);
            request.setAttribute("filter", filter);
            request.setAttribute("search", search);
            request.setAttribute("subjects", subjects);
            request.getRequestDispatcher("Views/Practice/PracticeListView.jsp").forward(request, response);
        }
    }
}
