/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controllers;

import DAOs.SliderDAO;
import Models.Slider;
import Utils.ValidationUtils;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class SliderListController extends HttpServlet {

    public static final int PAGE_LENGTH = 6;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // default search, sort, page, search_value
        int search = 1;
        int status = -1; // default to show all statuses
        int page = 1;
        String searchValue = "";

        // get parameters
        String rawSearch = request.getParameter("search");
        String rawStatus = request.getParameter("status");
        String rawPage = request.getParameter("page");
        String rawSearchValue = request.getParameter("search_value");

        // if raw is not a negative number, parse the value
        if (ValidationUtils.isValidInteger(rawSearch, 0, Integer.MAX_VALUE)) {
            search = Integer.parseInt(rawSearch);
        }
        if (ValidationUtils.isValidInteger(rawStatus, -1, Integer.MAX_VALUE)) {
            status = Integer.parseInt(rawStatus);
        }
        if (ValidationUtils.isValidInteger(rawPage, 0, Integer.MAX_VALUE)) {
            page = Integer.parseInt(rawPage);
        }
        if (ValidationUtils.isNotNull(rawSearchValue)) {
            searchValue = rawSearchValue;
        }
        List<Slider> slider = GetActiveSliders();
        List<Slider> sliders = GetSliders(search, status, page, PAGE_LENGTH, searchValue);
        int resultLength = GetResultLength(search, status, searchValue);
        int pagesNumber = GetPagesNumber(resultLength, PAGE_LENGTH);

        // if 0 < pages_number < page, bad request
        if (pagesNumber > 0 && pagesNumber < page) {
            response.sendError(400, "page has to be smaller or equal pages number");
        } else {
            request.setAttribute("search", search);
            request.setAttribute("status", status);
            request.setAttribute("page", page);
            request.setAttribute("search_value", searchValue);
            request.setAttribute("pages_number", pagesNumber);
            request.setAttribute("result_length", resultLength);
            request.setAttribute("sliders", sliders);

            request.getRequestDispatcher("Views/Marketing/SliderList.jsp").forward(request, response);
        }
    }

    private List<Slider> GetSliders(int search, int status, int page, int pageLength, String search_value) {
        SliderDAO dao = new SliderDAO();

        return dao.GetSliders(search, status, page, pageLength, search_value);
    }

    private List<Slider> GetActiveSliders(){
        try {
            SliderDAO dao = new SliderDAO();
            return dao.GetActiveSliders();
        } catch (SQLException ex) {
            Logger.getLogger(SliderListController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
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

    private int GetResultLength(int search, int status, String searchValue) {
        SliderDAO dao = new SliderDAO();
        return dao.GetResultLength(search, status, searchValue);
    }
}



