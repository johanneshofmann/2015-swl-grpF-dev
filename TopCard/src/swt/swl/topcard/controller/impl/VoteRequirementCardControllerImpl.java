package swt.swl.topcard.controller.impl;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.Slider;
import javafx.scene.control.TextArea;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import swt.swl.topcard.controller.Controller;
import swt.swl.topcard.controller.RequirementCardController;
import swt.swl.topcard.controller.VoteRequirementCardController;
import swt.swl.topcard.logic.RequirementCardSimple;
import swt.swl.topcard.logic.impl.RequirementCardSimpleImpl;
import swt.swl.topcard.model.RequirementCardModel;

public class VoteRequirementCardControllerImpl implements Controller, VoteRequirementCardController {

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
	private Label createdAtLabel, lastModifiedAtLabel, requirementCardNumberLabel, majorVersionLabel, minorVersionLabel;
	@FXML
	private TextArea sourceTextArea, userStoriesTextArea, supportingMaterialsTextArea, fitCriterionTextArea,
			titleTextArea, modulesTextArea, ownerTextArea, descriptionTextArea, rationaleTextArea;
	@FXML
	private Button closeButton, voteButton;

	@FXML
	void closeWindow(ActionEvent event) {
		cancel(event);
	}

	@FXML
	void voteButtonClicked(ActionEvent event) {

		model.newVoteSubmitted(toVote.getID(), getSelectedItems());
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
		RequirementCardSimpleImpl rqCard = (RequirementCardSimpleImpl) model.getOverviewDataFromSelectedRq(toVote);

		// assign it to the displayed Nodes:

		ownerTextArea.setText(rqCard.getOwnerName());
		modulesTextArea.setText(rqCard.getModules());
		requirementCardNumberLabel.setText("" + rqCard.getRqID());
		descriptionTextArea.setText(rqCard.getDescription());
		rationaleTextArea.setText(rqCard.getRationale());
		sourceTextArea.setText(rqCard.getSource());
		userStoriesTextArea.setText(rqCard.getUserStories());
		supportingMaterialsTextArea.setText(rqCard.getSupportingMaterials());
		fitCriterionTextArea.setText(rqCard.getFitCriterion());
		// 'IsFrozen' not required here..
		createdAtLabel.setText(rqCard.getCreatedAt().toString().substring(0, 19));
		lastModifiedAtLabel.setText(rqCard.getLastModifiedAt());
		titleTextArea.setText(rqCard.getTitle());
		majorVersionLabel.setText("" + rqCard.getMajorVersion());
		minorVersionLabel.setText("" + rqCard.getMinorVersion());
	}

	private void goGuiNodes() {

		fillLabels();

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
		setMainController(requirementCardController);
		this.toVote = toVote;

		goGuiNodes();
	}

	@Override
	public void setMainController(RequirementCardController requirementCardController) {
		this.mainController = requirementCardController;
	}

	@Override
	public void cancel(ActionEvent event) {
		event.consume();
		mainController.repaint();
	}

	@Override
	public void checkEmpty() {
		// not necessary here..
	}

}
