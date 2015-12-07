package swt.swl.topcard.model;

import java.sql.*;

public class DatabaseHelper {

	private String connstring = "jdbc:mysql://db.swt.wiai.uni-bamberg.de/GroupF";
	private String connuser = "GroupF";
	private String connpassword = "gruppe_f";

	public DatabaseHelper()
	{
		/*
		try
		{
			Class.forName("com.mysql.jdbc.Driver");
		}
		catch(ClassNotFoundException e)
		{
			e.printStackTrace();
		}
    	try(Connection conn = DriverManager.getConnection(connstring, connuser, connpassword)){
    		Statement stmt = new Statement()
    	}
    	catch(SQLException e)
    	{
    		e.printStackTrace();
    	}
    	*/
	}
}
