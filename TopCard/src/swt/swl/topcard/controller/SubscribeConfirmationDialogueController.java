package swt.swl.topcard.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
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

	private String team;

	@FXML
	void cancel(ActionEvent event) {
		mainController.repaint();
	}

	@FXML
	void rightButtonButtonClicked(ActionEvent event) {

		if (rightButton.getText().equals("On all")) {
			model.letUserBeMemberOf(team);
		}
		if (rightButton.getText().startsWith("Only on ")) {

			model.letUserExitAllTeams();

			model.letUserBeMemberOf(team);
		}
	}

	@FXML
	void leftButtonButtonClicked(ActionEvent event) {

		if (leftButton.getText().equals("Leave")) {
			model.letUserExitTeam(team);
		}
		if (leftButton.getText().equals("On all")) {
			model.letUserBeMemberOf(team);
		}
	}

	public void setData(RequirementCardModel model, RequirementCardController mainController, String team,
			String textRow1, String textRow2, String button1Config, String button2Config) {

		this.model = model;
		this.mainController = mainController;
		this.team = team;

		this.confirmationMessageRowOneLabel.setText(textRow1);
		this.confirmationMessageRowTwoLabel.setText(textRow2);

		this.leftButton.setVisible(button1Config.startsWith("t"));

		String leftButtonText = button1Config.replaceAll("true,", "");
		leftButtonText = leftButtonText.replaceAll("false,", "");

		this.leftButton.setText(leftButtonText);

		this.rightButton.setVisible(button2Config.startsWith("t"));

		String rightButtonText = button2Config.replaceAll("true,", "");
		rightButtonText = rightButtonText.replaceAll("false,", "");

		this.leftButton.setText(rightButtonText);
	}

}
