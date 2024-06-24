package DAOs;

import Contexts.DBContext;
import Models.User;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.sql.Timestamp;

public class UserDAO extends DBContext {

    public User GetUserLogin(String Email, String PasswordHash) throws SQLException {
        PreparedStatement ps = null;
        ResultSet rs = null;
        Connection connection = null;

        String sqlCommand = "SELECT [user_id]\n"
                + "      ,[full_name]\n"
                + "      ,[gender]\n"
                + "      ,[email]\n"
                + "      ,[mobile]\n"
                + "      ,[password_hash]\n"
                + "      ,[role]\n"
                + "      ,[avatar]\n"
                + "      ,[created_at]\n"
                + "      ,[updated_at]\n"
                + "      ,[status]\n"
                + "      ,[locked_until]"
                + "FROM [User] WHERE email = ? AND password_hash = ?";
        try {
            connection = getConnection();
            ps = connection.prepareStatement(sqlCommand);
            ps.setString(1, Email);
            ps.setString(2, PasswordHash);
            rs = ps.executeQuery();
            User currentUser = null;
            while (rs.next()) {
                LocalDateTime create = null;
                LocalDateTime update = null;
                LocalDateTime lock = null;

                if (rs.getDate("created_at") != null) {
                    create = rs.getTimestamp("created_at").toLocalDateTime();
                }
                if (rs.getDate("updated_at") != null) {
                    update = rs.getTimestamp("updated_at").toLocalDateTime();
                }
                if (rs.getDate("locked_until") != null) {
                    lock = rs.getTimestamp("locked_until").toLocalDateTime();
                }

                currentUser = new User(rs.getInt("user_id"),
                        rs.getString("full_name"),
                        rs.getInt("gender"),
                        rs.getString("email"),
                        rs.getString("mobile"),
                        "", rs.getInt("role"), rs.getString("avatar"),
                        create,
                        update,
                        rs.getInt("status"),
                        lock);
            }

            return currentUser;
        } catch (SQLException ex) {
            System.out.println("Errors occur in get user DAO: " + ex.getMessage());
        } finally {
            if (connection != null) {
            }
        }
        return null;
    }

    public User GetUserLoginGoogle(String sub) throws SQLException {
        PreparedStatement ps = null;
        ResultSet rs = null;
        Connection connection = null;

        String sqlCommand = "SELECT dbo.AuthenticationProvider.id , \n"
                + "		dbo.AuthenticationProvider.provider_key, \n"
                + "		dbo.AuthenticationProvider.user_id , \n"
                + "		dbo.AuthenticationProvider.provider_type, \n"
                + "		dbo.[User].user_id , \n"
                + "       dbo.[User].full_name , \n"
                + "	   dbo.[User].gender, \n"
                + "	   dbo.[User].email ,\n"
                + "	   dbo.[User].mobile , \n"
                + "	   dbo.[User].password_hash ,\n"
                + "	   dbo.[User].role , \n"
                + "	   dbo.[User].avatar , \n"
                + "            dbo.[User].created_at , dbo.[User].updated_at  , dbo.[User].status , dbo.[User].locked_until \n"
                + "FROM     dbo.AuthenticationProvider INNER JOIN\n"
                + "                  dbo.[User] ON dbo.AuthenticationProvider.user_id = dbo.[User].user_id\n"
                + "WHERE dbo.AuthenticationProvider.provider_key = ? AND dbo.AuthenticationProvider.provider_type = 'Google'";
        try {
            connection = getConnection();
            ps = connection.prepareStatement(sqlCommand);
            ps.setString(1, sub);
            rs = ps.executeQuery();
            User currentUser = null;
            while (rs.next()) {
                LocalDateTime create = null;
                LocalDateTime update = null;
                LocalDateTime lock = null;

                if (rs.getDate("created_at") != null) {
                    create = rs.getTimestamp("created_at").toLocalDateTime();
                }
                if (rs.getDate("updated_at") != null) {
                    update = rs.getTimestamp("updated_at").toLocalDateTime();
                }
                if (rs.getDate("locked_until") != null) {
                    lock = rs.getTimestamp("locked_until").toLocalDateTime();
                }

                currentUser = new User(rs.getInt("user_id"),
                        rs.getString("full_name"),
                        rs.getInt("gender"),
                        rs.getString("email"),
                        rs.getString("mobile"),
                        "", rs.getInt("role"), rs.getString("avatar"),
                        create,
                        update,
                        rs.getInt("status"),
                        lock);
            }

            return currentUser;
        } catch (SQLException ex) {
            System.out.println("Errors occur in get user DAO: " + ex.getMessage());
        } finally {
            if (connection != null) {
            }
        }
        return null;
    }

