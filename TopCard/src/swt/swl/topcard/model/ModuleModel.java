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

		ObservableList<String> modules = DatabaseHelper.getNameFrom("Module");

		// Clear observable array
		this.observableArray.clear();

		for (String moduleName : modules) {

			Module module = new Module(DatabaseHelper.XNameToID("Module", moduleName), moduleName);

			this.observableArray.add(module);
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

		String query = "INSERT INTO Module(ID,Name) VALUES (" + (DatabaseHelper.getMaxXFromY("ID", "Module") + 1) + ",'"
				+ moduleName + "')";

		DatabaseHelper.executeUpdate(query);

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

			String sqlInsert = "DELETE FROM Module WHERE ID =" + ID;
			stmt.executeUpdate(sqlInsert);

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void deleteModuleFromDatabase(String Name) {

		try (Connection conn = DriverManager.getConnection("jdbc:mysql://db.swt.wiai.uni-bamberg.de/GroupF", "GroupF",
				"gruppe_f")) {
			Statement stmt = conn.createStatement();

			String sqlInsert = "DELETE FROM Module WHERE Name =" + Name;
			stmt.executeUpdate(sqlInsert);

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
