/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package Controllers;

import DAOs.SubjectCategoryDAO;
import DAOs.SubjectDAO;
import Models.SubjectCategory;
import Models.Subject;
import Utils.ValidationUtils;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class SubjectListController extends HttpServlet {

    public static final int PAGE_LENGTH = 6;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // default search, sort, page, search_value
        int search = 1;
        int sort = 1;
        int page = 1;
        String filteredCategories = "";
        String searchValue = "1";

        // get parameters
        String rawSearch = request.getParameter("search");
        String rawSort = request.getParameter("sort");
        String rawPage = request.getParameter("page");
        String rawSearchValue = request.getParameter("search_value");
        String rawCategories = request.getParameter("categories");

        // if raw is not a negative number, parse the value
        if (ValidationUtils.isValidInteger(rawSearch, 0, Integer.MAX_VALUE)) {
            search = Integer.parseInt(rawSearch);
        }
        if (ValidationUtils.isValidInteger(rawSort, 0, Integer.MAX_VALUE)) {
            sort = Integer.parseInt(rawSort);
        }
        if (ValidationUtils.isValidInteger(rawPage, 0, Integer.MAX_VALUE)) {
            page = Integer.parseInt(rawPage);
        }

        if (ValidationUtils.isNotNull(rawSearchValue)) {
            searchValue = rawSearchValue;
        }
        if (ValidationUtils.isNotNull(rawCategories)) {
            filteredCategories = rawCategories;
        }
        List<Subject> subjects = GetSubjectsList(search, sort, page, PAGE_LENGTH, searchValue, filteredCategories);
        List<Subject> featuredSubjects = GetFeaturedSubjectsList();
        List<SubjectCategory> categories = GetAllCategories();
        int[] filteredCategoriesList = GetFilteredCategories(filteredCategories);

        int resultLength = GetResultLength(search, sort, searchValue, filteredCategories);
        int pagesNumber = GetPagesNumber(resultLength, PAGE_LENGTH);

        String message = GetMessage(search, searchValue, filteredCategoriesList);

        // if 0 < pages_number < page, bad request
        if (pagesNumber > 0 && pagesNumber < page) {
            response.sendError(400, "page has to be smaller or equal pages number");
        } else {
            request.setAttribute("search", search);
            request.setAttribute("sort", sort);
            request.setAttribute("page", page);
            request.setAttribute("search_value", searchValue);
            request.setAttribute("pages_number", pagesNumber);
            request.setAttribute("result_length", resultLength);
            request.setAttribute("categories", categories);
            request.setAttribute("filtered_categories", filteredCategories);
            request.setAttribute("filtered_categories_list", new SubjectCategoryDAO().GetCategoriesByIds(filteredCategoriesList));
            request.setAttribute("subjects", subjects);
            request.setAttribute("featured_subjects", featuredSubjects);
            request.setAttribute("message", message);

            request.getRequestDispatcher("Views/Subject/SubjectListView.jsp").forward(request, response);
        }

    }

    private List<Subject> GetSubjectsList(int search, int sort, int page, int pageLength, String search_value, String filtered_categories) {
        SubjectDAO dao = new SubjectDAO();

        return dao.GetPublishedSubjects(search, sort, page, pageLength, search_value, filtered_categories);
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

    private int GetPagesNumber(int resultLength, int pageLength) {

        if (pageLength <= 0) {
            pageLength = PAGE_LENGTH;
        }
        int result = resultLength / pageLength;

        if (resultLength % pageLength > 0) {
            result++;
        }
        return result;
    }

    private String GetMessage(int search, String searchValue, int[] categories) {

        String message = "Showing results of ";

        SubjectCategoryDAO dao = new SubjectCategoryDAO();
        List<SubjectCategory> categoryList = dao.GetCategoriesByIds(categories);

        switch (search) {
            case SubjectDAO.SEARCH_ALL -> {
                message += "all published subjects";
            }
            case SubjectDAO.SEARCH_BY_TITLE -> {
                if (searchValue.isEmpty()) {
                    message += "all published subjects";
                } else {
                    message += "subjects with title '" + searchValue + "'";
                }

            }
            case SubjectDAO.SEARCH_BY_FEATURED -> {
                message += "all featured subjects";
            }
            default ->
                throw new AssertionError();
        }

        if (categoryList != null && !categoryList.isEmpty()) {
            message += " in categories: ";
            for (SubjectCategory category : categoryList) {
                message += category.getCategory_name() + ", ";
            }
            message = message.substring(0, message.length() - 2);
        }

        return message;

    }

    private int GetResultLength(int search, int sort, String searchValue, String filtered_categories) {
        SubjectDAO dao = new SubjectDAO();
        return dao.GetResultLength(search, sort, searchValue, filtered_categories);
    }

    private int[] GetFilteredCategories(String filtered_categories) {
        try {
            String[] rawCategoryIds = filtered_categories.split(" ");
            int[] categoryIds = new int[rawCategoryIds.length];
            for (int i = 0; i < categoryIds.length; i++) {
                categoryIds[i] = Integer.parseInt(rawCategoryIds[i]);
            }
            return categoryIds;
        } catch (NumberFormatException e) {
            return null;
        }

    }

}