    public User GetUserLoginFacebook(String sub) throws SQLException {
        PreparedStatement ps = null;
        ResultSet rs = null;
        Connection connection = null;

        String sqlCommand = "SELECT dbo.AuthenticationProvider.id , \n"
                + "		dbo.AuthenticationProvider.provider_key, \n"
                + "		dbo.AuthenticationProvider.user_id , \n"
                + "		dbo.AuthenticationProvider.provider_type, \n"
                + "		dbo.[User].user_id , \n"
                + "       dbo.[User].full_name , \n"
                + "	   dbo.[User].gender, \n"
                + "	   dbo.[User].email ,\n"
                + "	   dbo.[User].mobile , \n"
                + "	   dbo.[User].password_hash ,\n"
                + "	   dbo.[User].role , \n"
                + "	   dbo.[User].avatar , \n"
                + "            dbo.[User].created_at , dbo.[User].updated_at  , dbo.[User].status , dbo.[User].locked_until \n"
                + "FROM     dbo.AuthenticationProvider INNER JOIN\n"
                + "                  dbo.[User] ON dbo.AuthenticationProvider.user_id = dbo.[User].user_id\n"
                + "WHERE dbo.AuthenticationProvider.provider_key = ? AND dbo.AuthenticationProvider.provider_type = 'Facebook'";
        try {
            connection = getConnection();
            ps = connection.prepareStatement(sqlCommand);
            ps.setString(1, sub);
            rs = ps.executeQuery();
            User currentUser = null;
            while (rs.next()) {
                LocalDateTime create = null;
                LocalDateTime update = null;
                LocalDateTime lock = null;

                if (rs.getDate("created_at") != null) {
                    create = rs.getTimestamp("created_at").toLocalDateTime();
                }
                if (rs.getDate("updated_at") != null) {
                    update = rs.getTimestamp("updated_at").toLocalDateTime();
                }
                if (rs.getDate("locked_until") != null) {
                    lock = rs.getTimestamp("locked_until").toLocalDateTime();
                }

                currentUser = new User(rs.getInt("user_id"),
                        rs.getString("full_name"),
                        rs.getInt("gender"),
                        rs.getString("email"),
                        rs.getString("mobile"),
                        "", rs.getInt("role"), rs.getString("avatar"),
                        create,
                        update,
                        rs.getInt("status"),
                        lock);
            }

            return currentUser;
        } catch (SQLException ex) {
            System.out.println("Errors occur in get user DAO: " + ex.getMessage());
        } finally {
            if (connection != null) {
            }
        }
        return null;
    }

    public User GetUserByEmail(String Email) throws SQLException {
        PreparedStatement ps = null;
        ResultSet rs = null;
        Connection connection = null;

        String sqlCommand = "SELECT [user_id]\n"
                + "      ,[full_name]\n"
                + "      ,[gender]\n"
                + "      ,[email]\n"
                + "      ,[mobile]\n"
                + "      ,[password_hash]\n"
                + "      ,[role]\n"
                + "      ,[avatar]\n"
                + "      ,[created_at]\n"
                + "      ,[updated_at]\n"
                + "      ,[status]\n"
                + "      ,[locked_until]"
                + "FROM [User] WHERE email = ? ";
        try {
            connection = getConnection();
            ps = connection.prepareStatement(sqlCommand);
            ps.setString(1, Email);
            rs = ps.executeQuery();
            User currentUser = null;
            while (rs.next()) {
                LocalDateTime create = null;
                LocalDateTime update = null;
                LocalDateTime lock = null;

                if (rs.getDate("created_at") != null) {
                    create = rs.getTimestamp("created_at").toLocalDateTime();
                }

                if (rs.getDate("updated_at") != null) {
                    update = rs.getTimestamp("updated_at").toLocalDateTime();
                }
                if (rs.getDate("locked_until") != null) {
                    lock = rs.getTimestamp("locked_until").toLocalDateTime();
                }
                currentUser = new User(rs.getInt("user_id"),
                        rs.getString("full_name"),
                        rs.getInt("gender"),
                        rs.getString("email"),
                        rs.getString("mobile"),
                        rs.getString("password_hash"), rs.getInt("role"), rs.getString("avatar"),
                        create,
                        update,
                        rs.getInt("status"),
                        lock);
            }
            return currentUser;
        } catch (SQLException ex) {
            System.out.println("Errors occur in get user DAO: " + ex.getMessage());
        } finally {
            if (connection != null) {
            }
        }
        return null;
    }

