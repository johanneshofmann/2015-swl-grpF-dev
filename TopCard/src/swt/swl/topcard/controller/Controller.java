package swt.swl.topcard.controller;

import javafx.event.ActionEvent;

public interface Controller {

	void setMainController(RequirementCardController requirementCardController);

	void cancel(ActionEvent event);

	void checkEmpty();
}
