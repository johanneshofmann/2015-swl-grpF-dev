package swt.swl.topcard.logic.DAOs.mvc.impl;

import java.util.HashMap;
import swt.swl.topcard.controller.Controller;
import swt.swl.topcard.logic.DAOs.mvc.ControllerDAO;

public class ControllerDAOImpl implements ControllerDAO {

	public static HashMap<String, Controller> controllers = new HashMap<>();

	public void addController(String name, Controller controller) {

		controllers.put(name, controller);
	}

}
