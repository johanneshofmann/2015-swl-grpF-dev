package swt.swl.topcard.controller;

import java.sql.Timestamp;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import swt.swl.topcard.logic.RequirementCardSimple;
import swt.swl.topcard.model.RequirementCardModel;

/**
 * 
 * @author swt-041649
 *
 */
public interface EditRequirementCardController {

	@FXML
	void closeWindow(ActionEvent event);

	@FXML
	void editButtonClicked(ActionEvent event);

	@FXML
	void deleteRequirementButtonClicked(ActionEvent event);

	void initializeFXNodes();

	Timestamp getCreatedAt();

	void fillRqCardDataInTextFields();

	void initCheckComboBoxes();

	void addEventHandlerToFrozenChoiceBox();

	void fillVoteResultsLabels();

	void setData(RequirementCardModel rqModel, RequirementCardController requirementCardController,
			RequirementCardSimple toEdit);

}
