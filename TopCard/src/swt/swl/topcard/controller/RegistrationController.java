package swt.swl.topcard.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import swt.swl.topcard.model.LoginModel;

public class RegistrationController {

	private LoginModel model;
	private LoginController loginController;

	@FXML
	private TextField firstNameTextField, lastNameTextField, loginNameTextField;

	@FXML
	private Button applyButton, cancelButton;

	@FXML
	void cancelButtonClicked(ActionEvent event) {
		Stage stage = loginController.getMainApp().getPrimaryStage();
		stage.close();
		stage.setScene(loginController.getLoginScene());
		stage.show();
	}

	@FXML
	void register(ActionEvent event) {

		String firstName = firstNameTextField.getText();
		String lastName = lastNameTextField.getText();
		String loginName = loginNameTextField.getText();

		if (firstName.isEmpty() || lastName.isEmpty() || loginName.isEmpty()) {
			new Alert(AlertType.WARNING, "Field(s) should not be empty.").showAndWait();

		} else {
			model.insertUserIntoDatabase(firstName, lastName, loginName);
			new Alert(AlertType.INFORMATION, "Registration successful.").showAndWait();
		}
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
}
