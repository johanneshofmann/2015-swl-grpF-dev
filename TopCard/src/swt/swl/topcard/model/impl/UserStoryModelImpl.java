package swt.swl.topcard.model.impl;

import swt.swl.topcard.logic.impl.DatabaseHelper;
import swt.swl.topcard.model.UserStoryModel;

public class UserStoryModelImpl implements UserStoryModel {

	public boolean hasUserStory(String userStory) {
		return DatabaseHelper.checkExistent("UserStory", userStory);
	}

	public void insertUserStory(String text) {
		DatabaseHelper.insertSimpleX("UserStory", text);

	}

}
