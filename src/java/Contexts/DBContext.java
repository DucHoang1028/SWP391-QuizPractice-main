package Contexts;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DBContext {

    protected Connection connection;

    public DBContext() {
        try {
            // Edit URL , username, password to authenticate with your MS SQL Server
            String url = "jdbc:sqlserver://localhost:1433;databaseName=QPS-SWP391";
            String username = "sa";
            String password = "123456";
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            connection = DriverManager.getConnection(url, username, password);
        } catch (ClassNotFoundException | SQLException ex) {
            System.out.println(ex);
        }
    }

    public Connection getConnection() throws SQLException {
        // Handle if connection over time and close then reopen it
        if (connection.isClosed()) {
            try {
                String url = "jdbc:sqlserver://localhost:1433;databaseName=QPS-SWP391";
                String username = "sa";
                String password = "123456";
                connection = DriverManager.getConnection(url, username, password);;
            } catch (SQLException ex) {
                Logger.getLogger(DBContext.class.getName()).log(Level.SEVERE, null, ex);
            }


        }
        return connection;
    }

}
