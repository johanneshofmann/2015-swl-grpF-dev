package swt.swl.topcard.controller;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;

import org.controlsfx.control.CheckComboBox;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import swt.swl.topcard.logic.RequirementCardSimple;
import swt.swl.topcard.logic.SubmittedVoteSimple;
import swt.swl.topcard.model.RequirementCardModel;

public class EditRequirementCardController implements Controller {

	private RequirementCardModel model;
	private RequirementCardController mainController;
	private RequirementCardSimple toEdit;

	private CheckComboBox<String> modulesCheckComboBox, userStoriesCheckComboBox, teamsCheckComboBox;

	@FXML
	private CheckBox frozenChoiceBox;
	@FXML
	private TextArea descriptionTextArea, rationaleTextArea;
	@FXML
	private TextField titleTextField, ownerTextField, supportingMaterialsTextField, fitCriterionTextField;
	@FXML
	private Button closeButton, editButton, showVoteResultsButton, deleteRequirementButton;
	@FXML
	private Label createdAtLabel, lastModifiedAtLabel, requirementCardNumberLabel, majorVersionLabel, minorVersionLabel;
	@FXML
	private Pane showVoteResultsPane;
	@FXML
	private HBox modulesHBox, userStoriesHBox, sourceHBox;
	@FXML
	private Label descriptionPreciseVoteResultLabel, descriptionUnderstandableVoteResultLabel,
			descriptionCorrectVoteResultLabel, descriptionCompleteVoteResultLabel, descriptionAtomicVoteResultLabel,
			rationalePreciseVoteResultLabel, rationaleUnderstandableVoteResultLabel, rationaleTraceableVoteResultLabel,
			rationaleCompleteVoteResultLabel, rationaleConsistentVoteResultLabel, fitCriterionCompleteVoteResultLabel;

	public void initializeFXNodes() {

		initCheckComboBoxes();
		fillRqCardDataInTextFields();
		addEventHandlerToFrozenChoiceBox();
		fillVoteResultsLabels();

		// all editable but..
		ownerTextField.setEditable(false);
		titleTextField.setEditable(false);
	}

	@FXML
	void closeWindow(ActionEvent event) {

		Alert closeConfirmation = new Alert(AlertType.CONFIRMATION, "Close without saving ?");
		closeConfirmation.showAndWait();

		ButtonType choice = closeConfirmation.getResult();

		if (choice == ButtonType.OK) {

			mainController.repaint();
			event.consume();
			closeConfirmation.close();

		} else {

			event.consume();
		}
	}

	@FXML
	void editButtonClicked(ActionEvent event) {

		checkEmpty();

		model.insertEditedRqIntoDatabase(modulesCheckComboBox.getCheckModel().getCheckedItems(),
				titleTextField.getText(), Integer.parseInt(requirementCardNumberLabel.getText()),
				Integer.parseInt(majorVersionLabel.getText()), (Integer.parseInt(minorVersionLabel.getText()) + 1),
				descriptionTextArea.getText(), rationaleTextArea.getText(),
				teamsCheckComboBox.getCheckModel().getCheckedItems(),
				userStoriesCheckComboBox.getCheckModel().getCheckedItems(), fitCriterionTextField.getText(),
				supportingMaterialsTextField.getText(), false, getCreatedAt());

		// if successful, show alert to user
		new Alert(AlertType.INFORMATION, "Changes saved.").showAndWait();
	}

	private Timestamp getCreatedAt() {

		String date = createdAtLabel.getText();

		String[] yearMonthDate = date.substring(0, 11).split("-");
		String[] hourMinuteSecond = date.substring(11, 19).split(":");

		Calendar cal = Calendar.getInstance();
		cal.set(Integer.parseInt(yearMonthDate[0]), Integer.parseInt(yearMonthDate[1]) - 1,
				Integer.parseInt(yearMonthDate[2].replaceAll(" ", "")), Integer.parseInt(hourMinuteSecond[0]),
				Integer.parseInt(hourMinuteSecond[1]), Integer.parseInt(hourMinuteSecond[2]));

		return new Timestamp(cal.getTimeInMillis());

	}

	@FXML
	void deleteRequirementButtonClicked(ActionEvent event) {
		model.deleteRequirement(requirementCardNumberLabel.getText(), majorVersionLabel.getText(),
				minorVersionLabel.getText());
	}

