package swt.swl.topcard.controller.impl;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import swt.swl.topcard.controller.Controller;
import swt.swl.topcard.controller.LoginController;
import swt.swl.topcard.controller.RegistrationController;
import swt.swl.topcard.controller.RequirementCardController;
import swt.swl.topcard.model.LoginModel;

public class RegistrationControllerImpl implements Controller, RegistrationController {

	private LoginModel model;
	private LoginController loginController;

	@FXML
	private TextField firstNameTextField, lastNameTextField, loginNameTextField;

	@FXML
	private Button applyButton, cancelButton;
	private Boolean cancelTransaction = new Boolean(false);

	@FXML
	public void cancelButtonClicked(ActionEvent event) {
		cancel(event);
	}

	@FXML
	public void register(ActionEvent event) {

		String firstName = firstNameTextField.getText();
		String lastName = lastNameTextField.getText();
		String loginName = loginNameTextField.getText();

		checkEmpty();

		if (cancelTransaction) {
			event.consume();
			cancelTransaction = false;
			return;
		}

		model.insertUserIntoDatabase(firstName, lastName, loginName);
		new Alert(AlertType.INFORMATION, "Registration successful.").showAndWait();
	}

	public LoginModel getModel() {
		return model;
	}

	public void setModel(LoginModel model) {
		this.model = model;
	}

	public void setLoginController(LoginController loginWindowController) {
		this.loginController = loginWindowController;
	}

	@Override
	public void setMainController(RequirementCardController requirementCardController) {
		// not necessary here
	}

	@Override
	public void cancel(ActionEvent event) {

		Stage stage = loginController.getMainApp().getPrimaryStage();
		stage.close();
		stage.setScene(loginController.getLoginScene());
		stage.show();
	}

	@Override
	public void checkEmpty() {

		if (firstNameTextField.getText().isEmpty() || lastNameTextField.getText().isEmpty()
				|| loginNameTextField.getText().isEmpty()) {
			cancelTransaction = true;
			new Alert(AlertType.WARNING, "Field(s) should not be empty.").showAndWait();

		}
	}
}
