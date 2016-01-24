package swt.swl.topcard.controller;

import javafx.event.ActionEvent;
import swt.swl.topcard.controller.impl.RequirementCardControllerImpl;

/**
 * A Controller-Interface with methods each Controller should provide.
 * 
 * @author Steve
 */
public interface Controller {

	/**
	 * A 'setter'-method which sets the main controller instance of each
	 * Controller e.g. necessary for an efficient redirect after performing some
	 * operations.
	 * 
	 * @param requirementCardController
	 */
	void setMainController(RequirementCardController requirementCardController);

	/**
	 * This method performs the redirect to the main controller instance, mostly
	 * called in conjunction with the repaint()-method of the
	 * {@link RequirementCardControllerImpl}
	 * 
	 * @param event
	 */
	void cancel(ActionEvent event);

	/**
	 * checks all necessary GUI-input-nodes for emptyness
	 */
	void checkEmpty();
}
