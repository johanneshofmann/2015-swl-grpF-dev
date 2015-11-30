package swt.swl.topcard.model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.util.Observable;

public class LoginModel extends Observable {

	public LoginModel() {
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

			String sqlInsert = "insert into User(FirstName,LastName,LoginName,CreatedAt) values (" + ", '" + firstName
					+ "', '" + lastName + "', '" + loginName + "', '" + LocalDateTime.now() + "');";
			stmt.executeUpdate(sqlInsert);
			triggerNotification(loginName);

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	// only invoked for testing..
	public void deleteUserFromDatabase(String loginName){

		try (Connection conn = DriverManager.getConnection("jdbc:mysql://db.swt.wiai.uni-bamberg.de/GroupF", "GroupF",
				"gruppe_f")) {
			Statement stmt = conn.createStatement();

			String sqlInsert = "delete from User where LoginName ='" + loginName + "'";
			stmt.executeUpdate(sqlInsert);

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	private void triggerNotification(String message) {
		setChanged();
		notifyObservers(message);
	}
}