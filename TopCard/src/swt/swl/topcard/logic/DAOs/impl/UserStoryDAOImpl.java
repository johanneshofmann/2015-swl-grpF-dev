package swt.swl.topcard.logic.DAOs.impl;

import java.util.Observable;

import swt.swl.topcard.logic.DAOs.UserStoryDAO;
import swt.swl.topcard.logic._impl.DatabaseHelper;
import swt.swl.topcard.model._Model;

public class UserStoryDAOImpl extends Observable implements _Model, UserStoryDAO {

	public boolean hasUserStory(String userStory) {
		return DatabaseHelper.checkExistent("UserStory", userStory);
	}

	public void insertUserStory(String text) {
		DatabaseHelper.insertSimpleX("UserStory", text);

	}

}
