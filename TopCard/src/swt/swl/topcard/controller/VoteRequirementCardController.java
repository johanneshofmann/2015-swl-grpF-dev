package swt.swl.topcard.controller;

import swt.swl.topcard.logic.RequirementCardSimple;
import swt.swl.topcard.model.RequirementCardModel;

/**
 * 
 * @author swt-041649
 *
 */
public interface VoteRequirementCardController {

	void setData(RequirementCardModel model, RequirementCardController requirementCardController,
			RequirementCardSimple rq);

}
