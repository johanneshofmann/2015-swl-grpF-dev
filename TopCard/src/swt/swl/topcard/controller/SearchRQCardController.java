package swt.swl.topcard.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import swt.swl.topcard.model.RequirementCardModel;
import swt.swl.topcard.model.SearchHelper;

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
	private CheckBox descriptionCorrectCheckBox, descriptionCompleteCheckBox, descriptionAtomicCheckBox,
			rationaleTraceableCheckBox, rationaleCorrectCheckBox, rationaleConsistentCheckBox;
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
			descriptionPreciseNumberComboBox;

	public SearchRQCardController() {

	}

	private void initialteComboBoxes() {

		ObservableList<String> compare = FXCollections.observableArrayList("<", "=", ">");
		ObservableList<String> numbers = FXCollections.observableArrayList("0", "1", "2", "3", "4", "5", "6", "7", "8",
				"9", "10");
		descriptionUnderstandableCompareComboBox.setItems(compare);
		descriptionUnderstandableNumberComboBox.setItems(numbers);
		rationaleUnderstandableCompareComboBox.setItems(compare);
		rationaleUnderstandableNumberComboBox.setItems(numbers);
		descriptionPreciseCompareComboBox.setItems(compare);
		descriptionPreciseNumberComboBox.setItems(numbers);
		rationalePreciseCompareComboBox.setItems(compare);
		rationalePreciseNumberComboBox.setItems(numbers);
	}

	@FXML
	void closeWindow(ActionEvent event) {
		Alert confirmation = new Alert(AlertType.CONFIRMATION, "Close without searching?");
		confirmation.showAndWait();
		if (confirmation.getResult().equals(ButtonType.OK)) {
			rqCardController.repaint();
		} else {
			event.consume();
		}

	}

	@FXML
	void searchButtonClicked(ActionEvent event) {

		String title = null;
		String owner = null;
		String fitCriterion = null;
		String source = null;
		String supportingMaterials = null;

		if (!titleTextField.getText().isEmpty()) {
			title = titleTextField.getText();
		}
		if (!ownerTextField.getText().isEmpty()) {
			owner = ownerTextField.getText();
		}
		if (!fitCriterionTextField.getText().isEmpty()) {
			fitCriterion = fitCriterionTextField.getText();
		}
		if (!sourceTextField.getText().isEmpty()) {
			source = sourceTextField.getText();
		}
		if (supportingMaterialsTextField.getText().isEmpty()) {
			supportingMaterials = supportingMaterialsTextField.getText();
		}

		SearchHelper.search(model.getObservableArray(),title, owner, fitCriterion, source, supportingMaterials);

		// redirect
		rqCardController.repaint();
	}

	@FXML
	void descriptionPreciseButtonClicked(ActionEvent event) {
		initialteComboBoxes();
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
	}
}