
package swt.swl.topcard.controller.impl;

import java.util.Observable;
import java.util.Observer;

import org.controlsfx.control.CheckComboBox;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import swt.swl.topcard.controller.Controller;
import swt.swl.topcard.controller.CreateRequirementCardController;
import swt.swl.topcard.controller.RequirementCardController;
import swt.swl.topcard.model.RequirementCardModel;

public class CreateRequirementCardControllerImpl implements Observer, Controller, CreateRequirementCardController {

	private RequirementCardModel model;
	private RequirementCardController mainController;

	// need to instanciate manually cause we're using external jar
	private CheckComboBox<String> modulesCheckComboBox, userStoriesCheckComboBox, teamsCheckComboBox;

	@FXML
	private HBox moduleHBox, userStoriesHBox, sourceHBox;

	@FXML
	private TextArea descriptionTextArea, rationaleTextArea;

	@FXML
	private TextField titleTextField, supportingMaterialsTextField, fitCriterionTextField;

	@FXML
	private CheckBox frozenChoiceBox;

	@FXML
	private Button closeButton, createButton;

	public void initFXNodes() {

		frozenChoiceBox.setDisable(true);
		createButton.setText("Create version 0.0");

		addActualModulesToCheckComboBox();
		addActualUserStoriesToCheckComboBox();
		addActualTeamsToCheckComboBox();
	}

	@FXML
	public void closeWindow(ActionEvent event) {
		cancel(event);
	}

	@FXML
	public void putUpForVote(ActionEvent event) {

		checkEmpty();

		registerOnModel();

		ObservableList<String> userStories = userStoriesCheckComboBox.getCheckModel().getCheckedItems(),
				teams = teamsCheckComboBox.getCheckModel().getCheckedItems();

		model.insertRqIntoDatabase(modulesCheckComboBox.getCheckModel().getCheckedItems(), titleTextField.getText(),
				descriptionTextArea.getText(), rationaleTextArea.getText(), userStories, teams,
				fitCriterionTextField.getText(), supportingMaterialsTextField.getText());
	}

	public void registerOnModel() {

		((Observable) model).addObserver(this);
	}

	public void setData(RequirementCardModel rqModel, RequirementCardController requirementCardController) {

		this.model = rqModel;
		setMainController(requirementCardController);
	}

	public void addActualModulesToCheckComboBox() {

		// give actual Modules to the ModulesCheckComboBox

		ObservableList<String> modules = model.getModules();
		modulesCheckComboBox = new CheckComboBox<>(modules);

		moduleHBox.getChildren().add(modulesCheckComboBox);
	}

	public void addActualUserStoriesToCheckComboBox() {

		ObservableList<String> userStories = model.getUserStories();
		userStoriesCheckComboBox = new CheckComboBox<>(userStories);
		userStoriesHBox.getChildren().add(userStoriesCheckComboBox);

	}

	public void addActualTeamsToCheckComboBox() {

		ObservableList<String> teams = model.getTeams();
		teamsCheckComboBox = new CheckComboBox<>(teams);
		sourceHBox.getChildren().add(teamsCheckComboBox);

	}

	@Override
	public void setMainController(RequirementCardController requirementCardController) {
		this.mainController = requirementCardController;
	}

	@Override
	public void cancel(ActionEvent event) {

		Alert confirmation = new Alert(AlertType.CONFIRMATION, "Close without creating RQ-Card?");
		confirmation.showAndWait();
		if (confirmation.getResult().equals(ButtonType.OK)) {
			mainController.repaint();
		} else {
			event.consume();
		}
	}

	@Override
	public void checkEmpty() {

		if (descriptionTextArea.getText().isEmpty() || rationaleTextArea.getText().isEmpty()) {

			Alert al;

			if (modulesCheckComboBox.getCheckModel().getCheckedItems().size() == 0) {

				al = new Alert(AlertType.WARNING, "For reasons of integrity you should choose at least one Module.");

			} else if (teamsCheckComboBox.getCheckModel().getCheckedItems().size() == 0) {

				al = new Alert(AlertType.WARNING,
						"For reasons of integrity you should choose at least one Team as source.");
			} else {

				al = new Alert(AlertType.WARNING, "For reasons of integrity these fields should not be empty.");
			}
			al.getDialogPane().setPrefWidth(al.getDialogPane().getWidth() + 150);
			al.showAndWait();

		}

	}

	@Override
	public void update(Observable o, Object arg) {
		new Alert(AlertType.INFORMATION, "Reqirement in database now.").showAndWait();
	}

}
