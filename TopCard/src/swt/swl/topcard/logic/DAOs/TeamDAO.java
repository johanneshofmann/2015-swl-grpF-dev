package swt.swl.topcard.logic.DAOs;

/**
 * 
 * @author swt-041649
 *
 */
public interface TeamDAO {

	/**
	 * 
	 * Checks wheather the team exists in the database.
	 * 
	 * @param value
	 * @return
	 */
	boolean hasTeam(String value);

	/**
	 * 
	 * Inserts a Team into the database.
	 * 
	 * @param value
	 */
	void insertTeam(String value);

}
