package swt.swl.topcard.logic.eventHandler;

import java.util.ArrayList;

import swt.swl.topcard.controller.RequirementCardController;
import swt.swl.topcard.model.RequirementCardModel;

/**
 * 
 * A change listener for the subscribe on team CheckComboBox
 * 
 * @author swt-041649
 *
 */
public interface TeamChangeListener {

	/**
	 * checks all teams where the user is subscribed and returns them.
	 * 
	 * @return ArrayList<String>
	 */
	ArrayList<String> checkAndGetTeamsUserIsSubscribed();

	void setData(RequirementCardModel model, RequirementCardController requirementCardController);
}
