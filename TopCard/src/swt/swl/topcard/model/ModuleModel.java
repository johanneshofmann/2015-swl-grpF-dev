package swt.swl.topcard.model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Observable;

import javafx.collections.ObservableList;

public class ModuleModel extends Observable {

	private ObservableList<Module> observableArray;

	public ObservableList<Module> getObservableArray() {
		return this.observableArray;
	}

	public ModuleModel() {

	}

	public void selectModules() {
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

	public Boolean hasModule(String name) {
		String query = "SELECT * FROM Module WHERE Name='" + name + "'";
		ResultSet resultSet = DatabaseHelper.executeQuery(query, name);
		return ResultSetUtil.contains(resultSet, 0, name);
	}

	public void insertModule(String name) {

		int ID = -1;

		try {
			ResultSet result = DatabaseHelper.executeQuery("SELECT MAX(ID) FROM Module");

			if (result.next()) {

				ID = result.getInt(1);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		if (ID == -1) {
			throw new IllegalArgumentException("Did not found maxID from Module.");
		}
		String query = "INSERT INTO Module(ID,Name) VALUES (" + ID + ",'" + name + "'";

		DatabaseHelper.executeUpdate(query, name);

		triggerNotification(name);
	}

	private void triggerNotification(Object message) {
		setChanged();
		notifyObservers(message);
	}

	public void deleteModuleFromDatabase(int ID) {

		try (Connection conn = DriverManager.getConnection("jdbc:mysql://db.swt.wiai.uni-bamberg.de/GroupF", "GroupF",
				"gruppe_f")) {
			Statement stmt = conn.createStatement();

			String sqlInsert = "delete from Module where ID =" + ID;
			stmt.executeUpdate(sqlInsert);

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void deleteModuleFromDatabase(String Name) {

		try (Connection conn = DriverManager.getConnection("jdbc:mysql://db.swt.wiai.uni-bamberg.de/GroupF", "GroupF",
				"gruppe_f")) {
			Statement stmt = conn.createStatement();

			String sqlInsert = "delete from Module where Name =" + Name;
			stmt.executeUpdate(sqlInsert);

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
