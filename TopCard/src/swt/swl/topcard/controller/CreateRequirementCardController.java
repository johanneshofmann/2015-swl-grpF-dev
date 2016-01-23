
package swt.swl.topcard.controller;

import org.controlsfx.control.CheckComboBox;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import swt.swl.topcard.model.RequirementCardModel;

public class CreateRequirementCardController implements Controller {

	private RequirementCardModel model;
	private RequirementCardController mainController;

	// need to instanciate manually cause we're using external jar
	private CheckComboBox<String> modulesCheckComboBox, userStoriesCheckComboBox, teamsCheckComboBox;

	@FXML
	private HBox moduleHBox, userStoriesHBox, sourceHBox;

	@FXML
	private TextArea descriptionTextArea, rationaleTextArea;

	@FXML
	private TextField titleTextField, supportingMaterialsTextField, fitCriterionTextField;

	@FXML
	private CheckBox frozenChoiceBox;

	@FXML
	private Button closeButton, createButton;

	public void initFXNodes() {

		frozenChoiceBox.setDisable(true);

		addActualModulesToCheckComboBox();
		addActualUserStoriesToCheckComboBox();
		addActualTeamsToCheckComboBox();
	}

	@FXML
	void closeWindow(ActionEvent event) {
		cancel(event);
	}

	/**
	 *
	 * checks weather title, description,rationale,source,fitCriterion or
	 * userstories is empty,
	 *
	 *
	 * @throws new
	 *             Alert if one is empty and returns to editable View.
	 * @param event
	 */
	@FXML
	void putUpForVote(ActionEvent event) {

		checkEmpty();

		model.insertRqIntoDatabase(modulesCheckComboBox.getCheckModel().getCheckedItems(), titleTextField.getText(),
				descriptionTextArea.getText(), rationaleTextArea.getText(),
				teamsCheckComboBox.getCheckModel().getCheckedItems(),
				userStoriesCheckComboBox.getCheckModel().getCheckedItems(), fitCriterionTextField.getText(),
				supportingMaterialsTextField.getText());
		new Alert(AlertType.INFORMATION, "Reqirement in database now.").showAndWait();
	}

	public void setData(RequirementCardModel rqModel, RequirementCardController requirementCardController) {

		this.model = rqModel;
		setMainController(requirementCardController);
	}

	private void addActualModulesToCheckComboBox() {

		// give actual Modules to the ModulesCheckComboBox

		ObservableList<String> modules = model.getModules();
		modulesCheckComboBox = new CheckComboBox<>(modules);

		moduleHBox.getChildren().add(modulesCheckComboBox);
	}

	private void addActualUserStoriesToCheckComboBox() {

		ObservableList<String> userStories = model.getUserStories();
		userStoriesCheckComboBox = new CheckComboBox<>(userStories);
		userStoriesHBox.getChildren().add(userStoriesCheckComboBox);

	}

	private void addActualTeamsToCheckComboBox() {

		ObservableList<String> teams = model.getTeams();
		teamsCheckComboBox = new CheckComboBox<>(teams);
		sourceHBox.getChildren().add(teamsCheckComboBox);

	}

	@Override
	public void setMainController(RequirementCardController requirementCardController) {
		this.mainController = requirementCardController;
	}

	@Override
	public void cancel(ActionEvent event) {

		Alert confirmation = new Alert(AlertType.CONFIRMATION, "Close without creating RQ-Card?");
		confirmation.showAndWait();
		if (confirmation.getResult().equals(ButtonType.OK)) {
			mainController.repaint();
		} else {
			event.consume();
		}
	}

	@Override
	public void checkEmpty() {

		if (titleTextField.getText().isEmpty() || descriptionTextArea.getText().isEmpty()
				|| rationaleTextArea.getText().isEmpty() || fitCriterionTextField.getText().isEmpty()) {
			if (modulesCheckComboBox.getCheckModel().getCheckedItems().size() == 0) {
				new Alert(AlertType.WARNING, "For reasons of integrity you should choose at least one Module.")
						.showAndWait();
			} else if (userStoriesCheckComboBox.getCheckModel().getCheckedItems().size() == 0) {
				new Alert(AlertType.WARNING, "For reasons of integrity you should choose at least one UserStory.")
						.showAndWait();
			} else if (teamsCheckComboBox.getCheckModel().getCheckedItems().size() == 0) {
				new Alert(AlertType.WARNING, "For reasons of integrity you should choose at least one Team as source.")
						.showAndWait();
			} else {
				new Alert(AlertType.WARNING, "For reasons of integrity these fields should not be empty.")
						.showAndWait();
			}
		}
	}

}
