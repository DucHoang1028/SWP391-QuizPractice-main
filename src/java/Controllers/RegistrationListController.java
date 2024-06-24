package Controllers;

import DAOs.RegistrationDAO;
import Models.RegistrationDetails;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.List;

public class RegistrationListController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        /// Handle table size
        RegistrationDAO dao = new RegistrationDAO();
        String subjectName = request.getParameter("name");
        String email = request.getParameter("email");
        String valid_from = request.getParameter("valid_from");
        String valid_to = request.getParameter("valid_to");
        String status = request.getParameter("status");

        if (subjectName == null) {
            subjectName = "";
        }
        if (email == null) {
            email = "";
        }
        if (valid_from == null) {
            valid_from = "";
        }
        if (valid_to == null) {
            valid_to = "";
        }
        int status2 = 3;
        if (status == null) {
            status2 = 3;
        } else {
            status2 = Integer.parseInt(status);
        }
         
        int size = dao.GetRegistrationsSize(subjectName, email, valid_from, valid_to, status2);
        request.setAttribute("size", size);
        int pageSize = 10;
        int numberOfPage = Math.round((float) size / pageSize+0.5f);
        System.out.println((float) size / pageSize);
        request.setAttribute("numberOfPage", numberOfPage);
        ///Handle page index
        int page = 1;
        String pageRaw = request.getParameter("page");
        if (pageRaw != null) {
            int tempPage = Integer.parseInt(pageRaw);
            if (tempPage > 0 && tempPage <= numberOfPage) {
                page = tempPage;
            }
        }

        List<RegistrationDetails> registrations = dao.GetRegistrationPage(page, pageSize,
                subjectName, email, valid_from, valid_to, status2);
        request.setAttribute("page", page);
        request.setAttribute("registrationList", registrations);

        request.getRequestDispatcher("./Views/Sale/RegistrationListView.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

    }

}
