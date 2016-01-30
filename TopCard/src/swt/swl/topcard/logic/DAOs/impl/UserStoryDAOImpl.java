package swt.swl.topcard.logic.DAOs.impl;

import swt.swl.topcard.logic.DAOs.UserStoryDAO;
import swt.swl.topcard.logic.impl.DatabaseHelper;

public class UserStoryDAOImpl implements UserStoryDAO {

	public boolean hasUserStory(String userStory) {
		return DatabaseHelper.checkExistent("UserStory", userStory);
	}

	public void insertUserStory(String text) {
		DatabaseHelper.insertSimpleX("UserStory", text);

	}

}
