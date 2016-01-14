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

	public boolean hasModule(String name) {
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

	public void insertModule(String moduleName) {

		try (Connection conn = DriverManager.getConnection("jdbc:mysql://db.swt.wiai.uni-bamberg.de/GroupF", "GroupF",
				"gruppe_f")) {

			Statement stmt = conn.createStatement();

			ResultSet resultSet = stmt.executeQuery("SELECT MAX(ID) FROM Module");

			if (resultSet.next()) {
				String query = "INSERT INTO Module(ID,Name) VALUES (" + (resultSet.getInt(1) + 1) + ",'" + moduleName
						+ "')";
				DatabaseHelper.executeUpdate(query);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		triggerNotification(moduleName);
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
