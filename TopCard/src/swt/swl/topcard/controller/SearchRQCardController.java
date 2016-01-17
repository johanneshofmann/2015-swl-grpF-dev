package swt.swl.topcard.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import swt.swl.topcard.model.RequirementCardModel;
import swt.swl.topcard.model.SearchHelper;
import swt.swl.topcard.model.search.SearchOperator;
import swt.swl.topcard.model.search.VoteValue;

public class SearchRQCardController {

	// Nodes for MVC :

	private RequirementCardModel model;
	private RequirementCardController rqCardController;

	// GUI Nodes:
	@FXML
	private MenuButton modulNameChoiceBox;

	@FXML
	private MenuItem modul1MenuItem, modul2MenuItem, modul3MenuItem, modul4MenuItem;

	@FXML
	private TextArea descriptionTextArea, rationaleTextArea;

	@FXML
	private TextField titleTextField, ownerTextField, sourceTextField, userStoriesTextField,
			supportingMaterialsTextField, fitCriterionTextField;

	@FXML
	private CheckBox frozenChoiceBox;

	@FXML
	private Button closeButton, searchButton;

	@FXML
	private Button descriptionPreciseButton, descriptionUnderstandableButton, rationalePreciseButton,
			rationaleUnderstandableButton;;

	@FXML
	private HBox descriptionUnderstandableHBox, descriptionPreciseHBox, rationalePreciseHBox,
			rationaleUnderstandableHBox;

	@FXML
	private ComboBox<String> descriptionUnderstandableCompareComboBox, descriptionUnderstandableNumberComboBox,
			rationaleUnderstandableCompareComboBox, rationaleUnderstandableNumberComboBox,
			rationalePreciseCompareComboBox, rationalePreciseNumberComboBox, descriptionPreciseCompareComboBox,
			descriptionPreciseNumberComboBox, descriptionCorrectComboBox, descriptionCompleteComboBox,
			descriptionAtomicComboBox, rationaleTraceableComboBox, rationaleCorrectComboBox,
			rationaleConsistentComboBox;

	public SearchRQCardController() {
	}

	private void initiateComboBoxes() {

		ObservableList<String> compare = FXCollections.observableArrayList("*", "<", "=", ">");
		ObservableList<String> numbers = FXCollections.observableArrayList("0", "1", "2", "3", "4", "5", "6", "7", "8",
				"9", "10");
		ObservableList<String> yesno = FXCollections.observableArrayList("*", "Yes", "No", "Unknown");

		descriptionPreciseCompareComboBox.setItems(compare);
		descriptionPreciseNumberComboBox.setItems(numbers);
		descriptionUnderstandableCompareComboBox.setItems(compare);
		descriptionUnderstandableNumberComboBox.setItems(numbers);
		descriptionCorrectComboBox.setItems(yesno);
		descriptionCompleteComboBox.setItems(yesno);
		descriptionAtomicComboBox.setItems(yesno);

		rationalePreciseCompareComboBox.setItems(compare);
		rationalePreciseNumberComboBox.setItems(numbers);
		rationaleUnderstandableCompareComboBox.setItems(compare);
		rationaleUnderstandableNumberComboBox.setItems(numbers);
		rationaleTraceableComboBox.setItems(yesno);
		rationaleCorrectComboBox.setItems(yesno);
		rationaleConsistentComboBox.setItems(yesno);

		// Select default items
		descriptionPreciseCompareComboBox.setValue("*");
		descriptionPreciseNumberComboBox.setValue("10");
		descriptionUnderstandableCompareComboBox.setValue("*");
		descriptionUnderstandableNumberComboBox.setValue("10");
		descriptionCorrectComboBox.setValue("*");
		descriptionCompleteComboBox.setValue("*");
		descriptionAtomicComboBox.setValue("*");
		rationalePreciseCompareComboBox.setValue("*");
		rationalePreciseNumberComboBox.setValue("10");
		rationaleUnderstandableCompareComboBox.setValue("*");
		rationaleUnderstandableNumberComboBox.setValue("10");
		rationaleTraceableComboBox.setValue("*");
		rationaleCorrectComboBox.setValue("*");
		rationaleConsistentComboBox.setValue("*");
	}

	@FXML
	void closeWindow(ActionEvent event) {

		rqCardController.repaint();

		event.consume();
	}

