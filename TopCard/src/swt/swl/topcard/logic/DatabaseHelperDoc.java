package swt.swl.topcard.logic;

import java.util.ArrayList;

import javafx.collections.ObservableList;
import swt.swl.topcard.logic.entitiy.Module;
import swt.swl.topcard.logic.entitiy.SubmittedVoteSimple;
import swt.swl.topcard.logic.entitiy.Team;
import swt.swl.topcard.logic.entity.impl.RequirementCardSimpleImpl;

/**
 * The Database-Helper is a central object in TopCard application. It is
 * responsible for fetching and updating data from the database.
 * 
 * @author swt-041649
 *
 */
public interface DatabaseHelperDoc {

	/**
	 * 
	 * Takes a Name and returns the specific ID.
	 * 
	 */
	int XNameToID(String string, String userName);

	/**
	 * 
	 * @returns all requirement IDs from a given user.
	 * 
	 */
	ArrayList<Integer> getAllRQIDsFromUser(String userName);

	/**
	 * 
	 * @returns SubmittedVoteSimple containing all voteResults of a specific
	 *          rqCard
	 */
	ArrayList<SubmittedVoteSimple> getVoteResultsFrom(Integer id);

	/**
	 * 
	 * Only use for {@link Module}, {@link Team}, {@link UserStory}. otherwise
	 * method will return invalid result.
	 * 
	 * @param string
	 * @return
	 */
	ObservableList<String> getNamesFromSource(String string);

	/**
	 * 
	 * Executes a standard-SQL-Update.
	 * 
	 */
	void executeUpdate(String sqlInsert);

	/**
	 * 
	 * Executes a deletion of a given User from the database by name.
	 * 
	 */
	void deleteXFromDatabaseByName(String string, String loginName);

	/**
	 * 
	 */
	ObservableList<String> getNameFrom(String string);

	/**
	 * 
	 */
	boolean checkExistent(String string, String name);

	/**
	 * 
	 */
	void insertSimpleX(String string, String moduleName);

	/**
	 * 
	 */
	void deleteXFromDatabaseByID(String string, int iD);

	/**
	 * 
	 */
	int getMaxXFromY(String string, String string2);

	/**
	 * 
	 */
	ArrayList<Integer> getIDsFromX(String source, ObservableList<String> itemNames);

	/**
	 * 
	 */
	RequirementCardSimpleImpl IDToRequirementCardSimple(int id);

	/**
	 * 
	 */
	boolean checkUserAlreadySubscribed(String loginName);

	/**
	 * 
	 */
	void deleteRqFromDatabase(int rqID, int majorVersion, int minorVersion);

}
