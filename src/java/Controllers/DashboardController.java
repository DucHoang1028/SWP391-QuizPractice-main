package Controllers;

import DAOs.RegistrationDAO;
import DAOs.SubjectCategoryDAO;
import DAOs.SubjectDAO;
import DAOs.UserDAO;
import Models.Subject;
import Models.SubjectCategory;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class DashboardController extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            int totalSubjectsCount = GetTotalSubjectsCount();
            int todayNewSubCount = GetNewSubjectsCount();
            int newlyRegisteredCustomer = GetNewlyRegisteredCustomer();
            int newlyBoughtCustomer = GetNewlyBoughtCustomer();
            BigDecimal totalRevenue = GetTotalRevenue();
            String totalRevenueString = FormatBigDecimal(totalRevenue);
            HashMap<String, Integer> subjectsCountByCategories = GetSubjectsByCategoriesCount();
            int successfullyRegistration = GetRegistrationCountByStatus(1);
            int cancelledRegistration = GetRegistrationCountByStatus(3);
            int submittedRegistration = GetRegistrationCountByStatus(0);
            HashMap<String, BigDecimal> revenuesByCategories = GetRevenuesByCategories();
            HashMap<String, String> formattedRevenuesByCategories = FormatRevenue(revenuesByCategories);
            HashMap<LocalDate, Integer> successOrderCount = GetSuccessOrdersTrendByDateRange(LocalDate.now().minusDays(5), LocalDate.now());
            HashMap<LocalDate, Integer> allOrderCount = GetAllOrdersTrendByDateRange(LocalDate.now().minusDays(5), LocalDate.now());
            List<SubjectCategory> categoryList = GetAllCategories();
            HashMap<String, BigDecimal> revenueBySubject = GetRevenuesBySubjects(1);
            HashMap<String, String> formattedRevenuesBySubjects = FormatRevenue(revenueBySubject);
            List<Subject> subjectList = GetSubjectsByCategoryId(1);
            
            request.setAttribute("totalSubjectsCount", totalSubjectsCount);
            request.setAttribute("todayNewSubCount", todayNewSubCount);
            request.setAttribute("newlyRegisteredCustomer", newlyRegisteredCustomer);
            request.setAttribute("newlyBoughtCustomer", newlyBoughtCustomer);
            request.setAttribute("totalRevenue", totalRevenueString);
            request.setAttribute("subjectsCountByCategories", subjectsCountByCategories);
            request.setAttribute("successfullyRegistration", successfullyRegistration);
            request.setAttribute("cancelledRegistration", cancelledRegistration);
            request.setAttribute("submittedRegistration", submittedRegistration);
            request.setAttribute("formattedRevenuesByCategories", formattedRevenuesByCategories);
            request.setAttribute("successOrderCount", successOrderCount);
            request.setAttribute("allOrderCount", allOrderCount);
            request.setAttribute("categoryList", categoryList);
            request.setAttribute("revenueBySubject", formattedRevenuesBySubjects);
            request.setAttribute("subjectList", subjectList);

            request.getRequestDispatcher("Views/Marketing/DashBoardView.jsp").forward(request, response);
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getParameter("action");
        System.out.println(action);
        if (action == null) {
            processRequest(request, response);
        }
        if ("view".equals(action)) {
            try {
                int category_id = Integer.parseInt(request.getParameter("category"));
                HashMap<String, BigDecimal> revenueBySubject = GetRevenuesBySubjects(category_id);
                JSONObject jsonResponse = new JSONObject();
                jsonResponse.put("revenueCount", revenueBySubject);

                response.setContentType("application/json");
                PrintWriter out = response.getWriter();
                out.println(jsonResponse.toString());
            } catch (JSONException ex) {
                Logger.getLogger(DashboardController.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else if ("viewSubject".equals(action)) {
            try {
                int category_id = Integer.parseInt(request.getParameter("category"));
                System.out.println("id : " + category_id);
                List<Subject> subjectByCategory = GetSubjectsByCategoryId(category_id);
                JSONArray subjectArray = new JSONArray();
                for (Subject subject : subjectByCategory) {
                    JSONObject subjectJson = new JSONObject();
                    subjectJson.put("title", subject.getTitle());
                    subjectJson.put("status", subject.getSubjectStatus(subject.getStatus()));
                    subjectJson.put("createdAt", subject.getCreated_at().toString());
                    subjectArray.put(subjectJson);
                }

                JSONObject jsonResponse = new JSONObject();
                jsonResponse.put("subjects", subjectArray);

                response.setContentType("application/json");
                PrintWriter out = response.getWriter();
                out.println(jsonResponse.toString());
            } catch (JSONException ex) {
                Logger.getLogger(DashboardController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(request.getInputStream()));
            String json = "";
            if (br != null) {
                json = br.readLine();
            }
            JSONObject jsonObject = new JSONObject(json);
            String start = jsonObject.getString("start");
            String end = jsonObject.getString("end");

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

            LocalDate startDate = LocalDate.parse(start, formatter);
            LocalDate endDate = LocalDate.parse(end, formatter);

            HashMap<LocalDate, Integer> successOrderCount = GetSuccessOrdersTrendByDateRange(startDate, endDate);
            HashMap<LocalDate, Integer> allOrderCount = GetAllOrdersTrendByDateRange(startDate, endDate);

            JSONObject jsonResponse = new JSONObject();
            jsonResponse.put("successOrderCount", successOrderCount);
            jsonResponse.put("allOrderCount", allOrderCount);

            PrintWriter out = response.getWriter();
            out.println(jsonResponse.toString());

        } catch (JSONException ex) {
            Logger.getLogger(DashboardController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public int GetTotalSubjectsCount() {
        SubjectDAO subjectDAO = new SubjectDAO();
        return subjectDAO.GetTotalSubjectsCount();
    }

    public int GetNewSubjectsCount() {
        SubjectDAO subjectDAO = new SubjectDAO();
        return subjectDAO.GetTodayNewSubjectsCount();
    }

    public int GetNewlyRegisteredCustomer() {
        UserDAO dao = new UserDAO();
        return dao.GetNewlyRegisteredCustomerCount();
    }

    public int GetNewlyBoughtCustomer() {
        RegistrationDAO registrationDAO = new RegistrationDAO();
        return registrationDAO.GetNewlyBoughtCustomerCount();
    }

    public BigDecimal GetTotalRevenue() {
        RegistrationDAO registrationDAO = new RegistrationDAO();
        return registrationDAO.GetTotalRevenue();
    }

    public String FormatBigDecimal(BigDecimal totalRevenue) {
        BigDecimal thousand = new BigDecimal(10000);
        BigDecimal million = new BigDecimal(1000000);
        BigDecimal billion = new BigDecimal(1000000000);

        if (totalRevenue.compareTo(thousand) >= 0 && totalRevenue.compareTo(million) < 0) {
            return totalRevenue.divide(thousand, 1, BigDecimal.ROUND_DOWN).stripTrailingZeros().toPlainString() + "K";
        } else if (totalRevenue.compareTo(million) >= 0 && totalRevenue.compareTo(billion) < 0) {
            return totalRevenue.divide(million, 1, BigDecimal.ROUND_DOWN).stripTrailingZeros().toPlainString() + "M";
        } else if (totalRevenue.compareTo(billion) >= 0) {
            return totalRevenue.divide(billion, 1, BigDecimal.ROUND_DOWN).stripTrailingZeros().toPlainString() + "B";
        } else {
            return totalRevenue.stripTrailingZeros().toPlainString();
        }
    }

    public int GetRegistrationCountByStatus(int status) {
        RegistrationDAO dao = new RegistrationDAO();
        return dao.GetRegistrationCountByStatus(status);
    }

    public HashMap<String, Integer> GetSubjectsByCategoriesCount() {
        SubjectCategoryDAO dao = new SubjectCategoryDAO();
        return dao.GetSubjectsByCategoriesCount();
    }

    public HashMap<String, BigDecimal> GetRevenuesByCategories() {
        SubjectCategoryDAO dao = new SubjectCategoryDAO();
        return dao.GetRevenueByCategories();
    }

    public HashMap<String, String> FormatRevenue(HashMap<String, BigDecimal> revenuesList) {
        HashMap<String, String> formattedRevenues = new HashMap<>();
        for (Map.Entry<String, BigDecimal> entry : revenuesList.entrySet()) {
            formattedRevenues.put(entry.getKey(), FormatBigDecimal(entry.getValue()));
        }
        return formattedRevenues;
    }

    public HashMap<LocalDate, Integer> GetSuccessOrdersTrendByDateRange(LocalDate startDate, LocalDate endDate) {
        RegistrationDAO dao = new RegistrationDAO();
        return dao.GetSuccessOrdersTrendByDateRange(startDate, endDate);
    }

    public HashMap<LocalDate, Integer> GetAllOrdersTrendByDateRange(LocalDate startDate, LocalDate endDate) {
        RegistrationDAO dao = new RegistrationDAO();
        return dao.GetAllOrdersTrendByDateRange(startDate, endDate);
    }

    public List<SubjectCategory> GetAllCategories() {
        SubjectCategoryDAO dao = new SubjectCategoryDAO();
        return dao.GetAllCategories();
    }

    public HashMap<String, BigDecimal> GetRevenuesBySubjects(int category_id) {
        RegistrationDAO dao = new RegistrationDAO();
        return dao.GetRevenuesBySubjects(category_id);
    }

    public List<Subject> GetSubjectsByCategoryId(int category_id) {
        SubjectCategoryDAO dao = new SubjectCategoryDAO();
        List<Subject> subjectList = dao.GetSubjectListByCategoryId(category_id);
        return subjectList;
    }
}
