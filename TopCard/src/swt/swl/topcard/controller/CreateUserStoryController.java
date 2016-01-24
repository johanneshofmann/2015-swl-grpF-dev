package swt.swl.topcard.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;

/**
 * 
 * @author swt-041649
 *
 */
public interface CreateUserStoryController {

	@FXML
	void cancel(ActionEvent event);

	@FXML
	void create(ActionEvent event);

	void setMainController(RequirementCardController requirementCardController);

}
