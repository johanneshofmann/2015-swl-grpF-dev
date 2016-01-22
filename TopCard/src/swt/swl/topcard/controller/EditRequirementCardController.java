package swt.swl.topcard.controller;

import java.util.ArrayList;

import org.controlsfx.control.CheckComboBox;

import javafx.collections.ObservableList;
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

public class EditRequirementCardController {

	private RequirementCardModel model;
	private RequirementCardController mainController;
	private RequirementCardSimple toEdit;

	private CheckComboBox<String> modulesCheckComboBox, userStoriesCheckComboBox;

	@FXML
	private CheckBox frozenChoiceBox;
	@FXML
	private TextArea descriptionTextArea, rationaleTextArea;
	@FXML
	private TextField titleTextField, ownerTextField, sourceTextField, supportingMaterialsTextField,
			fitCriterionTextField;
	@FXML
	private Button closeButton, editButton, showVoteResultsButton, deleteRequirementButton;
	@FXML
	private Label createdAtLabel, lastModifiedAtLabel, requirementCardNumberLabel, majorVersionLabel, minorVersionLabel;
	@FXML
	private Pane showVoteResultsPane;
	@FXML
	private HBox modulesHBox, userStoriesHBox;
	@FXML
	private Label descriptionPreciseVoteResultLabel, descriptionUnderstandableVoteResultLabel,
			descriptionCorrectVoteResultLabel, descriptionCompleteVoteResultLabel, descriptionAtomicVoteResultLabel,
			rationalePreciseVoteResultLabel, rationaleUnderstandableVoteResultLabel, rationaleTraceableVoteResultLabel,
			rationaleCompleteVoteResultLabel, rationaleConsistentVoteResultLabel, fitCriterionCompleteVoteResultLabel;

	public void initializeFXNodes() {

		initCheckComboBoxes();
		fillRqCardDataInTextFields();
		addEventHandlerToFrozenChoiceBox();

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
	void showVoteResultsButtonClicked() {
		showVoteResultsButton.setVisible(false);
		fillVoteResultsLabels();
		showVoteResultsPane.setVisible(true);

	}

	@FXML
	void editButtonClicked(ActionEvent event) {

		toEdit.setMinorVersion(toEdit.getMinorVersion() + 1);

		modifyRequirementCardToEdit();

		model.insertEditedRqIntoDatabase(toEdit);

		// if successful, show alert to user
		new Alert(AlertType.INFORMATION, "Changes saved.").showAndWait();
	}

	private void modifyRequirementCardToEdit() {

		toEdit.setModules(listToString(modulesCheckComboBox.getCheckModel().getCheckedItems()));
		toEdit.setDescription(descriptionTextArea.getText());
		toEdit.setRationale(rationaleTextArea.getText());
		toEdit.setSource(sourceTextField.getText());
		toEdit.setUserStories(listToString(userStoriesCheckComboBox.getCheckModel().getCheckedItems()));
		toEdit.setSupportingMaterials(supportingMaterialsTextField.getText());
		toEdit.setFitCriterion(fitCriterionTextField.getText());

	}

	private String listToString(ObservableList<String> list) {

		String string = "";

		int counter = 0;
		for (String str : list) {
			if (counter == 0) {
				counter++;
				string = str;
			}
			string += "," + str;
		}
		return string;
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
		sourceTextField.setText(toEdit.getSource());
		supportingMaterialsTextField.setText(toEdit.getSupportingMaterials());
		fitCriterionTextField.setText(toEdit.getFitCriterion());
		if (toEdit.getIsFrozen() == 1) {
			frozenChoiceBox.setSelected(true);
		}
		createdAtLabel.setText(toEdit.getCreatedAt().toString().substring(0, 19));
		lastModifiedAtLabel.setText(toEdit.getLastModifiedAt().toString());
		titleTextField.setText(toEdit.getTitle());
		majorVersionLabel.setText(String.valueOf(toEdit.getMajorVersion()));
		minorVersionLabel.setText(String.valueOf(toEdit.getMinorVersion()));
	}

	public void setData(RequirementCardModel rqModel, RequirementCardController requirementCardController,
			RequirementCardSimple toEdit) {
		this.model = rqModel;
		this.mainController = requirementCardController;
		this.toEdit = toEdit;
	}

	@SuppressWarnings("unchecked")
	private void initCheckComboBoxes() {

		// give actual Modules to the ModulesCheckComboBox

		modulesCheckComboBox = new CheckComboBox<>(model.getModules());
		modulesHBox.getChildren().add(modulesCheckComboBox);

		userStoriesCheckComboBox = new CheckComboBox<>(model.getUserStories());
		userStoriesHBox.getChildren().add(userStoriesCheckComboBox);

		int rqID = toEdit.getID();

		// check the ones set in database
		for (String module : (ArrayList<String>) model.getXNamesAsStringByRqID("Module", rqID, true)) {
			modulesCheckComboBox.getCheckModel().check(module);
		}
		for (String userStory : (ArrayList<String>) model.getXNamesAsStringByRqID("UserStory", rqID, true)) {
			userStoriesCheckComboBox.getCheckModel().check(userStory);
		}
	}

	private void addEventHandlerToFrozenChoiceBox() {

		frozenChoiceBox.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {

				toEdit.setMajorVersion(toEdit.getMajorVersion() + 1);
				toEdit.setMinorVersion(1);

				modifyRequirementCardToEdit();

				model.insertEditedRqIntoDatabase(toEdit);

				// if successful, show alert to user
				new Alert(AlertType.INFORMATION, "Saved changes.").showAndWait();

			}
		});
	}

	private void fillVoteResultsLabels() {

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

}
