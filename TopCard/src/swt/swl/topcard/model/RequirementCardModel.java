package swt.swl.topcard.model;

import java.sql.Timestamp;
import java.util.ArrayList;

import javafx.beans.Observable;
import javafx.collections.ObservableList;
import swt.swl.topcard.logic.RequirementCardSimple;
import swt.swl.topcard.logic.SubmittedVoteSimple;
import swt.swl.topcard.logic.impl.RequirementCardSimpleImpl;

/**
 * 
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

	RequirementCardSimpleImpl getOverviewDataFromSelectedRq(RequirementCardSimple toVote);

}
