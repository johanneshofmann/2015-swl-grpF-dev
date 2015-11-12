package swt.swl.topcard.controller;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import swt.swl.topcard.MainApp;


import swt.swl.topcard.model.LoginModel;

public class LoginWindowController {
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


    	boolean isInDatabase = model.checkDatabase(userNameTextField.getText());
    	if(isInDatabase){
    		createMainWindowView();
    	}else{
    		
    	}
    		
    }

	@FXML
	void registrateButtonClicked(ActionEvent event) {

	}

	public void setMainApp(MainApp mainApp) {
		this.mainApp = mainApp;
	}

	private void createMainWindowView() {
		/*
		 * try {
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
		*/
	}
}
