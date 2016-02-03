package swt.swl.topcard.model;

import javafx.beans.Observable;

/**
 * 
 * The Login Models' Interface
 * 
 * @author swt-041649
 *
 */
public interface LoginModel extends Observable {

	/**
	 * checks wheather an user is in the database or not.
	 * 
	 * @param text
	 */
	boolean checkDatabase(String text);

	/**
	 * 
	 * Inserts an User into the Database.
	 * 
	 * @param firstName
	 * @param lastName
	 * @param loginName
	 */
	void insertUserIntoDatabase(String firstName, String lastName, String loginName);

	/**
	 * 
	 * Deletes an User from the database.
	 * 
	 * @param loginName
	 */
	void deleteUserFromDatabase(String loginName);

}
