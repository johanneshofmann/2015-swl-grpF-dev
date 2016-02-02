package swt.swl.topcard.model;

import java.sql.Timestamp;
import java.util.ArrayList;

import javafx.beans.Observable;
import javafx.collections.ObservableList;
import swt.swl.topcard.logic.entitiy.RequirementCardSimple;

/**
 * 
 * TODO: javadoc
 * 
 * @author swt-041649
 *
 */
public interface RequirementCardModel extends Observable {

	/**
	 * 
	 * TODO: javadoc
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
	 * TODO: javadoc
	 * 
	 * @param text
	 * @param text2
	 * @param text3
	 */
	void deleteRequirement(String text, String text2, String text3);

	/**
	 * 
	 * TODO: javadoc
	 * 
	 * @return
	 */
	ObservableList<String> getModules();

	/**
	 * 
	 * TODO: javadoc
	 * 
	 * @return
	 */
	ObservableList<String> getUserStories();

	/**
	 * 
	 * TODO: javadoc
	 * 
	 * @return
	 */
	ObservableList<String> getTeams();

	/**
	 * 
	 * TODO: javadoc
	 * 
	 * @param string
	 * @param rqID
	 * @param b
	 * @return
	 */
	Object getXNamesAsStringByRqID(String string, int rqID, boolean b);

	/**
	 * 
	 * TODO: javadoc
	 * 
	 * @param id
	 * @return
	 */
	boolean noVotesSubmitted(int id);

	/**
	 * 
	 * TODO: javadoc
	 * 
	 * @param id
	 * @return
	 */
	Object[] getVoteResults(int id);

	/**
	 * 
	 * TODO: javadoc
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
	 * TODO: javadoc
	 * 
	 * @return
	 */
	ArrayList<String> getTeamsUserIsSubscribed();

	/**
	 * 
	 * TODO: javadoc
	 * 
	 * @param item
	 * @return
	 */
	boolean isFrozen(String item);

	/**
	 * 
	 * 
	 * TODO: javadoc
	 */
	void updateRequirementsList();

	/**
	 * 
	 * TODO: javadoc
	 * 
	 * @param ownerName
	 * @return
	 */
	boolean loginNameEqualsOwnerName(String ownerName);

	/**
	 * 
	 * TODO: javadoc
	 * 
	 * @param id
	 * @return
	 */
	boolean userAlreadyVoted(int id);

	/**
	 * 
	 * TODO: javadoc
	 * 
	 * @return
	 */
	boolean userAlreadySubscribed();

	/**
	 * 
	 * TODO: javadoc
	 * 
	 * @param string
	 */
	void letUserBeMemberOf(String string);

	/**
	 * 
	 * TODO: javadoc
	 * 
	 * @param string
	 */
	void letUserExitTeam(String string);

	/**
	 * 
	 * TODO: javadoc
	 * 
	 * @param loginName
	 */
	void setLoginName(String loginName);

	/**
	 * 
	 * TODO: javadoc
	 * 
	 * @param observableList
	 */
	void setObservableArray(ObservableList<RequirementCardSimple> observableList);

	/**
	 * 
	 * TODO: javadoc
	 * 
	 * @return
	 */
	ObservableList<RequirementCardSimple> getObservableArray();

	/**
	 * 
	 * TODO: javadoc
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
