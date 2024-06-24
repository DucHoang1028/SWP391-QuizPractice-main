package DAOs;

import java.time.LocalDateTime;
import Contexts.DBContext;
import Models.Post;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class PostDAO extends DBContext {

    public static final int SORT_BY_UPDATE = 1; // Constant for sorting by update date
    public static final int SORT_BY_TITLE = 2;  // Constant for sorting by title
    public static final int SEARCH_ALL = 1;
    public static final int SEARCH_BY_TITLE = 2;
    public static final int SEARCH_BY_CATEGORY = 3;
    public static final int SEARCH_BY_FEATURED = 4;
    // Method to get the top 5 hot posts (status = 1) ordered by creation date in descending order

    public List<Post> GetHotPosts() throws SQLException {
        List<Post> posts = new ArrayList<>();
        String query = "SELECT post_id, thumbnail, title, author_id, content, summary, created_at, status FROM Post WHERE status = 1 AND is_featured = 1";

        // Try-with-resources to ensure the resources are closed after usage
        try (Connection conn = getConnection(); PreparedStatement stmt = conn.prepareStatement(query); ResultSet rs = stmt.executeQuery()) {

            // Loop through the result set and populate the list of posts
            while (rs.next()) {
                Post post = new Post();
                post.setPost_id(rs.getInt("post_id"));
                post.setThumbnail(rs.getString("thumbnail"));
                post.setTitle(rs.getString("title"));
                post.setAuthor_id(rs.getInt("author_id"));
                post.setContent(rs.getString("content"));
                post.setSummary(rs.getString("summary"));
                post.setPost_date(rs.getTimestamp("created_at").toLocalDateTime());
                post.setStatus(rs.getInt("status"));
                posts.add(post);
            }
        }
        return posts;
    }

    // Method to get the latest 5 posts with status = 1 ordered by creation date in descending order
    public List<Post> GetLatestPosts() {
        List<Post> posts = new ArrayList<>();
        String query = "SELECT post_id, thumbnail, title, summary, created_at "
                + "FROM Post WHERE status = 1 "
                + "ORDER BY created_at DESC "
                + "OFFSET 0 ROWS FETCH NEXT 5 ROWS ONLY";

        try (Connection conn = getConnection(); PreparedStatement stmt = conn.prepareStatement(query); ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Post post = new Post();
                post.setPost_id(rs.getInt("post_id"));
                post.setThumbnail(rs.getString("thumbnail"));
                post.setTitle(rs.getString("title"));
                post.setSummary(rs.getString("summary"));
                post.setPost_date(rs.getTimestamp("created_at").toLocalDateTime());
                posts.add(post);
            }
        } catch (SQLException ex) {
            Logger.getLogger(PostDAO.class.getName()).log(Level.SEVERE, null, ex);
        }

        return posts;
    }

  public List<Post> GetBlogPosts(int limit, int offset, String searchValue, List<Integer> categoryIds, int sort) {
    List<Post> posts = new ArrayList<>();
    String sortOption = GetSortOption(sort);

    StringBuilder queryBuilder = new StringBuilder();
    queryBuilder.append("SELECT post_id, thumbnail, title, summary, created_at, author_id, status, category_id ")
                .append("FROM Post WHERE status = 1 AND title LIKE ? ");

    if (categoryIds != null && !categoryIds.isEmpty()) {
        queryBuilder.append("AND category_id IN (");
        for (int i = 0; i < categoryIds.size(); i++) {
            queryBuilder.append("?");
            if (i < categoryIds.size() - 1) {
                queryBuilder.append(",");
            }
        }
        queryBuilder.append(") ");
    }

    queryBuilder.append(sortOption)
                .append(" OFFSET ? ROWS FETCH NEXT ? ROWS ONLY");

    String query = queryBuilder.toString();

    try (Connection conn = getConnection(); PreparedStatement stmt = conn.prepareStatement(query)) {
        int paramIndex = 1;
        stmt.setString(paramIndex++, "%" + searchValue + "%");

        if (categoryIds != null && !categoryIds.isEmpty()) {
            for (Integer categoryId : categoryIds) {
                stmt.setInt(paramIndex++, categoryId);
            }
        }

        stmt.setInt(paramIndex++, offset);
        stmt.setInt(paramIndex, limit);
        ResultSet rs = stmt.executeQuery();

        while (rs.next()) {
            Post post = new Post();
            post.setPost_id(rs.getInt("post_id"));
            post.setThumbnail(rs.getString("thumbnail"));
            post.setTitle(rs.getString("title"));
            post.setSummary(rs.getString("summary"));
            post.setPost_date(rs.getTimestamp("created_at").toLocalDateTime());
            post.setAuthor_id(rs.getInt("author_id"));
            post.setStatus(rs.getInt("status"));
            post.setCategory_id(rs.getInt("category_id"));
            posts.add(post);
        }
    } catch (SQLException ex) {
        Logger.getLogger(PostDAO.class.getName()).log(Level.SEVERE, null, ex);
    }

    return posts;
}

public int GetResultLength(String searchValue, List<Integer> categoryIds) {
    StringBuilder queryBuilder = new StringBuilder();
    queryBuilder.append("SELECT COUNT(*) FROM Post WHERE status = 1 AND title LIKE ? ");

    if (categoryIds != null && !categoryIds.isEmpty()) {
        queryBuilder.append("AND category_id IN (");
        for (int i = 0; i < categoryIds.size(); i++) {
            queryBuilder.append("?");
            if (i < categoryIds.size() - 1) {
                queryBuilder.append(",");
            }
        }
        queryBuilder.append(") ");
    }

    String query = queryBuilder.toString();

    try (Connection conn = getConnection(); PreparedStatement stmt = conn.prepareStatement(query)) {
        int paramIndex = 1;
        stmt.setString(paramIndex++, "%" + searchValue + "%");

        if (categoryIds != null && !categoryIds.isEmpty()) {
            for (Integer categoryId : categoryIds) {
                stmt.setInt(paramIndex++, categoryId);
            }
        }

        ResultSet rs = stmt.executeQuery();
        if (rs.next()) {
            return rs.getInt(1);
        }
    } catch (SQLException ex) {
        Logger.getLogger(PostDAO.class.getName()).log(Level.SEVERE, null, ex);
    }

    return 0;
}

    // Method to get the sort option based on the sort parameter
    public String GetSortOption(int sort) {
        switch (sort) {
            case SORT_BY_UPDATE:
                return "ORDER BY created_at DESC";
            case SORT_BY_TITLE:
                return "ORDER BY title ASC";
            default:
                throw new AssertionError();
        }
    }

    
    public Post getBlogPostDetails(int postId) {
        String query = "SELECT * FROM Post WHERE post_id = ? AND status = ?";
        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setInt(1, postId);
            ps.setInt(2, Post.STATUS_ACTIVE);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return mapPost(rs);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
       private Post mapPost(ResultSet rs) throws SQLException {
        Post post = new Post();
        post.setPost_id(rs.getInt("post_id"));
        post.setTitle(rs.getString("title"));
        post.setCategory_id(rs.getInt("category_id"));
        post.setAuthor_id(rs.getInt("author_id"));
        post.setPost_date(rs.getTimestamp("created_at").toLocalDateTime());
        post.setThumbnail(rs.getString("thumbnail"));
        post.setContent(rs.getString("content"));
        return post;
    }
}