	private void fillRqCardDataInTextFields() {

		// assign data to the displayed Nodes:

		ownerTextField.setText(toEdit.getOwnerName());

		String[] modules = toEdit.getModules().split(",");
		for (int i = 0; i < modules.length; i++) {
			modulesCheckComboBox.getCheckModel().check(modules[i]);
		}
		requirementCardNumberLabel.setText(String.valueOf(toEdit.getRqID()));
		descriptionTextArea.setText(toEdit.getDescription());
		rationaleTextArea.setText(toEdit.getRationale());
		supportingMaterialsTextField.setText(toEdit.getSupportingMaterials());
		fitCriterionTextField.setText(toEdit.getFitCriterion());
		createdAtLabel.setText(toEdit.getCreatedAt().toString().substring(0, 19));
		lastModifiedAtLabel.setText(toEdit.getLastModifiedAt().toString());
		titleTextField.setText(toEdit.getTitle());
		majorVersionLabel.setText(String.valueOf(toEdit.getMajorVersion()));
		minorVersionLabel.setText(String.valueOf(toEdit.getMinorVersion()));
		if (Integer.parseInt(minorVersionLabel.getText()) == 0) {
			editButton.setText("Put up for Vote");
		}
		frozenChoiceBox.setSelected(true);
	}

	@SuppressWarnings("unchecked")
	private void initCheckComboBoxes() {

		// give actual Modules to the ModulesCheckComboBox

		modulesCheckComboBox = new CheckComboBox<>(model.getModules());
		modulesHBox.getChildren().add(modulesCheckComboBox);

		userStoriesCheckComboBox = new CheckComboBox<>(model.getUserStories());
		userStoriesHBox.getChildren().add(userStoriesCheckComboBox);

		teamsCheckComboBox = new CheckComboBox<>(model.getTeams());
		sourceHBox.getChildren().add(teamsCheckComboBox);

		int rqID = toEdit.getID();

		// check the ones set in database
		for (String module : (ArrayList<String>) model.getXNamesAsStringByRqID("Module", rqID, true)) {
			modulesCheckComboBox.getCheckModel().check(module);
		}
		for (String userStory : (ArrayList<String>) model.getXNamesAsStringByRqID("UserStory", rqID, true)) {
			userStoriesCheckComboBox.getCheckModel().check(userStory);
		}
		for (String team : (ArrayList<String>) model.getXNamesAsStringByRqID("Team", rqID, true)) {
			teamsCheckComboBox.getCheckModel().check(team);
		}
	}

	private void addEventHandlerToFrozenChoiceBox() {

		frozenChoiceBox.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {

				checkEmpty();

				model.insertEditedRqIntoDatabase(modulesCheckComboBox.getCheckModel().getCheckedItems(),
						titleTextField.getText(), Integer.parseInt(requirementCardNumberLabel.getText()),
						Integer.parseInt(majorVersionLabel.getText()),
						(Integer.parseInt(minorVersionLabel.getText()) + 1), descriptionTextArea.getText(),
						rationaleTextArea.getText(), teamsCheckComboBox.getCheckModel().getCheckedItems(),
						userStoriesCheckComboBox.getCheckModel().getCheckedItems(), fitCriterionTextField.getText(),
						supportingMaterialsTextField.getText(), true, getCreatedAt());

				// if successful, show alert to user
				new Alert(AlertType.INFORMATION, "Saved changes.").showAndWait();
			}
		});
	}

	private void fillVoteResultsLabels() {

		if (model.noVotesSubmitted(toEdit.getID())) {
			return;
		}
		SubmittedVoteSimple voteResult = model.getVoteResults(toEdit.getID());

		descriptionPreciseVoteResultLabel.setText("" + voteResult.getDescriptionPrecise());
		descriptionUnderstandableVoteResultLabel.setText("" + voteResult.getDescriptionUnderstandable());
		descriptionCorrectVoteResultLabel.setText("" + voteResult.getDescriptionCorrect());
		descriptionCompleteVoteResultLabel.setText("" + voteResult.getDescriptionComplete());
		descriptionAtomicVoteResultLabel.setText("" + voteResult.getDescriptionAtomic());
		rationalePreciseVoteResultLabel.setText("" + voteResult.getRationalePrecise());
		rationaleUnderstandableVoteResultLabel.setText("" + voteResult.getRationaleUnderstandable());
		rationaleTraceableVoteResultLabel.setText("" + voteResult.getRationaleTraceable());
		rationaleCompleteVoteResultLabel.setText("" + voteResult.getRationaleComplete());
		rationaleConsistentVoteResultLabel.setText("" + voteResult.getRationaleConsistent());
		fitCriterionCompleteVoteResultLabel.setText("" + voteResult.getFitCriterionCorrect());
	}

	public void setData(RequirementCardModel rqModel, RequirementCardController requirementCardController,
			RequirementCardSimple toEdit) {
		this.model = rqModel;
		setMainController(requirementCardController);
		this.toEdit = toEdit;
	}

	@Override
	public void setMainController(RequirementCardController requirementCardController) {
		this.mainController = requirementCardController;
	}

	@Override
	public void cancel(ActionEvent event) {
		// already implemented
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