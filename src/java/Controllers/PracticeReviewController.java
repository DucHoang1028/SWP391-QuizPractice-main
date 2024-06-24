package Controllers;

import DAOs.PracticeDAO;
import Models.Practice;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class PracticeReviewController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String rawRecordId = request.getParameter("record_id");
        if (rawRecordId != null && !rawRecordId.isEmpty()) {
            int recordId = Integer.parseInt(rawRecordId);

            PracticeDAO dao = new PracticeDAO();
            Practice practice = dao.GetPracticeById(recordId);

            if (practice != null) {
                request.setAttribute("practice", practice);
                request.getRequestDispatcher("Views/Practice/ReviewPracticeView.jsp").forward(request, response);
            } else {
                response.sendError(HttpServletResponse.SC_NOT_FOUND, "Practice not found");
            }
        } else {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid record ID");
        }
    }
}
