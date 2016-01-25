package swt.swl.topcard.model;

import java.sql.Timestamp;
import java.util.ArrayList;

import javafx.beans.Observable;
import javafx.collections.ObservableList;
import swt.swl.topcard.logic.RequirementCardSimple;
import swt.swl.topcard.logic.SubmittedVoteSimple;

/**
 * 
 * @author swt-041649
 *
 */
public interface RequirementCardModel extends Observable {

	void insertEditedRqIntoDatabase(ObservableList<String> checkedItems, String text, int parseInt, int parseInt2,
			int i, String text2, String text3, ObservableList<String> checkedItems2,
			ObservableList<String> checkedItems3, String text4, String text5, boolean b, Timestamp createdAt);

	void deleteRequirement(String text, String text2, String text3);

	ObservableList<String> getModules();

	ObservableList<String> getUserStories();

	ObservableList<String> getTeams();

	Object getXNamesAsStringByRqID(String string, int rqID, boolean b);

	boolean noVotesSubmitted(int id);

	SubmittedVoteSimple getVoteResults(int id);

	int insertRqIntoDatabase(ObservableList<String> modules, String title, String description,
			String rationale, ObservableList<String> source, ObservableList<String> userStories, String fitCriterion, String supportingMaterials);
	
	ArrayList<String> getTeamsUserIsSubscribed();

	boolean isFrozen(String item);

	void updateRequirementsList();

	boolean loginNameEqualsOwnerName(String ownerName);

	boolean userAlreadyVoted(int id);

	boolean userAlreadySubscribed();

	void letUserBeMemberOf(String string);

	void letUserExitTeam(String string);

	void setLoginName(String loginName);

	void setObservableArray(ObservableList<RequirementCardSimple> observableList);

	ObservableList<RequirementCardSimple> getObservableArray();

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
