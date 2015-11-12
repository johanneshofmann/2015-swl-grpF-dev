package swt.swl.topcard.controller;

import java.sql.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import swt.swl.topcard.MainApp;

import swt.swl.topcard.model.LoginModel;


public class LoginWindowController {
<<<<<<< HEAD
	private Stage primaryStage;
	private Pane rootLayout;
	
=======

	private LoginModel model;

	@FXML
    private Button loginButton,registrateButton;

>>>>>>> origin/master
    @FXML
    private TextField userNameTextField;

	public LoginWindowController() {
		model = new LoginModel();
	}

    @FXML
    void loginButtonClicked(ActionEvent event) {
<<<<<<< HEAD

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
    				//new MainWindowController(loginName);
    				createMainWindowView();
    				this.primaryStage.close();
    				System.out.println("LoginName accepted. Access granted");
    				break;
    			}

    		}


    	}catch (SQLException e) {
    		e.printStackTrace();
=======
    	boolean isInDatabase = model.checkDatabase(userNameTextField.getText());
    	if(isInDatabase){
    		new MainWindowController();
    	}else{
    		
>>>>>>> origin/master
    	}
    		
    }
    @FXML
    void registrateButtonClicked(ActionEvent event) {

    }
    
    private void createMainWindowView(){
    	try {
    		
			primaryStage = new Stage();
			primaryStage.setTitle("TopCard");
			FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("view/MainWindowView.fxml"));
            rootLayout = (Pane) loader.load();
            Scene scene = new Scene(rootLayout);
            primaryStage.setScene(scene);
            primaryStage.show();
		} catch (Exception e) {
			e.printStackTrace();
		}
    }
}
