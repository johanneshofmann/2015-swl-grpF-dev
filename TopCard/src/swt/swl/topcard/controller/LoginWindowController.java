package swt.swl.topcard.controller;

import java.util.Observable;
import java.util.Observer;

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

public class LoginWindowController implements Observer{
	private LoginModel model;
	private MainApp mainApp;
	private Pane rootLayout;
	private Scene scene;

	@FXML
	private Button loginButton, registrateButton;

	@FXML
	private TextField userNameTextField;

	public LoginWindowController() {
		model = new LoginModel();
		model.addObserver(this);
	}

	@FXML
	void loginButtonClicked(ActionEvent event) {

		boolean isInDatabase = model.checkDatabase(userNameTextField.getText());

		if (isInDatabase) {
			createMainWindowView(userNameTextField.getText());
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

	@FXML
	void registrateButtonClicked(ActionEvent event) {
		createRegistrationView();
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

	private void createMainWindowView(String loginName) {
		try {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(getClass().getResource("/swt/swl/topcard/view/MainWindowView.fxml"));
			rootLayout = (Pane) loader.load();
			((MainWindowController) loader.getController()).setLoginName(loginName);
			((MainWindowController) loader.getController()).setMainApp(mainApp);
			scene = new Scene(rootLayout);
			mainApp.getPrimaryStage().setScene(scene);
			mainApp.getPrimaryStage().show();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	@Override
	public void update(Observable o, Object message) {
	
			createMainWindowView(message.toString());
	}

	public void setMainApp(MainApp mainApp) {
		this.mainApp = mainApp;
	}

	public Scene getScene() {
		return scene;
	}

	public void setScene(Scene scene) {
		this.scene = scene;
	}
}

