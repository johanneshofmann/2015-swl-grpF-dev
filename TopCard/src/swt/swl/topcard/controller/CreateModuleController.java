package swt.swl.topcard.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import swt.swl.topcard.model.ModuleModel;

/**
 * The Controller for creating Modules connects the {@code ModuleModel} with the
 * {@code RequirementCardController} Instantiated in the CreateModuleView
 */
public class CreateModuleController {

	private ModuleModel model;
	private RequirementCardController mainController;

	/**
	 * Constructor of {@code CreateModuleController} Initializes the
	 * {@code model} as new {@code model}
	 */
	public CreateModuleController() {
		model = new ModuleModel();
	}

	@FXML
	private Button createButton, cancelButton;

	@FXML
	private TextField moduleNameTextField;

	/**
	 * After clicking the {@code createButton} a new {@code module} will be
	 * created if the {@code moduleNameTextField} is not empty and the
	 * {@code module} is not already existing in the database
	 * 
	 */
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
				mainController.repaint();
			} else {
				new Alert(AlertType.WARNING, "Module with the name " + value + " already exists.").showAndWait();
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
