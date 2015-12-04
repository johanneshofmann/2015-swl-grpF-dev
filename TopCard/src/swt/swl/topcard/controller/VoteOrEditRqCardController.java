package swt.swl.topcard.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import swt.swl.topcard.model.RequirementCardModel;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.MenuButton;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;

public class VoteOrEditRqCardController {

	private RequirementCardModel model;
	private RequirementCardController mainController;
	private String toEdit;
	private boolean vote$ = false;

	public VoteOrEditRqCardController() {
		fillTextFields();
	}

	@FXML
	private Label requirementCardNumberLabel, createdAtLabel, lastModifiedAtLabel, voteOrEditRequirementLabel;
	@FXML
	private MenuButton modulNameChoiceBox, modulNamesMenuButton;

	@FXML
	private TextArea descriptionTextArea, rationaleTextArea;

	@FXML
	private TextField titleTextField, ownerTextField, moduleNamesTextField, sourceTextField, userStoriesTextField,
			supportingMaterialsTextField, fitCriterionTextField;

	@FXML
	private CheckBox frozenChoiceBox, modul1CheckBox, modul2CheckBox, modul3CheckBox;

	@FXML
	private Button closeButton, voteOrEditButton;

	@FXML
	void closeWindow(ActionEvent event) {

		Alert closeConfirmation = new Alert(AlertType.CONFIRMATION, "Close without saving ?");
		closeConfirmation.showAndWait();
		ButtonType choice = closeConfirmation.getResult();
		if (choice == ButtonType.OK) {
			mainController.repaint();
		} else {
			event.consume();
		}
	}

	@FXML
	void voteOrEditButtonClicked(ActionEvent event) {

		// if(edit): model.delete, then insert new OR model.insert with
		// newMinorVersion ?
	}

	private void fillTextFields() {
		// fetch data:
		String[] data = model.getOverviewDataFromSelectedRq(toEdit);

		// assign it to the displayed Nodes:
		ownerTextField.setText(data[0]);
		moduleNamesTextField.setText(data[1]);
		requirementCardNumberLabel.setText(data[2]);
		descriptionTextArea.setText(data[3]);
		rationaleTextArea.setText(data[4]);
		sourceTextField.setText(data[5]);
		userStoriesTextField.setText(data[6]);
		supportingMaterialsTextField.setText(data[7]);
		fitCriterionTextField.setText(data[8]);
		if (Integer.parseInt(data[9]) == 1) {
			frozenChoiceBox.setSelected(true);
		}
		createdAtLabel.setText(data[10]);
		lastModifiedAtLabel.setText(data[11]);
	}

	public void initializeNodes(String rqTitle) {

		ownerTextField.setEditable(false);
		titleTextField.setEditable(false);

		if (model.checkUserName(rqTitle)) {

			vote$ = false;

			// TODO : initiate edit functionalities

			voteOrEditRequirementLabel.setText("Edit Requirement Card: ");
			voteOrEditButton.setText("Save Changes");
			modulNamesMenuButton.setVisible(false);
			moduleNamesTextField.setPrefWidth(222);

		} else {

			vote$ = true;

			// TODO: inintiate 2 other buttons as visible/editable:
			// and open the voting window.

			voteOrEditRequirementLabel.setText("View Requirement Card: ");
			voteOrEditButton.setText("Vote >");
			modulNamesMenuButton.setVisible(true);
			moduleNamesTextField.setPrefWidth(0);

		}
	}

	public void setData(RequirementCardModel rqModel, RequirementCardController requirementCardController,
			String toEdit) {
		this.model = rqModel;
		this.mainController = requirementCardController;
		this.toEdit = toEdit;
	}

}
