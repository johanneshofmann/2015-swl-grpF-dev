package swt.swl.topcard.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.Pane;
import swt.swl.topcard.MainApp;

import swt.swl.topcard.model.LoginModel;

public class LoginWindowController {
	private LoginModel model;
	private MainApp mainApp;
	private Pane rootLayout;

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

		if (isInDatabase) {
			createMainWindowView();
		} else {
			Alert al = new Alert(AlertType.CONFIRMATION, "Not registrated. Registrate now ? ");
			al.showAndWait();
			String confirmation = al.getResult().getText();
			if (confirmation.equals("OK")) {
				createRegistrationView();
			} else {
				// do nothing..
				mainApp.getPrimaryStage().close();
			}
		}
	}

	private void createRegistrationView() {
		try {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(getClass().getResource("/swt/swl/topcard/view/RegistrationView.fxml"));
			rootLayout = (Pane) loader.load();
			((RegistrationController) loader.getController()).setModel(this.model);
			Scene scene = new Scene(rootLayout);
			mainApp.getPrimaryStage().setScene(scene);
			mainApp.getPrimaryStage().show();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@FXML
	void registrateButtonClicked(ActionEvent event) {
		createRegistrationView();
	}

	public void setMainApp(MainApp mainApp) {
		this.mainApp = mainApp;
	}

	private void createMainWindowView() {
		try {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(getClass().getResource("/swt/swl/topcard/view/MainWindowView.fxml"));
			rootLayout = (Pane) loader.load();
			((RegistrationController) loader.getController()).setModel(this.model);
			Scene scene = new Scene(rootLayout);
			mainApp.getPrimaryStage().setScene(scene);
			mainApp.getPrimaryStage().show();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
}
