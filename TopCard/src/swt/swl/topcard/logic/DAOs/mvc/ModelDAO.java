package swt.swl.topcard.logic.DAOs.mvc;

import swt.swl.topcard.model._Model;

/**
 * 
 * Interface for a DAO(Data-Access-Object) that deals with models.
 * 
 * @author -steve-
 *
 */
public interface ModelDAO {

	/**
	 * 
	 * The commom way to add an entity.
	 * 
	 * @param name
	 * @param model
	 */
	void addModel(String name, _Model model);

}
