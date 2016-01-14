package swt.swl.topcard.model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Observable;

import javafx.collections.ObservableList;
import swt.swl.topcard.logic.DatabaseHelper;
import swt.swl.topcard.logic.Module;

public class TeamModel extends Observable {

	private ObservableList<Module> observableArray;

	public ObservableList<Module> getObservableArray() {
		return this.observableArray;
	}

	public TeamModel() {
	}

	public void selectTeams() {
		// Clear observable array
		this.observableArray.clear();

		String query = "SELECT * FROM Module;";

		ResultSet resultSet = DatabaseHelper.executeQuery(query);
		try {
			while (resultSet.next()) {
				Module module = new Module(resultSet.getInt(1), resultSet.getString(1));
				this.observableArray.add(module);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public boolean hasTeam(String name) {
		try (Connection conn = DriverManager.getConnection("jdbc:mysql://db.swt.wiai.uni-bamberg.de/GroupF", "GroupF",
				"gruppe_f")) {

			Statement stmt = conn.createStatement();

			String query = "SELECT Name FROM Module WHERE Name='" + name + "'";
			ResultSet resultSet = stmt.executeQuery(query);

			if (resultSet.next()) {
				return (name.equals(resultSet.getString(1)) ? true : false);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

	public void insertTeam(String teamName) {

		try (Connection conn = DriverManager.getConnection("jdbc:mysql://db.swt.wiai.uni-bamberg.de/GroupF", "GroupF",
				"gruppe_f")) {

			Statement stmt = conn.createStatement();

			ResultSet resultSet = stmt.executeQuery("SELECT MAX(ID) FROM Team");

			if (resultSet.next()) {
				String query = "INSERT INTO Team(ID,Name) VALUES (" + (resultSet.getInt(1) + 1) + ",'" + teamName
						+ "')";
				DatabaseHelper.executeUpdate(query);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		triggerNotification(teamName);
	}

	private void triggerNotification(Object message) {
		setChanged();
		notifyObservers(message);
	}

	public void deleteTeamFromDatabase(int ID) {

		try (Connection conn = DriverManager.getConnection("jdbc:mysql://db.swt.wiai.uni-bamberg.de/GroupF", "GroupF",
				"gruppe_f")) {
			Statement stmt = conn.createStatement();

			String sqlInsert = "DELETE FROM Team WHERE ID =" + ID;

			stmt.executeUpdate(sqlInsert);

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void deleteModuleFromDatabase(String Name) {

		try (Connection conn = DriverManager.getConnection("jdbc:mysql://db.swt.wiai.uni-bamberg.de/GroupF", "GroupF",
				"gruppe_f")) {
			Statement stmt = conn.createStatement();

			String sqlInsert = "DELETE FROM Team WHERE Name ='" + Name + "'";

			stmt.executeUpdate(sqlInsert);

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
