package swt.swl.topcard.controller;

import swt.swl.topcard.model.RequirementCardModel;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.VBox;

public class ShowAndVoteRqCardController {

	private RequirementCardModel model;
	private RequirementCardController mainController;
	private String toVote;

	@FXML
	private ComboBox<String> dPreciseBox, dUnderstandableBox, rPreciseBox, rUnderstandableBox;;

	@FXML
	private ChoiceBox<String> dCorrectBox, dCompleteBox, dAtomicBox, rCompleteBox, rTraceableBox, rConsistentBox;

	@FXML
	private VBox voteRationale, voteRationaleVBoxContainingComboBoxes, voteDescription,
			voteDescriptionVBoxContainingComboBoxes;

	@FXML
	private Label sourceLabel, storyLabel, supportLabel, fitCriterionLabel, createdAtLabel, lastModifiedAtLabel,
			requirementCardNumberLabel, majorVersionLabel, minorVersionLabel, titleLabel, modulLabel, ownerLabel,
			descriptionLabel, rationaleLabel;
	@FXML
	private CheckBox frozenChoiceBox;

	@FXML
	private Button closeButton, voteButton, voteDescriptionButton, voteRationaleButton;

	@FXML
	void closeWindow(ActionEvent event) {
		Alert closeConfirmation = new Alert(AlertType.CONFIRMATION, "Close without saving ?");
		closeConfirmation.showAndWait();
		ButtonType choice = closeConfirmation.getResult();
		if (choice == ButtonType.OK) {
			closeConfirmation.close();
			event.consume();
			mainController.repaint();

		} else {
			event.consume();
		}
	}

	@FXML
	public void voteRationaleButtonClicked() {
		voteRationaleButton.setVisible(false);
		voteRationale.setVisible(true);
		voteRationaleVBoxContainingComboBoxes.setVisible(true);
	}

	@FXML
	public void voteDescriptionButtonClicked() {
		voteDescriptionButton.setVisible(false);
		voteDescription.setVisible(true);
		voteDescriptionVBoxContainingComboBoxes.setVisible(true);
	}

	@FXML
	void voteButtonClicked(ActionEvent event) {
		String[] selectedItems = new String[10];
		selectedItems [0] = dPreciseBox.getSelectionModel().getSelectedItem();
		selectedItems [1] = dUnderstandableBox.getSelectionModel().getSelectedItem();
		selectedItems [2] = dCorrectBox.getSelectionModel().getSelectedItem();
		selectedItems [3] = dCompleteBox.getSelectionModel().getSelectedItem();
		selectedItems [4] = dAtomicBox.getSelectionModel().getSelectedItem();
		
		selectedItems [5] = rPreciseBox.getSelectionModel().getSelectedItem();
		selectedItems [6] = rUnderstandableBox.getSelectionModel().getSelectedItem();
		selectedItems [7] = rTraceableBox.getSelectionModel().getSelectedItem();
		selectedItems [8] = rCompleteBox.getSelectionModel().getSelectedItem();
		selectedItems [9] = rConsistentBox.getSelectionModel().getSelectedItem();
		
		model.newVoteSubmitted(toVote, selectedItems);
	}

	private void fillComboBoxes() {
		ObservableList<String> choice = FXCollections.observableArrayList("Yes", "No", "?");
		ObservableList<String> numbers = FXCollections.observableArrayList("0", "1", "2", "3", "4", "5", "6", "7", "8",
				"9", "10");

		dPreciseBox.setItems(numbers);
		dUnderstandableBox.setItems(numbers);
		rPreciseBox.setItems(numbers);
		rUnderstandableBox.setItems(numbers);
		dCorrectBox.setItems(choice);
		dCompleteBox.setItems(choice);
		dAtomicBox.setItems(choice);
		rCompleteBox.setItems(choice);
		rTraceableBox.setItems(choice);
		rConsistentBox.setItems(choice);

	}

	public void fillLabels() {
		// fetch data:
		String[] data = model.getOverviewDataFromSelectedRq(toVote);

		// assign it to the displayed Nodes:
		ownerLabel.setText(data[0]);
		// TODO: moduleNamesTextField.setText(data[1]);
		requirementCardNumberLabel.setText(data[2]);
		descriptionLabel.setText(data[3]);
		rationaleLabel.setText(data[4]);
		sourceLabel.setText(data[5]);
		// TODO: userStoriesTextField.setText(data[6]);
		supportLabel.setText(data[7]);
		fitCriterionLabel.setText(data[8]);
		if (Integer.parseInt(data[9]) == 1) {
			frozenChoiceBox.setSelected(true);
		}
		createdAtLabel.setText(data[10]);
		lastModifiedAtLabel.setText(data[11]);
		titleLabel.setText(data[12]);
		majorVersionLabel.setText(data[13]);
		minorVersionLabel.setText(data[14]);
	}

	public void setData(RequirementCardModel rqModel, RequirementCardController requirementCardController,
			String toVote) {
		this.model = rqModel;
		this.mainController = requirementCardController;
		this.toVote = toVote;
		fillLabels();
		voteRationale.setVisible(false);
		voteRationaleVBoxContainingComboBoxes.setVisible(false);
		voteDescription.setVisible(false);
		voteDescriptionVBoxContainingComboBoxes.setVisible(false);
		fillComboBoxes();
	}

}
