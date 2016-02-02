package swt.swl.topcard.logic.eventHandler;

import java.util.ArrayList;

import swt.swl.topcard.controller.RequirementCardController;
import swt.swl.topcard.model.RequirementCardModel;

/**
 * 
 * TODO: javadoc
 * 
 * @author swt-041649
 *
 */
public interface TeamChangeListener {

	ArrayList<String> checkAndGetTeamsUserIsSubscribed();

	void setData(RequirementCardModel model, RequirementCardController requirementCardController);
}
