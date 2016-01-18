package swt.swl.topcard.logic;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

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
			return -404;
		}
	}

	public static int getMaxRequirementID() {

		if (!isInitialized)
			initialize();

		try (Connection conn = DriverManager.getConnection(connString, connUser, connPassword)) {

			Statement stmt = conn.createStatement();

			ResultSet maxRqIDSet = stmt.executeQuery("SELECT MAX(Requirement) FROM Requirement");

			if (maxRqIDSet.next()) {
				return maxRqIDSet.getInt(1);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return -404;
	}

	public static void deleteRqFromDatabase(String title) {

		if (!isInitialized)
			initialize();

		try (Connection conn = DriverManager.getConnection(connString, connUser, connPassword)) {

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

	public static String getModulesByRequirementID(int rQID) {

		if (!isInitialized)
			initialize();

		ArrayList<Integer> modules = new ArrayList<>();

		try (Connection conn = DriverManager.getConnection(connString, connUser, connPassword)) {

			Statement getModules = conn.createStatement();

			ResultSet modulesContainer = getModules
					.executeQuery("SELECT ModuleID FROM RequirementModule WHERE RequirementID=" + rQID);

			while (modulesContainer.next()) {

				modules.add(modulesContainer.getInt(1));
			}
			String modulesAsString = "";

			int counter = 0;
			for (Integer i : modules) {

				if (counter == 0) {
					modulesAsString += i;
					counter++;
				} else {
					modulesAsString += ", " + i;
				}
			}
			return modulesAsString;

		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}

	public static ObservableList<String> getModules() {

		if (!isInitialized)
			initialize();

		try (Connection conn = DriverManager.getConnection("jdbc:mysql://db.swt.wiai.uni-bamberg.de/GroupF", "GroupF",
				"gruppe_f")) {

			Statement stmt = conn.createStatement();

			String query = "SELECT Name FROM Module";

			ResultSet modulesSet = stmt.executeQuery(query);
			ObservableList<String> modules = FXCollections.observableArrayList();

			while (modulesSet.next()) {
				modules.add(modulesSet.getString(1));
			}
			return modules;

		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}

	}

	public static boolean checkUserAlreadySubscribed(String userName) {

		if (!isInitialized)
			initialize();

		try (Connection conn = DriverManager.getConnection(connString, connUser, connPassword)) {

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

		if (!isInitialized)
			initialize();

		try (Connection conn = DriverManager.getConnection(connString, connUser, connPassword)) {

			Statement stmt = conn.createStatement();

			stmt.executeUpdate("INSERT INTO TeamUser(TeamID, UserID) VALUES(" + loginNameToID(loginName) + ","
					+ teamNameToID(teamName) + ")");

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public static int loginNameToID(String loginName) {

		if (!isInitialized)
			initialize();

		try (Connection conn = DriverManager.getConnection(connString, connUser, connPassword)) {

			Statement stmt = conn.createStatement();

			String sql = "SELECT ID FROM User WHERE LoginName='" + loginName + "'";
			ResultSet set = stmt.executeQuery(sql);
			if (set.next()) {
				return set.getInt(1);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return -404;
	}

	public static String IDToLoginName(int ownerID) {

		if (!isInitialized)
			initialize();

		String ownerName = null;

		try (Connection conn = DriverManager.getConnection(connString, connUser, connPassword)) {

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

		if (!isInitialized)
			initialize();

		try (Connection conn = DriverManager.getConnection(connString, connUser, connPassword)) {

			Statement stmt = conn.createStatement();

			String sql = "SELECT ID FROM Team WHERE Name='" + teamName + "'";

			ResultSet set = stmt.executeQuery(sql);

			if (set.next()) {
				return set.getInt(1);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return -404;
	}

	public static String IDToTeamName(int teamID) {

		if (!isInitialized)
			initialize();

		String teamName = null;

		try (Connection conn = DriverManager.getConnection(connString, connUser, connPassword)) {

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

		if (!isInitialized)
			initialize();

		try (Connection conn = DriverManager.getConnection(connString, connUser, connPassword)) {

			Statement stmt = conn.createStatement();

			String sql = "DELETE FROM TeamUser WHERE UserID=" + loginNameToID(loginName) + " AND TeamID="
					+ teamNameToID(team);

			stmt.executeUpdate(sql);

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public static ArrayList<String> getTeamsUserIsSubscribed(String userName) {

		if (!isInitialized)
			initialize();

		ArrayList<String> teams = new ArrayList<>();

		try (Connection conn = DriverManager.getConnection(connString, connUser, connPassword)) {

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

		if (!isInitialized)
			initialize();

		try (Connection conn = DriverManager.getConnection(connString, connUser, connPassword)) {

			Statement stmt = conn.createStatement();

			String sql = "DELETE FROM TeamUser WHERE UserID=" + loginNameToID(loginName);

			stmt.executeUpdate(sql);

		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	public static ObservableList<String> getTeams() {

		if (!isInitialized)
			initialize();

		try (Connection conn = DriverManager.getConnection("jdbc:mysql://db.swt.wiai.uni-bamberg.de/GroupF", "GroupF",
				"gruppe_f")) {

			Statement stmt = conn.createStatement();

			String query = "SELECT Name FROM Team";

			ResultSet modulesSet = stmt.executeQuery(query);
			ObservableList<String> teams = FXCollections.observableArrayList();

			while (modulesSet.next()) {
				teams.add(modulesSet.getString(1));
			}
			return teams;

		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}

	}

	public static ArrayList<Integer> getIDsFromModules(ObservableList<String> modules) {

		if (!isInitialized)
			initialize();

		ArrayList<Integer> moduleIDs = new ArrayList<>();

		try (Connection conn = DriverManager.getConnection(connString, connUser, connPassword)) {

			Statement stmt = conn.createStatement();

			String sqlSelectIdFromModuleQuery = generateSelectModuleQuery(modules);

			ResultSet moduleIDsContainer = stmt.executeQuery(sqlSelectIdFromModuleQuery);

			while (moduleIDsContainer.next()) {

				moduleIDs.add(moduleIDsContainer.getInt(1));
			}
		} catch (SQLException e) {

			e.printStackTrace();
		}
		return moduleIDs;
	}

	private static String generateSelectModuleQuery(ObservableList<String> modules) {

		if (!isInitialized)
			initialize();

		String sqlSelectModuleID = "";

		for (int i = 0; i < modules.size(); i++) {

			if (i == 0) {

				sqlSelectModuleID += "SELECT ID FROM Module WHERE Name='" + modules.get(0) + "'";
			}
			if (i > 0) {
				sqlSelectModuleID += " OR Name='" + modules.get(i) + "'";
			}
		}

		if (sqlSelectModuleID.equals("")) {
			throw new IllegalArgumentException("Invalid query, query was: " + sqlSelectModuleID + ".");
		}
		return sqlSelectModuleID;
	}

	public static void insertRequirementIntoDatabase(String sqlInsertIntoRequirementUpdate) {

		if (!isInitialized)
			initialize();

		try (Connection conn = DriverManager.getConnection(connString, connUser, connPassword)) {

			Statement stmt = conn.createStatement();

			stmt.executeUpdate(sqlInsertIntoRequirementUpdate);

		} catch (SQLException e) {

			e.printStackTrace();
		}
	}

	public static ArrayList<RequirementCardSimple> getRequirements() {

		if (!isInitialized)
			initialize();

		ArrayList<RequirementCardSimple> requirements = new ArrayList<>();

		try (Connection conn = DriverManager.getConnection(connString, connUser, connPassword)) {

			Statement stmt = conn.createStatement();

			ResultSet requirementsSet = stmt.executeQuery("SELECT * FROM Requirement");

			while (requirementsSet.next()) {
				requirements.add(new RequirementCardSimple(requirementsSet.getInt(1), requirementsSet.getString(2),
						requirementsSet.getInt(3), requirementsSet.getInt(4), requirementsSet.getInt(5),
						IDToLoginName(requirementsSet.getInt(1)), requirementsSet.getInt(6),
						getModulesByRequirementID(requirementsSet.getInt(5)), requirementsSet.getString(7),
						requirementsSet.getString(8), requirementsSet.getString(9), requirementsSet.getString(10),
						requirementsSet.getString(11), requirementsSet.getInt(12), requirementsSet.getTimestamp(13),
						requirementsSet.getString(14)));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return requirements;
	}

	public static RequirementCardSimple IDToRequirementCardSimple(int ID) {

		if (!isInitialized)
			initialize();

		try (Connection conn = DriverManager.getConnection(connString, connUser, connPassword)) {

			Statement stmt = conn.createStatement();

			ResultSet requirementsSet = stmt.executeQuery("SELECT * FROM Requirement WHERE Requirement =" + ID);

			if (requirementsSet.next()) {

				return new RequirementCardSimple(requirementsSet.getInt(1), requirementsSet.getString(2),
						requirementsSet.getInt(3), requirementsSet.getInt(4), requirementsSet.getInt(5),
						IDToLoginName(requirementsSet.getInt(1)), requirementsSet.getInt(6),
						getModulesByRequirementID(requirementsSet.getInt(1)), requirementsSet.getString(7),
						requirementsSet.getString(8), requirementsSet.getString(9), requirementsSet.getString(10),
						requirementsSet.getString(11), requirementsSet.getInt(12), requirementsSet.getTimestamp(13),
						requirementsSet.getString(14));
			} else {
				throw new IllegalArgumentException("No requirementCards matching ID: " + ID);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;

	}

	/**
	 * voteResults[0] = description precise voteResult<br>
	 * voteResults[1] = description understandable voteResult<br>
	 * voteResults[2] = description correct voteResult<br>
	 * voteResults[3] = description complete voteResult<br>
	 * voteResults[4] = description atomic voteResult<br>
	 * voteResults[5] = rationale precise voteResult<br>
	 * voteResults[6] = rationale understandable voteResult<br>
	 * voteResults[7] = rationale traceable voteResult<br>
	 * voteResults[8] = rationale complete voteResult<br>
	 * voteResults[9] = rationale consistent voteResult<br>
	 * voteResults[10] = fit Criterion complete voteResult<br>
	 *
	 *
	 * @returns SubmittedVoteSimple containing all voteResults of a specific
	 *          rqCard
	 */
	public static ArrayList<SubmittedVoteSimple> getVoteResultsFrom(int rqCardID) {

		if (!isInitialized)
			initialize();

		ArrayList<SubmittedVoteSimple> allVoteResults = new ArrayList<SubmittedVoteSimple>();

		try (Connection conn = DriverManager.getConnection(connString, connUser, connPassword)) {

			Statement getVoteResults = conn.createStatement();
			String sql = "SELECT * FROM Vote WHERE RequirementID =" + rqCardID;
			ResultSet rqVote = getVoteResults.executeQuery(sql);

			while (rqVote.next()) {

				allVoteResults.add(new SubmittedVoteSimple(rqVote.getInt(4), rqVote.getInt(5), rqVote.getInt(6),
						rqVote.getInt(7), rqVote.getInt(8), rqVote.getInt(9), rqVote.getInt(10), rqVote.getInt(11),
						rqVote.getInt(12), rqVote.getInt(13), rqVote.getInt(14)));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return allVoteResults;
	}

	public static int rQTitleToID(String requirement) {

		if (!isInitialized)
			initialize();

		try (Connection conn = DriverManager.getConnection(connString, connUser, connPassword)) {

			Statement getReqID = conn.createStatement();

			ResultSet reqID = getReqID.executeQuery("SELECT ID FROM Requirement WHERE Title ='" + requirement + "'");

			if (reqID.next()) {
				return reqID.getInt(1);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		throw new IllegalArgumentException("ResultSet is empty.");
	}
}
