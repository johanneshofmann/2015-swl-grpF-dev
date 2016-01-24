package swt.swl.topcard.model.impl;

import swt.swl.topcard.logic.impl.DatabaseHelperImpl;
import swt.swl.topcard.model.UserStoryModel;

public class UserStoryModelImpl implements UserStoryModel {

	public boolean hasUserStory(String userStory) {
		return DatabaseHelperImpl.checkExistent("UserStory", userStory);
	}

	public void insertUserStory(String text) {
		DatabaseHelperImpl.insertSimpleX("UserStory", text);

	}

}
