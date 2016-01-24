package swt.swl.topcard.model.impl;

import java.util.Observable;

import javafx.beans.InvalidationListener;
import javafx.collections.ObservableList;
import swt.swl.topcard.logic.impl.DatabaseHelperImpl;
import swt.swl.topcard.model.LoginModel;

public class LoginModelImpl extends Observable implements LoginModel {

	public boolean checkDatabase(String loginName) {

		ObservableList<String> users = DatabaseHelperImpl.getNamesFromSource("User");

		for (String userName : users) {

			if (userName.equals(loginName)) {

				System.out.println("LoginName accepted. Access granted");

				return true;
			}
		}
		return false;
	}

	public void insertUserIntoDatabase(String firstName, String lastName, String loginName) {

		String sqlInsert = "INSERT INTO User(FirstName,LastName,LoginName) VALUES ('" + firstName + "', '" + lastName
				+ "', '" + loginName + "');";

		DatabaseHelperImpl.executeUpdate(sqlInsert);

		triggerNotification(loginName);
	}

	// only invoked for testing..
	public void deleteUserFromDatabase(String loginName) {

		DatabaseHelperImpl.deleteXFromDatabaseByName("User", loginName);
	}

	private void triggerNotification(String message) {
		setChanged();
		notifyObservers(message);
	}

	@Override
	public void addListener(InvalidationListener listener) {
		// TODO Auto-generated method stub

	}

	@Override
	public void removeListener(InvalidationListener listener) {
		// TODO Auto-generated method stub

	}

}