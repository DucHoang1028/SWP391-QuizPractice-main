package DAOs;

import Contexts.DBContext;
import Models.PostCategory;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.sql.SQLException;

public class PostCategoryDAO extends DBContext {

    // function to get all categories from database
    public List<PostCategory> GetAllCategories() {

        List<PostCategory> categories = new ArrayList<>();
        String query = "SELECT * FROM PostCategory ";
        try (Connection conn = getConnection(); PreparedStatement stmt = conn.prepareStatement(query); ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                PostCategory cat = new PostCategory(rs.getInt("category_id"), rs.getString("category_name"));
                categories.add(cat);
            }
        } catch (Exception ex) {
            Logger.getLogger(PostCategoryDAO.class.getName()).log(Level.SEVERE, null, ex);
        }

        return categories;
    }

    public PostCategory GetCategoryById(int category_id) {
        PostCategory category = new PostCategory();

        String query = "SELECT * FROM PostCategory WHERE category_id = ? ";
        try (Connection conn = getConnection(); PreparedStatement stmt = conn.prepareStatement(query);) {

            stmt.setInt(1, category_id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                category = new PostCategory(rs.getInt("category_id"), rs.getString("category_name"));
            }
        } catch (Exception ex) {
            Logger.getLogger(PostCategoryDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return category;
    }

    public List<PostCategory> GetCategories() {
        List<PostCategory> categories = new ArrayList<>();

        // SQL query to select category details along with the count of posts in each category
        String query = "SELECT c.category_id, c.category_name, COUNT(p.post_id) as post_count "
                + "FROM PostCategory c "
                + "JOIN Post p ON c.category_id = p.category_id "
                + "WHERE p.status = 1 "
                + "GROUP BY c.category_id, c.category_name";

        try (Connection conn = getConnection(); PreparedStatement stmt = conn.prepareStatement(query); ResultSet rs = stmt.executeQuery()) {

            // Process the result set
            while (rs.next()) {
                // Create a new Category object with the retrieved data
                PostCategory category = new PostCategory(rs.getInt("category_id"), rs.getString("category_name"));
                // Add the category to the list
                categories.add(category);
            }
        } catch (SQLException ex) {
            Logger.getLogger(PostCategoryDAO.class.getName()).log(Level.SEVERE, null, ex);
        }

        return categories;
    }

    public List<PostCategory> getPostCategories(int postId) {
        List<PostCategory> categories = new ArrayList<>();
        String sql = "SELECT c.category_id, c.category_name FROM PostCategory c JOIN Post pc ON c.category_id = pc.category_id WHERE pc.post_id = ?";
        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setInt(1, postId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                PostCategory category = new PostCategory();
                category.setCategory_id(rs.getInt("category_id"));
                category.setCategory_name(rs.getString("category_name"));
                categories.add(category);
            }
        } catch (SQLException ex) {
            Logger.getLogger(PostCategoryDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return categories;
    }

    public List<PostCategory> GetCategoriesByIds(int[] categoryIds) {
        // use GetCategoryById to get category by id
        try {
            List<PostCategory> categories = new ArrayList<>();
            for (int categoryId : categoryIds) {
                PostCategory category = GetCategoryById(categoryId);
                categories.add(category);
            }
            return categories;
        } catch (Exception e) {
            return null;
        }

    }
     // Method to get the category name based on its ID
    public String getCategoryName(int categoryId) {
        String query = "SELECT category_name FROM PostCategory WHERE category_id = ?";
        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setInt(1, categoryId);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getString("category_name");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

}
