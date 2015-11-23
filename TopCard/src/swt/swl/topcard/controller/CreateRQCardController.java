package swt.swl.topcard.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import swt.swl.topcard.model.RequirementCardModel;

public class CreateRQCardController {

	private RequirementCardModel model;

	@FXML
	private MenuButton modulNameChoiceBox;

	@FXML
	private MenuItem modul1MenuItem, modul2MenuItem, modul3MenuItem, modul4MenuItem;

	@FXML
	private TextArea descriptionTextArea, rationaleTextArea;

	@FXML
	private TextField titleTextField, sourceTextField, userStoriesTextField, supportingMaterialsTextField,
			fitCriterionTextField;

	@FXML
	private CheckBox frozenChoiceBox;

	@FXML
	private Button closeButton, createButton;

	@FXML
	void closeWindow(ActionEvent event) {
		//TODO:
	}

	/**
	 *
	 * checks weather title, description,rationale,source,fitCriterion or
	 * userstories is empty,
	 *
	 * @throws new
	 *             Alert if one is empty and returns to editable View.
	 * @param event
	 */
	@FXML
	void putUpForVote(ActionEvent event) {

		if (titleTextField.getText().isEmpty() || descriptionTextArea.getText().isEmpty()
				|| rationaleTextArea.getText().isEmpty() || sourceTextField.getText().isEmpty()
				|| userStoriesTextField.getText().isEmpty() || fitCriterionTextField.getText().isEmpty()|| supportingMaterialsTextField.getText().isEmpty()) {
			new Alert(AlertType.WARNING, "For reasons of integrity these fields should not be empty.").showAndWait();
		} else {
			model.insertRQIntoDatabase(titleTextField.getText(), descriptionTextArea.getText(),
					rationaleTextArea.getText(), sourceTextField.getText(), userStoriesTextField.getText(),
					fitCriterionTextField.getText(),supportingMaterialsTextField.getText(),frozenChoiceBox.isSelected());
		}
	}

	public void setModel(RequirementCardModel model) {
		this.model = model;
	}

}
