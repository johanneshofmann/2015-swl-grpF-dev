package swt.swl.topcard.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import swt.swl.topcard.model.LoginModel;
import javafx.scene.control.Alert.AlertType;

public class RegistrationController {

	private LoginModel model;

	@FXML
	private TextField firstNameTextField, lastNameTextField, loginNameTextField;

	@FXML
	private Button applyButton;

	@FXML
	void registrate(ActionEvent event) {

		String firstName = firstNameTextField.getText();
		String lastName = lastNameTextField.getText();
		String loginName = loginNameTextField.getText();
		
		if (firstName.isEmpty() || lastName.isEmpty()
				|| loginName.isEmpty()) {
			new Alert(AlertType.WARNING, "Field(s) should not be empty.").showAndWait();

		} else {
			model.insertUserIntoDatabase(firstName,lastName,loginName);
			new Alert(AlertType.INFORMATION, "Registration successful.").showAndWait();
		}
	}

	public LoginModel getModel() {
		return model;
	}

	public void setModel(LoginModel model) {
		this.model = model;
	}
}
