package swt.swl.topcard.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.MenuButton;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import swt.swl.topcard.model.RequirementCardModel;
import swt.swl.topcard.model.RequirementCardSimple;
import swt.swl.topcard.model.SubmittedVoteSimple;

public class EditRqCardController {

	private RequirementCardModel model;
	private RequirementCardController mainController;
	private RequirementCardSimple toEdit;

	@FXML
	private MenuButton modulNamesMenuButton;

	@FXML
	private CheckBox modul1CheckBox, modul2CheckBox, modul3CheckBox, frozenChoiceBox;

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
		model.insertRqIntoDatabase(titleTextField.getText(), descriptionTextArea.getText(), rationaleTextArea.getText(),
				sourceTextField.getText(), userStoriesTextField.getText(), fitCriterionTextField.getText(),
				supportingMaterialsTextField.getText(), frozenChoiceBox.isSelected());
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
		// TODO: EditRqCardController: moduleNamesTextField.setText(data[1]);
		requirementCardNumberLabel.setText("" + data.getRqID());
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

	public void initializeNodes() {

		fillTextFields();

		ownerTextField.setEditable(false);
		titleTextField.setEditable(false);
	}

	public void setData(RequirementCardModel rqModel, RequirementCardController requirementCardController,
			RequirementCardSimple toEdit) {
		this.model = rqModel;
		this.mainController = requirementCardController;
		this.toEdit = toEdit;
		initializeNodes();
	}

}
