package swt.swl.topcard.logic.DAOs;

/**
 * 
 * @author swt-041649
 *
 */
public interface UserStoryDAO {

	/**
	 * 
	 * TODO: javadoc
	 * 
	 * @param userStory
	 * @return
	 */
	boolean hasUserStory(String userStory);

	/**
	 * 
	 * TODO: javadoc
	 * 
	 * @param text
	 */
	void insertUserStory(String text);

}
