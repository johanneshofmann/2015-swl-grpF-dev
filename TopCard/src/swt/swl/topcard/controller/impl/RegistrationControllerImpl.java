package swt.swl.topcard.controller.impl;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import swt.swl.topcard.controller.Controller;
import swt.swl.topcard.controller.LoginController;
import swt.swl.topcard.controller.RegistrationController;
import swt.swl.topcard.controller.RequirementCardController;
import swt.swl.topcard.controller.logic.ViewBuilder;
import swt.swl.topcard.model.LoginModel;
import swt.swl.topcard.model._Model;

public class RegistrationControllerImpl implements Controller, RegistrationController {

	private _Model model;
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

		((LoginModel) model).insertUserIntoDatabase(firstName, lastName, loginName);
		new Alert(AlertType.INFORMATION, "Registration successful.").showAndWait();
	}

	public _Model getModel() {
		return model;
	}

	public void setData(LoginController loginWindowController, _Model model) {

		this.model = model;
		this.loginController = loginWindowController;
	}

	@Override
	public void setMainController(RequirementCardController requirementCardController) {
		// not necessary here
	}

	@Override
	public void cancel(ActionEvent event) {

		ViewBuilder.changeGUI(loginController.getLoginScene());
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
