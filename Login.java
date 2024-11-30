package realTimeChatApp;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Login extends JFrame implements ActionListener {

    JTextField usernameField;
    JPasswordField passwordField;

    Login() {
        setTitle("User Login");
        setSize(300, 200);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel();
        panel.setLayout(null);

        JLabel usernameLabel = new JLabel("Username:");
        usernameLabel.setBounds(10, 20, 80, 25);
        panel.add(usernameLabel);

        usernameField = new JTextField(20);
        usernameField.setBounds(100, 20, 165, 25);
        panel.add(usernameField);

        JLabel passwordLabel = new JLabel("Password:");
        passwordLabel.setBounds(10, 50, 80, 25);
        panel.add(passwordLabel);

        passwordField = new JPasswordField(20);
        passwordField.setBounds(100, 50, 165, 25);
        panel.add(passwordField);

        JButton loginButton = new JButton("Login");
        loginButton.setBounds(10, 80, 150, 25);
        loginButton.addActionListener(this);
        panel.add(loginButton);

        add(panel);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String username = usernameField.getText();
        String password = new String(passwordField.getPassword());
        if (DatabaseConnection.validateUser(username, password)) {
            JOptionPane.showMessageDialog(this, "Login Successful!");
            dispose(); // Close the login window
            // Open the chat application window
            new Client().fr.setVisible(true);
        } else {
            JOptionPane.showMessageDialog(this, "Invalid Username or Password!");
        }
    }

    public static void main(String[] args) {
        new Login().setVisible(true);
    }
}