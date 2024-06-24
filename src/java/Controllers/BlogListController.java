package Controllers;

import DAOs.PostDAO;
import DAOs.PostCategoryDAO;
import Models.Post;
import Models.PostCategory;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BlogListController extends HttpServlet {

    public static final int PAGE_LENGTH = 6; // Number of posts per page

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // Default values
        int page = 1;
        int sort = 1;
        List<Integer> categoryIds = new ArrayList<>();
        String searchValue = "";

        // Retrieve parameters from the request
        String rawPage = request.getParameter("page");
        String rawSort = request.getParameter("sort");
        String rawSearchValue = request.getParameter("search_value");
        String rawCategoryIds = request.getParameter("category_id");

        // Parse and set parameters if they exist
        if (rawPage != null && !rawPage.isEmpty()) {
            page = Integer.parseInt(rawPage);
        }
        if (rawSort != null && !rawSort.isEmpty()) {
            sort = Integer.parseInt(rawSort);
        }
        if (rawSearchValue != null && !rawSearchValue.isEmpty()) {
            searchValue = rawSearchValue;
        }
        if (rawCategoryIds != null && !rawCategoryIds.isEmpty()) {
            String[] categoryIdArray = rawCategoryIds.split(" ");
            for (String categoryId : categoryIdArray) {
                try {
                    categoryIds.add(Integer.parseInt(categoryId));
                } catch (NumberFormatException e) {
                    e.printStackTrace();
                }
            }
        }

        // Fetch data
        List<Post> posts = FetchBlogPosts(sort, page, PAGE_LENGTH, searchValue, categoryIds);
        List<PostCategory> categories = FetchCategories();
        List<Post> latestPosts = FetchLatestPosts();
        int resultLength = GetResultLength(searchValue, categoryIds);
        int pagesNumber = GetPagesNumber(resultLength, PAGE_LENGTH);

        // Create a map of category IDs to category names
        Map<Integer, String> categoryMap = new HashMap<>();
        for (PostCategory category : categories) {
            categoryMap.put(category.getCategory_id(), category.getCategory_name());
        }

        // Validate page number
        if (pagesNumber > 0 && pagesNumber < page) {
            response.sendError(400, "Page number exceeds total pages");
        } else {
            // Set attributes for the request
            request.setAttribute("sort", sort);
            request.setAttribute("page", page);
            request.setAttribute("search_value", searchValue);
            request.setAttribute("category_id", rawCategoryIds);
            request.setAttribute("pages_number", pagesNumber);
            request.setAttribute("result_length", resultLength);
            request.setAttribute("categories", categories);
            request.setAttribute("blogs", posts);
            request.setAttribute("latest_posts", latestPosts);
            request.setAttribute("categoryMap", categoryMap); // Set the map as a request attribute

            // Forward the request to the view
            request.getRequestDispatcher("Views/Blog/BlogListView.jsp").forward(request, response);
        }
    }

    // Fetch blog posts based on sorting, pagination, search value, and category
    private List<Post> FetchBlogPosts(int sort, int page, int pageLength, String searchValue, List<Integer> categoryIds) {
        PostDAO dao = new PostDAO();
        int offset = (page - 1) * pageLength;
        return dao.GetBlogPosts(pageLength, offset, searchValue, categoryIds, sort);
    }

    // Fetch all categories
    private List<PostCategory> FetchCategories() {
        PostCategoryDAO dao = new PostCategoryDAO();
        return dao.GetCategories();
    }

    // Fetch latest posts
    private List<Post> FetchLatestPosts() {
        PostDAO dao = new PostDAO();
        return dao.GetLatestPosts();
    }

    // Calculate the number of pages needed
    private int GetPagesNumber(int resultLength, int pageLength) {
        int result = resultLength / pageLength;
        if (resultLength % pageLength > 0) {
            result++;
        }
        return result;
    }

    // Get the total number of results based on search value and category
    private int GetResultLength(String searchValue, List<Integer> categoryIds) {
        PostDAO dao = new PostDAO();
        return dao.GetResultLength(searchValue, categoryIds);
    }
}
