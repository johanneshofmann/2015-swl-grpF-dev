package swt.swl.topcard.model;

import static org.junit.Assert.*;
import org.junit.Test;

public class TestLoginModel {

	private LoginModel loginModel;

	public TestLoginModel() {

		loginModel = new LoginModel();
	}

	@Test
	public void testUserIsInDatabase() {
		String loginName = "hw5";
		boolean toCheck = loginModel.checkDatabase(loginName);
		assertEquals(true, toCheck);
	}

	@Test
	public void testUserIsNotInDatabase() {
		String loginName = "-wrong user name-";
		boolean toCheck = loginModel.checkDatabase(loginName);
		assertEquals(false, toCheck);
	}

	@Test
	public void afterInsertUserIntoDatabaseUserShouldBeIntoDatabase() {

		String firstName = "firstName";
		String lastName = "lastName";
		String loginName = "loginName";

		loginModel.insertUserIntoDatabase(firstName, lastName, loginName);

		boolean toCheck = loginModel.checkDatabase(loginName);

		assertEquals(true, toCheck);

		// remove after checking ..
		loginModel.deleteUserFromDatabase(loginName);
	}

	@Test
	public void ifUserWasDeletedFromDatabaseUserShouldBeRemovedAfterwards() {
		
		// first insert user : 
		
		String firstName = "firstName";
		String lastName = "lastName";
		String loginName = "loginName";

		loginModel.insertUserIntoDatabase(firstName, lastName, loginName);
		
		// then remove it:
		loginModel.deleteUserFromDatabase(loginName);

		boolean stillInDatabase = loginModel.checkDatabase(loginName);
		
		assertEquals(false, stillInDatabase);
	}
}
