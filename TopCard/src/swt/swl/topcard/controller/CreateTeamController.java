package swt.swl.topcard.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;

/**
 * 
 * @author swt-041649
 *
 */
public interface CreateTeamController {

	@FXML
	void create();

	@FXML
	public void cancel(ActionEvent event);

	void setMainController(RequirementCardController requirementCardController);
}
