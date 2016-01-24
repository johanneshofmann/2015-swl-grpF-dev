package swt.swl.topcard.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;

/**
 * 
 * The Controller for creating Modules connects the {@code ModuleModel} with the
 * {@code RequirementCardController} Instantiated in the CreateModuleView
 * 
 * @author swt-041649
 *
 */
public interface CreateModuleController {

	/**
	 * After clicking the {@code createButton} a new {@code module} will be
	 * created if the {@code moduleNameTextField} is not empty and the
	 * {@code module} is not already existing in the database
	 * 
	 */
	@FXML
	void create();

	@FXML
	public void cancel(ActionEvent event);

	void setMainController(RequirementCardController requirementCardControllerImpl);
}
