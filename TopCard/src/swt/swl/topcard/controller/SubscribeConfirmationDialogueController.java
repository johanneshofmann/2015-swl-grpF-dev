package swt.swl.topcard.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import swt.swl.topcard.model.RequirementCardModel;

public class SubscribeConfirmationDialogueController {

	private RequirementCardController mainController;
	private RequirementCardModel model;

	@FXML
	private Button cancelButton, rightButton, leftButton;
	@FXML
	private Label confirmationMessageRowOneLabel, confirmationMessageRowTwoLabel;

	private String changedTeam;
	private boolean teamAdded;

	@FXML
	void cancel(ActionEvent event) {

		mainController.restoreChangedTeam(changedTeam, teamAdded);
		mainController.repaint();
	}

	@FXML
	void rightButtonClicked(ActionEvent event) {

		if (rightButton.getText().equals("On all")) {
			model.letUserBeMemberOf(changedTeam);

			new Alert(AlertType.INFORMATION, "On all teams subscribed.").showAndWait();
			mainController.repaint();
		}
		if (rightButton.getText().startsWith("Only on ")) {

			for (String team : mainController.getRqModel().getTeamsUserIsSubscribed()) {
				mainController.restoreChangedTeam(team, true);
			}
			model.letUserExitAllTeams();
			model.letUserBeMemberOf(changedTeam);
			mainController.restoreChangedTeam(changedTeam, false);

			new Alert(AlertType.INFORMATION, "You exited all teams but the one you have chosen.").showAndWait();
			mainController.repaint();
		}
	}

	@FXML
	void leftButtonClicked(ActionEvent event) {

		if (leftButton.getText().equals("Leave")) {

			model.letUserExitTeam(changedTeam);
			mainController.restoreChangedTeam(changedTeam, true);

			new Alert(AlertType.INFORMATION, "Left team " + changedTeam + ".").showAndWait();
			mainController.repaint();
		}
		if (leftButton.getText().equals("On all")) {

			model.letUserBeMemberOf(changedTeam);

			new Alert(AlertType.INFORMATION, "On all teams subscribed.").showAndWait();
			mainController.repaint();
		}
	}

	public void setData(RequirementCardModel model, RequirementCardController mainController, String changedTeam,
			String textRow1, String textRow2, String[] leftButtonConfig, String[] rightButtonConfig) {

		// first set necessary mvc members:
		this.model = model;
		this.mainController = mainController;

		// then the team that changed:
		this.changedTeam = changedTeam;

		// now put parameters textRow1/textRow2 in the specific labels:
		this.confirmationMessageRowOneLabel.setText(textRow1);
		this.confirmationMessageRowTwoLabel.setText(textRow2);

		// processing data given with the *Config parameters..

		// first config left button:

		this.leftButton.setVisible(leftButtonConfig[0].equals("true"));

		this.leftButton.setText(leftButtonConfig[1]);

		// then right button:

		this.rightButton.setVisible(rightButtonConfig[0].equals("true"));

		this.rightButton.setText(rightButtonConfig[1]);

		// is used later atcancel(ActionEvent event);
		this.teamAdded = rightButton.isVisible();
		System.out.println(teamAdded);
	}
}
