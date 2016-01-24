package swt.swl.topcard.model;

import javafx.beans.Observable;

/**
 * 
 * 
 * @author swt-041649
 *
 */
public interface LoginModel extends Observable {

	boolean checkDatabase(String text);

	void insertUserIntoDatabase(String firstName, String lastName, String loginName);

}
