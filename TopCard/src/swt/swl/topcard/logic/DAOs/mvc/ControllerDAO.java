package swt.swl.topcard.logic.DAOs.mvc;

import swt.swl.topcard.controller.Controller;

/**
 * 
 * TODO: javadoc description
 * 
 * @author -steve-
 *
 */
public interface ControllerDAO {

	/**
	 * 
	 * TODO: javadoc
	 * 
	 * @param name
	 * @param controller
	 */
	void addController(String name, Controller controller);

}
