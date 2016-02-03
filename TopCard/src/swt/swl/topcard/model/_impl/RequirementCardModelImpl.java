package swt.swl.topcard.model._impl;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Observable;

import javafx.beans.InvalidationListener;
import javafx.collections.ObservableList;
import swt.swl.topcard.logic._impl.DatabaseHelper;
import swt.swl.topcard.logic._impl.StatisticsHelper;
import swt.swl.topcard.logic.entitiy.RequirementCardSimple;
import swt.swl.topcard.logic.entitiy.SubmittedVoteSimple;
import swt.swl.topcard.model.RequirementCardModel;
import swt.swl.topcard.model._Model;

public class RequirementCardModelImpl extends Observable implements _Model, RequirementCardModel {

	// loginName is set when the registrationView is created.
	private String loginName;

	private static boolean INSERTFIRSTVERSION = true;

	private ObservableList<RequirementCardSimple> observableArray;

	public RequirementCardModelImpl() {
		createLastModifiedAtString();

	}

	public boolean loginNameEqualsOwnerName(String ownerName) {

		return (loginName.equals(ownerName) ? true : false);
	}

	public synchronized void insertEditedRqIntoDatabase(ObservableList<String> modules, String title, int rqID,
			int majorVersion, int minorVersion, String description, String rationale, ObservableList<String> source,
			ObservableList<String> userStories, String fitCriterion, String supportingMaterials, boolean isFrozen,
			Timestamp createdAt) {

		INSERTFIRSTVERSION = false;

		if (isFrozen) {
			majorVersion += 1;
			minorVersion = 0;
		}
		// fetch ownerID
		int ownerID = DatabaseHelper.XNameToID("User", loginName);

		// first insert into Requirement table
		String sqlInsertIntoRequirementUpdate = "INSERT INTO Requirement(Title, MajorVersion, MinorVersion, OwnerID, Requirement, Description, Rationale, SupportingMaterials, FitCriterion, CreatedAt,LastModifiedAt) VALUES ('"
				+ title + "', " + majorVersion + ", " + minorVersion + ", " + ownerID + ", " + rqID + ", '"
				+ description + "', '" + rationale + "', '" + supportingMaterials + "', '" + fitCriterion + "', '"
				+ createdAt + "', '" + createLastModifiedAtString() + "')";

		DatabaseHelper.executeUpdate(sqlInsertIntoRequirementUpdate);

		// Fetch ID from added RQ :
		int ID = DatabaseHelper.getMaxXFromY("ID", "Requirement");

		insertAllPairsIntoEachTable(ID, modules, userStories, source);

		// let the controller know that sth. has changed
		triggerNotification(INSERTFIRSTVERSION);
	}

	public synchronized int insertRqIntoDatabase(ObservableList<String> modules, String title, String description,
			String rationale, ObservableList<String> source, ObservableList<String> userStories, String fitCriterion,
			String supportingMaterials) {

		INSERTFIRSTVERSION = true;

		int minorVersion = 1;
		int majorVersion = 1;

		// fetch ownerID
		int ownerID = DatabaseHelper.XNameToID("User", loginName);

		// fetch biggest RqID
		int rqCardID = 1 + DatabaseHelper.getMaxXFromY("Requirement", "Requirement");

		// first insert into Requirement table
		String sqlInsertIntoRequirementUpdate = "INSERT INTO Requirement(Title, MajorVersion, MinorVersion, OwnerID, Requirement, Description, Rationale, SupportingMaterials, FitCriterion, LastModifiedAt) VALUES ('"
				+ title + "', " + majorVersion + ", " + minorVersion + ", " + ownerID + ", " + rqCardID + ", '"
				+ description + "', '" + rationale + "', '" + supportingMaterials + "', '" + fitCriterion + "', '"
				+ createLastModifiedAtString() + "')";

		DatabaseHelper.executeUpdate(sqlInsertIntoRequirementUpdate);

		// Fetch ID from added RQ :

		int rqID = DatabaseHelper.getMaxXFromY("ID", "Requirement");

		insertAllPairsIntoEachTable(rqID, modules, userStories, source);

		// let the controller know that sth. has changed
		triggerNotification(INSERTFIRSTVERSION);

		return rqID;
	}

	private void insertAllPairsIntoEachTable(int rqID, ObservableList<String> modules,
			ObservableList<String> userStories, ObservableList<String> source) {

		// insert each (RqID,ModuleID)-Pair,
		insertEachRqIDXIDPairIntoDatabase("Module", modules, rqID);

		if (userStories.size() != 0) {
			// each (RqID,UserStoryID)-Pair ..
			insertEachRqIDXIDPairIntoDatabase("UserStory", userStories, rqID);
		}

		// .. and each (RqID,TeamID)-Pair into table

		insertEachRqIDXIDPairIntoDatabase("Team", source, rqID);
	}

	private void insertEachRqIDXIDPairIntoDatabase(String source, ObservableList<String> itemNames, int rqID) {

		ArrayList<Integer> sourceIDs = DatabaseHelper.getIDsFromX(source, itemNames);

		for (Integer sourceID : sourceIDs) {

			String sqlInsertIntoRequirementSourceUpdate = "INSERT INTO Requirement" + source + " (RequirementID, "
					+ source + "ID) VALUES(" + rqID + "," + sourceID + ")";

			DatabaseHelper.executeUpdate(sqlInsertIntoRequirementSourceUpdate);
		}
	}

