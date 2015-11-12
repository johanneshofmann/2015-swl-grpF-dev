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
    	boolean isInDatabase = model.checkDatabase(userNameTextField.getText());
    	if(isInDatabase){
    		new MainWindowController();
    	}else{
    		
    	}
    		
    }
    @FXML
    void registrateButtonClicked(ActionEvent event) {

    }
}
