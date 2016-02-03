package swt.swl.topcard.logic.DAOs.mvc;

import swt.swl.topcard.controller.Controller;

/**
 * 
 * Interface for a DAO(Data-Access-Object) that deals with controllers.
 * 
 * @author -steve-
 *
 */
public interface ControllerDAO {

	/**
	 * 
	 * The commom way to add an entity.
	 * 
	 * @param name
	 * @param controller
	 */
	void addController(String name, Controller controller);

}
