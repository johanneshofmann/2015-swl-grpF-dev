package swt.swl.topcard.controller.impl;

import java.util.Observable;
import java.util.Observer;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import swt.swl.topcard.MainApp;
import swt.swl.topcard.MainAppImpl;
import swt.swl.topcard.controller.Controller;
import swt.swl.topcard.controller.LoginController;
import swt.swl.topcard.controller.RequirementCardController;
import swt.swl.topcard.controller.logic.ViewBuilderImpl;
import swt.swl.topcard.logic.DAOs.mvc.impl.ControllerDAOImpl;
import swt.swl.topcard.logic.DAOs.mvc.impl.ModelDAOImpl;
import swt.swl.topcard.model.LoginModel;
import swt.swl.topcard.model._Model;
import swt.swl.topcard.model._impl.LoginModelImpl;

public class LoginControllerImpl implements Observer, Controller, LoginController {

	private LoginModel model;
	private Scene loginScene, requirementCardViewScene;

	private Boolean cancelEvent = false;

	@FXML
	private Button loginButton, registerButton;

	@FXML
	private TextField userNameTextField;

	public LoginControllerImpl() {

		model = new LoginModelImpl();
		ModelDAOImpl.models.put("Login", (_Model) model);

		registerOnModel();
	}

	@FXML
	public void enterPressed(KeyEvent key) {

		if (key.getCode().equals(KeyCode.ENTER)) {
			System.out.println("Enter Pressed");
			loginButton.fire();
		}
	}

	@FXML
	public void loginButtonClicked(ActionEvent event) {

		checkEmpty();

		if (cancelEvent) {
			event.consume();
			cancelEvent = false;
			return;
		}

		boolean isInDatabase = model.checkDatabase(userNameTextField.getText());

		if (isInDatabase) {
			createRequirementCardView(userNameTextField.getText());
		} else {
			Alert al = new Alert(AlertType.CONFIRMATION, "Not registrated. Registrate now ? ");
			al.setTitle("Not registrated");
			al.showAndWait();
			String confirmation = al.getResult().getText();
			if (confirmation.equals("OK")) {
				createRegistrationView();
				event.consume();
			} else {
				al.close();
			}
		}
	}

	@FXML
	public void registerButtonClicked(ActionEvent event) {
		createRegistrationView();
	}

	private void createRegistrationView() {

		ViewBuilderImpl.INSTANCE.buildView("Registration", this);
	}

	public void createRequirementCardView(String loginName) {

		// fetch pre-loaded view
		MainAppImpl.vB.buildView("RequirementCard");

		// fetch corresponding controller
		RequirementCardController controller = (RequirementCardController) ControllerDAOImpl.controllers
				.get("RequirementCard");

		// and call setData()-method in the common way..
		controller.setData(userNameTextField.getText(), this);
	}

	@Override
	public void update(Observable o, Object message) {

		createRequirementCardView(message.toString());
	}

	public void setLoginScene(MainApp mainApp, Scene loginScene) {

		setLoginScene(loginScene);
	}

	public _Model getModel() {
		return (_Model) model;
	}

	public void setModel(LoginModel model) {
		this.model = model;
	}

	public Scene getRequirementCardViewScene() {
		return requirementCardViewScene;
	}

	public void setLoginScene(Scene scene) {
		this.loginScene = scene;
	}

	public Scene getLoginScene() {
		this.userNameTextField.setText("");
		return loginScene;
	}

	@Override
	public void cancel(ActionEvent event) {
		// not necessary here.. only cancel option is exit programm.
		throw new UnsupportedOperationException("Not supported. Only cancel operation is exit programm.");
	}

	@Override
	public void checkEmpty() {

		if (userNameTextField.getText().isEmpty()) {
			cancelEvent = true;
			Alert al = new Alert(AlertType.INFORMATION, "No username given, please enter one. ");

			al.setTitle("No username given");

			al.showAndWait();
		}
	}

	public void setData(RequirementCardController mainController, Scene requirementCardScene) {

		setScenes(requirementCardScene);
	}

	@Override
	public void setMainController(RequirementCardController requirementCardController) {
		// not necessary here..
	}

	@Override
	public void registerOnModel() {
		((Observable) model).addObserver(this);
	}

	@Override
	public void setScenes(Scene sc) {
		this.loginScene = MainAppImpl.vB.getSystemScenes().get("Login");
		this.requirementCardViewScene = sc;
	}

}
