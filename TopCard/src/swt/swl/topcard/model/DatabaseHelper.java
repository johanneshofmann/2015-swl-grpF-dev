package swt.swl.topcard.model;

import java.sql.*;

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

	public static String requestOwnerName(int ownerID) {

		String getOwnerNameQuery = "SELECT LoginName FROM User WHERE ID=" + ownerID;

		ResultSet ownerNameContainer = executeQuery(getOwnerNameQuery);

		String ownerName = null;

		try {
			if (ownerNameContainer.next()) {
				ownerName = ownerNameContainer.getString(1);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return ownerName;
	}
}
