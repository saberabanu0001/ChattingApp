package realTimeChatApp;

import javax.swing.*;
import java.awt.event.*;
import java.sql.*;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Login {

    static JFrame frame = new JFrame("Login"); // Make frame static

    public static void main(String[] args) {
        frame.setSize(300, 150);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel panel = new JPanel();
        frame.add(panel);
        placeComponents(panel);

        frame.setVisible(true);
    }

    private static void placeComponents(JPanel panel) {
        panel.setLayout(null);

        JLabel userLabel = new JLabel("Username:");
        userLabel.setBounds(10, 20, 80, 25);
        panel.add(userLabel);

        JTextField userText = new JTextField(20);
        userText.setBounds(100, 20, 165, 25);
        panel.add(userText);

        JLabel passwordLabel = new JLabel("Password:");
        passwordLabel.setBounds(10, 50, 80, 25);
        panel.add(passwordLabel);

        JPasswordField passwordText = new JPasswordField(20);
        passwordText.setBounds(100, 50, 165, 25);
        panel.add(passwordText);

        JButton loginButton = new JButton("Login");
        loginButton.setBounds(10, 80, 80, 25);
        panel.add(loginButton);

        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String username = userText.getText();
                String password = new String(passwordText.getPassword());

                // Database connection
                try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/ChatApp", "root", "your_password")) {
                    String query = "SELECT password_hash FROM Users WHERE username = ?";
                    PreparedStatement preparedStmt = connection.prepareStatement(query);
                    preparedStmt.setString(1, username);
                    ResultSet resultSet = preparedStmt.executeQuery();

                    if (resultSet.next()) {
                        String storedHash = resultSet.getString("password_hash");
                        if (storedHash.equals(hashPassword(password))) {
                            JOptionPane.showMessageDialog(null, "Login Successful!");
                            frame.dispose(); // Close the login window
                            new Client(); // Launch the chat client
                        } else {
                            JOptionPane.showMessageDialog(null, "Invalid username or password!");
                        }
                    } else {
                        JOptionPane.showMessageDialog(null, "Invalid username or password!");
                    }
                } catch (SQLException ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(null, "Login Failed!");
                }
            }
        });
    }

    // Method to hash the password
    public static String hashPassword(String password) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] hash = md.digest(password.getBytes());
            StringBuilder hexString = new StringBuilder();
            for (byte b : hash) {
                hexString.append(String.format("%02x", b));
            }
            return hexString.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return null;
        }
    }
}
