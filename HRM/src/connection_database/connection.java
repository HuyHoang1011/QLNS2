package connection_database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class connection {
    private static final String URL = "jdbc:sqlserver://localhost:1555;databaseName=HRM";
    private static final String USER = "sa";
    private static final String PASSWORD = "123";
    private static final String DRIVER = "com.microsoft.sqlserver.jdbc.SQLServerDriver";

    public static Connection getConnection() {
        Connection con = null;
        try {
            // Load the SQL Server JDBC driver
            Class.forName(DRIVER);

            // Establish the connection
            con = DriverManager.getConnection(URL, USER, PASSWORD);
            System.out.println("Connection established successfully.");
        } catch (ClassNotFoundException e) {
            System.err.println("JDBC Driver not found!");
            e.printStackTrace();
        } catch (SQLException e) {
            System.err.println("Failed to establish connection to the database.");
            e.printStackTrace();
        }
        return con;
    }

    public static void closeConnection(Connection con) {
        if (con != null) {
            try {
                con.close();
                System.out.println("Connection closed successfully.");
            } catch (SQLException e) {
                System.err.println("Error while closing the connection.");
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        // Test the connection
        Connection connection = getConnection();
        
    }
}
