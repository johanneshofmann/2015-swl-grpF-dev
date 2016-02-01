package swt.swl.topcard.controller;

import swt.swl.topcard.model.RequirementCardModel;

/**
 * 
 * @author swt-041649
 *
 */
public interface RequirementCardController {

	void repaint();

	boolean getCancelTransaction();

	void checkTeam(String teamStr);

	String getFirstTeam();

	RequirementCardModel getRqModel();

	void setData(String loginName, LoginController loginController);

	void initializeFXNodes();

}
