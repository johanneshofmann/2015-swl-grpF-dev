package swt.swl.topcard.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.Slider;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.VBox;
import swt.swl.topcard.model.RequirementCardModel;

public class ShowAndVoteRqCardController {

	private RequirementCardModel model;
	private RequirementCardController mainController;
	private String toVote;

	@FXML
	private Slider descriptionPreciseSlider, descriptionUnderstandableSlider, rationalePreciseSlider,
			rationaleUnderstandableSlider;

	@FXML
	private RadioButton rationaleTraceableNoRadioButton, rationaleTraceableYesRadioButton,
			descriptionCorrectYesRadioButton, descriptionCorrectNoRadioButton, descriptionCorrectDontKnowRadioButton,
			descriptionCompleteYesRadioButton, descriptionCompleteNoRadioButton, descriptionCompleteDontKnowRadioButton,
			descriptionAtomicYesRadioButton, descriptionAtomicNoRadioButton, descriptionAtomicDontKnowRadioButton,
			rationaleTraceableDontKnowRadioButton, rationaleCompleteYesRadioButton, rationaleCompleteNoRadioButton,
			rationaleCompleteDontKnowRadioButton, rationaleConsistentYesRadioButton, rationaleConsistenNoRadioButton,
			rationaleConsistentDontKnowRadioButton;

	@FXML
	private ToggleGroup rationaleTraceableGroup, rationaleCompleteGroup, rationaleConsistentGroup,
			descriptionAtomicGroup, descriptionCorrectGroup, descriptionCompleteGroup;
	@FXML
	private VBox voteRationaleLabelsVBox, voteRationaleSelectionNodesVBox, voteDescriptionLabelsVBox,
			voteDescriptionSelectionNodesVBox;

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
		voteRationaleLabelsVBox.setVisible(true);
		voteRationaleSelectionNodesVBox.setVisible(true);
	}

	@FXML
	public void voteDescriptionButtonClicked() {
		voteDescriptionButton.setVisible(false);
		voteDescriptionLabelsVBox.setVisible(true);
		voteDescriptionSelectionNodesVBox.setVisible(true);
	}

	@FXML
	void voteButtonClicked(ActionEvent event) {
		String[] selectedItems = new String[10];
		selectedItems[0] = "" + (int) descriptionPreciseSlider.getValue();
		selectedItems[1] = "" + (int) descriptionUnderstandableSlider.getValue();

		selectedItems[2] = ((RadioButton) descriptionCorrectGroup.getSelectedToggle()).getText();
		selectedItems[3] = ((RadioButton) descriptionCompleteGroup.getSelectedToggle()).getText();
		selectedItems[4] = ((RadioButton) descriptionAtomicGroup.getSelectedToggle()).getText();
		selectedItems[5] = "" + (int) rationalePreciseSlider.getValue();
		selectedItems[6] = "" + (int) rationaleUnderstandableSlider.getValue();
		selectedItems[7] = ((RadioButton) rationaleTraceableGroup.getSelectedToggle()).getText();
		selectedItems[8] = ((RadioButton) rationaleCompleteGroup.getSelectedToggle()).getText();
		selectedItems[9] = ((RadioButton) rationaleConsistentGroup.getSelectedToggle()).getText();

		model.newVoteSubmitted(toVote, selectedItems);
		new Alert(AlertType.INFORMATION, "Vote submitted !");
		mainController.repaint();
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

	private void goGuiNodes() {
		fillLabels();
		voteRationaleLabelsVBox.setVisible(false);
		voteRationaleSelectionNodesVBox.setVisible(false);
		voteDescriptionLabelsVBox.setVisible(false);
		voteDescriptionSelectionNodesVBox.setVisible(false);

		// DontKnow as default:
		descriptionCompleteDontKnowRadioButton.setSelected(true);
		descriptionAtomicDontKnowRadioButton.setSelected(true);
		descriptionCorrectDontKnowRadioButton.setSelected(true);

		rationaleTraceableDontKnowRadioButton.setSelected(true);
		rationaleCompleteDontKnowRadioButton.setSelected(true);
		rationaleConsistentDontKnowRadioButton.setSelected(true);

	}

	public void setData(RequirementCardModel rqModel, RequirementCardController requirementCardController,
			String toVote) {
		this.model = rqModel;
		this.mainController = requirementCardController;
		this.toVote = toVote;
		goGuiNodes();

	}

}
