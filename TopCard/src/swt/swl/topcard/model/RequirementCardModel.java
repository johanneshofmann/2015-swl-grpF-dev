package swt.swl.topcard.model;

import java.sql.Timestamp;
import java.util.ArrayList;

import javafx.beans.Observable;
import javafx.collections.ObservableList;
import swt.swl.topcard.logic.entitiy.RequirementCardSimple;

/**
 * 
 * The Model for all operations dealing with Requirements
 * 
 * @author swt-041649
 *
 */
public interface RequirementCardModel extends Observable {

	/**
	 * 
	 * Inserts an edited Requirement Card into the database.
	 * 
	 * @param checkedItems
	 * @param text
	 * @param parseInt
	 * @param parseInt2
	 * @param i
	 * @param text2
	 * @param text3
	 * @param checkedItems2
	 * @param checkedItems3
	 * @param text4
	 * @param text5
	 * @param b
	 * @param createdAt
	 */
	void insertEditedRqIntoDatabase(ObservableList<String> checkedItems, String text, int parseInt, int parseInt2,
			int i, String text2, String text3, ObservableList<String> checkedItems2,
			ObservableList<String> checkedItems3, String text4, String text5, boolean b, Timestamp createdAt);

	/**
	 * 
	 * Deletes a requirement by
	 * 
	 * @param text
	 * @param text2
	 * @param text3
	 */
	void deleteRequirement(int text, int text2, int text3);

	/**
	 * 
	 * Lets the DatabaseHelper fetch all current Modules from database.
	 * 
	 * @return it in an ObservableList
	 * 
	 */
	ObservableList<String> getModules();

	/**
	 * 
	 * Lets the DatabaseHelper fetch all current serStories from database.
	 * 
	 * @return it in an ObservableList
	 */
	ObservableList<String> getUserStories();

	/**
	 * 
	 * Lets the DatabaseHelper fetch all current Teams from database.
	 * 
	 * @return it in an ObservableList
	 */
	ObservableList<String> getTeams();

	/**
	 * 
	 * 
	 * @param string
	 * @param rqID
	 * @param b
	 * @return
	 */
	Object getXNamesAsStringByRqID(String string, int rqID, boolean b);

	/**
	 *
	 * Checks wheather there are any votes on the given requirementID
	 *
	 * @param id
	 * @return
	 */
	boolean noVotesSubmitted(int id);

	/**
	 * 
	 * @param id
	 * @returns the vote results of a given requirementID
	 */
	Object[] getVoteResults(int id);

	/**
	 * 
	 * This method makes the DatabaseHelper insert the requirement into
	 * database.
	 * 
	 * @param modules
	 * @param title
	 * @param description
	 * @param rationale
	 * @param source
	 * @param userStories
	 * @param fitCriterion
	 * @param supportingMaterials
	 * @return
	 */
	int insertRqIntoDatabase(ObservableList<String> modules, String title, String description, String rationale,
			ObservableList<String> source, ObservableList<String> userStories, String fitCriterion,
			String supportingMaterials);

	/**
	 * 
	 * Lets the DatabaseHelper fetch all teams the user is subsribed from
	 * database.
	 * 
	 * @return
	 */
	ArrayList<String> getTeamsUserIsSubscribed();

	/**
	 * 
	 * @param item
	 * @return true, if RQ is frozen.
	 */
	boolean isFrozen(String item);

	/**
	 * Calls the getRequirements()-method on DatabaseHelper and allocates all
	 * current requirements to the observable list.
	 * 
	 */
	void updateRequirementsList();

	/**
	 * 
	 * Checks wheather the loginName equals the ownerName.
	 * 
	 * @param ownerName
	 * @return
	 */
	boolean loginNameEqualsOwnerName(String ownerName);

	/**
	 * 
	 * Checks wheather an user has already votes on a requirement card.
	 * 
	 * @param id
	 * @return
	 */
	boolean userAlreadyVoted(int id);

	/**
	 * 
	 * Checks wheather an user is already subscribed.
	 * 
	 * @return
	 */
	boolean userAlreadySubscribed();

	/**
	 * 
	 * Lets the user enter a team identified by the given string.
	 * 
	 * @param string
	 */
	void letUserBeMemberOf(String string);

	/**
	 * 
	 * Lets the user exit a team identified by the given string.
	 * 
	 * @param string
	 */
	void letUserExitTeam(String string);

	/**
	 * 
	 * Simple Setter-Method.
	 * 
	 * @param loginName
	 */
	void setLoginName(String loginName);

	/**
	 * 
	 * Simple Setter-Method.
	 * 
	 * @param observableList
	 */
	void setObservableArray(ObservableList<RequirementCardSimple> observableList);

	/**
	 * 
	 * Simple Getter-Method.
	 * 
	 * @return
	 */
	ObservableList<RequirementCardSimple> getObservableArray();

	/**
	 * 
	 * Lets the DatabaseHelper insert a new Vote with the given values into
	 * database.
	 * 
	 * @param id
	 * @param selectedItems
	 */
	void newVoteSubmitted(int id, int[] selectedItems);

	/**
	 * Stores all Data for showing an overview from a selected Requirement Card
	 * in a RequirementCardSimple and returns it.<br>
	 *
	 * @param selected,
	 *            the selected Item from the RqCardTableView
	 * @returns RequirementCardSimple containing all data of a selected
	 *          requirement, including <br>
	 *          <ul>
	 *          <li>OwnerName,</li>
	 *          <li>ModuleName</li>
	 *          <li>Title</li>
	 *          <li>Description,</li>
	 *          <li>Rationale,</li>
	 *          <li>Source,</li>
	 *          <li>UserStories</li>
	 *          <li>SupportingMaterials,</li>
	 *          <li>FitCriterion,</li>
	 *          <li>IsFrozen,</li>
	 *          <li>CreatedAt</li>
	 *          <li>LastUpdatedAt</li>
	 *          </ul>
	 */
	RequirementCardSimple getOverviewDataFromSelectedRq(RequirementCardSimple toVote);

}
