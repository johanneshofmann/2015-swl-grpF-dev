package swt.swl.topcard.logic;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class DatabaseHelper {

	private static Boolean isInitialized = false;
	private static String connString = "jdbc:mysql://db.swt.wiai.uni-bamberg.de/GroupF";
	private static String connUser = "GroupF";
	private static String connPassword = "gruppe_f";

	public static void initialize() {
		try {
			Class.forName("com.mysql.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

	public static ResultSet executeQuery(String query) {

		if (!isInitialized)
			initialize();
		try (Connection conn = DriverManager.getConnection(connString, connUser, connPassword)) {
			Statement stmt = conn.createStatement();
			ResultSet result = stmt.executeQuery(query);
			return result;
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}

	public static ResultSet executeQuery(String query, String param) {
		if (!isInitialized)
			initialize();
		try (Connection conn = DriverManager.getConnection(connString, connUser, connPassword)) {

			Statement stmt = conn.createStatement();
			ResultSet result = stmt.executeQuery(query);
			return result;

		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}

	public static int executeUpdate(String query) {
		if (!isInitialized)
			initialize();

		try (Connection conn = DriverManager.getConnection(connString, connUser, connPassword)) {

			Statement stmt = conn.createStatement();
			int result = stmt.executeUpdate(query);
			return result;

		} catch (SQLException e) {
			e.printStackTrace();
			return -1;
		}
	}

	public static String generateModulesString(int rQID) {
		try (Connection conn = DriverManager.getConnection("jdbc:mysql://db.swt.wiai.uni-bamberg.de/GroupF", "GroupF",
				"gruppe_f")) {

			Statement getModules = conn.createStatement();

			ResultSet modulesContainer = getModules.executeQuery(
					"SELECT ModuleID, Module2ID, Module3ID FROM RequirementModule WHERE RequirementID=" + rQID);

			String modules = "";

			if (modulesContainer.next()) {
				if (modulesContainer.getString(1) != null) {
					modules += modulesContainer.getString(1);
				}
				if (modulesContainer.getString(2) != null) {
					modules += "," + modulesContainer.getString(2);
				}
				if (modulesContainer.getString(3) != null) {
					modules += "," + modulesContainer.getString(3);
				}
			}
			return modules;
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}

	public static boolean checkUserAlreadySubscribed(String userName) {

		try (Connection conn = DriverManager.getConnection("jdbc:mysql://db.swt.wiai.uni-bamberg.de/GroupF", "GroupF",
				"gruppe_f")) {

			Statement stmt = conn.createStatement();

			ResultSet modulesContainer = stmt.executeQuery("SELECT Name FROM Team");

			ArrayList<String> teams = new ArrayList<>();

			while (modulesContainer.next()) {

				teams.add(modulesContainer.getString(1));
			}

			// TODO: first implement subsribe functionality, also table
			// Team/User
			return false;

		} catch (SQLException e) {

			e.printStackTrace();
			return false;
		}
	}

	public static void subscribe(String loginName, String teamName) {

		try (Connection conn = DriverManager.getConnection("jdbc:mysql://db.swt.wiai.uni-bamberg.de/GroupF", "GroupF",
				"gruppe_f")) {

			Statement stmt = conn.createStatement();

			stmt.executeUpdate("INSERT INTO TeamUser(TeamID, UserID) VALUES(" + loginNameToID(loginName) + ","
					+ teamNameToID(teamName) + ")");

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public static int loginNameToID(String loginName) {

		try (Connection conn = DriverManager.getConnection("jdbc:mysql://db.swt.wiai.uni-bamberg.de/GroupF", "GroupF",
				"gruppe_f")) {

			Statement stmt = conn.createStatement();

			String sql = "SELECT ID FROM User WHERE LoginName='" + loginName + "'";
			ResultSet set = stmt.executeQuery(sql);
			if (set.next()) {
				return set.getInt(1);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return 0;
	}

	public static int teamNameToID(String teamName) {

		try (Connection conn = DriverManager.getConnection("jdbc:mysql://db.swt.wiai.uni-bamberg.de/GroupF", "GroupF",
				"gruppe_f")) {

			Statement stmt = conn.createStatement();

			String sql = "SELECT ID FROM Team WHERE TeamName='" + teamName + "'";

			ResultSet set = stmt.executeQuery(sql);

			if (set.next()) {
				return set.getInt(1);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return 0;
	}

	public static void exitXFromY(String loginName, String string) {
		try (Connection conn = DriverManager.getConnection("jdbc:mysql://db.swt.wiai.uni-bamberg.de/GroupF", "GroupF",
				"gruppe_f")) {

			Statement stmt = conn.createStatement();

			String sql = "DELETE * FROM UserTeam WHERE UserID=" + loginNameToID(loginName);

			stmt.executeUpdate(sql);

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
