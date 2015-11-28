package swt.swl.topcard.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.control.Slider;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import swt.swl.topcard.model.RequirementCardModel;

public class SearchRQCardController {

	//Nodes for MVC :

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
	private TextField titleTextField, ownerTextField, sourceTextField, userStoriesTextField, supportingMaterialsTextField,
			fitCriterionTextField;

	@FXML
	private CheckBox frozenChoiceBox;

	@FXML
	private Button closeButton, searchButton;

	@FXML
	private Slider descriptionPreciseSmallerSlider, descriptionPreciseEvenSlider, descriptionPreciseBiggerSlider,
			descriptionUnderstandableSmallerSlider, descriptionUnderstandableEvenSlider,
			descriptionUnderstandableBiggerSlider;

	@FXML
	private CheckBox descriptionCorrectCheckBox, descriptionCompleteCheckBox, descriptionAtomicCheckBox;

	@FXML
	private Slider rationalePreciseSmallerSlider, rationalePreciseEvenSlider, rationalePreciseBiggerSlider,
			rationaleUnderstandableSmallerSlider, rationaleUnderstandableEvenSlider,
			rationaleUnderstandableBiggerSlider;

	@FXML
	private CheckBox rationaleUnderstandableTraceableCheckBox, rationaleUnderstandableCorrectCheckBox,
			rationaleUnderstandableConsistentCheckBox;

	@FXML
	void closeWindow(ActionEvent event) {
		rqCardController.startButtonClicked(new ActionEvent());
	}

	@FXML
	void searchButtonClicked(ActionEvent event) {

	}


	public void setData(RequirementCardModel model, RequirementCardController controller) {
		this.model = model;
		this.rqCardController = controller;
	}

}
