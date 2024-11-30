package realTimeChatApp;

import java.awt.*;
import javax.swing.*;
import javax.swing.border.*;
import java.awt.event.*;
import java.util.*;
import java.text.*;
import java.net.*;
import java.io.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Client implements ActionListener {

    private JTextField text1;
    static JPanel a1;
    static Box vertical = Box.createVerticalBox();
    
    static JFrame fr = new JFrame();
    
    static DataOutputStream dout;
    static String userName = "Banu"; // Set the user name

    Client() {
        fr.setLayout(null);

        JPanel panel = new JPanel();
        panel.setBackground(new Color(7, 94, 84));
        panel.setBounds(0, 0, 450, 80);
        panel.setLayout(null);
        fr.add(panel);

        // Arrow Icon
        ImageIcon icon = new ImageIcon("src/realTimeChatApp/Images/arrow2.png");
        Image icon2 = icon.getImage().getScaledInstance(30, 30, Image.SCALE_DEFAULT);
        ImageIcon icon3 = new ImageIcon(icon2);
        JLabel label1 = new JLabel(icon3);
        label1.setBounds(5, 20, 30, 30);
        panel.add(label1);

        label1.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent ae) {
                fr.dispose(); // Properly close the application
            }
        });

        // Profile Image
        ImageIcon icon4 = new ImageIcon("src/realTimeChatApp/Images/Sabera1.png");
        Image icon5 = icon4.getImage().getScaledInstance(50, 50, Image.SCALE_DEFAULT);
        ImageIcon icon6 = new ImageIcon(icon5);
        JLabel profile = new JLabel(icon6);
        profile.setBounds(45, 10, 50, 50);
        panel.add(profile);

        // Video Image
        ImageIcon icon7 = new ImageIcon("src/realTimeChatApp/Images/video.png");
        Image icon8 = icon7.getImage().getScaledInstance(25, 22, Image.SCALE_DEFAULT);
        ImageIcon icon9 = new ImageIcon(icon8);
        JLabel video = new JLabel(icon9);
        video.setBounds(300, 20, 30, 30);
        panel.add(video);

        // Phone Image
        ImageIcon icon10 = new ImageIcon("src/realTimeChatApp/Images/phoneIcon.png");
        Image icon11 = icon10.getImage().getScaledInstance(25, 22, Image.SCALE_DEFAULT);
        ImageIcon icon12 = new ImageIcon(icon11);
        JLabel phone = new JLabel(icon12);
        phone.setBounds(360, 20, 35, 30);
        panel.add(phone);

        // Three-Dot Image
        ImageIcon icon13 = new ImageIcon("src/realTimeChatApp/Images/threeDot.png");
        Image icon14 = icon13.getImage().getScaledInstance(25, 25, Image.SCALE_DEFAULT);
        ImageIcon icon15 = new ImageIcon(icon14);
        JLabel threedot = new JLabel(icon15);
        threedot.setBounds(410, 23, 10, 25);
        panel.add(threedot);

        JLabel name = new JLabel("Banu");
        name.setBounds(100, 25, 100, 18);
        name.setForeground(Color.WHITE);
        name.setFont(new Font("SAN_SERIF", Font.BOLD, 18));
        panel.add(name);

        // Activity Status
        JLabel active = new JLabel("Active Now");
        active.setBounds(100, 40, 100, 18);
        active.setForeground(Color.WHITE);
        active.setFont(new Font("SAN_SERIF", Font.PLAIN, 8));
        panel.add(active);

        a1 = new JPanel();
        a1.setBounds(5, 80, 440, 570);
        a1.setLayout(new BorderLayout());
        fr.add(a1);

        // Adding Text Area
        text1 = new JTextField();
        text1.setBounds(5, 655, 310, 40);
        text1.setFont(new Font("SAN_SERIF", Font.PLAIN, 16));
        fr.add(text1);

        JButton sendButton = new JButton("Send");
        sendButton.setBounds(320, 655, 123, 40);
        sendButton.setBackground(new Color(7, 94, 84));
        sendButton.setForeground(Color.WHITE);
        sendButton.addActionListener(this); // Action for the send button
        sendButton.setFont(new Font("SAN_SERIF", Font.PLAIN, 16));
        fr.add(sendButton);

        // Body Background
        fr.setSize(450, 700);
        fr.setLocation(800, 50);
        fr.setUndecorated(true); // Removing the JFrame border
        fr.getContentPane().setBackground(Color.WHITE);
        fr.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent ae) {
        try {
            String message = text1.getText(); 
            
            JPanel panel2 = formatLabel(message, true); // Sent message
            
            a1.setLayout(new BorderLayout());
            
            JPanel right = new JPanel(new BorderLayout());
            right.add(panel2, BorderLayout.LINE_END);
            vertical.add(right);
            vertical.add(Box.createVerticalStrut(15));
            
            a1.add(vertical, BorderLayout.PAGE_START);
            
            dout.writeUTF(message);
            
            
            
            text1.setText("");  // Clear the text field after sending the text
            
            fr.repaint();
            fr.invalidate();
            fr.validate();
            
            
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    public static JPanel formatLabel(String message, boolean isSent) {
        JPanel panell = new JPanel();
        panell.setLayout(new BoxLayout(panell, BoxLayout.Y_AXIS));
        
        JLabel output = new JLabel("<html><p style=\"width:150px;\">" + message + "</p></html>");
        output.setFont(new Font("Tahoma", Font.PLAIN, 15));
        output.setBackground(isSent ? new Color(37, 211, 102) : new Color(200, 200, 255)); // Different colors for sent and received
        output.setOpaque(true);
        output.setBorder(new EmptyBorder(15, 15, 15, 50));
        panell.setBorder(new EmptyBorder(10, 10, 10, 10));
        
        panell.add(output);
        
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
        
        JLabel time = new JLabel(sdf.format(calendar.getTime()));
        time.setFont(new Font("Tahoma", Font.PLAIN, 12));
        
        panell.add(time); // Add the time label to the panel
        
        return panell;
    }

    public static void addMessage(String message, boolean isSent) {
        JPanel panel = formatLabel(message, isSent);

        JPanel left = new JPanel(new BorderLayout());
        left.add(panel, BorderLayout.LINE_START);
        vertical.add(left);
        vertical.add(Box.createVerticalStrut(15)); // Add space between messages
        a1.add(vertical, BorderLayout.PAGE_START);

        fr.validate();
        fr.repaint();
    }

    public static void main(String[] args) {
         new Client();
         
         try {
             Socket soc = new Socket("172.30.1.77", 1015);
             DataInputStream din = new DataInputStream(soc.getInputStream());
             dout = new DataOutputStream(soc.getOutputStream());
             
             while(true) {
                 a1.setLayout(new BorderLayout());
                 String msg = din.readUTF();
                 addMessage(msg, false); // Received message
             }
             
         } catch(Exception e) {
             e.printStackTrace();
         }
    }
}