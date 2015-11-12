package swt.swl.topcard.controller;

import java.sql.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;


public class LoginWindowController {

    @FXML
    private TextField userNameTextField;
    @FXML
    private Button loginButton;

    @FXML
    void loginButtonClicked(ActionEvent event) {

    	try{
    		Class.forName("com.mysql.jdbc.Driver");
    	}catch(ClassNotFoundException e){
    		e.printStackTrace();
    	}
    	try(Connection conn = DriverManager.getConnection("jdbc:mysql://db.swt.wiai.uni-bamberg.de/GroupF", "GroupF" , "gruppe_f")){
    		Statement stmt = conn.createStatement();
    		ResultSet result = stmt.executeQuery("Select LoginName from User");
    		String loginName = userNameTextField.getText();
    		while(result.next()){
    			String userName = result.getString("LoginName");
    			if(userName.equals(loginName)){
    				new MainWindowController(loginName);
    				System.out.println("LoginName accepted. Access granted");
    				break;
    			}

    		}


    	}catch (SQLException e) {
    		e.printStackTrace();
    	}
    }
}
