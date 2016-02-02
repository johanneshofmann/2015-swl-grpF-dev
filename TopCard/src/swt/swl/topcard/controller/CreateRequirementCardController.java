package swt.swl.topcard.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import swt.swl.topcard.logic.entitiy.Team;
import swt.swl.topcard.model.RequirementCardModel;

/**
 * 
 * Interface for {@link CreateRequirementCardControllerImpl}.
 * 
 * @author swt-041649
 *
 */
public interface CreateRequirementCardController {

	/**
	 *
	 * Performs the following actions:
	 * <ul>
	 * <li>First calls the {@link Controller}-Interfaces' {@code checkEmpty()}
	 * -method,</li>
	 * <li>then registers on model,</li>
	 * <li>fetches data from GUI-nodes and</li>
	 * <li>calls the {@link RequirementCardModel}s' {@code insertIntoDatabase()}
	 * -method.
	 * </ul>
	 *
	 * @param event
	 */
	@FXML
	void putUpForVote(ActionEvent event);

	/**
	 * 
	 * Initializes all GUI-nodes that haven't been initialized by the
	 * {@link FXMLLoader}.
	 * 
	 */
	void initFXNodes();

	/**
	 * 
	 * Fetches all current {@link Module}s from the database and allocates it to
	 * the {@code CheckComboBoxes'} {@code children}.
	 * 
	 */
	void addActualModulesToCheckComboBox();

	/**
	 * 
	 * Fetches all current {@link UserStory}s from the database and allocates it
	 * to the {@code CheckComboBoxes'} {@code children}.
	 * 
	 */
	void addActualUserStoriesToCheckComboBox();

	/**
	 * 
	 * Fetches all current {@link Team}s from the database and allocates it to
	 * the {@code CheckComboBoxes'} {@code children}.
	 * 
	 */
	void addActualTeamsToCheckComboBox();

	/**
	 * 
	 * In the most cases, the {@code setData()-method} executes the two
	 * {@code Setter-methods} <br>
	 * <ul>
	 * <li>setModel()</li>
	 * <li>setMainController</li><br>
	 * with the given arguments. <br>
	 * <br>
	 * 
	 * @param model
	 * @param requirementCardController
	 */
	void setData(RequirementCardModel model, RequirementCardController requirementCardController);

}
