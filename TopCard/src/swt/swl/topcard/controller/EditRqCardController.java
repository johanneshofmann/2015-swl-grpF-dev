package swt.swl.topcard.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import swt.swl.topcard.model.RequirementCardModel;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.CheckBox;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;

public class EditRqCardController {

	private RequirementCardModel model;
	private RequirementCardController mainController;
	private String toEdit;

	public EditRqCardController() {
		fillTextFields();
	}

	@FXML
	private MenuButton modulNameChoiceBox;

	@FXML
	private MenuItem modul1MenuItem, modul2MenuItem, modul3MenuItem, modul4MenuItem;

	@FXML
	private TextArea descriptionTextArea, rationaleTextArea;

	@FXML
	private TextField sourceTextField, userStoriesTextField, supportingMaterialsTextField, fitCriterionTextField;

	@FXML
	private CheckBox frozenChoiceBox;

	@FXML
	private Button closeButton, saveButton;

	@FXML
	void closeWindow(ActionEvent event) {

		Alert closeConfirmation = new Alert(AlertType.CONFIRMATION, "Close without saving ?");
		closeConfirmation.showAndWait();
		ButtonType choice = closeConfirmation.getResult();
		if (choice == ButtonType.OK) {
			mainController.repaint();
			;
		} else {
			event.consume();
		}
	}

	@FXML
	void saveChangesButtonClicked(ActionEvent event) {
		//TODO: model.delete, then insert new OR model.insert with new MinorVersion ?
	}

	private void fillTextFields() {
		String[] data = model.getDataFromSelectedRq(toEdit);
		descriptionTextArea.setText(data[0]);
		rationaleTextArea.setText(data[1]);
		sourceTextField.setText(data[2]);
		supportingMaterialsTextField.setText(data[3]);
		fitCriterionTextField.setText(data[4]);

		if (Integer.parseInt(data[5]) == 1) {
			frozenChoiceBox.setSelected(true);
		}
	}

	public void setData(RequirementCardModel rqModel, RequirementCardController requirementCardController,
			String toEdit) {
		this.model = rqModel;
		this.mainController = requirementCardController;
		this.toEdit = toEdit;
	}
}
