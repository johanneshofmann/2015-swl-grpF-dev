package swt.swl.topcard.logic.impl;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import swt.swl.topcard.logic.SubmittedVoteSimple;

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

		if (source.equals("User")) {
			return loginNameToID(name);
		}

		ObservableList<String> nameList = FXCollections.observableArrayList();
		nameList.add(name);

		try (Connection conn = DriverManager.getConnection(connString, connUser, connPassword)) {

			Statement stmt = conn.createStatement();

			String query = generateSelectXIDsQuery(source, nameList);

			ResultSet resultSet = stmt.executeQuery(query);

			if (resultSet.next()) {
				return resultSet.getInt(1);
			}

		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
		throw new IllegalStateException("Illegal state.");
		// return getIDsFromX(source, nameList).get(0);
	}

	private static Integer loginNameToID(String name) {

		try (Connection conn = DriverManager.getConnection(connString, connUser, connPassword)) {

			Statement stmt = conn.createStatement();

			String query = "SELECT ID FROM User WHERE LoginName='" + name + "'";
			ResultSet resultSet = stmt.executeQuery(query);

			if (resultSet.next()) {
				return resultSet.getInt(1);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
		throw new IllegalStateException("Illegal State.");
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

	public static ObservableList<String> getNamesFromSource(String source) {

		if (!isInitialized)
			initialize();

		try (Connection conn = DriverManager.getConnection(connString, connUser, connPassword)) {

			Statement stmt = conn.createStatement();

			String name = evaluateName(source);

			String query = "SELECT " + name + " FROM " + source;

			ResultSet xSet = stmt.executeQuery(query);

			ObservableList<String> items = FXCollections.observableArrayList();

			while (xSet.next()) {
				items.add(xSet.getString(1));
			}
			return items;

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

			ResultSet xSet = stmt.executeQuery(query);
			ArrayList<Integer> IDs = new ArrayList<Integer>();

			while (xSet.next()) {
				IDs.add(XNameToID(x, xSet.getString(1)));
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

	// for modules or userStories:
	public static ArrayList<Integer> getXIDsByRequirementID(String x, int rQID) {

		if (!isInitialized)
			initialize();

		ArrayList<Integer> IDs = new ArrayList<>();

		try (Connection conn = DriverManager.getConnection(connString, connUser, connPassword)) {

			Statement getIDs = conn.createStatement();

			ResultSet IDsContainer = getIDs
					.executeQuery("SELECT " + x + "ID FROM Requirement" + x + " WHERE RequirementID=" + rQID);

			while (IDsContainer.next()) {

				IDs.add(IDsContainer.getInt(1));
			}
			return IDs;

		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}

	public static ArrayList<String> getXNameAsListByRequirementID(String x, int rQID) {

		ArrayList<String> names = new ArrayList<>();

		for (Integer iD : getXIDsByRequirementID(x, rQID)) {

			names.add(XIDToName(x, iD));
		}

		return names;
	}

	public static String getXNameAsStringByRequirementID(String x, int rQID) {

		ArrayList<Integer> IDs = getXIDsByRequirementID(x, rQID);

		String xAsString = "";

		int counter = 0;

		for (Integer iD : IDs) {

			if (counter == 0) {
				xAsString += XIDToName(x, iD);
				counter++;
			} else {
				xAsString += ", " + XIDToName(x, iD);
			}
		}
		return xAsString;
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

		Integer teamID = XNameToID("Team", teamName);
		Integer userID = XNameToID("User", loginName);

		if (userID == null) {
			throw new IllegalArgumentException("For reasons of integrity column 'UserID' cannot be null");
		}
		if (teamID == null) {
			throw new IllegalArgumentException("For reasons of integrity column 'TeamID' cannot be null");
		}

		executeUpdate("INSERT INTO TeamUser(TeamID, UserID) VALUES(" + teamID + "," + userID + ")");

	}

	public static void exitUserFromTeam(String loginName, String team) {

		if (!isInitialized)
			initialize();

		try (Connection conn = DriverManager.getConnection(connString, connUser, connPassword)) {

			Statement stmt = conn.createStatement();

			String sql = "DELETE FROM TeamUser WHERE UserID=" + XNameToID("User", loginName) + " AND TeamID="
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

			while (teamsContainer.next()) {
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

			String sql = "DELETE FROM TeamUser WHERE UserID=" + XNameToID("User", loginName);

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

	public static ArrayList<RequirementCardSimpleImpl> getRequirements() {

		ArrayList<RequirementCardSimpleImpl> requirements = new ArrayList<>();

		ArrayList<Integer> reqIDs = getRequirementIDs();

		for (int i = 0; i < reqIDs.size(); i++) {

			requirements.add(getDistinctRequirementCard(reqIDs.get(i)));
		}
		return requirements;
	}

	private static RequirementCardSimpleImpl getDistinctRequirementCard(int rqID) {

		int majorVersion = getMaxMajorVersion(rqID);
		int minorVersion = getMaxMinorVersion(rqID, majorVersion);

		try (Connection conn = DriverManager.getConnection(connString, connUser, connPassword)) {

			Statement stmt = conn.createStatement();

			String sqlSelectAllFromRequirementQuery = "SELECT * FROM Requirement WHERE Requirement=" + rqID
					+ " AND MajorVersion=" + majorVersion + " AND MinorVersion=" + minorVersion;

			ResultSet requirementSet = stmt.executeQuery(sqlSelectAllFromRequirementQuery);

			if (requirementSet.next()) {

				int ownerID = requirementSet.getInt(5);
				int ID = requirementSet.getInt(1);

				return new RequirementCardSimpleImpl(ID, requirementSet.getString(2), minorVersion, majorVersion,
						ownerID, XIDToName("User", ownerID), rqID, getXNameAsStringByRequirementID("Module", ID),
						requirementSet.getString(7), requirementSet.getString(8),
						getXNameAsStringByRequirementID("Team", ID), getXNameAsStringByRequirementID("UserStory", ID),
						requirementSet.getString(9), requirementSet.getString(10), requirementSet.getInt(11),
						requirementSet.getTimestamp(12), requirementSet.getString(13));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		throw new IllegalStateException("Should have returned RequirementSimple. Given rqID was: " + rqID);
	}

	private static ArrayList<Integer> getRequirementIDs() {

		try (Connection conn = DriverManager.getConnection(connString, connUser, connPassword)) {

			Statement stmt = conn.createStatement();

			ResultSet requirementIDsSet = stmt.executeQuery("SELECT Requirement FROM Requirement");

			ArrayList<Integer> reqIDs = new ArrayList<>();

			while (requirementIDsSet.next()) {
				if (!reqIDs.contains((Integer) requirementIDsSet.getInt(1))) {
					reqIDs.add(requirementIDsSet.getInt(1));
				}
			}
			return reqIDs;

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	private static Integer getMaxMajorVersion(int rqID) {

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

	private static Integer getMaxMinorVersion(int rqID, int majorVersion) {

		if (!isInitialized)
			initialize();

		try (Connection conn = DriverManager.getConnection(connString, connUser, connPassword)) {

			Statement stmt = conn.createStatement();

			String query = "SELECT MAX(MinorVersion) FROM Requirement WHERE Requirement=" + rqID + " AND MajorVersion="
					+ majorVersion;

			ResultSet minorVersionSet = stmt.executeQuery(query);

			while (minorVersionSet.next()) {
				return minorVersionSet.getInt(1);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return -404;
	}

	public static RequirementCardSimpleImpl IDToRequirementCardSimple(int ID) {

		if (!isInitialized)
			initialize();

		try (Connection conn = DriverManager.getConnection(connString, connUser, connPassword)) {

			Statement stmt = conn.createStatement();

			ResultSet requirementSet = stmt.executeQuery("SELECT * FROM Requirement WHERE ID =" + ID);

			if (requirementSet.next()) {

				int ownerID = requirementSet.getInt(5), requirementID = requirementSet.getInt(6);

				return new RequirementCardSimpleImpl(ID, requirementSet.getString(2), requirementSet.getInt(4),
						requirementSet.getInt(3), ownerID, XIDToName("User", ownerID), requirementID,
						getXNameAsStringByRequirementID("Module", ID), requirementSet.getString(7),
						requirementSet.getString(8), getXNameAsStringByRequirementID("Team", ID),
						getXNameAsStringByRequirementID("UserStory", ID), requirementSet.getString(9),
						requirementSet.getString(10), requirementSet.getInt(11), requirementSet.getTimestamp(12),
						requirementSet.getString(13));
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
	public static ArrayList<SubmittedVoteSimple> getVoteResultsFrom(int rqCardUniqueID) {

		if (!isInitialized)
			initialize();

		ArrayList<SubmittedVoteSimple> allVoteResults = new ArrayList<SubmittedVoteSimple>();

		try (Connection conn = DriverManager.getConnection(connString, connUser, connPassword)) {

			Statement getVoteResults = conn.createStatement();
			String sql = "SELECT * FROM Vote WHERE RequirementID =" + rqCardUniqueID;
			ResultSet rqVote = getVoteResults.executeQuery(sql);
			while (rqVote.next()) {
				SubmittedVoteSimple currentVote = new SubmittedVoteSimpleImpl(rqVote.getInt(4), rqVote.getInt(5),
						rqVote.getInt(6), rqVote.getInt(7), rqVote.getInt(8), rqVote.getInt(9), rqVote.getInt(10),
						rqVote.getInt(11), rqVote.getInt(12), rqVote.getInt(13), rqVote.getInt(14));
				allVoteResults.add(currentVote);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		if (allVoteResults.isEmpty()) {
			throw new IllegalStateException("no votes added.. \r\r");
		}
		return allVoteResults;
	}

	public static ArrayList<Integer> getIDsFromX(String source, ObservableList<String> names) {

		String sqlSelectIdFromSourceQuery = generateSelectXIDsQuery(source, names);
		System.out.println(sqlSelectIdFromSourceQuery);

		if (!isInitialized)
			initialize();

		try (Connection conn = DriverManager.getConnection(connString, connUser, connPassword)) {

			Statement stmt = conn.createStatement();

			ResultSet xIDsContainer = stmt.executeQuery(sqlSelectIdFromSourceQuery);

			ArrayList<Integer> xIDs = new ArrayList<>();

			while (xIDsContainer.next()) {

				xIDs.add(xIDsContainer.getInt(1));
			}
			return xIDs;

		} catch (SQLException e) {

			e.printStackTrace();
		}
		throw new IllegalStateException("Illegal state.");
	}

	private static String generateSelectXIDsQuery(String x, ObservableList<String> names) {

		if (!isInitialized)
			initialize();

		String name = evaluateName(x);

		String sqlSelectXID = "";

		// Select ID from Team|Module|UserStory where Name ='name'

		for (int i = 0; i < names.size(); i++) {

			if (i == 0) {

				sqlSelectXID += "SELECT ID FROM " + x + " WHERE " + name + "='" + names.get(0) + "'";
			}
			if (i > 0) {
				sqlSelectXID += " OR " + name + "='" + names.get(i) + "'";
			}
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

	public static void deleteXFromDatabaseByID(String x, String option, int ID) {

		String sql = "DELETE FROM " + x + " WHERE ID" + option + +ID;

		executeUpdate(sql);
	}

	public static void deleteXFromDatabaseByName(String x, String name) {

		String toDelete = evaluateName(x);

		String sql = "DELETE FROM " + x + " WHERE " + toDelete + "='" + name + "'";

		executeUpdate(sql);
	}

	private static String evaluateName(String x) {

		String name = "Name";

		if (x.equals("Requirement")) {
			name = "Title";
		} else if (x.equals("User")) {
			name = "LoginName";
		}
		return name;
	}

	public static void deleteRqFromDatabase(int rqID, int majorVersion, int minorVersion) {

		if (!isInitialized)
			initialize();

		int ID = getRqID(rqID, majorVersion, minorVersion);

		executeUpdate("DELETE FROM RequirementModule WHERE RequirementID=" + ID);
		executeUpdate("DELETE FROM RequirementUserStory WHERE RequirementID=" + ID);
		executeUpdate("DELETE FROM RequirementTeam WHERE RequirementID=" + ID);
		executeUpdate("DELETE FROM Vote WHERE RequirementID=" + ID);
		executeUpdate("DELETE FROM Requirement WHERE ID= " + ID);
	}

	public static Integer getRqID(int rqID, int majorVersion, int minorVersion) {

		Integer ID = null;

		try (Connection conn = DriverManager.getConnection(connString, connUser, connPassword)) {

			Statement stmt = conn.createStatement();

			String sql = "SELECT ID FROM Requirement WHERE Requirement=" + rqID + " AND MajorVersion=" + majorVersion
					+ " AND MinorVersion=" + minorVersion;

			ResultSet set = stmt.executeQuery(sql);

			if (set.next()) {
				ID = set.getInt(1);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return ID;
	}

	public static boolean userAlreadyVoted(String loginName, int ID) {

		try (Connection conn = DriverManager.getConnection(connString, connUser, connPassword)) {

			Statement stmt = conn.createStatement();

			String sql = "SELECT ID FROM Vote WHERE RequirementID=" + ID + " AND UserID="
					+ XNameToID("User", loginName);

			ResultSet set = stmt.executeQuery(sql);

			return (set.next() ? true : false);

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return false;
	}

	public static boolean noVotesSubmitted(int ID) {

		try (Connection conn = DriverManager.getConnection(connString, connUser, connPassword)) {

			Statement stmt = conn.createStatement();

			String sql = "SELECT ID FROM Vote WHERE RequirementID=" + ID;
			ResultSet set = stmt.executeQuery(sql);

			return (set.next() ? false : true);

		} catch (SQLException e) {
			e.printStackTrace();
		}
		throw new IllegalStateException("Some unhandled exception occured.");
	}

	public static boolean isFrozen(Integer ID) {

		try (Connection conn = DriverManager.getConnection(connString, connUser, connPassword)) {

			Statement stmt = conn.createStatement();

			String sql = "SELECT MinorVersion FROM Requirement WHERE ID=" + ID;

			ResultSet set = stmt.executeQuery(sql);

			if (set.next()) {
				return (set.getInt(1) == 0) ? true : false;
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return false;
	}

	public static ArrayList<Integer> getAllRQIDsFromUser(String userName) {

		String sql = "SELECT ID FROM Requirement where OwnerID=" + DatabaseHelper.loginNameToID(userName);

		try (Connection conn = DriverManager.getConnection(connString, connUser, connPassword)) {

			Statement stmt = conn.createStatement();

			ResultSet set = stmt.executeQuery(sql);

			ArrayList<Integer> rqIDs = new ArrayList<>();
			while (set.next()) {
				rqIDs.add(set.getInt(1));
			}

			return rqIDs;

		} catch (SQLException e) {
			e.printStackTrace();
		}
		throw new IllegalStateException("Illegal state.");
	}

}
