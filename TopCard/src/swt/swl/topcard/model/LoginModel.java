package swt.swl.topcard.model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class LoginModel {
	
	private int ID = 1000;
	
	public LoginModel(){
		try {
			Class.forName("com.mysql.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

	public boolean checkDatabase(String loginName) {


		try (Connection conn = DriverManager.getConnection("jdbc:mysql://db.swt.wiai.uni-bamberg.de/GroupF", "GroupF",
				"gruppe_f")) {
			Statement stmt = conn.createStatement();
			ResultSet result = stmt.executeQuery("Select LoginName from User");
			while (result.next()) {
				String userName = result.getString("LoginName");
				if (userName.equals(loginName)) {
					System.out.println("LoginName accepted. Access granted");
					return true;
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

	public void insertUserIntoDatabase(String firstName, String lastName, String loginName) {

		try (Connection conn = DriverManager.getConnection("jdbc:mysql://db.swt.wiai.uni-bamberg.de/GroupF", "GroupF",
				"gruppe_f")) {
			Statement stmt = conn.createStatement();

			stmt.executeUpdate("insert into User values (" + ID + ", '" + firstName + "', '" + lastName + "', '" + loginName + "');");
			ID++;

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}


}
