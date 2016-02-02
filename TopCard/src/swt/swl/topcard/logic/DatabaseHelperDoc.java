package swt.swl.topcard.logic;

import java.util.ArrayList;

import javafx.collections.ObservableList;
import swt.swl.topcard.logic.entitiy.Module;
import swt.swl.topcard.logic.entitiy.SubmittedVoteSimple;
import swt.swl.topcard.logic.entitiy.Team;
import swt.swl.topcard.logic.entity.impl.RequirementCardSimpleImpl;

public interface DatabaseHelperDoc {

	/**
	 * 
	 * TODO: javadoc
	 * 
	 */
	int XNameToID(String string, String userName);

	/**
	 * 
	 * TODO: javadoc
	 * 
	 */
	ArrayList<Integer> getAllRQIDsFromUser(String userName);

	/**
	 * voteResults[0] = description precise voteResult<br>
	 * voteResults[1] = description understandable voteResult<br>
	 * voteResults[2] = description correct voteResult<br>
	 * voteResults[3] = description complete voteResult<br>
	 * voteResults[4] = description atomic voteResult<br>
	 * voteResults[5] = rationale precise voteResult<br>
	 * voteResults[6] = rationale understandable voteResult<br>
	 * voteResults[7] = rationale traceable voteResult<br>
	 * voteResults[8] = rationale complete voteResult<br>
	 * voteResults[9] = rationale consistent voteResult<br>
	 * voteResults[10] = fit Criterion complete voteResult<br>
	 *
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
	 * TODO: javadoc
	 * 
	 */
	void executeUpdate(String sqlInsert);

	/**
	 * 
	 * TODO: javadoc
	 * 
	 */
	void deleteXFromDatabaseByName(String string, String loginName);

	/**
	 * 
	 * TODO: javadoc
	 * 
	 */
	ObservableList<String> getNameFrom(String string);

	/**
	 * 
	 * TODO: javadoc
	 * 
	 */
	boolean checkExistent(String string, String name);

	/**
	 * 
	 * TODO: javadoc
	 * 
	 */
	void insertSimpleX(String string, String moduleName);

	/**
	 * 
	 * TODO: javadoc
	 * 
	 */
	void deleteXFromDatabaseByID(String string, int iD);

	/**
	 * 
	 * TODO: javadoc
	 * 
	 */
	int getMaxXFromY(String string, String string2);

	/**
	 * 
	 * TODO: javadoc
	 * 
	 */
	ArrayList<Integer> getIDsFromX(String source, ObservableList<String> itemNames);

	/**
	 * 
	 * TODO: javadoc
	 * 
	 */
	RequirementCardSimpleImpl IDToRequirementCardSimple(int id);

	/**
	 * 
	 * TODO: javadoc
	 * 
	 */
	boolean checkUserAlreadySubscribed(String loginName);

	/**
	 * 
	 * TODO: javadoc
	 * 
	 */
	void deleteRqFromDatabase(int rqID, int majorVersion, int minorVersion);

}
