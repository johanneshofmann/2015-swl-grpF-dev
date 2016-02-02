package swt.swl.topcard.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import swt.swl.topcard.logic.entitiy.RequirementCardSimple;
import swt.swl.topcard.model.RequirementCardModel;

/**
 * Interface for {@link EditRequirementCardControllerImpl}.
 * 
 * @author swt-041649
 *
 */
public interface EditRequirementCardController {

	/**
	 * 
	 * Performs the following actions:
	 * <ul>
	 * <li>First calls the {@link Controller}-Interfaces' {@code checkEmpty()}
	 * -method,</li>
	 * <li>then registers on model,</li>
	 * <li>fetches data from GUI-nodes and</li>
	 * <li>calls the {@link RequirementCardModel}s'
	 * {@code insertEditedIntoDatabase()} -method.
	 * </ul>
	 * <br>
	 * 
	 * @param event
	 */
	@FXML
	void editButtonClicked(ActionEvent event);

	/**
	 * 
	 * Deletes an actual {@link RequirementCardSimple} from the database by its
	 * {@code RQID,Major- and MinorVersion}.
	 * 
	 * @param event
	 */
	@FXML
	void deleteRequirementButtonClicked(ActionEvent event);

	/**
	 * 
	 * Initializes all GUI-nodes that haven't been initialized by the
	 * {@link FXMLLoader}.
	 * 
	 */
	void initializeFXNodes();

	/**
	 * 
	 * ???
	 * 
	 */
	void fillRqCardDataInTextFields();

	/**
	 * 
	 * Performs the following actions:
	 * <ul>
	 * <li>initializes {@code CheckComboBoxes},</li>
	 * <li>fetches all current data from the database,</li>
	 * <li>allocates it to the {@code CheckComboBoxes'} {@code children}.</li
	 * <li>asks the model for actual values that need to be checked</li>
	 * <li>checks actual values in the {@code CheckComboBoxes}</li>
	 * </ul>
	 * <br>
	 */
	void initCheckComboBoxes();

	/**
	 * 
	 * Simply defines an {@link EventHandler} for the isFrozen {@code ChoiceBox}
	 * 
	 */
	void addEventHandlerToFrozenChoiceBox();

	/**
	 * 
	 * Let the {@link Model}fetch -if present- actual {@code Votes} from the
	 * database and add the calculated everage vote value to corresponding
	 * {@link TextField}s.
	 * 
	 */
	void fillVoteResultsLabels();

	/**
	 * 
	 * In the most cases, the {@code setData()}-method executes the three
	 * {@code Setter}-methods <br>
	 * <ul>
	 * <li>{@code setModel()</li>
	 * <li>{@code setMainController()}</li>
	 * <li>{@code setToEdit()}</li>
	 * </ul>
	 * <br>
	 * with the given arguments. Or performs this actions on an other way.<br>
	 * <br>
	 * 
	 */
	void setData(RequirementCardModel rqModel, RequirementCardController requirementCardController,
			RequirementCardSimple toEdit);

}
