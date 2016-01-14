package swt.swl.topcard.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.Slider;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import swt.swl.topcard.logic.RequirementCardSimple;
import swt.swl.topcard.model.RequirementCardModel;

public class VoteRqCardController {

	private RequirementCardModel model;
	private RequirementCardController mainController;
	private RequirementCardSimple toVote;

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
			rationaleConsistentDontKnowRadioButton, fitCriterionCompleteYesRadioButton,
			fitCriterionCompleteNoRadioButton, fitCriterionCompleteDontKnowRadioButton;

	@FXML
	private ToggleGroup rationaleTraceableGroup, rationaleCompleteGroup, rationaleConsistentGroup,
			descriptionAtomicGroup, descriptionCorrectGroup, descriptionCompleteGroup, fitCriterionCompleteGroup;
	@FXML
	private VBox voteRationaleLabelsVBox, voteRationaleSelectionNodesVBox, voteDescriptionLabelsVBox,
			voteDescriptionSelectionNodesVBox;

	@FXML
	private HBox voteFitCriterionHBox;

	@FXML
	private Label sourceLabel, storyLabel, supportLabel, fitCriterionLabel, createdAtLabel, lastModifiedAtLabel,
			requirementCardNumberLabel, majorVersionLabel, minorVersionLabel, titleLabel, modulLabel, ownerLabel,
			descriptionLabel, rationaleLabel;

	@FXML
	private Button voteFitCriterionButton, closeButton, voteButton, voteDescriptionButton, voteRationaleButton;

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
	public void voteFitCriterionButtonClicked() {
		voteFitCriterionButton.setVisible(false);
		voteFitCriterionHBox.setVisible(true);

	}

	@FXML
	void voteButtonClicked(ActionEvent event) {

		model.newVoteSubmitted(toVote.getTitle(), getSelectedItems());
		new Alert(AlertType.INFORMATION, "Vote submitted !").showAndWait();

		mainController.repaint();
	}

	private int[] getSelectedItems() {

		int[] selectedItems = new int[11];

		selectedItems[0] = (int) descriptionPreciseSlider.getValue();
		selectedItems[1] = (int) descriptionUnderstandableSlider.getValue();
		selectedItems[2] = evaluateTextChoice(((RadioButton) descriptionCorrectGroup.getSelectedToggle()).getText());
		selectedItems[3] = evaluateTextChoice(((RadioButton) descriptionCompleteGroup.getSelectedToggle()).getText());
		selectedItems[4] = evaluateTextChoice(((RadioButton) descriptionAtomicGroup.getSelectedToggle()).getText());
		selectedItems[5] = (int) rationalePreciseSlider.getValue();
		selectedItems[6] = (int) rationaleUnderstandableSlider.getValue();
		selectedItems[7] = evaluateTextChoice(((RadioButton) rationaleTraceableGroup.getSelectedToggle()).getText());
		selectedItems[8] = evaluateTextChoice(((RadioButton) rationaleCompleteGroup.getSelectedToggle()).getText());
		selectedItems[9] = evaluateTextChoice(((RadioButton) rationaleConsistentGroup.getSelectedToggle()).getText());
		selectedItems[10] = evaluateTextChoice(((RadioButton) fitCriterionCompleteGroup.getSelectedToggle()).getText());

		return selectedItems;
	}

	private int evaluateTextChoice(String text) {

		if (text.startsWith("Y")) {
			return 1;
		} else if (text.startsWith("N")) {
			return 0;
		} else if (text.startsWith("D")) {
			return 2;
		} else {

			throw new IllegalArgumentException("Invalid value for input String 'text'. Value was: " + text);
		}

	}

	public void fillLabels() {

		// fetch data:
		RequirementCardSimple rqCard = model.getOverviewDataFromSelectedRq(toVote);

		// assign it to the displayed Nodes:

		ownerLabel.setText(rqCard.getOwnerName());
		// moduleNamesTextField.setText(data);
		requirementCardNumberLabel.setText("" + rqCard.getRqID());
		descriptionLabel.setText(rqCard.getDescription());
		rationaleLabel.setText(rqCard.getRationale());
		sourceLabel.setText(rqCard.getSource());
		// TODO: ShowAndVoteRqCardController:
		// userStoriesTextField.setText(data);
		supportLabel.setText(rqCard.getSupportingMaterials());
		fitCriterionLabel.setText(rqCard.getFitCriterion());
		// "IsFrozen" (data[9]) not required here..
		createdAtLabel.setText(rqCard.getCreatedAt().toString());
		lastModifiedAtLabel.setText(rqCard.getLastModifiedAt());
		titleLabel.setText(rqCard.getTitle());
		majorVersionLabel.setText("" + rqCard.getMajorVersion());
		minorVersionLabel.setText("" + rqCard.getMinorVersion());
	}

	private void goGuiNodes() {
		fillLabels();
		voteRationaleLabelsVBox.setVisible(false);
		voteRationaleSelectionNodesVBox.setVisible(false);
		voteDescriptionLabelsVBox.setVisible(false);
		voteDescriptionSelectionNodesVBox.setVisible(false);
		voteFitCriterionHBox.setVisible(false);

		// DontKnow as default:
		descriptionCompleteDontKnowRadioButton.setSelected(true);
		descriptionAtomicDontKnowRadioButton.setSelected(true);
		descriptionCorrectDontKnowRadioButton.setSelected(true);

		rationaleTraceableDontKnowRadioButton.setSelected(true);
		rationaleCompleteDontKnowRadioButton.setSelected(true);
		rationaleConsistentDontKnowRadioButton.setSelected(true);
		fitCriterionCompleteDontKnowRadioButton.setSelected(true);

	}

	public void setData(RequirementCardModel rqModel, RequirementCardController requirementCardController,
			RequirementCardSimple toVote) {
		this.model = rqModel;
		this.mainController = requirementCardController;
		this.toVote = toVote;
		goGuiNodes();

	}

}