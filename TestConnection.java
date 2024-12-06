package realTimeChatApp;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class TestConnection {
    public static void main(String[] args) {
        try {
            // Load the MySQL JDBC driver
            Class.forName("com.mysql.cj.jdbc.Driver");

            // Establish a connection to the database
            String url = "jdbc:mysql://localhost:3306/your_database_name";  // Change "your_database_name"
            String username = "root";  // Use your MySQL username
            String password = "your_password";  // Use your MySQL password

            // Create the connection
            Connection connection = DriverManager.getConnection(url, username, password);

            // Check if the connection is established
            if (connection != null) {
                System.out.println("Connection Successful!");
            } else {
                System.out.println("Connection Failed.");
            }

        } catch (ClassNotFoundException e) {
            // Handle errors for Class.forName
            e.printStackTrace();
        } catch (SQLException e) {
            // Handle errors for JDBC
            e.printStackTrace();
        }
    }
}