    public void UpdatePassword(String email, String password_hash) {
        PreparedStatement ps = null;
        ResultSet rs = null;
        Connection connection = null;

        String sqlCommand = "UPDATE [dbo].[User]\n"
                + "          SET [password_hash] = ? \n"
                + "         WHERE email = ? ";
        try {
            connection = getConnection();
            ps = connection.prepareStatement(sqlCommand);
            ps.setString(1, password_hash);
            ps.setString(2, email);
            ps.executeUpdate();

        } catch (SQLException ex) {
            System.out.println("Errors occur in get user DAO: " + ex.getMessage());
        } finally {

        }
    }

    public void UpdateLockTemp(String email, LocalDateTime time) {
        PreparedStatement ps = null;
        ResultSet rs = null;
        Connection connection = null;

        String sqlCommand = "UPDATE [dbo].[User]\n"
                + "          SET [locked_until] = ? \n"
                + "         WHERE email = ? ";
        try {
            connection = getConnection();
            ps = connection.prepareStatement(sqlCommand);
            ps.setTimestamp(1, Timestamp.valueOf(time));
            ps.setString(2, email);
            ps.executeUpdate();

        } catch (SQLException ex) {
            System.out.println("Errors occur in get user DAO: " + ex.getMessage());
        } finally {

        }
    }

