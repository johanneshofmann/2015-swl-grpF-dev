package swt.swl.topcard.controller;

import java.sql.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

import swt.swl.topcard.model.LoginModel;


public class LoginWindowController {

	private LoginModel model;

	@FXML
    private Button loginButton,registrateButton;

    @FXML
    private TextField userNameTextField;

	public LoginWindowController() {
		model = new LoginModel();
	}

    @FXML
    void loginButtonClicked(ActionEvent event) {
    	model.checkDatabase(userNameTextField.getText());
    }

    @FXML
    void registrateButtonClicked(ActionEvent event) {

    }

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
    				return true;
    				new MainWindowController(loginName);
    				System.out.println("LoginName accepted. Access granted");
    				break;
    			}
    		}
    		return false;

    		if(!isInDatabase){

    		}


    	}catch (SQLException e) {
    		e.printStackTrace();
    	}
    }
}