	@FXML
	void searchButtonClicked(ActionEvent event) {

		String title = null;
		String owner = null;
		String moduleName = null;
		String description = null;
		String rationale = null;
		String source = null;
		String userStories = null;
		String supportingMaterials = null;
		String fitCriterion = null;
		Integer isFrozen = null;

		SearchOperator descPreciseOp = null;
		Integer descPrecise = null;
		SearchOperator descUnderstandableOp = null;
		Integer descUnderstandable = null;
		VoteValue descCorrect = null;
		VoteValue descComplete = null;
		VoteValue descAtomic = null;

		SearchOperator ratPreciseOp = null;
		Integer ratPrecise = null;
		SearchOperator ratUnderstandableOp = null;
		Integer ratUnderstandable = null;
		VoteValue ratTraceable = null;
		VoteValue ratCorrect = null;
		VoteValue ratConsistent = null;

		if (!titleTextField.getText().isEmpty()) {
			title = titleTextField.getText();
		}
		if (!ownerTextField.getText().isEmpty()) {
			owner = ownerTextField.getText();
		}
		// TODO: Modules
		if (!descriptionTextArea.getText().isEmpty()) {
			description = descriptionTextArea.getText();
		}
		if (!rationaleTextArea.getText().isEmpty()) {
			rationale = rationaleTextArea.getText();
		}
		if (!sourceTextField.getText().isEmpty()) {
			source = sourceTextField.getText();
		}
		if (!userStoriesTextField.getText().isEmpty()) {
			userStories = userStoriesTextField.getText();
		}
		if (supportingMaterialsTextField.getText().isEmpty()) {
			supportingMaterials = supportingMaterialsTextField.getText();
		}
		if (!fitCriterionTextField.getText().isEmpty()) {
			fitCriterion = fitCriterionTextField.getText();
		}

		isFrozen = frozenChoiceBox.isSelected() ? 1 : 0;

		descPreciseOp = SearchOperator.values()[descriptionPreciseCompareComboBox.getSelectionModel()
				.getSelectedIndex()];
		descPrecise = Integer.getInteger(descriptionPreciseNumberComboBox.getValue());
		descUnderstandableOp = SearchOperator.values()[descriptionUnderstandableCompareComboBox.getSelectionModel()
				.getSelectedIndex()];
		descUnderstandable = Integer.getInteger(descriptionUnderstandableNumberComboBox.getValue());

		ratPreciseOp = SearchOperator.values()[rationalePreciseCompareComboBox.getSelectionModel().getSelectedIndex()];
		ratPrecise = Integer.getInteger(rationalePreciseNumberComboBox.getValue());
		ratUnderstandableOp = SearchOperator.values()[rationaleUnderstandableCompareComboBox.getSelectionModel()
				.getSelectedIndex()];
		ratUnderstandable = Integer.getInteger(rationaleUnderstandableNumberComboBox.getValue());

		descCorrect = VoteValue.values()[descriptionCorrectComboBox.getSelectionModel().getSelectedIndex()];
		descComplete = VoteValue.values()[descriptionCompleteComboBox.getSelectionModel().getSelectedIndex()];
		descAtomic = VoteValue.values()[descriptionAtomicComboBox.getSelectionModel().getSelectedIndex()];

		ratTraceable = VoteValue.values()[rationaleTraceableComboBox.getSelectionModel().getSelectedIndex()];
		ratCorrect = VoteValue.values()[rationaleCorrectComboBox.getSelectionModel().getSelectedIndex()];
		ratConsistent = VoteValue.values()[rationaleConsistentComboBox.getSelectionModel().getSelectedIndex()];

		SearchHelper.search(model.getObservableArray(), title, owner, moduleName, description, rationale, source,
				userStories, supportingMaterials, fitCriterion, isFrozen, descPreciseOp, descPrecise,
				descUnderstandableOp, descUnderstandable, descCorrect, descComplete, descAtomic, ratPreciseOp,
				ratPrecise, ratUnderstandableOp, ratUnderstandable, ratTraceable, ratCorrect, ratConsistent);

		rqCardController.repaint();
	}

	@FXML
	void descriptionPreciseButtonClicked(ActionEvent event) {
		descriptionPreciseButton.setVisible(false);
		descriptionPreciseHBox.setVisible(true);
	}

	@FXML
	void descriptionUnderstandableButtonClicked(ActionEvent event) {
		descriptionUnderstandableButton.setVisible(false);
		descriptionUnderstandableHBox.setVisible(true);
	}

	@FXML
	void rationalePreciseButtonClicked(ActionEvent event) {
		rationalePreciseButton.setVisible(false);
		rationalePreciseHBox.setVisible(true);
	}

	@FXML
	void rationaleUnderstandableButtonClicked(ActionEvent event) {
		rationaleUnderstandableButton.setVisible(false);
		rationaleUnderstandableHBox.setVisible(true);
	}

	public void setData(RequirementCardModel model, RequirementCardController controller) {
		this.model = model;
		this.rqCardController = controller;
		this.initiateComboBoxes();
	}
}