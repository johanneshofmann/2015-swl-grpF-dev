package swt.swl.topcard.logic.DAOs;

/**
 * 
 * @author swt-041649
 *
 */
public interface ModuleDAO {

	/**
	 * 
	 * Checks wheather a module already exists or not.
	 * 
	 * @param value
	 * @return
	 */
	boolean hasModule(String value);

	/**
	 * 
	 * Inserts a module into the database.
	 * 
	 * @param value
	 */
	void insertModule(String value);

}
