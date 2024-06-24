package DAOs;

import Contexts.DBContext;
import Models.PricePackage;
import Models.Subject;

import java.math.BigDecimal;
import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class SubjectDAO extends DBContext {

    public static final int SORT_BY_UPDATE = 1;
    public static final int SORT_BY_TITLE = 2;
    public static final int SORT_BY_PRICE = 3;

    public static final int SEARCH_ALL = 1;
    public static final int SEARCH_BY_TITLE = 2;
    public static final int SEARCH_BY_FEATURED = 3;


    
    public List<Subject> GetFeaturedSubjects() throws SQLException {
        List<Subject> subjects = new ArrayList<>();
        String query = "SELECT subject_id, "
                + "thumbnail, title, tag_line, description "
                + "FROM Subject WHERE status = 1 AND is_featured = 1";

        // Try-with-resources to ensure the resources are closed after usage
        try (Connection conn = getConnection(); PreparedStatement stmt = conn.prepareStatement(query); ResultSet rs = stmt.executeQuery()) {

            // Loop through the result set and populate the list of subjects
            while (rs.next()) {
                Subject subject = new Subject();
                subject.setSubject_id(rs.getInt("subject_id"));
                subject.setThumbnail(rs.getString("thumbnail"));
                subject.setTitle(rs.getString("title"));
                subject.setTag_line(rs.getString("tag_line"));
                subject.setDescription(rs.getString("description"));
                subjects.add(subject);
            }
        }
        return subjects;
    }
    
    // Method to get featured subjects (status = 1) ordered randomly
    public List<Subject> GetRandomFeaturedSubjects(int amount) throws SQLException {
        List<Subject> subjects = new ArrayList<>();
        String query = "SELECT TOP " + amount + " subject_id, "
                + "thumbnail, title, tag_line, description "
                + "FROM Subject WHERE status = 1 AND is_featured = 1 ORDER BY NEWID()";

        // Try-with-resources to ensure the resources are closed after usage
        try (Connection conn = getConnection(); PreparedStatement stmt = conn.prepareStatement(query); ResultSet rs = stmt.executeQuery()) {

            // Loop through the result set and populate the list of subjects
            while (rs.next()) {
                Subject subject = new Subject();
                subject.setSubject_id(rs.getInt("subject_id"));
                subject.setThumbnail(rs.getString("thumbnail"));
                subject.setTitle(rs.getString("title"));
                subject.setTag_line(rs.getString("tag_line"));
                subject.setDescription(rs.getString("description"));
                subjects.add(subject);
            }
        }
        return subjects;
    }
    
    

    // method to get the list of published subjects from the database paginated
    public List<Subject> GetPublishedSubjects(int search, int sort, int page, int pageLength, String searchValue, String categories) {

        List<Subject> subjects = new ArrayList<>();

        String searchOption = GetSearchOption(search);
        String sortOption = GetSortOption(sort);
        String categoryOption = GetCategoryOption(categories);

        int offset = GetOffset(page, pageLength);

        String query = "SELECT DISTINCT "
                + "    s.subject_id,"
                + "    s.title,"
                + "    s.thumbnail,"
                + "    s.tag_line,"
                + "    s.status,"
                + "    s.created_at,"
                + "    s.updated_at,"
                + "    CAST(s.description AS varchar(MAX)) AS description,"
                + "    s.is_featured,"
                + "    s.category_id,"
                + "    pp.list_price,"
                + "    pp.sale_price "
                + "FROM "
                + "    [dbo].[Subject] s "
                + "JOIN "
                + "    [dbo].[PricePackage] pp "
                + "    ON s.subject_id = pp.subject_id "
                + "WHERE "
                + "    pp.sale_price = ( "
                + "        SELECT "
                + "            MIN(sub_pp.sale_price) "
                + "        FROM "
                + "            [dbo].[PricePackage] sub_pp "
                + "        WHERE "
                + "            sub_pp.subject_id = s.subject_id "
                + "         AND pp.status = 1 ) "
                + searchOption + " "
                + categoryOption + " "
                + sortOption + " "
                + "OFFSET ? ROWS "
                + "FETCH NEXT ? ROWS ONLY ";

        try (Connection conn = getConnection(); PreparedStatement stmt = conn.prepareStatement(query)) {

            // if search option is search by title, modify the search value
            if (search == SEARCH_BY_TITLE) {
                searchValue = "%" + searchValue + "%";
            }

            // if search option is search all, there will be no search_value
            if (search == SEARCH_ALL) {
                stmt.setInt(1, offset);
                stmt.setInt(2, pageLength);
            } else {
                stmt.setString(1, searchValue);
                stmt.setInt(2, offset);
                stmt.setInt(3, pageLength);
            }

            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {

                LocalDateTime create = null;
                LocalDateTime update = null;

                if (rs.getDate("created_at") != null) {
                    create = rs.getTimestamp("created_at").toLocalDateTime();
                }
                if (rs.getDate("updated_at") != null) {
                    update = rs.getTimestamp("updated_at").toLocalDateTime();
                }

                Subject subject = new Subject();
                subject.setSubject_id(rs.getInt("subject_id"));
                subject.setTitle(rs.getString("title"));
                subject.setThumbnail(rs.getString("thumbnail"));
                subject.setTag_line(rs.getString("tag_line"));
                subject.setStatus(rs.getInt("status"));
                subject.setCreated_at(create);
                subject.setUpdated_at(update);
                subject.setDescription(rs.getString("description"));
                subject.setIs_featured(rs.getBoolean("is_featured"));
                subject.setCategory_id(rs.getInt("category_id"));
                subject.setList_price(BigDecimal.valueOf(rs.getDouble("list_price")));
                subject.setSale_price(BigDecimal.valueOf(rs.getDouble("sale_price")));

                subjects.add(subject);
            }
        } catch (SQLException ex) {
            Logger.getLogger(SubjectDAO.class.getName()).log(Level.SEVERE, null, ex);
        }

        return subjects;
    }

    public Subject getSubjectById(int id) {
        Subject subject = null;

        String query = "SELECT DISTINCT "
                + "    s.subject_id,"
                + "    s.title,"
                + "    s.thumbnail,"
                + "    s.tag_line,"
                + "    s.status,"
                + "    s.created_at,"
                + "    s.updated_at,"
                + "    CAST(s.description AS varchar(MAX)) AS description,"
                + "    s.is_featured,"
                + "    s.category_id,"
                + "    pp.list_price,"
                + "    pp.sale_price "
                + "FROM "
                + "    [dbo].[Subject] s "
                + "JOIN "
                + "    [dbo].[PricePackage] pp "
                + "    ON s.subject_id = pp.subject_id "
                + "WHERE "
                + "    s.subject_id = ? "
                + "    AND pp.sale_price = ( "
                + "        SELECT "
                + "            MIN(sub_pp.sale_price) "
                + "        FROM "
                + "            [dbo].[PricePackage] sub_pp "
                + "        WHERE "
                + "            sub_pp.subject_id = s.subject_id "
                + "         AND pp.status = 1 )";

        try (Connection conn = getConnection(); PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {

                LocalDateTime create = null;
                LocalDateTime update = null;

                if (rs.getDate("created_at") != null) {
                    create = rs.getTimestamp("created_at").toLocalDateTime();
                }
                if (rs.getDate("updated_at") != null) {
                    update = rs.getTimestamp("updated_at").toLocalDateTime();
                }
                subject = new Subject();
                subject.setSubject_id(rs.getInt("subject_id"));
                subject.setTitle(rs.getString("title"));
                subject.setThumbnail(rs.getString("thumbnail"));
                subject.setTag_line(rs.getString("tag_line"));
                subject.setStatus(rs.getInt("status"));
                subject.setCreated_at(create);
                subject.setUpdated_at(update);
                subject.setDescription(rs.getString("description"));
                subject.setIs_featured(rs.getBoolean("is_featured"));
                subject.setCategory_id(rs.getInt("category_id"));
                subject.setList_price(BigDecimal.valueOf(rs.getDouble("list_price")));
                subject.setSale_price(BigDecimal.valueOf(rs.getDouble("sale_price")));

            }

        } catch (SQLException ex) {
            Logger.getLogger(SubjectDAO.class.getName()).log(Level.SEVERE, null, ex);
        }

        return subject;
    }
    public List<PricePackage> getPricePackagesBySubjectId(int subjectId) throws SQLException {
    List<PricePackage> pricePackages = new ArrayList<>();
    String query = "SELECT * "
            + "     FROM [PricePackage] "
            + "     WHERE [subject_id] = ? "
            + "     AND [status] = 1";

    try (Connection conn = getConnection(); PreparedStatement stmt = conn.prepareStatement(query)) {
        stmt.setInt(1, subjectId);
        ResultSet rs = stmt.executeQuery();
        
        while (rs.next()) {
            PricePackage pricePackage = new PricePackage();
            pricePackage.setPackage_id(rs.getInt("package_id"));
            pricePackage.setSubject_id(rs.getInt("subject_id"));
            pricePackage.setName(rs.getString("name"));
            pricePackage.setList_price(BigDecimal.valueOf(rs.getDouble("list_price")));
            pricePackage.setSale_price(BigDecimal.valueOf(rs.getDouble("sale_price")));
            pricePackage.setDuration(rs.getInt("duration"));
            pricePackages.add(pricePackage);
        }
       
    }
    return pricePackages; 
} 


    public String GetSearchOption(int search) {
        switch (search) {
            case SEARCH_BY_TITLE -> {
                return "AND title LIKE ? ";
            }
            case SEARCH_BY_FEATURED -> {
                return "AND is_featured = ?";
            }
            case SEARCH_ALL -> {
                return "";
            }
            default ->
                throw new Error("GetSearchOption failed");
        }
    }

    public String GetSortOption(int sort) {

        switch (sort) {
            case SORT_BY_UPDATE -> {
                return "ORDER BY updated_at DESC";
            }
            case SORT_BY_TITLE -> {
                return "ORDER BY title ASC";
            }
            case SORT_BY_PRICE -> {
                return "ORDER BY sale_price ASC";
            }
            default ->
                throw new Error("GetSearchOption failed");
        }
    }

    public String GetCategoryOption(String categories) {
        try {
            if (categories != null && !categories.isEmpty()) {
                String[] rawCategoryIds = categories.split(" ");
                int[] categoryIds = new int[rawCategoryIds.length];
                for (int i = 0; i < categoryIds.length; i++) {
                    categoryIds[i] = Integer.parseInt(rawCategoryIds[i]);
                }
                StringBuilder categoryOption = new StringBuilder("AND s.category_id IN (");
                for (int i = 0; i < categoryIds.length; i++) {
                    categoryOption.append(categoryIds[i]);
                    if (i < categoryIds.length - 1) {
                        categoryOption.append(",");
                    }
                }
                categoryOption.append(")");
                return categoryOption.toString();
            }

            return "";
        } catch (NumberFormatException e) {
            return "";
        }

    }

    public int GetOffset(int page, int length) {

        return (page - 1) * length;
    }

    public int GetResultLength(int search, int sort, String searchValue, String categories) {
        List<Subject> list = GetPublishedSubjects(search, sort, 1, Integer.MAX_VALUE, searchValue, categories);

        return list.size();
    }

    public Subject GetPublishedSubjectById(int subject) {
        Subject s = new Subject();
        String query = "SELECT * FROM Subject WHERE subject_id = ? AND status = 1";
        try (Connection conn = getConnection(); PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, subject);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                LocalDateTime create = null;
                LocalDateTime update = null;

                if (rs.getDate("created_at") != null) {
                    create = rs.getTimestamp("created_at").toLocalDateTime();
                }
                if (rs.getDate("updated_at") != null) {
                    update = rs.getTimestamp("updated_at").toLocalDateTime();
                }

                s.setSubject_id(rs.getInt("subject_id"));
                s.setTitle(rs.getString("title"));
                s.setThumbnail(rs.getString("thumbnail"));
                s.setTag_line(rs.getString("tag_line"));
                s.setStatus(rs.getInt("status"));
                s.setCreated_at(create);
                s.setUpdated_at(update);
                s.setDescription(rs.getString("description"));
                s.setIs_featured(rs.getBoolean("is_featured"));
                s.setCategory_id(rs.getInt("category_id"));
            }
        } catch (SQLException ex) {
            Logger.getLogger(SubjectDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return s;
    }
    
    public int GetTotalSubjectsCount(){
        int totalSubjectsCount = 0;
        String query = "SELECT COUNT(subject_id) AS [totalSubjectsCount]"
                + "     FROM [Subject];";
        try (Connection conn = getConnection(); PreparedStatement stmt = conn.prepareStatement(query)) {
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                totalSubjectsCount = rs.getInt("totalSubjectsCount");
            }
        } catch (SQLException ex) {
            Logger.getLogger(SubjectDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return totalSubjectsCount;
    }
    
    public int GetTodayNewSubjectsCount(){
        int newSubjectsCount = 0;
        String query = "SELECT COUNT(subject_id) AS [newSubjectsCount]"
                + "     FROM [Subject]"
                + "     WHERE CONVERT(DATE, created_at) = CONVERT(DATE, GETDATE());";
        try (Connection conn = getConnection(); PreparedStatement stmt = conn.prepareStatement(query)) {
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                newSubjectsCount = rs.getInt("newSubjectsCount");
            }
        } catch (SQLException ex) {
            Logger.getLogger(SubjectDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return newSubjectsCount;
    }
    
    public boolean CheckUserAccess(int user_id, int subject_id) {

        // user -> registration -> pricepackage -> subject_id
        PreparedStatement ps = null;
        ResultSet rs = null;
        Connection connection = null;

        boolean result = false;
        String sqlCommand = "SELECT [dbo].[User].user_id\n"
                + "FROM     [dbo].[User] INNER JOIN\n"
                + "  [dbo].[Registration] ON [dbo].[User].user_id = [dbo].[Registration].user_id INNER JOIN\n"
                + "  [dbo].[PricePackage] ON [dbo].[Registration].package_id = [dbo].[PricePackage].package_id INNER JOIN\n"
                + "  [dbo].[Subject] ON [dbo].[PricePackage].subject_id = [dbo].[Subject].subject_id \n"
                + "WHERE [dbo].[User].user_id = ? AND [dbo].[Subject].subject_id = ? "
                + "AND [dbo].[PricePackage].status = 1 "
                + "AND [dbo].[Registration].status = 1 ";
        // Try-with-resources to ensure the resources are closed after usage
        try {
            connection = getConnection();
            ps = connection.prepareStatement(sqlCommand);
            ps.setInt(1, user_id);
            ps.setInt(2, subject_id);
            rs = ps.executeQuery();

            while (rs.next()) {
                result = true;
            }

        } catch (SQLException ex) {
            System.out.println("Errors occur in get quiz DAO: " + ex.getMessage());
        } finally {
        }
        return result;

    }
    
    public List<Subject> GetRegisteredSubjects(int user_id) {

        // user -> registration -> pricepackage -> subject_id
        PreparedStatement ps = null;
        ResultSet rs = null;
        Connection connection = null;
        
        List<Subject> subjects = new ArrayList();

        boolean result = false;
        String sqlCommand = "SELECT DISTINCT "
                + "[dbo].[Subject].subject_id, \n"
                + "[dbo].[Subject].title \n"
                + "FROM     [dbo].[User] INNER JOIN\n"
                + "  [dbo].[Registration] ON [dbo].[User].user_id = [dbo].[Registration].user_id INNER JOIN\n"
                + "  [dbo].[PricePackage] ON [dbo].[Registration].package_id = [dbo].[PricePackage].package_id INNER JOIN\n"
                + "  [dbo].[Subject] ON [dbo].[PricePackage].subject_id = [dbo].[Subject].subject_id \n"
                + "WHERE [dbo].[User].user_id = ? "
                + "AND [dbo].[PricePackage].status = 1 "
                + "AND [dbo].[Registration].status = 1 ";
        // Try-with-resources to ensure the resources are closed after usage
        try {
            connection = getConnection();
            ps = connection.prepareStatement(sqlCommand);
            ps.setInt(1, user_id);
            rs = ps.executeQuery();

            while (rs.next()) {

                Subject subject = new Subject();
                subject.setSubject_id(rs.getInt("subject_id"));
                subject.setTitle(rs.getString("title"));

                subjects.add(subject);
            }

        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        } finally {
        }
        return subjects;

    }
    
    public static void main(String[] args) throws SQLException {
        SubjectDAO dao = new SubjectDAO();
        List<Subject> list = dao.GetRegisteredSubjects(6);
        for (Subject subject : list) {
            System.out.println(subject.getSubject_id());
        }
    }

    public void registerSubjects(int userId, int subjectId, int pricePackageId) throws SQLException {
        String query="INSERT INTO registered_subjects (user_id, subject_id, price_package_id, registration_time) VALUES (?, ?, ?, NOW())";
        try (Connection conn = getConnection(); PreparedStatement stmt = conn.prepareStatement(query)) {
            ResultSet rs = stmt.executeQuery();
            stmt.setInt(1, userId);
            stmt.setInt(2, subjectId);
            stmt.setInt(3, pricePackageId);
            stmt.executeUpdate();
        }
    }
    public void updateRegistration(Subject registedSubjects) throws SQLException {
        String query = "UPDATE registered_subjects SET total_cost = ?, valid_from = ?, valid_to = ?, status = ? WHERE subject_id = ?";
        
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            ResultSet rs = stmt.executeQuery();
            stmt.setBigDecimal(1, BigDecimal.valueOf(rs.getDouble("total_cost")));
            stmt.setString(2, registedSubjects.getValid_from());
            stmt.setString(3, registedSubjects.getValid_to());
            stmt.setInt(4, registedSubjects.getStatus());
            stmt.setInt(5, registedSubjects.getSubject_id());

            stmt.executeUpdate();
        }
    }
}


    
    


