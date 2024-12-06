package realTimeChatApp;

import java.awt.*;
import javax.swing.*;
import javax.swing.border.*;
import java.awt.event.*;
import java.io.*;
import java.net.*;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class Client implements ActionListener {

    private JTextField text1;
    static JPanel a1;
    static Box vertical = Box.createVerticalBox();

    static JFrame fr;

    static DataOutputStream dout;

    public Client() {
    	fr = new JFrame();
        fr.setLayout(null);

        // Panel for top section (with profile and icons)
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

        // Video Icon
        ImageIcon icon7 = new ImageIcon("src/realTimeChatApp/Images/video.png");
        Image icon8 = icon7.getImage().getScaledInstance(25, 22, Image.SCALE_DEFAULT);
        ImageIcon icon9 = new ImageIcon(icon8);
        JLabel video = new JLabel(icon9);
        video.setBounds(300, 20, 30, 30);
        panel.add(video);

        // Phone Icon
        ImageIcon icon10 = new ImageIcon("src/realTimeChatApp/Images/phoneIcon.png");
        Image icon11 = icon10.getImage().getScaledInstance(25, 22, Image.SCALE_DEFAULT);
        ImageIcon icon12 = new ImageIcon(icon11);
        JLabel phone = new JLabel(icon12);
        phone.setBounds(360, 20, 35, 30);
        panel.add(phone);

        // Three-Dot Icon
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

        // Main chat area panel
        a1 = new JPanel();
        a1.setBounds(5, 80, 440, 570);
        a1.setLayout(new BorderLayout());
        fr.add(a1);
        
        JScrollPane scrollPane = new JScrollPane(a1);
        scrollPane.setBounds(5, 80, 440, 570);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        fr.add(scrollPane);

        // Text input field
        text1 = new JTextField();
        text1.setBounds(5, 655, 210, 40);
        text1.setFont(new Font("SAN_SERIF", Font.PLAIN, 16));
        fr.add(text1);

        JButton sendButton = new JButton("Send");
        sendButton.setBounds(240, 655, 80, 40);
        sendButton.setBackground(new Color(7, 94, 84));
        sendButton.setForeground(Color.WHITE);
        sendButton.addActionListener(this);
        sendButton.setFont(new Font("SAN_SERIF", Font.PLAIN, 16));
        fr.add(sendButton);

        JButton fileButton = new JButton("Send File");
        fileButton.setBounds(330, 655, 100, 40);
        fileButton.setBackground(new Color(7, 94, 84));
        fileButton.setForeground(Color.WHITE);
        fileButton.setFont(new Font("SAN_SERIF", Font.PLAIN, 14));
        fr.add(fileButton);

        fileButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser fileChooser = new JFileChooser();
                int result = fileChooser.showOpenDialog(fr);

                if (result == JFileChooser.APPROVE_OPTION) {
                    File file = fileChooser.getSelectedFile();
                    sendFile(file);
                }
            }
        });

        fr.setSize(450, 700);
        fr.setLocation(800, 50);
        fr.setUndecorated(true);
        fr.getContentPane().setBackground(Color.WHITE);
        fr.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent ae) {
        try {
            String message = text1.getText();

            JPanel panel2 = formatLabel(message, true);

            a1.setLayout(new BorderLayout());

            JPanel right = new JPanel(new BorderLayout());
            right.add(panel2, BorderLayout.LINE_END);
            vertical.add(right);
            vertical.add(Box.createVerticalStrut(15));

            a1.add(vertical, BorderLayout.PAGE_START);

            dout.writeUTF("TEXT:" + message);
            text1.setText("");

            fr.repaint();
            fr.invalidate();
            fr.validate();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void sendFile(File file) {
        try {
            dout.writeUTF("FILE:" + file.getName());
            dout.writeLong(file.length());

            FileInputStream fis = new FileInputStream(file);
            byte[] buffer = new byte[4096];
            int bytesRead;

            while ((bytesRead = fis.read(buffer)) != -1) {
                dout.write(buffer, 0, bytesRead);
            }

            fis.close();

            JPanel panel = formatLabel("File Sent: " + file.getName(), true);
            JPanel right = new JPanel(new BorderLayout());
            right.add(panel, BorderLayout.LINE_END);
            vertical.add(right);
            vertical.add(Box.createVerticalStrut(15));

            a1.add(vertical, BorderLayout.PAGE_START);
            fr.validate();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private boolean isImageFile(File file) {
        String fileName = file.getName().toLowerCase();
        return fileName.endsWith(".png") || fileName.endsWith(".jpg") || fileName.endsWith(".jpeg") || fileName.endsWith(".gif");
    }
    
    //format messages for display in the UI

    public static JPanel formatLabel(String message, boolean isSent) {
        JPanel panell = new JPanel();
        panell.setLayout(new BoxLayout(panell, BoxLayout.Y_AXIS));

        JLabel output = new JLabel("<html><p style=\"width:150px;\">" + message + "</p></html>");
        output.setFont(new Font("Tahoma", Font.PLAIN, 15));
        output.setBackground(isSent ? new Color(37, 211, 102) : new Color(200, 200, 255));
        output.setOpaque(true);
        output.setBorder(new EmptyBorder(15, 15, 15, 50));
        panell.setBorder(new EmptyBorder(10, 10, 10, 10));

        JLabel time = new JLabel(new SimpleDateFormat("HH:mm").format(Calendar.getInstance().getTime()));
        time.setFont(new Font("Tahoma", Font.PLAIN, 12));
        panell.add(output);
        panell.add(time);

        return panell;
    }

    public static void main(String[] args) {
        new Client();

        try {
            Socket soc = new Socket("127.0.0.1", 1061);
            DataInputStream din = new DataInputStream(soc.getInputStream());
            dout = new DataOutputStream(soc.getOutputStream());

            while (true) {
                String type = din.readUTF();

                if (type.startsWith("TEXT:")) {
                    String message = type.substring(5);
                    JPanel panel = formatLabel(message, false);
                    JPanel left = new JPanel(new BorderLayout());
                    left.add(panel, BorderLayout.LINE_START);
                    vertical.add(left);
                    vertical.add(Box.createVerticalStrut(15));
                    a1.add(vertical, BorderLayout.PAGE_START);
                    fr.validate();
                } else if (type.startsWith("FILE:")) {
                    String fileName = type.substring(5);
                    long fileSize = din.readLong();

                    File file = new File("Received_" + fileName);
                    FileOutputStream fos = new FileOutputStream(file);

                    byte[] buffer = new byte[4096];
                    int bytesRead;
                    long totalRead = 0;

                    while (totalRead < fileSize) {
                        bytesRead = din.read(buffer, 0, (int) Math.min(buffer.length, fileSize - totalRead));
                        fos.write(buffer, 0, bytesRead);
                        totalRead += bytesRead;
                    }

                    fos.close();

                    if (fileName.toLowerCase().matches(".*(jpg|jpeg|png|gif)$")) {
                        JLabel imageLabel = new JLabel(new ImageIcon(new ImageIcon(file.getAbsolutePath()).getImage().getScaledInstance(100, 100, Image.SCALE_DEFAULT)));
                        JPanel panel = new JPanel(new BorderLayout());
                        panel.add(imageLabel, BorderLayout.CENTER);
                        vertical.add(panel);
                    } else {
                        JPanel panel = formatLabel("File Received: " + fileName, false);
                        JPanel left = new JPanel(new BorderLayout());
                        left.add(panel, BorderLayout.LINE_START);
                        vertical.add(left);
                    }

                    a1.add(vertical, BorderLayout.PAGE_START);
                    fr.validate();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
