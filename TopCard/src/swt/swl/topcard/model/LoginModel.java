package swt.swl.topcard.model;

import javafx.beans.Observable;

/**
 * 
 * TODO: javadoc
 * 
 * @author swt-041649
 *
 */
public interface LoginModel extends Observable {

	/**
	 * 
	 * TODO: javadoc
	 * 
	 * @param text
	 * @return
	 */
	boolean checkDatabase(String text);

	/**
	 * 
	 * TODO: javadoc
	 * 
	 * @param firstName
	 * @param lastName
	 * @param loginName
	 */
	void insertUserIntoDatabase(String firstName, String lastName, String loginName);

	/**
	 * 
	 * TODO: javadoc
	 * 
	 * @param loginName
	 */
	void deleteUserFromDatabase(String loginName);

}
