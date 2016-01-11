package swt.swl.topcard.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import swt.swl.topcard.model.ModuleModel;

public class CreateModuleController {

	private ModuleModel model;
	private RequirementCardController mainController;

	@FXML
	private Button createButton, cancelButton;

	@FXML
	private TextField moduleNameTextField;

	@FXML
	protected void create() {
		// 0. Check whether a string has been entered in the text field
		if (!this.moduleNameTextField.getText().isEmpty()) {
			// 1. Check whether module with the name exists
			String value = this.moduleNameTextField.getText();
			if (!model.hasModule(value)) {
				// 2. Add module to the database
				model.insertModule(value);
				new Alert(AlertType.CONFIRMATION, "Module has been added successfully.").showAndWait();
			} else {
				new Alert(AlertType.WARNING, "Module with the name " + value + "already exists.").showAndWait();
			}
		} else {
			new Alert(AlertType.WARNING, "Module name is empty.").showAndWait();
		}
	}

	@FXML
	protected void cancel(ActionEvent event) {
		mainController.repaint();
		event.consume();
	}

	public void setMainController(RequirementCardController requirementCardController) {
		this.mainController = requirementCardController;
	}
}
