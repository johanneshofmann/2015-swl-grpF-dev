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

	//
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

	public static void deleteRqFromDatabase(String title) {
		try (Connection conn = DriverManager.getConnection("jdbc:mysql://db.swt.wiai.uni-bamberg.de/GroupF", "GroupF",
				"gruppe_f")) {

			Statement stmt$0 = conn.createStatement();
			Statement stmt$1 = conn.createStatement();

			ResultSet rqID = stmt$0.executeQuery("SELECT Requirement FROM Requirement WHERE Title='" + title + "'");
			int rqCardID = 0;
			while (rqID.next()) {
				rqCardID = rqID.getInt(1);
				stmt$1.executeUpdate("DELETE FROM Requirement WHERE Requirement= " + rqCardID);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public static String generateModulesString(int rQID) {

		try (Connection conn = DriverManager.getConnection("jdbc:mysql://db.swt.wiai.uni-bamberg.de/GroupF", "GroupF",
				"gruppe_f")) {

			Statement getModules = conn.createStatement();

			ResultSet modulesContainer = getModules
					.executeQuery("SELECT ModuleID FROM RequirementModule WHERE RequirementID=" + rQID);

			String modules = "";

			if (modulesContainer.next()) {
				modules += modulesContainer.getString(1);
			} else if (modulesContainer.next()) {
				modules += "," + modulesContainer.getString(1);
			} else if (modulesContainer.next()) {
				modules += "," + modulesContainer.getString(1);
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

			int userID = loginNameToID(userName);

			ResultSet teamsContainer = stmt.executeQuery("SELECT TeamID FROM TeamUser WHERE UserID=" + userID);

			if (teamsContainer.next()) {
				return true;
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
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
		return -1;
	}

	public static String IDToLoginName(int ownerID) {

		String ownerName = null;

		try (Connection conn = DriverManager.getConnection("jdbc:mysql://db.swt.wiai.uni-bamberg.de/GroupF", "GroupF",
				"gruppe_f")) {

			Statement stmt = conn.createStatement();

			String getOwnerNameQuery = "SELECT LoginName FROM User WHERE ID=" + ownerID;

			ResultSet ownerNameContainer = stmt.executeQuery(getOwnerNameQuery);

			if (ownerNameContainer.next()) {
				ownerName = ownerNameContainer.getString(1);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return ownerName;
	}

	public static int teamNameToID(String teamName) {

		try (Connection conn = DriverManager.getConnection("jdbc:mysql://db.swt.wiai.uni-bamberg.de/GroupF", "GroupF",
				"gruppe_f")) {

			Statement stmt = conn.createStatement();

			String sql = "SELECT ID FROM Team WHERE Name='" + teamName + "'";

			ResultSet set = stmt.executeQuery(sql);

			if (set.next()) {
				return set.getInt(1);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return -1;
	}

	public static String IDToTeamName(int teamID) {

		String teamName = null;

		try (Connection conn = DriverManager.getConnection("jdbc:mysql://db.swt.wiai.uni-bamberg.de/GroupF", "GroupF",
				"gruppe_f")) {

			Statement stmt = conn.createStatement();

			String getTeamNameQuery = "SELECT Name FROM Team WHERE ID=" + teamID;

			ResultSet teamNameContainer = stmt.executeQuery(getTeamNameQuery);

			if (teamNameContainer.next()) {
				teamName = teamNameContainer.getString(1);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return teamName;
	}

	public static void exitXFromY(String loginName, String team) {
		try (Connection conn = DriverManager.getConnection("jdbc:mysql://db.swt.wiai.uni-bamberg.de/GroupF", "GroupF",
				"gruppe_f")) {

			Statement stmt = conn.createStatement();

			String sql = "DELETE FROM TeamUser WHERE UserID=" + loginNameToID(loginName) + " AND TeamID="
					+ teamNameToID(team);

			stmt.executeUpdate(sql);

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public static ArrayList<String> getTeamsUserIsSubscribed(String userName) {

		ArrayList<String> teams = new ArrayList<>();

		try (Connection conn = DriverManager.getConnection("jdbc:mysql://db.swt.wiai.uni-bamberg.de/GroupF", "GroupF",
				"gruppe_f")) {

			Statement stmt = conn.createStatement();

			int userID = loginNameToID(userName);

			ResultSet teamsContainer = stmt.executeQuery("SELECT TeamID FROM TeamUser WHERE UserID=" + userID);

			if (teamsContainer.next()) {
				int teamID = teamsContainer.getInt(1);
				teams.add(IDToTeamName(teamID));
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return teams;
	}

	public static void exitUserFromAllTeams(String loginName) {
		try (Connection conn = DriverManager.getConnection("jdbc:mysql://db.swt.wiai.uni-bamberg.de/GroupF", "GroupF",
				"gruppe_f")) {

			Statement stmt = conn.createStatement();

			String sql = "DELETE FROM TeamUser WHERE UserID=" + loginNameToID(loginName);

			stmt.executeUpdate(sql);

		} catch (SQLException e) {
			e.printStackTrace();
		}

	}
}
