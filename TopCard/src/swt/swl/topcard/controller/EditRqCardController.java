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

public class EditRqCardController {

	private RequirementCardModel model;
	private RequirementCardController mainController;
	private String toEdit;

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

		// model.delete, then insert new OR model.insert with
		// newMinorVersion ?

		// TODO:
		// insert
		model.insertRQIntoDatabase(titleTextField.getText(), descriptionTextArea.getText(), rationaleTextArea.getText(),
				sourceTextField.getText(), userStoriesTextField.getText(), fitCriterionTextField.getText(),
				supportingMaterialsTextField.getText(), frozenChoiceBox.isSelected());
		new Alert(AlertType.INFORMATION, "Reqirement in database now.").showAndWait();
	}

	public void setData(RequirementCardModel rqModel, RequirementCardController requirementCardController) {
		this.model = rqModel;
		this.mainController = requirementCardController;
	}

	private void fillVoteResultsLabels() {

		String[] data = model.getVoteResults(toEdit);

		descriptionPreciseVoteResultLabel.setText(data[0]);
		descriptionUnderstandableVoteResultLabel.setText(data[1]);
		descriptionCorrectVoteResultLabel.setText(data[2]);
		descriptionCompleteVoteResultLabel.setText(data[3]);
		descriptionAtomicVoteResultLabel.setText(data[4]);
		rationalePreciseVoteResultLabel.setText(data[5]);
		rationaleUnderstandableVoteResultLabel.setText(data[6]);
		rationaleTraceableVoteResultLabel.setText(data[7]);
		rationaleCompleteVoteResultLabel.setText(data[8]);
		rationaleConsistentVoteResultLabel.setText(data[9]);
		fitCriterionCompleteVoteResultLabel.setText(data[10]);
	}

	private void fillTextFields() {
		// fetch data:
		String[] data = model.getOverviewDataFromSelectedRq(toEdit);

		// assign it to the displayed Nodes:
		ownerTextField.setText(data[0]);
		// TODO: moduleNamesTextField.setText(data[1]);
		requirementCardNumberLabel.setText(data[2]);
		descriptionTextArea.setText(data[3]);
		rationaleTextArea.setText(data[4]);
		sourceTextField.setText(data[5]);
		// TODO: userStoriesTextField.setText(data[6]);
		supportingMaterialsTextField.setText(data[7]);
		fitCriterionTextField.setText(data[8]);
		if (Integer.parseInt(data[9]) == 1) {
			frozenChoiceBox.setSelected(true);
		}
		createdAtLabel.setText(data[10]);
		lastModifiedAtLabel.setText(data[11]);
		titleTextField.setText(data[12]);
		majorVersionLabel.setText(data[13]);
		minorVersionLabel.setText(data[14]);
	}

	public void initializeNodes(String rqTitle) {

		fillTextFields();

		ownerTextField.setEditable(false);
		titleTextField.setEditable(false);
	}

	public void setData(RequirementCardModel rqModel, RequirementCardController requirementCardController,
			String toEdit) {
		this.model = rqModel;
		this.mainController = requirementCardController;
		this.toEdit = toEdit;
		initializeNodes(toEdit);
	}

}
