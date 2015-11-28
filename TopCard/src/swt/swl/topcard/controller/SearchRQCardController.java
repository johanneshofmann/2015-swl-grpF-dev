package swt.swl.topcard.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.control.Slider;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import swt.swl.topcard.model.RequirementCardModel;

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
	private ComboBox<String> descriptionUnderstandableCompareComboBox,
			descriptionUnderstandableNumberComboBoxrationaleUnderstandableCompareComboBox,
			rationaleUnderstandableNumberComboBox, descriptionPreciseCompareComboBox, descriptionPreciseNumberComboBox;

	@FXML
	void closeWindow(ActionEvent event) {
		rqCardController.startButtonClicked(new ActionEvent());
	}

	@FXML
	void searchButtonClicked(ActionEvent event) {

		//TODO:
	}

	public void setData(RequirementCardModel model, RequirementCardController controller) {
		this.model = model;
		this.rqCardController = controller;
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
}