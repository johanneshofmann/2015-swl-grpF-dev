package swt.swl.topcard.controller;

import org.controlsfx.control.CheckComboBox;

import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import swt.swl.topcard.logic.RequirementCardSimple;
import swt.swl.topcard.logic.SubmittedVoteSimple;
import swt.swl.topcard.model.RequirementCardModel;

public class EditRqCardController {

	private RequirementCardModel model;
	private RequirementCardController mainController;
	private RequirementCardSimple toEdit;

	private CheckComboBox<String> modulesCheckComboBox;
	private ObservableList<String> newModules;

	@FXML
	private CheckBox frozenChoiceBox;
	@FXML
	private TextArea descriptionTextArea, rationaleTextArea;
	@FXML
	private TextField titleTextField, ownerTextField, sourceTextField, userStoriesTextField,
			supportingMaterialsTextField, fitCriterionTextField;
	@FXML
	private Button closeButton, editButton, showVoteResultsButton;
	@FXML
	private Label createdAtLabel, lastModifiedAtLabel, requirementCardNumberLabel, majorVersionLabel, minorVersionLabel;
	@FXML
	private Pane showVoteResultsPane;
	@FXML
	private HBox moduleHBox;
	@FXML
	private Label descriptionPreciseVoteResultLabel, descriptionUnderstandableVoteResultLabel,
			descriptionCorrectVoteResultLabel, descriptionCompleteVoteResultLabel, descriptionAtomicVoteResultLabel,
			rationalePreciseVoteResultLabel, rationaleUnderstandableVoteResultLabel, rationaleTraceableVoteResultLabel,
			rationaleCompleteVoteResultLabel, rationaleConsistentVoteResultLabel, fitCriterionCompleteVoteResultLabel;

	public void initializeFXNodes() {

		initToCheckComboBox();
		fillRqCardDataInTextFields();
		addEventHandlerToFrozenChoiceBox();

		// all editable but..
		ownerTextField.setEditable(false);
		titleTextField.setEditable(false);
	}

	@FXML
	void closeWindow(ActionEvent event) {
		Alert closeConfirmation = new Alert(AlertType.CONFIRMATION, "Close without saving ?");
		closeConfirmation.showAndWait();
		ButtonType choice = closeConfirmation.getResult();
		if (choice == ButtonType.OK) {
			mainController.repaint();
			event.consume();
			closeConfirmation.close();
		} else {
			event.consume();
		}
	}

	@FXML
	void showVoteResultsButtonClicked() {
		showVoteResultsButton.setVisible(false);
		fillVoteResultsLabels();
		showVoteResultsPane.setVisible(true);

	}

	@FXML
	void editButtonClicked(ActionEvent event) {

		model.insertEditedRqIntoDatabase(toEdit, false);

		// if successful, show alert to user
		new Alert(AlertType.INFORMATION, "Reqirement in database now.").showAndWait();
	}

	private void fillRqCardDataInTextFields() {

		// assign data to the displayed Nodes:

		ownerTextField.setText(toEdit.getOwnerName());

		String[] modules = toEdit.getModules().split(",");
		for (int i = 0; i < modules.length; i++) {
			modulesCheckComboBox.getCheckModel().check(modules[i]);
		}
		requirementCardNumberLabel.setText(String.valueOf(toEdit.getRqID()));
		descriptionTextArea.setText(toEdit.getDescription());
		rationaleTextArea.setText(toEdit.getRationale());
		sourceTextField.setText(toEdit.getSource());
		// TODO: userStoriesTextField.setText(data[6]);
		supportingMaterialsTextField.setText(toEdit.getSupportingMaterials());
		fitCriterionTextField.setText(toEdit.getFitCriterion());
		if (toEdit.getIsFrozen() == 1) {
			frozenChoiceBox.setSelected(true);
		}
		createdAtLabel.setText(toEdit.getCreatedAt().toString());
		lastModifiedAtLabel.setText(toEdit.getLastModifiedAt().toString());
		titleTextField.setText(toEdit.getTitle());
		majorVersionLabel.setText("" + toEdit.getMajorVersion());
		minorVersionLabel.setText("" + toEdit.getMinorVersion());
	}

	public void setData(RequirementCardModel rqModel, RequirementCardController requirementCardController,
			RequirementCardSimple toEdit) {
		this.model = rqModel;
		this.mainController = requirementCardController;
		this.toEdit = toEdit;
	}

	private void initToCheckComboBox() {

		// give actual Modules to the ModulesCheckComboBox

		ObservableList<String> allModules = model.getModules();
		modulesCheckComboBox = new CheckComboBox<>(allModules);
		moduleHBox.getChildren().add(modulesCheckComboBox);

		// check the ones set in database
		newModules = model.getModulesByRqID(toEdit.getRqID());
		for (String module : newModules) {
			modulesCheckComboBox.getCheckModel().check(module);
		}

		addEventHandlerToModulesCheckComboBox(allModules);
	}

	private void addEventHandlerToModulesCheckComboBox(ObservableList<String> allModules) {

		modulesCheckComboBox.getCheckModel().getCheckedItems().addListener(new ListChangeListener<String>() {

			@Override
			public void onChanged(javafx.collections.ListChangeListener.Change<? extends String> changedModule) {

				if (changedModule.next()) {

					if (changedModule.wasAdded()) {

						for (String module : changedModule.getAddedSubList()) {

							newModules.add(module);
						}
					}

					// if module was removed..
					else if (changedModule.wasRemoved()) {

						// modulesAdded==false->true
						for (String module : changedModule.getRemoved()) {

							newModules.remove(module);
						}
					}
				}
			}
		});
	}

	private void addEventHandlerToFrozenChoiceBox() {

		frozenChoiceBox.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {

				model.insertEditedRqIntoDatabase(toEdit, true);
				// if successful, show alert to user
				new Alert(AlertType.INFORMATION, "Reqirement in database now.").showAndWait();

			}
		});
	}

	private void fillVoteResultsLabels() {

		SubmittedVoteSimple voteResult = model.getVoteResults(toEdit.getID());

		descriptionPreciseVoteResultLabel.setText("" + voteResult.getDescriptionPrecise());
		descriptionUnderstandableVoteResultLabel.setText("" + voteResult.getDescriptionUnderstandable());
		descriptionCorrectVoteResultLabel.setText("" + voteResult.getDescriptionCorrect());
		descriptionCompleteVoteResultLabel.setText("" + voteResult.getDescriptionComplete());
		descriptionAtomicVoteResultLabel.setText("" + voteResult.getDescriptionAtomic());
		rationalePreciseVoteResultLabel.setText("" + voteResult.getRationalePrecise());
		rationaleUnderstandableVoteResultLabel.setText("" + voteResult.getRationaleUnderstandable());
		rationaleTraceableVoteResultLabel.setText("" + voteResult.getRationaleTraceable());
		rationaleCompleteVoteResultLabel.setText("" + voteResult.getRationaleComplete());
		rationaleConsistentVoteResultLabel.setText("" + voteResult.getRationaleConsistent());
		fitCriterionCompleteVoteResultLabel.setText("" + voteResult.getFitCriterionCorrect());
	}

}
