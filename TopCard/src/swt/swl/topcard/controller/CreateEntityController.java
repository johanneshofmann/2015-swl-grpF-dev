package swt.swl.topcard.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import swt.swl.topcard.logic.DAOs.mvc.ModelDAO;

/**
 * 
 * A Controller-Interface with methods for creating Entities connecting one of
 * the DAOs t the {@link ModuleModel} with the {@link RequirementCardController}
 * Instantiated in the CreateModuleView
 * 
 * @author swt-041649
 *
 */
public interface CreateEntityController {

	/**
	 * 
	 * FXML-Annotated Event-Handler-method which calls first the
	 * {@link Controller} -Interfaces' {@code checkEmpty()}-method, then invokes
	 * a {@link Module} creation on the specific Entity-DAO
	 * 
	 * @see {@link ModelDAO}, {@link TeamDAO}, {@link UserStoryDAO}
	 */
	@FXML
	void create(ActionEvent event);

	/**
	 * 
	 * Method to perform a simple redirect to the previous view.
	 * 
	 * @param event
	 */
	@FXML
	public void cancel(ActionEvent event);
}
