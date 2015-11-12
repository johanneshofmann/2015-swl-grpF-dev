package swt.swl.topcard.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import swt.swl.topcard.MainApp;
import javafx.scene.layout.Pane;

import swt.swl.topcard.model.LoginModel;

public class LoginWindowController {
<<<<<<< HEAD

=======
>>>>>>> refs/remotes/origin/master
	private Stage primaryStage;
	private Pane rootLayout;
<<<<<<< HEAD
=======
	
>>>>>>> refs/remotes/origin/master

	private LoginModel model;
	private MainApp mainApp;

	@FXML
	private Button loginButton, registrateButton;

	@FXML
	private TextField userNameTextField;

	public LoginWindowController() {
		model = new LoginModel();
	}

	@FXML
	void loginButtonClicked(ActionEvent event) {

<<<<<<< HEAD
		boolean isInDatabase = model.checkDatabase(userNameTextField.getText());
		if (isInDatabase) {
			// remove: new MainWindowController();
		} else {
			mainApp.getPrimaryStage().setScene(NEUESCENE);
		}
=======
    	boolean isInDatabase = model.checkDatabase(userNameTextField.getText());
    	if(isInDatabase){
    		//new MainWindowController();
    		createMainWindowView();
    	}else{
    		
    	}
    		
    }
    @FXML
    void registrateButtonClicked(ActionEvent event) {
>>>>>>> refs/remotes/origin/master

	}

	@FXML
	void registrateButtonClicked(ActionEvent event) {

	}

	public void setMainApp(MainApp mainApp) {
		this.mainApp = mainApp;
	}

	private void createMainWindowView() {
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
