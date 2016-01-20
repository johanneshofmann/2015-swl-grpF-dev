package swt.swl.topcard.logic;

import java.util.ArrayList;

import javafx.collections.ListChangeListener;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import swt.swl.topcard.controller.RequirementCardController;
import swt.swl.topcard.model.RequirementCardModel;

public class TeamChangeListener implements ListChangeListener<String> {

	private RequirementCardModel model;
	private RequirementCardController controller;

	private ArrayList<String> teamsFromUser;

	public TeamChangeListener(RequirementCardModel model, RequirementCardController controller) {

		setData(model, controller);
		teamsFromUser = checkAndGetTeamsUserIsSubscribed();
	}

	@Override
	public void onChanged(Change<? extends String> changedTeam) {

		if (controller.getCancelTransaction()) {
			System.out.println("canceltrans true;");
			return;
		}
		System.out.println("canceltrans false;");

		changedTeam.next();

		// refrech List:
		teamsFromUser = checkAndGetTeamsUserIsSubscribed();

		int actualSize = teamsFromUser.size();

		if (model.userAlreadySubscribed()) {

			// if team was added..
			if (changedTeam.wasAdded()) {

				for (String team : changedTeam.getAddedSubList()) {
					model.letUserBeMemberOf(team);

					new Alert(AlertType.INFORMATION, "You are now member of the team " + team + ".").showAndWait();
				}
				// if team was removed ..
			}
			if (changedTeam.wasRemoved()) {

				Alert removeConfirmation = null;
				String teamStr = null;

				for (String team : changedTeam.getRemoved()) {

					teamStr = team;

					if (actualSize <= 1) {

						String textRow = "You're about to leave the only team you're joined. Really leave team?";

						removeConfirmation = new Alert(AlertType.CONFIRMATION, textRow);

						removeConfirmation.getButtonTypes().set(0, new ButtonType("Cancel"));
						removeConfirmation.getButtonTypes().set(1, new ButtonType("Leave"));

					} else {
						model.letUserExitTeam(team);
						removeConfirmation = new Alert(AlertType.INFORMATION, "Left team " + team + ".");
					}
				}
				removeConfirmation.showAndWait();

				if (removeConfirmation.getResult().getText().toString().equals("Leave")) {

					model.letUserExitTeam(teamStr);
					new Alert(AlertType.INFORMATION, "Left team " + teamStr + ".").showAndWait();
				} else {
					controller.setCancelTransaction(true);
					// try {
					controller.checkTeam(teamStr);
					// } catch (UnsupportedOperationException e) {
					// ignore;
					// }
					removeConfirmation.close();
				}
			}
		} else {

			// simply let user enter team..
			model.letUserBeMemberOf(controller.getFirstTeam());
			new Alert(AlertType.INFORMATION, "You are now member of the team " + controller.getFirstTeam() + ".")
					.showAndWait();
		}
	}

	private ArrayList<String> checkAndGetTeamsUserIsSubscribed() {

		ArrayList<String> teamsFromUser = new ArrayList<>();

		for (String team : model.getTeamsUserIsSubscribed()) {

			teamsFromUser.add(team);
			controller.checkTeam(team);
		}
		return teamsFromUser;
	}

	public void setData(RequirementCardModel model, RequirementCardController requirementCardController) {
		this.model = model;
		this.controller = requirementCardController;
	}

}
