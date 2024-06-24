package DAOs;

import Contexts.DBContext;

import Models.Quiz.Quiz;
import Models.RegistrationDetails;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.sql.Date;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

public class RegistrationDAO extends DBContext {

    public int GetNewlyBoughtCustomerCount() {
        int newlyBoughtCustomer = 0;
        String query = "SELECT COUNT(DISTINCT user_id) AS [NewlyBoughtCustomer] "
                + "     FROM [Registration] "
                + "     WHERE status = 1 AND CONVERT(DATE, created_at) = CONVERT(DATE, GETDATE());";
        try (Connection conn = getConnection(); PreparedStatement stmt = conn.prepareStatement(query)) {
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                newlyBoughtCustomer = rs.getInt("NewlyBoughtCustomer");
            }
        } catch (SQLException ex) {
            Logger.getLogger(SubjectDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return newlyBoughtCustomer;
    }

    public int GetRegistrationsSize(String subjectName, String email, String from, String to, int status) {

        int count = 0;
        String query = "SELECT COUNT(*) FROM Registration\n"
                + "LEFT JOIN [User] as registerUser ON registerUser.user_id = Registration.user_id\n"
                + "LEFT JOIN PricePackage ON PricePackage.package_id = Registration.package_id\n"
                + "LEFT JOIN [Subject] ON [Subject].subject_id = PricePackage.subject_id \n"
                + "WHERE 1 = 1 AND title LIKE ?\n"
                + "AND registerUser.email LIKE ?\n";
        if (status != 3) {
            query += "AND [Registration].status = " + status;
        }
        if (!from.equals("") && !to.equals("")) {
            query += "AND Registration.created_at BETWEEN '" + from + "' AND '" + to + "'\n";
        }
        try (Connection conn = getConnection(); PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, "%" + subjectName.trim() + "%");
            stmt.setString(2, "%" + email.trim() + "%");
            System.out.println("After : " + stmt.toString());

            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                count = rs.getInt(1);

            }
        } catch (SQLException ex) {
            Logger.getLogger(SubjectDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        System.out.println(count);
        return count;
    }

    public BigDecimal GetTotalRevenue() {
        BigDecimal totalRevenue = null;
        String query = "SELECT SUM(sale_price) AS [TotalRevenue] "
                + "     FROM [Registration] "
                + "     WHERE status = 1;";
        try (Connection conn = getConnection(); PreparedStatement stmt = conn.prepareStatement(query)) {
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                totalRevenue = rs.getBigDecimal("TotalRevenue");

            }
        } catch (SQLException ex) {
            Logger.getLogger(SubjectDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return totalRevenue;
    }

    public List<RegistrationDetails> GetRegistrationPage(int page, int pageSize,
            String subjectName, String email, String from, String to, int status) {
        PreparedStatement ps = null;
        ResultSet rs = null;
        Connection connection = null;

        Quiz quiz = null;
        List<RegistrationDetails> result = new ArrayList<>();

        int startIndex = (page - 1) * pageSize + 1;
        int endIndex = (page) * pageSize;

        String query = "Select * from (SELECT Registration.registration_id,\n"
                + "Registration.created_at,\n"
                + "	   registerUser.email,\n"
                + "	   [Subject].title,\n"
                + "	   PricePackage.[name],\n"
                + "	   Registration.valid_from,\n"
                + "	   Registration.valid_to,\n"
                + "	   UserUpdate.email as lastUpdate,\n"
                + "	   Registration.sale_price as total,\n"
                + "	   Registration.[status],\n"
                + "	   ROW_NUMBER() OVER(ORDER BY registration_id) as rowIndex\n"
                + "FROM Registration\n"
                + "LEFT JOIN [User] as registerUser ON registerUser.user_id = Registration.user_id\n"
                + "LEFT JOIN PricePackage ON PricePackage.package_id = Registration.package_id\n"
                + "LEFT JOIN [Subject] ON [Subject].subject_id = PricePackage.subject_id\n"
                + "LEFT JOIN [User] as UserUpdate ON UserUpdate.user_id = Registration.last_updated_by"
                + " WHERE title LIKE ?"
                + " AND registerUser.email LIKE ?\n";
        if (status != 3) {
            query += "AND [Registration].status = " + status;
        }
        if (!from.equals("") && !to.equals("")) {
            query += "AND Registration.created_at BETWEEN '" + from + "' AND '" + to + "'\n";
        }
        query += ") As cursor2\n"
                + "WHERE rowIndex between ? AND ?";
        try {
            connection = getConnection();
            ps = connection.prepareStatement(query);
            ps.setInt(3, startIndex);
            ps.setInt(4, endIndex);
            ps.setString(1, "%" + subjectName.trim() + "%");
            ps.setString(2, "%" + email.trim() + "%");

            rs = ps.executeQuery();
            RegistrationDetails item = null;
            while (rs.next()) {
                LocalDateTime create = null;
                LocalDateTime valid_from = null;
                LocalDateTime valid_to = null;

                if (rs.getDate("created_at") != null) {
                    create = rs.getTimestamp("created_at").toLocalDateTime();
                }
                if (rs.getDate("valid_from") != null) {
                    valid_from = rs.getTimestamp("valid_from").toLocalDateTime();
                }
                if (rs.getDate("valid_to") != null) {
                    valid_to = rs.getTimestamp("valid_to").toLocalDateTime();
                }

                item = new RegistrationDetails(rs.getInt("registration_id"),
                        create,
                        rs.getString("email"),
                        rs.getString("title"),
                        rs.getString("name"),
                        valid_from,
                        valid_to,
                        rs.getString("lastUpdate"),
                        rs.getFloat("total"),
                        rs.getInt("status"));
                result.add(item);
            }

            return result;
        } catch (SQLException ex) {
            System.out.println("Errors occur in get user DAO: " + ex.getMessage());
        } finally {
            if (connection != null) {
            }
        }
        return null;

    }

    public RegistrationDetails GetRegistrationSpecific(int registration_id) {
        PreparedStatement ps = null;
        ResultSet rs = null;
        Connection connection = null;

        RegistrationDetails result = null;

        String query = "Select * from (SELECT Registration.registration_id,\n"
                + "Registration.created_at,\n"
                + "	   registerUser.email,\n"
                + "	   [Subject].title,\n"
                + "	   PricePackage.[name],\n"
                + "	   Registration.valid_from,\n"
                + "	   Registration.valid_to,\n"
                + "	   UserUpdate.email as lastUpdate,"
                + "Registration.list_price,Registration.notes, \n"
                + "	   Registration.sale_price as total,\n"
                + "	   Registration.[status],\n"
                + "	   ROW_NUMBER() OVER(ORDER BY registration_id) as rowIndex\n"
                + "FROM Registration\n"
                + "LEFT JOIN [User] as registerUser ON registerUser.user_id = Registration.user_id\n"
                + "LEFT JOIN PricePackage ON PricePackage.package_id = Registration.package_id\n"
                + "LEFT JOIN [Subject] ON [Subject].subject_id = PricePackage.subject_id\n"
                + "LEFT JOIN [User] as UserUpdate ON UserUpdate.user_id = Registration.last_updated_by"
                + ") As cursor2\n"
                + "WHERE  registration_id = ?";
        try {
            connection = getConnection();
            ps = connection.prepareStatement(query);
            ps.setInt(1, registration_id);

            rs = ps.executeQuery();
            RegistrationDetails item = null;
            if (rs.next()) {
                LocalDateTime create = null;
                LocalDateTime valid_from = null;
                LocalDateTime valid_to = null;

                if (rs.getDate("created_at") != null) {
                    create = rs.getTimestamp("created_at").toLocalDateTime();
                }
                if (rs.getDate("valid_from") != null) {
                    valid_from = rs.getTimestamp("valid_from").toLocalDateTime();
                }
                if (rs.getDate("valid_to") != null) {
                    valid_to = rs.getTimestamp("valid_to").toLocalDateTime();
                }

                item = new RegistrationDetails(rs.getInt("registration_id"),
                        create,
                        rs.getString("email"),
                        rs.getString("title"),
                        rs.getString("name"),
                        valid_from,
                        valid_to,
                        rs.getString("lastUpdate"),
                        rs.getFloat("total"),
                        rs.getInt("status"),
                        rs.getFloat("list_price"),
                        rs.getString("notes"));
                result = item;
            }

            return result;
        } catch (SQLException ex) {
            System.out.println("Errors occur in get registration DAO: " + ex.getMessage());
        } finally {
            if (connection != null) {
            }
        }
        return result;

    }

    public int GetRegistrationCountByStatus(int status) {
        int registrationCount = 0;
        String query = "SELECT COUNT(registration_id) AS [status_count] "
                + "     FROM [Registration] "
                + "     WHERE status = ? AND CONVERT(DATE, created_at) = CONVERT(DATE, GETDATE());";
        try (Connection conn = getConnection(); PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, status);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                registrationCount = rs.getInt("status_count");
            }
        } catch (SQLException ex) {
            Logger.getLogger(SubjectDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return registrationCount;
    }

    public HashMap<LocalDate, Integer> GetSuccessOrdersTrendByDateRange(LocalDate start, LocalDate end) {
        LinkedHashMap<LocalDate, Integer> orderCount = new LinkedHashMap<>();
        String query = "WITH DateSeries AS (\n"
                + "    SELECT CAST(? AS DATE) AS Date\n"
                + "    UNION ALL\n"
                + "    SELECT DATEADD(DAY, 1, Date)\n"
                + "    FROM DateSeries\n"
                + "    WHERE Date < ?\n" // Adjusted to include the end date
                + ")\n"
                + "SELECT COALESCE(COUNT(r.registration_id), 0) AS registration_count,\n"
                + "       ds.Date\n"
                + "FROM DateSeries ds\n"
                + "LEFT JOIN (\n"
                + "    SELECT *\n"
                + "    FROM [Registration]\n"
                + "    WHERE status = 1\n" // Filter registrations with status = 1
                + ") r ON CAST(r.updated_at AS DATE) = ds.Date\n"
                + "GROUP BY ds.Date\n"
                + "ORDER BY ds.Date;";
        try (Connection conn = getConnection(); PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setDate(1, Date.valueOf(start));
            stmt.setDate(2, Date.valueOf(end));
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                orderCount.put(rs.getDate("date").toLocalDate(), rs.getInt("registration_count"));
            }
        } catch (SQLException ex) {
            Logger.getLogger(SubjectDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return orderCount;
    }

    public HashMap<LocalDate, Integer> GetAllOrdersTrendByDateRange(LocalDate start, LocalDate end) {
        LinkedHashMap<LocalDate, Integer> orderCount = new LinkedHashMap<>();
        String query = "WITH DateSeries AS (\n"
                + "    SELECT CAST(? AS DATE) AS Date\n"
                + "    UNION ALL\n"
                + "    SELECT DATEADD(DAY, 1, Date)\n"
                + "    FROM DateSeries\n"
                + "    WHERE Date < ?\n"
                + "),\n"
                + "OrderCounts AS (\n"
                + "    SELECT \n"
                + "        CAST(created_at AS DATE) AS Date, \n"
                + "        COUNT(*) AS created_count,\n"
                + "        0 AS updated_count\n"
                + "    FROM Registration\n"
                + "    WHERE created_at >= ? AND created_at < DATEADD(DAY, 1, ?)\n"
                + "    GROUP BY CAST(created_at AS DATE)\n"
                + "    \n"
                + "    UNION ALL\n"
                + "    \n"
                + "    SELECT \n"
                + "        CAST(updated_at AS DATE) AS Date, \n"
                + "        0 AS created_count,\n"
                + "        COUNT(*) AS updated_count\n"
                + "    FROM Registration\n"
                + "    WHERE updated_at >= ? AND updated_at < DATEADD(DAY, 1, ?) AND created_at <> updated_at\n"
                + "    GROUP BY CAST(updated_at AS DATE)\n"
                + "),\n"
                + "AggregatedCounts AS (\n"
                + "    SELECT \n"
                + "        Date, \n"
                + "        SUM(created_count + updated_count) AS total_count\n"
                + "    FROM OrderCounts\n"
                + "    GROUP BY Date\n"
                + ")\n"
                + "SELECT \n"
                + "    ds.Date, \n"
                + "    COALESCE(ac.total_count, 0) AS registration_count\n"
                + "FROM DateSeries ds\n"
                + "LEFT JOIN AggregatedCounts ac ON ds.Date = ac.Date\n"
                + "ORDER BY ds.Date;";

        try (Connection conn = getConnection(); PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setDate(1, Date.valueOf(start));
            stmt.setDate(2, Date.valueOf(end));
            stmt.setDate(3, Date.valueOf(start));
            stmt.setDate(4, Date.valueOf(end));
            stmt.setDate(5, Date.valueOf(start));
            stmt.setDate(6, Date.valueOf(end));

            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                LocalDate date = rs.getDate("Date").toLocalDate();
                int count = rs.getInt("registration_count");
                orderCount.put(date, count);
            }
        } catch (SQLException ex) {
            Logger.getLogger(SubjectDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return orderCount;
    }

    public HashMap<String, BigDecimal> GetRevenuesBySubjects(int category_id) {
        HashMap<String, BigDecimal> revenuesByCategories = new HashMap<>();
        String query = "SELECT \n"
                + "    [Subject].title AS [subject_name],\n"
                + "    COALESCE(SUM(CASE WHEN [Registration].status = 1 THEN [Registration].sale_price ELSE 0 END), 0) AS Revenue\n"
                + "FROM \n"
                + "    [Subject]\n"
                + "    LEFT JOIN [PricePackage] ON [Subject].subject_id = [PricePackage].subject_id\n"
                + "    LEFT JOIN [Registration] ON [PricePackage].package_id = [Registration].package_id\n"
                + "    LEFT JOIN [SubjectCategory] ON [Subject].category_id = [SubjectCategory].category_id\n"
                + "WHERE \n"
                + "    [SubjectCategory].category_id = ?\n"
                + "GROUP BY \n"
                + "    [Subject].title;";
        try (Connection conn = getConnection(); PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, category_id);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                revenuesByCategories.put(rs.getString("subject_name"), rs.getBigDecimal("Revenue"));
            }
        } catch (SQLException ex) {
            Logger.getLogger(SubjectDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return revenuesByCategories;

    }

    public void UpdateRegistrationSale(int status, String notes,int id) {
        PreparedStatement ps = null;
        ResultSet rs = null;
        Connection connection = null;

        String sqlCommand = "UPDATE [dbo].[Registration]\n"
                + "   SET\n"
                + "      [status] =?\n"
                + "      \n"
                + "      ,[notes] = ?\n"
                + "      \n"
                + " WHERE [Registration].registration_id = ? ";
        try {
            connection = getConnection();
            ps = connection.prepareStatement(sqlCommand);
            ps.setInt(1, status);
            ps.setString(2, notes);
            System.out.println(notes);
            ps.setInt(3, id);
           
            ps.executeUpdate();

        } catch (SQLException ex) {
            System.out.println("Errors occur in get user DAO: " + ex.getMessage());
        } finally {

        }
    }

    public static void main(String[] args) {
        RegistrationDAO dao = new RegistrationDAO();
        System.out.println("count : " + dao.GetAllOrdersTrendByDateRange(LocalDate.now().minusDays(7), LocalDate.now()));
        System.out.println("count success : " + dao.GetSuccessOrdersTrendByDateRange(LocalDate.now().minusDays(7), LocalDate.now()));
    }
}
