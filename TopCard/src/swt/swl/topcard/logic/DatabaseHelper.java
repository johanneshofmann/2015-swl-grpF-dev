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

	public static String XIDToName(String x, Integer XID) {

		if (!isInitialized)
			initialize();

		String moduleName = null;

		try (Connection conn = DriverManager.getConnection(connString, connUser, connPassword)) {

			Statement stmt = conn.createStatement();
			String getNameQuery = "";
			if (x.equals("User")) {
				getNameQuery = "SELECT LoginName FROM " + x + " WHERE ID=" + XID;
			} else {
				getNameQuery = "SELECT Name FROM " + x + " WHERE ID=" + XID;
			}

			ResultSet nameContainer = stmt.executeQuery(getNameQuery);

			if (nameContainer.next()) {
				moduleName = nameContainer.getString(1);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return moduleName;
	}

	public static Integer XNameToID(String source, String name) {

		if (!isInitialized)
			initialize();

		Integer ID = null;

		try (Connection conn = DriverManager.getConnection(connString, connUser, connPassword)) {

			Statement stmt = conn.createStatement();

			String getIDQuery = "";

			if (source.equals("Requirement")) {

				getIDQuery = "SELECT ID FROM " + source + " WHERE Title='" + name + "'";

			} else if (source.equals("User")) {
				getIDQuery = "SELECT ID FROM " + source + " WHERE LoginName='" + name + "'";
			} else {

				getIDQuery = "SELECT ID FROM " + source + " WHERE Name='" + name + "'";
			}

			ResultSet IDContainer = stmt.executeQuery(getIDQuery);

			if (IDContainer.next()) {
				ID = IDContainer.getInt(1);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return ID;
	}

	public static ObservableList<String> getNameFrom(String x) {

		if (!isInitialized)
			initialize();

		try (Connection conn = DriverManager.getConnection(connString, connUser, connPassword)) {

			Statement stmt = conn.createStatement();

			String query = "SELECT Name FROM " + x;

			ResultSet resultSet = stmt.executeQuery(query);
			ObservableList<String> xList = FXCollections.observableArrayList();

			while (resultSet.next()) {
				xList.add(resultSet.getString(1));
			}
			return xList;

		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}

	public static ObservableList<String> getXNames(String x) {

		if (!isInitialized)
			initialize();

		try (Connection conn = DriverManager.getConnection(connString, connUser, connPassword)) {

			Statement stmt = conn.createStatement();

			String query = "SELECT Name FROM " + x;

			ResultSet userStoriesSet = stmt.executeQuery(query);
			ObservableList<String> userStories = FXCollections.observableArrayList();

			while (userStoriesSet.next()) {
				userStories.add(userStoriesSet.getString(1));
			}
			return userStories;

		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}

	public static ArrayList<Integer> XNamesToIDs(String x, ObservableList<String> observableList) {

		if (!isInitialized)
			initialize();

		try (Connection conn = DriverManager.getConnection(connString, connUser, connPassword)) {

			Statement stmt = conn.createStatement();

			String query = "SELECT Name FROM " + x;

			ResultSet userStoriesSet = stmt.executeQuery(query);
			ArrayList<Integer> IDs = new ArrayList<Integer>();

			while (userStoriesSet.next()) {
				IDs.add(XNameToID(x, userStoriesSet.getString(1)));
			}
			return IDs;

		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}

	public static Integer getMaxXFromY(String x, String y) {

		if (!isInitialized)
			initialize();

		try (Connection conn = DriverManager.getConnection(connString, connUser, connPassword)) {

			Statement stmt = conn.createStatement();

			ResultSet maxValue = stmt.executeQuery("SELECT MAX(" + x + ") FROM " + y);

			if (maxValue.next()) {
				return maxValue.getInt(1);
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
				stmt$1.executeUpdate("DELETE * FROM Requirement WHERE Requirement= " + rqCardID);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public static ArrayList<Integer> getModulesByRequirementID(int rQID) {

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
			return modules;

		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}

	public static String getModulesAsStringByRequirementID(int rQID) {

		ArrayList<Integer> modules = getModulesByRequirementID(rQID);

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

	}

	public static boolean checkUserAlreadySubscribed(String userName) {

		if (!isInitialized)
			initialize();

		try (Connection conn = DriverManager.getConnection(connString, connUser, connPassword)) {

			Statement stmt = conn.createStatement();

			int userID = XNameToID("User", userName);

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

		executeUpdate("INSERT INTO TeamUser(TeamID, UserID) VALUES(" + XNameToID("Team", teamName) + ","
				+ XNameToID("User", loginName) + ")");

	}

	public static void exitUserFromTeam(String loginName, String team) {

		if (!isInitialized)
			initialize();

		try (Connection conn = DriverManager.getConnection(connString, connUser, connPassword)) {

			Statement stmt = conn.createStatement();

			String sql = "DELETE * FROM TeamUser WHERE UserID=" + XNameToID("User", loginName) + " AND TeamID="
					+ XNameToID("Team", team);

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

			int userID = XNameToID("User", userName);

			ResultSet teamsContainer = stmt.executeQuery("SELECT TeamID FROM TeamUser WHERE UserID=" + userID);

			if (teamsContainer.next()) {
				int teamID = teamsContainer.getInt(1);
				teams.add(XIDToName("Team", teamID));
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

			String sql = "DELETE * FROM TeamUser WHERE UserID=" + XNameToID("User", loginName);

			stmt.executeUpdate(sql);

		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	public static ArrayList<String> getModuleNamesByIDs(ObservableList<Integer> IDs) {

		if (!isInitialized)
			initialize();

		ArrayList<String> moduleNames = new ArrayList<>();

		try (Connection conn = DriverManager.getConnection(connString, connUser, connPassword)) {

			Statement stmt = conn.createStatement();

			String sqlSelectIdFromModuleQuery = generateSelectXNamesQuery("Module", IDs);

			ResultSet moduleIDContainer = stmt.executeQuery(sqlSelectIdFromModuleQuery);

			while (moduleIDContainer.next()) {
				moduleNames.add(moduleIDContainer.getString(1));
			}

			return moduleNames;

		} catch (SQLException e) {

			e.printStackTrace();
		}
		return null;
	}

	public static ArrayList<RequirementCardSimple> getRequirements() {

		if (!isInitialized)
			initialize();

		ArrayList<RequirementCardSimple> requirements = new ArrayList<>();

		try (Connection conn = DriverManager.getConnection(connString, connUser, connPassword)) {

			Statement stmt = conn.createStatement();

			ArrayList<Integer> reqIDs = getRequirementIDs();
			ArrayList<Integer> majorVersions = new ArrayList<>();
			ArrayList<Integer> minorVersions = new ArrayList<>();

			for (int i = 0; i < reqIDs.size(); i++) {
				majorVersions.add(getMajorVersion(reqIDs.get(i)));
				minorVersions.add(getMinorVersion(reqIDs.get(i)));
			}

			for (int i = 0; i < reqIDs.size(); i++) {

				ResultSet requirementSet = stmt.executeQuery(
						"SELECT * FROM Requirement WHERE Requirement=" + reqIDs.get(i) + " AND MajorVersion="
								+ majorVersions.get(i) + " AND MinorVersion=" + minorVersions.get(i));

				if (requirementSet.next()) {

					int rqID = reqIDs.get(i);
					int ownerID = requirementSet.getInt(5);

					int minorVersion = minorVersions.get(i), majorVersion = majorVersions.get(i);

					requirements.add(new RequirementCardSimple(requirementSet.getInt(1), requirementSet.getString(2),
							minorVersion, majorVersion, ownerID, XIDToName("User", ownerID), rqID,
							getModulesAsStringByRequirementID(rqID), requirementSet.getString(7),
							requirementSet.getString(8), requirementSet.getString(9),
							getUserStoryIDsAsStringByRequirementID(rqID), requirementSet.getString(10),
							requirementSet.getString(11), requirementSet.getInt(12), requirementSet.getTimestamp(13),
							requirementSet.getString(14)));
				}
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return requirements;

	}

	private static String getUserStoryIDsAsStringByRequirementID(int rqID) {

		ArrayList<Integer> userStories = getUserStoryIDsByRequirementID(rqID);

		String userStoriesAsString = "";

		int counter = 0;

		for (Integer i : userStories) {

			if (counter == 0) {
				userStoriesAsString += i;
				counter++;
			} else {
				userStoriesAsString += ", " + i;
			}
		}
		return userStoriesAsString;
	}

	private static ArrayList<Integer> getUserStoryIDsByRequirementID(int rqID) {
		if (!isInitialized)
			initialize();

		ArrayList<Integer> userStoryIDs = new ArrayList<>();

		try (Connection conn = DriverManager.getConnection(connString, connUser, connPassword)) {

			Statement getUserStoryIDs = conn.createStatement();

			ResultSet modulesContainer = getUserStoryIDs
					.executeQuery("SELECT UserStoryID FROM RequirementUserStory WHERE RequirementID=" + rqID);

			while (modulesContainer.next()) {

				userStoryIDs.add(modulesContainer.getInt(1));
			}
			return userStoryIDs;

		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}

	private static ArrayList<Integer> getRequirementIDs() {

		try (Connection conn = DriverManager.getConnection(connString, connUser, connPassword)) {

			Statement stmt = conn.createStatement();

			ResultSet requirementIDsSet = stmt.executeQuery("SELECT Requirement FROM Requirement");

			ArrayList<Integer> reqIDs = new ArrayList<>();

			while (requirementIDsSet.next()) {
				if (!reqIDs.contains((int) requirementIDsSet.getInt(1))) {
					reqIDs.add(requirementIDsSet.getInt(1));
				}
			}
			return reqIDs;

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	private static Integer getMajorVersion(int rqID) {

		if (!isInitialized)
			initialize();

		try (Connection conn = DriverManager.getConnection(connString, connUser, connPassword)) {

			Statement stmt = conn.createStatement();

			String query = "SELECT MAX(MajorVersion) FROM Requirement WHERE Requirement=" + rqID;

			ResultSet majorVersionSet = stmt.executeQuery(query);

			while (majorVersionSet.next()) {
				return majorVersionSet.getInt(1);
			}

		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
		return -404;
	}

	private static Integer getMinorVersion(int rqID) {

		if (!isInitialized)
			initialize();

		try (Connection conn = DriverManager.getConnection(connString, connUser, connPassword)) {

			Statement stmt = conn.createStatement();

			String query = "SELECT MAX(MinorVersion) FROM Requirement WHERE Requirement=" + rqID;

			ResultSet minorVersionSet = stmt.executeQuery(query);

			while (minorVersionSet.next()) {
				return minorVersionSet.getInt(1);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return -404;
	}

	public static RequirementCardSimple IDToRequirementCardSimple(int ID) {

		if (!isInitialized)
			initialize();

		try (Connection conn = DriverManager.getConnection(connString, connUser, connPassword)) {

			Statement stmt = conn.createStatement();

			ResultSet requirementsSet = stmt.executeQuery("SELECT * FROM Requirement WHERE Requirement =" + ID);

			if (requirementsSet.next()) {
				int ownerID = requirementsSet.getInt(5), requirementID = requirementsSet.getInt(6);

				return new RequirementCardSimple(requirementsSet.getInt(1), requirementsSet.getString(2),
						requirementsSet.getInt(3), requirementsSet.getInt(4), ownerID, XIDToName("User", ownerID),
						requirementID, getModulesAsStringByRequirementID(requirementID), requirementsSet.getString(7),
						requirementsSet.getString(8), requirementsSet.getString(9),
						getUserStoryIDsAsStringByRequirementID(requirementID), requirementsSet.getString(10),
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

	public static ArrayList<Integer> getIDsFromX(String x, ObservableList<String> modules) {

		if (!isInitialized)
			initialize();

		ArrayList<Integer> xIDs = new ArrayList<>();

		try (Connection conn = DriverManager.getConnection(connString, connUser, connPassword)) {

			Statement stmt = conn.createStatement();

			String sqlSelectIdFromModuleQuery = generateSelectXIDsQuery(x, modules);

			ResultSet xIDsContainer = stmt.executeQuery(sqlSelectIdFromModuleQuery);

			while (xIDsContainer.next()) {

				xIDs.add(xIDsContainer.getInt(1));
			}
		} catch (SQLException e) {

			e.printStackTrace();
		}
		return xIDs;
	}

	private static String generateSelectXIDsQuery(String x, ObservableList<String> names) {

		if (!isInitialized)
			initialize();

		String sqlSelectXID = "";

		for (int i = 0; i < names.size(); i++) {

			if (i == 0) {

				sqlSelectXID += "SELECT ID FROM " + x + " WHERE Name='" + names.get(0) + "'";
			}
			if (i > 0) {
				sqlSelectXID += " OR Name='" + names.get(i) + "'";
			}
		}

		if (sqlSelectXID.equals("")) {
			throw new IllegalArgumentException("Invalid query, query was: " + sqlSelectXID + ".");
		}
		return sqlSelectXID;
	}

	private static String generateSelectXNamesQuery(String x, ObservableList<Integer> moduleIDs) {

		if (!isInitialized)
			initialize();

		String sqlSelectModuleName = "";

		for (int i = 0; i < moduleIDs.size(); i++) {

			if (i == 0) {

				sqlSelectModuleName += "SELECT Name FROM " + x + " WHERE ID=" + moduleIDs.get(0);
			}
			if (i > 0) {
				sqlSelectModuleName += " OR ID=" + moduleIDs.get(i);
			}
		}

		if (sqlSelectModuleName.equals("")) {
			throw new IllegalArgumentException("Invalid query, query was: " + sqlSelectModuleName + ".");
		}
		return sqlSelectModuleName;
	}

	public static boolean checkExistent(String string, String userStory) {

		return false;
	}

	// for modules, userStories, which can exist without a requirement pointing
	// to it

	public static void insertSimpleX(String x, String name) {

		String sqlInsertIntoX = "INSERT INTO " + x + "(Name) VALUES('" + name + "')";
		executeUpdate(sqlInsertIntoX);

	}

	public static void deleteXFromDatabaseByID(String x, int ID) {

		String sql = "DELETE * FROM " + x + " WHERE ID=" + ID;

		executeUpdate(sql);
	}

	public static void deleteXFromDatabaseByName(String x, String name) {

		String sql = "DELETE * FROM " + x + " WHERE Name=" + name;

		executeUpdate(sql);
	}

}
