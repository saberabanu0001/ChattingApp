package realTimeChatApp;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

public class Register {
    private JFrame frame;
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JPasswordField confirmPasswordField;

    public static void main(String[] args) {
        new Register();
    }

    public Register() {
        frame = new JFrame("ChatApp - Register");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 200);

        JPanel panel = new JPanel(new GridLayout(3, 2));
        frame.add(panel);

        JLabel usernameLabel = new JLabel("Username:");
        panel.add(usernameLabel);

        usernameField = new JTextField();
        panel.add(usernameField);

        JLabel passwordLabel = new JLabel("Password:");
        panel.add(passwordLabel);

        passwordField = new JPasswordField();
        panel.add(passwordField);

        JLabel confirmPasswordLabel = new JLabel("Confirm Password:");
        panel.add(confirmPasswordLabel);

        confirmPasswordField = new JPasswordField();
        panel.add(confirmPasswordField);

        JButton registerButton = new JButton("Register");
        panel.add(registerButton);

        JButton backButton = new JButton("Back");
        panel.add(backButton);

        registerButton.addActionListener(new RegisterAction());
        backButton.addActionListener(e -> {
            frame.dispose();
            // Add the logic to navigate back, if needed
        });

        frame.setVisible(true);
    }

    private class RegisterAction implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String username = usernameField.getText();
            String password = new String(passwordField.getPassword());
            String confirmPassword = new String(confirmPasswordField.getPassword());

            if (!password.equals(confirmPassword)) {
                JOptionPane.showMessageDialog(frame, "Passwords do not match!");
                return;
            }

            try (Connection con = getConnection()) {
                if (con != null) {
                    createDatabaseIfNotExists(con);
                    String query = "INSERT INTO users (username, password) VALUES (?, ?)";
                    PreparedStatement pst = con.prepareStatement(query);
                    pst.setString(1, username);
                    pst.setString(2, password);
                    pst.executeUpdate();
                    JOptionPane.showMessageDialog(frame, "Registration Successful");
                    frame.dispose();
                    // Add logic to proceed to the next step, if needed
                } else {
                    JOptionPane.showMessageDialog(frame, "Database Connection Failed!");
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(frame, "Registration Failed. Username might already be taken.");
            }
        }

        private Connection getConnection() {
            try {
                Class.forName("com.mysql.cj.jdbc.Driver");
                System.out.println("Connecting to database...");
                return DriverManager.getConnection("jdbc:mysql://localhost:3306/", "root", ""); // Update with your DB credentials
            } catch (ClassNotFoundException | SQLException e) {
                e.printStackTrace();
                return null;
            }
        }

        private void createDatabaseIfNotExists(Connection con) throws SQLException {
            String createDbQuery = "CREATE DATABASE IF NOT EXISTS chatApp";
            String useDbQuery = "USE chatApp";
            String createTableQuery = "CREATE TABLE IF NOT EXISTS users (" +
                                      "id INT AUTO_INCREMENT PRIMARY KEY, " +
                                      "username VARCHAR(50) NOT NULL UNIQUE, " +
                                      "password VARCHAR(255) NOT NULL)";
            try (Statement stmt = con.createStatement()) {
                System.out.println("Creating database if not exists...");
                stmt.executeUpdate(createDbQuery);
                stmt.executeUpdate(useDbQuery);
                stmt.executeUpdate(createTableQuery);
                System.out.println("Database and table check complete.");
            }
        }
    }
}
