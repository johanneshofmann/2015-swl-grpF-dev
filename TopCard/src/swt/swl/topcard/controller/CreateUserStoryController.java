package swt.swl.topcard.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import swt.swl.topcard.model.UserStoryModel;

public class CreateUserStoryController {

	private RequirementCardController mainController;
	private UserStoryModel model;

	@FXML
	private TextField userStoryTextField;

	@FXML
	private Button createButton, cancelButton;

	public CreateUserStoryController() {

		model = new UserStoryModel();

	}

	@FXML
	void cancel(ActionEvent event) {
		mainController.repaint();
	}

	@FXML
	void create(ActionEvent event) {

		// 0. Check whether a string has been entered in the text field
		if (!userStoryTextField.getText().isEmpty()) {

			String userStory = this.userStoryTextField.getText();

			// 1. Check whether userStory with the name exists
			if (!model.hasUserStory(userStory)) {

				// 2. Add userStory to the database
				model.insertUserStory(userStoryTextField.getText());

				new Alert(AlertType.CONFIRMATION, "UserStoriy has been added successfully.").showAndWait();
				mainController.repaint();

			} else {
				new Alert(AlertType.WARNING, "UserStory with the name " + userStory + " already exists.").showAndWait();
			}
		} else {
			new Alert(AlertType.WARNING, "UserStory name is empty.").showAndWait();

		}
	}

	public void setData(RequirementCardController requirementCardController) {
		this.mainController = requirementCardController;
	}

}
