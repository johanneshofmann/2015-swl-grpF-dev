package swt.swl.topcard.controller;

import java.util.Observable;
import java.util.Observer;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import swt.swl.topcard.MainApp;
import swt.swl.topcard.model.LoginModel;

public class LoginWindowController implements Observer {
	private LoginModel model;
	private MainApp mainApp;
	private Pane rootLayout;
	private Scene thisLoginScene, requirementCardViewScene;

	@FXML
	private Button loginButton, registerButton;

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
			createRequirementCardView(userNameTextField.getText());
		} else {
			Alert al = new Alert(AlertType.CONFIRMATION, "Not registrated. Registrate now ? ");
			al.showAndWait();
			String confirmation = al.getResult().getText();
			if (confirmation.equals("OK")) {
				mainApp.getPrimaryStage().close();
				createRegistrationView();
				event.consume();
			} else {
				al.close();
			}
		}
	}

	@FXML
	void registerButtonClicked(ActionEvent event) {
		createRegistrationView();
	}

	private void createRegistrationView() {
		try {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(getClass().getResource("/swt/swl/topcard/view/RegistrationView.fxml"));
			rootLayout = (Pane) loader.load();
			((RegistrationController) loader.getController()).setModel(this.model);
			((RegistrationController) loader.getController()).setLoginController(this);
			Scene scene = new Scene(rootLayout);
			mainApp.getPrimaryStage().setScene(scene);
			mainApp.getPrimaryStage().show();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void createRequirementCardView(String loginName) {
		try {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(getClass().getResource("/swt/swl/topcard/view/RequirementCardView.fxml"));
			rootLayout = (Pane) loader.load();
			((RequirementCardController) loader.getController()).setData(loginName, mainApp, this);
			((RequirementCardController) loader.getController()).initializeFXNodes();
			requirementCardViewScene = new Scene(rootLayout);
			mainApp.getPrimaryStage().setScene(requirementCardViewScene);
			mainApp.getPrimaryStage().show();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void update(Observable o, Object message) {

		createRequirementCardView(message.toString());
	}

	public void setData(MainApp mainApp, Scene loginScene) {
		this.mainApp = mainApp;
		setLoginScene(loginScene);
	}

	public MainApp getMainApp() {
		return mainApp;
	}

	public Scene getRequirementCardViewScene() {
		return requirementCardViewScene;
	}

	public void setLoginScene(Scene scene) {
		this.thisLoginScene = scene;
	}

	public Scene getLoginScene() {
		this.userNameTextField.setText("");
		return thisLoginScene;
	}
}
