package swt.swl.topcard.controller;

import java.util.ArrayList;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import swt.swl.topcard.model.RequirementCardModel;

public class SubscribeConfirmationDialogueController {

	private RequirementCardController mainController;
	private RequirementCardModel model;

	@FXML
	private Button cancelButton, onNewTeamButton, onAllButton;

	private ArrayList<String> newTeams;

	@FXML
	void cancel(ActionEvent event) {
		mainController.repaint();
	}

	@FXML
	void onAllButtonClicked(ActionEvent event) {

		// model.letUserBeMemberOf(newTeam);
	}

	@FXML
	void onNewTeamButtonClicked(ActionEvent event) {

		// model.letUserBeMemberOf(newTeam);
		// model.letUserExitTeam(oldTeam);
	}

	public void setData(RequirementCardModel model, RequirementCardController mainController,
			ArrayList<String> newTeams) {
		this.model = model;
		this.mainController = mainController;
		this.newTeams = newTeams;
	}

}
