package swt.swl.topcard.model;

import java.util.Observable;

import javafx.collections.ObservableList;
import swt.swl.topcard.logic.DatabaseHelper;

public class LoginModel extends Observable {

	public boolean checkDatabase(String loginName) {

		ObservableList<String> users = DatabaseHelper.getNamesFromSource("User");

		for (String userName : users) {

			if (userName.equals(loginName)) {

				System.out.println("LoginName accepted. Access granted");

				return true;
			}
		}
		return false;
	}

	public void insertUserIntoDatabase(String firstName, String lastName, String loginName) {

		int ownerID = DatabaseHelper.getMaxXFromY("ID", "User");

		String sqlInsert = "INSERT INTO User(ID,FirstName,LastName,LoginName) VALUES (" + ownerID + ", '" + firstName
				+ "', '" + lastName + "', '" + loginName + "');";

		DatabaseHelper.executeUpdate(sqlInsert);

		triggerNotification(loginName);
	}

	// only invoked for testing..
	public void deleteUserFromDatabase(String loginName) {

		DatabaseHelper.deleteXFromDatabaseByName("User", loginName);
	}

	private void triggerNotification(String message) {
		setChanged();
		notifyObservers(message);
	}
}