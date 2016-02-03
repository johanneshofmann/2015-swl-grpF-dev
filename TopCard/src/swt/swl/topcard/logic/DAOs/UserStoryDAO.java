package swt.swl.topcard.logic.DAOs;

/**
 * 
 * @author swt-041649
 *
 */
public interface UserStoryDAO {

	/**
	 * 
	 * Checks wheather a UserStory exists.
	 * 
	 * @param userStory
	 * @returns true, if yes
	 */
	boolean hasUserStory(String userStory);

	/**
	 * 
	 * Inserts a UserStory into the database.
	 * 
	 * @param text
	 */
	void insertUserStory(String text);

}
