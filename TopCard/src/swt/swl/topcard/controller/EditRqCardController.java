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
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import swt.swl.topcard.logic.RequirementCardSimple;
import swt.swl.topcard.logic.SubmittedVoteSimple;
import swt.swl.topcard.model.RequirementCardModel;

public class EditRqCardController {

	private RequirementCardModel model;
	private RequirementCardController mainController;
	private RequirementCardSimple toEdit;

	private CheckComboBox<String> modulesCheckComboBox;

	@FXML
	private CheckBox frozenChoiceBox;

	@FXML
	private TextArea descriptionTextArea, rationaleTextArea;

	@FXML
	private TextField titleTextField, ownerTextField, sourceTextField, userStoriesTextField,
			supportingMaterialsTextField, fitCriterionTextField;

	@FXML
	private Button closeButton, editButton, showVoteResultsButton;

	@FXML
	private Label createdAtLabel, lastModifiedAtLabel, requirementCardNumberLabel, majorVersionLabel, minorVersionLabel;

	@FXML
	private Pane showVoteResultsPane;
	@FXML
	private HBox moduleHBox;
	@FXML
	private Label descriptionPreciseVoteResultLabel, descriptionUnderstandableVoteResultLabel,
			descriptionCorrectVoteResultLabel, descriptionCompleteVoteResultLabel, descriptionAtomicVoteResultLabel,
			rationalePreciseVoteResultLabel, rationaleUnderstandableVoteResultLabel, rationaleTraceableVoteResultLabel,
			rationaleCompleteVoteResultLabel, rationaleConsistentVoteResultLabel, fitCriterionCompleteVoteResultLabel;

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
		// TODO: EditRequirementCardController:

		// insert
		model.insertRqIntoDatabase(null, titleTextField.getText(), descriptionTextArea.getText(),
				rationaleTextArea.getText(), sourceTextField.getText(), userStoriesTextField.getText(),
				fitCriterionTextField.getText(), supportingMaterialsTextField.getText(), frozenChoiceBox.isSelected());
		new Alert(AlertType.INFORMATION, "Reqirement in database now.").showAndWait();
	}

	public void setData(RequirementCardModel rqModel, RequirementCardController requirementCardController) {
		this.model = rqModel;
		this.mainController = requirementCardController;
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

	private void fillTextFields() {
		// fetch data:
		RequirementCardSimple data = model.getOverviewDataFromSelectedRq(toEdit);

		// assign it to the displayed Nodes:

		ownerTextField.setText(data.getOwnerName());

		String[] modules = data.getModules().split(",");
		for (int i = 0; i < modules.length; i++) {
			modulesCheckComboBox.getCheckModel().check(modules[i]);
		}
		requirementCardNumberLabel.setText(String.valueOf(data.getRqID()));
		descriptionTextArea.setText(data.getDescription());
		rationaleTextArea.setText(data.getRationale());
		sourceTextField.setText(data.getSource());
		// TODO: userStoriesTextField.setText(data[6]);
		supportingMaterialsTextField.setText(data.getSupportingMaterials());
		fitCriterionTextField.setText(data.getFitCriterion());
		if (data.getIsFrozen() == 1) {
			frozenChoiceBox.setSelected(true);
		}
		createdAtLabel.setText(data.getCreatedAt().toString());
		lastModifiedAtLabel.setText(data.getLastModifiedAt().toString());
		titleTextField.setText(data.getTitle());
		majorVersionLabel.setText("" + data.getMajorVersion());
		minorVersionLabel.setText("" + data.getMinorVersion());
	}

	private void initializeNodes() {

		fillTextFields();

		ownerTextField.setEditable(false);
		titleTextField.setEditable(false);
	}

	public void setData(RequirementCardModel rqModel, RequirementCardController requirementCardController,
			RequirementCardSimple toEdit) {
		this.model = rqModel;
		this.mainController = requirementCardController;
		this.toEdit = toEdit;

		addActualModulesToCheckComboBox();

		initializeNodes();
	}

	private void addActualModulesToCheckComboBox() {

		// give actual Modules to the ModulesCheckComboBox

		ObservableList<String> modules = model.getModules();
		modulesCheckComboBox = new CheckComboBox<>(modules);
		moduleHBox.getChildren().add(modulesCheckComboBox);
	}

}
