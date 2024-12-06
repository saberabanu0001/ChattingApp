package realTimeChatApp;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

public class Jdbc {

	public static void main(String[] args) {
		{
			try (Connection con = DatabaseConnection.getConnection()) {
				Statement stmt = con.createStatement();
				ResultSet rs = stmt.executeQuery("SELECT * FROM users");
				while (rs.next()) {
					System.out.println("ID: " + rs.getInt("id"));
					System.out.println("Username: " + rs.getString("username"));
					System.out.println("Password: " + rs.getString("password"));
					System.out.println("-----------------------");
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

	}

}
