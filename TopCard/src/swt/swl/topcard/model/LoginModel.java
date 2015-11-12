package swt.swl.topcard.model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

import swt.swl.topcard.controller.MainWindowController;

public class LoginModel {

	public boolean checkDatabase(String loginName) {

    	try{
    		Class.forName("com.mysql.jdbc.Driver");
    	}catch(ClassNotFoundException e){
    		e.printStackTrace();
    	}
    	try(Connection conn = DriverManager.getConnection("jdbc:mysql://db.swt.wiai.uni-bamberg.de/GroupF", "GroupF" , "gruppe_f")){
    		Statement stmt = conn.createStatement();
    		ResultSet result = stmt.executeQuery("Select LoginName from User");
    		while(result.next()){
    			String userName = result.getString("LoginName");
    			if(userName.equals(loginName)){
    				return true;
    				new MainWindowController(loginName);
    				System.out.println("LoginName accepted. Access granted");
    				break;
    			}
    		}
    		return false;

	}

}
