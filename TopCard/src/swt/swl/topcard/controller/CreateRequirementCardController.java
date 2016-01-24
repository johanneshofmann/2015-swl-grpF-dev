package swt.swl.topcard.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import swt.swl.topcard.model.RequirementCardModel;

/**
 * 
 * 
 * @author swt-041649
 *
 */
public interface CreateRequirementCardController {

	@FXML
	void closeWindow(ActionEvent event);

	/**
	 *
	 * checks weather title, description,rationale,source,fitCriterion or
	 * userstories is empty,
	 *
	 *
	 * @throws new
	 *             Alert if one is empty and returns to editable View.
	 * @param event
	 */
	@FXML
	void putUpForVote(ActionEvent event);

	void initFXNodes();

	void addActualModulesToCheckComboBox();

	void addActualUserStoriesToCheckComboBox();

	void addActualTeamsToCheckComboBox();

	void setData(RequirementCardModel model, RequirementCardController requirementCardController);

}
