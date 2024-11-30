package realTimeChatApp;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.ResultSet;
import java.sql.Statement;

public class DatabaseConnection {
    public static Connection getConnection() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            return DriverManager.getConnection("jdbc:mysql://localhost:3306/chatApp", "root", "your_password"); // Update with your DB credentials
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static void checkDatabaseAndTable() {
        try (Connection con = getConnection(); Statement stmt = con.createStatement()) {
            // Check if database exists
            ResultSet rs = stmt.executeQuery("SHOW DATABASES LIKE 'chatApp';");
            if (rs.next()) {
                System.out.println("Database 'chatApp' exists.");
                
                // Check if table exists
                rs = stmt.executeQuery("SHOW TABLES LIKE 'users';");
                if (rs.next()) {
                    System.out.println("Table 'users' exists.");
                } else {
                    System.out.println("Table 'users' does not exist.");
                }
            } else {
                System.out.println("Database 'chatApp' does not exist.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
