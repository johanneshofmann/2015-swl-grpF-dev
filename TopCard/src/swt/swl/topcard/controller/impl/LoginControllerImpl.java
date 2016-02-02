package swt.swl.topcard.controller.impl;

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
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import swt.swl.topcard.MainApp;
import swt.swl.topcard.MainAppImpl;
import swt.swl.topcard.controller.Controller;
import swt.swl.topcard.controller.LoginController;
import swt.swl.topcard.controller.RequirementCardController;
import swt.swl.topcard.controller.logic.impl.ViewBuilderImpl;
import swt.swl.topcard.logic.DAOs.mvc.impl.ModelDAOImpl;
import swt.swl.topcard.controller.logic.ViewBuilder;
import swt.swl.topcard.model.LoginModel;
import swt.swl.topcard.model._Model;
import swt.swl.topcard.model._impl.LoginModelImpl;

public class LoginControllerImpl implements Observer, Controller, LoginController {

	private LoginModel model;
	private MainApp mainApp;
	private Pane rootLayout;
	private Scene thisLoginScene, requirementCardViewScene;

	private ViewBuilder viewBuilder;

	private Boolean cancelEvent = false;

	@FXML
	private Button loginButton, registerButton;

	@FXML
	private TextField userNameTextField;

	public LoginControllerImpl() {

		model = new LoginModelImpl();
		ModelDAOImpl.models.put("Login", (_Model) model);

		((Observable) model).addObserver(this);
	}

	@FXML
	public void enterPressed(KeyEvent ke) {
		if (ke.getCode().equals(KeyCode.ENTER)) {
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
				MainAppImpl.primaryStage.close();
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

		ViewBuilderImpl i = new ViewBuilderImpl(null);

		i.buildView("Registration", this);
	}

	private void createRequirementCardView(String loginName) {

		viewBuilder.buildView("RequirementCard", loginName, this);
		try {

			FXMLLoader loader = new FXMLLoader();

			loader.setLocation(getClass().getResource("/swt/swl/topcard/view/RequirementCardView.fxml"));
			rootLayout = (Pane) loader.load();
			((RequirementCardControllerImpl) loader.getController()).setData(loginName, this);
			((RequirementCardControllerImpl) loader.getController()).initializeFXNodes();
			requirementCardViewScene = new Scene(rootLayout);

			MainAppImpl.primaryStage.setScene(requirementCardViewScene);
			MainAppImpl.primaryStage.show();

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

}