	private String createLastModifiedAtString() {

		String time = new java.util.Date().toString().substring(11, 19);

		return (new Date(System.currentTimeMillis()) + " " + time);
	}

	public RequirementCardSimple getOverviewDataFromSelectedRq(RequirementCardSimple selected) {

		return DatabaseHelper.IDToRequirementCardSimple(selected.getID());

	}

	public void updateRequirementsList() {

		ArrayList<RequirementCardSimple> requirements = DatabaseHelper.getRequirements();

		observableArray.clear();

		for (RequirementCardSimple rqCard : requirements) {

			observableArray.add(rqCard);
		}
	}

	private void triggerNotification(Boolean message) {
		setChanged();
		notifyObservers(message);
	}

	public void newVoteSubmitted(int requirementID, int[] selectedItems) {

		int userIDInt = DatabaseHelper.XNameToID("User", loginName);

		String query = "INSERT INTO Vote(RequirementID, UserID ,DescriptionPrecise , DescriptionUnderstandable ,DescriptionCorrect ,"
				+ "DescriptionComplete, DescriptionAtomic, RationalePrecise, RationaleUnderstandable, RationaleTraceable, "
				+ "RationaleComplete, RationaleConsistent, FitCriterionComplete, CreatedAt) VALUES (" + requirementID
				+ "," + userIDInt + "," + selectedItems[0] + "," + selectedItems[1] + "," + selectedItems[2] + ", "
				+ selectedItems[3] + ", " + selectedItems[4] + ", " + selectedItems[5] + "," + selectedItems[6] + ", "
				+ selectedItems[7] + ", " + selectedItems[8] + ", " + selectedItems[9] + "," + selectedItems[10] + ", '"
				+ new Timestamp(Calendar.getInstance().getTime().getTime()) + "')";

		DatabaseHelper.executeUpdate(query);
	}

	public Object[] getVoteResults(int rqCardID) {

		ArrayList<SubmittedVoteSimple> voteResults = DatabaseHelper.getVoteResultsFrom(rqCardID);

		Object[] result = { StatisticsHelper.generateAverageVoteResult(voteResults), voteResults.size() };
		return result;
	}

	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}

	public ObservableList<RequirementCardSimple> getObservableArray() {
		return observableArray;
	}

	public void setObservableArray(ObservableList<RequirementCardSimple> observableArray) {
		this.observableArray = observableArray;
	}

	public String getLoginName() {
		return loginName;
	}

	public ObservableList<String> getModules() {

		return DatabaseHelper.getNamesFromSource("Module");
	}

	public ObservableList<String> getTeams() {
		return DatabaseHelper.getNamesFromSource("Team");
	}

	public ObservableList<String> getUserStories() {
		return DatabaseHelper.getNamesFromSource("UserStory");
	}

	public boolean userAlreadySubscribed() {
		return DatabaseHelper.checkUserAlreadySubscribed(loginName);
	}

	public void letUserBeMemberOf(String team) {

		DatabaseHelper.subscribe(loginName, team);

	}

	public void letUserExitTeam(String team) {
		DatabaseHelper.exitUserFromTeam(loginName, team);
	}

	public ArrayList<String> getTeamsUserIsSubscribed() {
		return DatabaseHelper.getTeamsUserIsSubscribed(loginName);
	}

	public void letUserExitAllTeams() {
		DatabaseHelper.exitUserFromAllTeams(loginName);
	}

	public ArrayList<Integer> getXFromRequirement(String x, int id) {
		return DatabaseHelper.getXIDsByRequirementID(x, id);
	}

	public ArrayList<Integer> getIDsFromModules(ObservableList<String> observableList) {
		return DatabaseHelper.XNamesToIDs("Module", observableList);
	}

	public Integer getIDFromModule(String module) {
		return DatabaseHelper.XNameToID("Module", module);
	}

	// for modules or userStories:
	public Object getXNamesAsStringByRqID(String x, int rQID, boolean asList) {

		if (asList) {
			return DatabaseHelper.getXNameAsListByRequirementID(x, rQID);
		} else {
			return DatabaseHelper.getXNameAsStringByRequirementID(x, rQID);
		}

	}

	public void deleteModulesFromRequirement(int rqID) {

		DatabaseHelper.executeUpdate("DELETE * FROM RequirementModule WHERE RequirementID=" + rqID);
	}

	public void addModuleToRequirement(String module, int rqID) {
		DatabaseHelper.executeUpdate("INSERT INTO RequirementModule(RequirementID,ModuleID) VALUES(" + rqID + ","
				+ getIDFromModule(module) + ")");
	}

	public void removeModuleFromRequirement(String module, int rqID) {
		DatabaseHelper.executeUpdate("DELETE * FROM RequirementModule WHERE ModuleID=" + getIDFromModule(module)
				+ " AND RequirementID=" + rqID);
	}

	public void deleteRequirement(int rqID, int majorVersion, int minorVersion) {

		DatabaseHelper.deleteRqFromDatabase(rqID, majorVersion, minorVersion);

		// let the controller know that sth. has changed
		triggerNotification(false);
	}

	public boolean userAlreadyVoted(int ID) {
		return DatabaseHelper.userAlreadyVoted(loginName, ID);
	}

	public boolean noVotesSubmitted(int ID) {
		return DatabaseHelper.noVotesSubmitted(ID);
	}

	public boolean isFrozen(String item) {
		return DatabaseHelper.isFrozen(item);
	}

	@Override
	public void addListener(InvalidationListener listener) {

	}

	@Override
	public void removeListener(InvalidationListener listener) {

	}

}
