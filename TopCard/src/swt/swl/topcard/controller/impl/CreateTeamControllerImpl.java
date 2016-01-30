package swt.swl.topcard.controller.impl;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import swt.swl.topcard.controller.Controller;
import swt.swl.topcard.controller.CreateTeamController;
import swt.swl.topcard.controller.RequirementCardController;
import swt.swl.topcard.logic.DAOs.TeamDAO;
import swt.swl.topcard.logic.DAOs.impl.TeamDAOImpl;

public class CreateTeamControllerImpl implements Controller, CreateTeamController {

	private TeamDAO model;
	private RequirementCardController mainController;

	@FXML
	private Button createButton, cancelButton;

	@FXML
	private TextField teamNameTextField;

	public CreateTeamControllerImpl() {
		model = new TeamDAOImpl();
	}

	@FXML
	public void create() {
		// 0. Check whether a string has been entered in the text field
		checkEmpty();
		// 1. Check whether module with the name exists
		String value = this.teamNameTextField.getText();
		if (!model.hasTeam(value)) {
			// 2. Add module to the database
			model.insertTeam(value);
			new Alert(AlertType.CONFIRMATION, "Team has been added successfully.").showAndWait();
			mainController.repaint();
		} else {
			new Alert(AlertType.WARNING, "Team with the name " + value + " already exists.").showAndWait();
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
		if (!this.teamNameTextField.getText().isEmpty()) {
			new Alert(AlertType.WARNING, "Team name is empty.").showAndWait();
		}
	}
}
