package DAOs;

import Contexts.DBContext;
import Models.Subject;
import Models.SubjectCategory;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.LinkedHashMap;

public class SubjectCategoryDAO extends DBContext {

    // function to get all categories from database
    public List<SubjectCategory> GetAllCategories() {

        List<SubjectCategory> categories = new ArrayList<>();
        String query = "SELECT * FROM SubjectCategory ";
        try (Connection conn = getConnection(); PreparedStatement stmt = conn.prepareStatement(query); ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                SubjectCategory cat = new SubjectCategory(rs.getInt("category_id"), rs.getString("category_name"));
                categories.add(cat);
            }
        } catch (Exception ex) {
            Logger.getLogger(SubjectCategoryDAO.class.getName()).log(Level.SEVERE, null, ex);
        }

        return categories;
    }

    public SubjectCategory GetCategoryById(int category_id) {
        SubjectCategory category = new SubjectCategory();

        String query = "SELECT * FROM SubjectCategory WHERE category_id = ? ";
        try (Connection conn = getConnection(); PreparedStatement stmt = conn.prepareStatement(query);) {

            stmt.setInt(1, category_id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                category = new SubjectCategory(rs.getInt("category_id"), rs.getString("category_name"));
            }
        } catch (Exception ex) {
            Logger.getLogger(SubjectCategoryDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return category;
    }

    public List<SubjectCategory> GetCategories() {
        List<SubjectCategory> categories = new ArrayList<>();

        // SQL query to select category details along with the count of posts in each category
        String query = "SELECT c.category_id, c.category_name, COUNT(p.post_id) as post_count "
                + "FROM SubjectCategory c "
                + "JOIN Post p ON c.category_id = p.category_id "
                + "WHERE p.status = 1 "
                + "GROUP BY c.category_id, c.category_name";

        try (Connection conn = getConnection(); PreparedStatement stmt = conn.prepareStatement(query); ResultSet rs = stmt.executeQuery()) {

            // Process the result set
            while (rs.next()) {
                // Create a new Category object with the retrieved data
                SubjectCategory category = new SubjectCategory(rs.getInt("category_id"), rs.getString("category_name"));
                // Add the category to the list
                categories.add(category);
            }
        } catch (SQLException ex) {
            Logger.getLogger(SubjectCategoryDAO.class.getName()).log(Level.SEVERE, null, ex);
        }

        return categories;
    }

    public List<SubjectCategory> getPostCategories(int postId) {
        List<SubjectCategory> categories = new ArrayList<>();
        String sql = "SELECT c.category_id, c.category_name FROM SubjectCategory c JOIN Post pc ON c.category_id = pc.category_id WHERE pc.post_id = ?";
        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setInt(1, postId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                SubjectCategory category = new SubjectCategory();
                category.setCategory_id(rs.getInt("category_id"));
                category.setCategory_name(rs.getString("category_name"));
                categories.add(category);
            }
        } catch (SQLException ex) {
            Logger.getLogger(SubjectCategoryDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return categories;
    }

    public List<SubjectCategory> GetCategoriesByIds(int[] categoryIds) {
        // use GetCategoryById to get category by id
        try {
            List<SubjectCategory> categories = new ArrayList<>();
            for (int categoryId : categoryIds) {
                SubjectCategory category = GetCategoryById(categoryId);
                categories.add(category);
            }
            return categories;
        } catch (Exception e) {
            return null;
        }
    }

    public HashMap<String, Integer> GetSubjectsByCategoriesCount() {
        PreparedStatement ps = null;
        ResultSet rs = null;
        Connection connection = null;

        HashMap<String, Integer> subjectsCount = new HashMap<>();
        String sqlCommand = "SELECT TOP(6) category_name, subject_count "
                + "FROM ( "
                + "   SELECT [SubjectCategory].category_name, "
                + "          COUNT([Subject].subject_id) AS subject_count, "
                + "          MAX([Subject].created_at) AS latest_created_at "
                + "   FROM [SubjectCategory] "
                + "   JOIN [Subject] ON [SubjectCategory].category_id = [Subject].category_id "
                + "   GROUP BY [SubjectCategory].category_name "
                + ") AS aggregated "
                + "ORDER BY latest_created_at DESC;";

        // Try-with-resources to ensure the resources are closed after usage
        try {
            connection = getConnection();
            ps = connection.prepareStatement(sqlCommand);
            rs = ps.executeQuery();
            while (rs.next()) {
                subjectsCount.put(rs.getString("category_name"), rs.getInt("subject_count"));
            }
        } catch (SQLException ex) {
            System.out.println("Errors occur in get quiz DAO: " + ex.getMessage());
        } finally {
        }
        return subjectsCount;
    }

    public HashMap<String, BigDecimal> GetRevenueByCategories() {
        PreparedStatement ps = null;
        ResultSet rs = null;
        Connection connection = null;

        HashMap<String, BigDecimal> categoriesCount = new HashMap<>();
        String sqlCommand = "SELECT TOP(6) [SubjectCategory].category_name, "
                + "                 SUM([Registration].sale_price) AS [Revenues] "
                + "          FROM [Registration] "
                + "          JOIN [PricePackage] ON [Registration].package_id = [PricePackage].package_id "
                + "          JOIN [Subject] ON [PricePackage].subject_id = [Subject].subject_id "
                + "          JOIN [SubjectCategory] ON [Subject].category_id = [SubjectCategory].category_id "
                + "          WHERE [Registration].status = 1 "
                + "          GROUP BY [SubjectCategory].category_name "
                + "          ORDER BY MAX([Registration].updated_at) DESC;";
        try {
            connection = getConnection();
            ps = connection.prepareStatement(sqlCommand);
            rs = ps.executeQuery();
            while (rs.next()) {
                categoriesCount.put(rs.getString("category_name"), rs.getBigDecimal("Revenues"));
            }
        } catch (SQLException ex) {
            System.out.println("Errors occur in get quiz DAO: " + ex.getMessage());
        } finally {
        }
        return categoriesCount;
    }

    public List<Subject> GetSubjectListByCategoryId(int category_id) {
        PreparedStatement ps = null;
        ResultSet rs = null;
        Connection connection = null;

        List<Subject> list = new ArrayList<>();
        String sqlCommand = "SELECT [Subject].title, [Subject].status, [Subject].created_at\n"
                + "  FROM [Subject]\n"
                + "  JOIN [SubjectCategory] ON [Subject].category_id = [SubjectCategory].category_id\n"
                + "  WHERE [SubjectCategory].category_id = ?\n"
                + "  ORDER BY [Subject].created_at DESC;";
        try {
            connection = getConnection();
            ps = connection.prepareStatement(sqlCommand);
            ps.setInt(1, category_id);
            rs = ps.executeQuery();
            while (rs.next()) {
                Subject sub = new Subject();
                sub.setTitle(rs.getString("title"));
                sub.setStatus(rs.getInt("status"));
                sub.setCreated_at(rs.getTimestamp("created_at").toLocalDateTime());
                list.add(sub);
            }
        } catch (SQLException ex) {
            System.out.println("Errors occur in get quiz DAO: " + ex.getMessage());
        } finally {
        }
        return list;
    }

    public static void main(String[] args) {
        SubjectCategoryDAO dao = new SubjectCategoryDAO();
        System.out.println("dao : " + dao.GetSubjectListByCategoryId(1));
    }
}
