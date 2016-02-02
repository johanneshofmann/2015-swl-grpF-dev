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
	private MainApp mainApp;
	private Scene thisLoginScene, requirementCardViewScene;

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
		System.out.println("enter pressed entered.....");
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

	public void setData(MainApp mainApp, Scene loginScene) {

		this.mainApp = mainApp;
		setLoginScene(loginScene);
	}

	public MainApp getMainApp() {
		return mainApp;
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
		this.thisLoginScene = scene;
	}

	public Scene getLoginScene() {
		this.userNameTextField.setText("");
		return thisLoginScene;
	}

	@Override
	public void setMainController(RequirementCardController requirementCardController) {
		// not necessary here..
	}

	@Override
	public void cancel(ActionEvent event) {
		// not necessary here..

	}

	@Override
	public void checkEmpty() {

		if (userNameTextField.getText().isEmpty()) {
			cancelEvent = true;
			Alert al = new Alert(AlertType.INFORMATION, "No username given, please enter one. ");

			// TODO: k??nnte man mal bei allen Alerts die so gezeigt werden
			// machen..
			al.setTitle("No username given");

			al.showAndWait();
		}

	}

	@Override
	public void setData(RequirementCardController mainController, Scene loginScene) {
		// TODO Auto-generated method stub

	}

	@Override
	public void registerOnModel() {
		((Observable) model).addObserver(this);
	}

}
