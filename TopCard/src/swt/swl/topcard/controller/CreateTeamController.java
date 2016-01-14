package swt.swl.topcard.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import swt.swl.topcard.model.TeamModel;

public class CreateTeamController {

	private TeamModel model;
	private RequirementCardController mainController;

	@FXML
	private Button createButton, cancelButton;

	@FXML
	private TextField teamNameTextField;

	public CreateTeamController() {
		model = new TeamModel();
	}

	@FXML
	protected void create() {
		// 0. Check whether a string has been entered in the text field
		if (!this.teamNameTextField.getText().isEmpty()) {
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
		} else {
			new Alert(AlertType.WARNING, "Team name is empty.").showAndWait();
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
