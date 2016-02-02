package swt.swl.topcard.controller.impl;

import java.util.Observable;
import java.util.Observer;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import swt.swl.topcard.controller.Controller;
import swt.swl.topcard.controller.CreateUserStoryController;
import swt.swl.topcard.controller.RequirementCardController;
import swt.swl.topcard.logic.DAOs.UserStoryDAO;
import swt.swl.topcard.logic.DAOs.impl.UserStoryDAOImpl;
import swt.swl.topcard.logic.DAOs.mvc.impl.ModelDAOImpl;
import swt.swl.topcard.model._Model;

public class CreateUserStoryControllerImpl implements Observer, Controller, CreateUserStoryController {

	private RequirementCardController mainController;
	private UserStoryDAO model;

	@FXML
	private TextField userStoryTextField;

	@FXML
	private Button createButton, cancelButton;

	public CreateUserStoryControllerImpl() {

		model = new UserStoryDAOImpl();
		ModelDAOImpl.models.put("UserStory", (_Model) model);

		registerOnModel();
	}

	@FXML
	public void cancel(ActionEvent event) {
		mainController.repaint();
	}

	@FXML
	public void create(ActionEvent event) {

		// 0. Check whether a string has been entered in the text field
		checkEmpty();

		String userStory = this.userStoryTextField.getText();

		// 1. Check whether userStory with the name exists
		if (!model.hasUserStory(userStory)) {

			// 2. Add userStory to the database
			model.insertUserStory(userStoryTextField.getText());

		} else {
			new Alert(AlertType.WARNING, "UserStory with the name " + userStory + " already exists.").showAndWait();
		}
	}

	@Override
	public void setMainController(RequirementCardController requirementCardController) {

		this.mainController = requirementCardController;
	}

	@Override
	public void checkEmpty() {

		if (userStoryTextField.getText().isEmpty()) {
			new Alert(AlertType.WARNING, "UserStory name is empty.").showAndWait();
		}
	}

	@Override
	public void update(Observable o, Object arg) {

		new Alert(AlertType.CONFIRMATION, "UserStoriy has been added successfully.").showAndWait();
		mainController.repaint();
	}

	@Override
	public void registerOnModel() {
		((Observable) model).addObserver(this);
	}
}
