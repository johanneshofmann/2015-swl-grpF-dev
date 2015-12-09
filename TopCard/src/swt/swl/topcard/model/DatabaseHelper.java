package swt.swl.topcard.model;

import java.sql.*;

public class DatabaseHelper {

	private static Boolean isInitialized = false;
	private static String connString = "jdbc:mysql://db.swt.wiai.uni-bamberg.de/GroupF";
	private static String connUser = "GroupF";
	private static String connPassword = "gruppe_f";

	public static void Initialize(){
		try {
			Class.forName("com.mysql.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	public static ResultSet executeQuery(String query) {
		if(!isInitialized) Initialize();
		try (Connection conn = DriverManager.getConnection(connString, connUser, connPassword)) {
			PreparedStatement stmt = conn.prepareStatement(query);
			ResultSet result = stmt.executeQuery(query);
			return result;
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}

	public static ResultSet executeQuery(String query, String param) {
		if(!isInitialized) Initialize();
		try (Connection conn = DriverManager.getConnection(connString, connUser, connPassword)) {
			PreparedStatement stmt = conn.prepareStatement(query);
			stmt.setString(0, param);
			ResultSet result = stmt.executeQuery(query);
			return result;
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public static int executeUpdate(String query, String param)
	{
		if(!isInitialized) Initialize();
		try (Connection conn = DriverManager.getConnection(connString, connUser, connPassword)) {
			PreparedStatement stmt = conn.prepareStatement(query);
			stmt.setString(0, param);
			int result = stmt.executeUpdate(query);
			return result;
		} catch (SQLException e) {
			e.printStackTrace();
			return -1;
		}
	}
}
