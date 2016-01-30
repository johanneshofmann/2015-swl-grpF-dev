package swt.swl.topcard.controller.impl;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import swt.swl.topcard.controller.Controller;
import swt.swl.topcard.controller.CreateModuleController;
import swt.swl.topcard.controller.RequirementCardController;
import swt.swl.topcard.logic.DAOs.ModuleDAO;
import swt.swl.topcard.logic.DAOs.impl.ModuleDAOImpl;

public class CreateModuleControllerImpl implements Controller, CreateModuleController {

	private ModuleDAO model;
	private RequirementCardController mainController;

	public CreateModuleControllerImpl() {
		// create the model :
		model = new ModuleDAOImpl();
	}

	@FXML
	private Button createButton, cancelButton;

	@FXML
	private TextField moduleNameTextField;

	@FXML
	public void create() {

		// 0. Check whether a string has been entered in the text field
		checkEmpty();

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
	}

	@FXML
	public void cancel(ActionEvent event) {

		mainController.repaint();
		event.consume();
	}

	@Override
	public void setMainController(RequirementCardController requirementCardController) {

		this.mainController = requirementCardController;
	}

	@Override
	public void checkEmpty() {

		if (moduleNameTextField.getText().isEmpty()) {
			new Alert(AlertType.WARNING, "Module name is empty.").showAndWait();
		}
	}
}
