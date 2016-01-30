package swt.swl.topcard.logic.DAOs;

/**
 * 
 * 
 * @author swt-041649
 *
 */
public interface UserStoryDAO {

	boolean hasUserStory(String userStory);

	void insertUserStory(String text);

}