    public void RegisterUser(String fullname, int gender, String email, String mobile, String password_hash) {
        PreparedStatement ps = null;
        ResultSet rs = null;
        Connection connection = null;

        String sqlCommand = "INSERT INTO [dbo].[User]\n"
                + "          ([full_name], \n"
                + "          [gender], \n"
                + "          [email], \n"
                + "          [mobile], \n"
                + "          [password_hash], \n"
                + "          [role], \n"
                + "          [avatar], \n"
                + "          [created_at], \n"
                + "          [updated_at], \n"
                + "          [status], \n"
                + "          [locked_until]) \n"
                + "          VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try {
            connection = getConnection();
            ps = connection.prepareStatement(sqlCommand);
            ps.setString(1, fullname);
            ps.setInt(2, gender);
            ps.setString(3, email);
            ps.setString(4, mobile);
            ps.setString(5, password_hash);
            ps.setInt(6, 1);
            ps.setString(7, "Assets/Images/User/default-avatar.jpg");
            ps.setTimestamp(8, new Timestamp(System.currentTimeMillis()));
            ps.setTimestamp(9, new Timestamp(System.currentTimeMillis()));
            ps.setInt(10, 1);
            ps.setTimestamp(11, null);

            ps.executeUpdate();

        } catch (SQLException ex) {
            System.out.println("Errors occur in get user DAO: " + ex.getMessage());
        } finally {

        }
    }

    public void UpdateUserProfile(String fullname, int gender, String email, String mobile, String avatarPath) {
        PreparedStatement ps = null;
        ResultSet rs = null;
        Connection connection = null;

        String sqlCommand = "UPDATE [User] \n"
                + "          SET [full_name] = ?, \n"
                + "              [gender] = ?, \n"
                + "              [mobile] = ?, \n"
                + "              [avatar] = ? \n"
                + "          WHERE [email] = ?";
        try {
            connection = getConnection();
            ps = connection.prepareStatement(sqlCommand);
            ps.setString(1, fullname);
            ps.setInt(2, gender);
            ps.setString(3, mobile);
            ps.setString(4, avatarPath);
            ps.setString(5, email);

            ps.executeUpdate();
        } catch (SQLException ex) {
            System.out.println("Errors occur in update user: " + ex.getMessage());
        } finally {
        }
    }

    public void RegisterTempoUser(String fullname, int gender, String email, String mobile, String password_hash) {
        PreparedStatement ps = null;
        ResultSet rs = null;
        Connection connection = null;

        String sqlCommand = "INSERT INTO [dbo].[User]\n"
                + "          ([full_name], \n"
                + "          [gender], \n"
                + "          [email], \n"
                + "          [mobile], \n"
                + "          [password_hash], \n"
                + "          [role], \n"
                + "          [avatar], \n"
                + "          [created_at], \n"
                + "          [updated_at], \n"
                + "          [status], \n"
                + "          [locked_until]) \n"
                + "          VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try {
            connection = getConnection();
            ps = connection.prepareStatement(sqlCommand);
            ps.setString(1, fullname);
            ps.setInt(2, gender);
            ps.setString(3, email);
            ps.setString(4, mobile);
            ps.setString(5, password_hash);
            ps.setInt(6, 1);
            ps.setString(7, "Assets/Images/User/default-avatar.jpg");
            ps.setTimestamp(8, new Timestamp(System.currentTimeMillis()));
            ps.setTimestamp(9, null);
            ps.setInt(10, 0);
            ps.setTimestamp(11, null);

            ps.executeUpdate();

        } catch (SQLException ex) {
            System.out.println("Errors occur in get user DAO: " + ex.getMessage());
        } finally {

        }
    }

    public void UpdateStatusInactiveToActive(String email) {
        PreparedStatement ps = null;
        ResultSet rs = null;
        Connection connection = null;

        String sqlCommand = "UPDATE [User] \n"
                + "          SET [status] = ?, \n"
                + "              [updated_at] = ? \n"
                + "              [created_at] = ? \n"
                + "          WHERE [email] = ?";
        try {
            connection = getConnection();
            ps = connection.prepareStatement(sqlCommand);
            ps.setInt(1, 1);
            ps.setTimestamp(2, Timestamp.valueOf(LocalDateTime.now()));
            ps.setTimestamp(3, Timestamp.valueOf(LocalDateTime.now()));
            ps.setString(4, email);

            ps.executeUpdate();
        } catch (SQLException ex) {
            System.out.println("Errors occur in update user status: " + ex.getMessage());
        } finally {
        }
    }
    // Method to get the author name based on their ID

    public String getAuthorName(int authorId) {
        String query = "SELECT full_name FROM [User] WHERE user_id = ?";
        try (Connection conn = getConnection(); PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setInt(1, authorId);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getString("full_name");
            } else {
                System.out.println("No user found with ID: " + authorId);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
    
    public int GetNewlyRegisteredCustomerCount(){
        int newlyRegisteredCustomer = 0;
        String query = "SELECT COUNT(user_id) AS NewlyRegistered"
                + "     FROM [User]"
                + "     WHERE role = 1 AND CONVERT(DATE, created_at) = CONVERT(DATE, GETDATE());";
        try (Connection conn = getConnection(); PreparedStatement ps = conn.prepareStatement(query)) {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                 newlyRegisteredCustomer = rs.getInt("NewlyRegistered");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return newlyRegisteredCustomer;
    }
    
    public static void main(String[] args) {
        UserDAO dao = new UserDAO();
//        dao.RegisterUser("Aquafina", 0, "aquafina257@gmail.com", "0868613072", "4e406736058aaf3029fd19e4bc8a22e8");
        System.out.println("newly : " + dao.GetNewlyRegisteredCustomerCount());
    }
    public String getAuthorAvatar(int authorId) {
    String query = "SELECT avatar FROM [User] WHERE user_id = ?";
    try (Connection conn = getConnection(); PreparedStatement ps = conn.prepareStatement(query)) {
        ps.setInt(1, authorId);
        ResultSet rs = ps.executeQuery();
        if (rs.next()) {
            return rs.getString("avatar");
        } else {
            System.out.println("No user found with ID: " + authorId);
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }
    return null;
}
}
