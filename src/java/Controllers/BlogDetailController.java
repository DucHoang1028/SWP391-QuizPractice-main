package Controllers;

import DAOs.PostDAO;
import DAOs.PostCategoryDAO;
import DAOs.UserDAO;
import Models.Post;
import Models.PostCategory;
import java.io.IOException;
import java.util.List;
import java.util.HashMap;
import java.util.Map;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class BlogDetailController extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String rawPostId = request.getParameter("blog_id");

        // Validate postId parameter
        if (rawPostId == null || rawPostId.isEmpty() || !rawPostId.matches("\\d+")) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid Post ID format");
            return;
        }

        int postId = Integer.parseInt(rawPostId);

        Post post = FetchBlogPostDetails(postId);
        if (post == null) {
            response.sendError(HttpServletResponse.SC_NOT_FOUND, "Post not found");
            return;
        }

        String categoryName = FetchCategoryName(post.getCategory_id());
        String authorName = FetchAuthorName(post.getAuthor_id());
        String authorAvatar = FetchAuthorAvatar(post.getAuthor_id());

        // Fetch all categories for the sidebar
        List<PostCategory> categories = FetchCategories();

        // Fetch latest posts
        List<Post> latestPosts = FetchLatestPosts();

        // Create a map of category IDs to category names
        Map<Integer, String> categoryMap = new HashMap<>();
        for (PostCategory category : categories) {
            categoryMap.put(category.getCategory_id(), category.getCategory_name());
        }

        // Set attributes for the request
        request.setAttribute("post", post);
        request.setAttribute("categoryName", categoryName);
        request.setAttribute("authorName", authorName);
        request.setAttribute("authorAvatar", authorAvatar);
        request.setAttribute("categories", categories);
        request.setAttribute("latest_posts", latestPosts);
        request.setAttribute("categoryMap", categoryMap); // Set the map as a request attribute

        // Forward the request to the view
        request.getRequestDispatcher("Views/Blog/BlogDetailView.jsp").forward(request, response);
    }

    // Fetch blog post details by post ID
    private Post FetchBlogPostDetails(int postId) {
        PostDAO dao = new PostDAO();
        return dao.getBlogPostDetails(postId);
    }

    // Fetch category name by category ID
    private String FetchCategoryName(int categoryId) {
        PostCategoryDAO dao = new PostCategoryDAO();
        return dao.getCategoryName(categoryId);
    }

    // Fetch author name by author ID
    private String FetchAuthorName(int authorId) {
        UserDAO dao = new UserDAO();
        return dao.getAuthorName(authorId);
    }

    // Fetch author avatar by author ID
    private String FetchAuthorAvatar(int authorId) {
        UserDAO dao = new UserDAO();
        return dao.getAuthorAvatar(authorId);
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

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response);
    }

    @Override
    public String getServletInfo() {
        return "Blog detail controller";
    }
}
