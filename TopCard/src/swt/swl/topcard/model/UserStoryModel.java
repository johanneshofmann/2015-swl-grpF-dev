package swt.swl.topcard.model;

import swt.swl.topcard.logic.DatabaseHelper;

public class UserStoryModel {

	public boolean hasUserStory(String userStory) {
		return DatabaseHelper.checkExistent("UserStory", userStory);
	}

	public void insertUserStory(String text) {
		DatabaseHelper.insertSimpleX("UserStory", text);

	}

}
