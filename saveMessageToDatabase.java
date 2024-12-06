package realTimeChatApp;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

private void saveMessageToDatabase(boolean isSent, String message) {
    String query = "INSERT INTO chat_messages (is_sent, message) VALUES (?, ?)";
    try (Connection con = DatabaseConnection.getConnection();
         PreparedStatement ps = con.prepareStatement(query)) {
        ps.setBoolean(1, isSent); // TRUE if sent by the client
        ps.setString(2, message); // The message content
        ps.executeUpdate();
    } catch (SQLException e) {
        e.printStackTrace();
    }
}

