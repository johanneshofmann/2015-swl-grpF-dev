package swt.swl.topcard.model;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Observable;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import swt.swl.topcard.logic.DatabaseHelper;
import swt.swl.topcard.logic.RequirementCardSimple;
import swt.swl.topcard.logic.SubmittedVoteSimple;

public class RequirementCardModel extends Observable {

	// loginName is set when the registrationView is created.
	private String loginName;

	private ObservableList<RequirementCardSimple> observableArray;

	public RequirementCardModel() {

	}

	public boolean checkUserName(String ownerName) {

		return (loginName.equals(ownerName) ? true : false);
	}

	public void insertEditedRqIntoDatabase(RequirementCardSimple toInsert, boolean newMajorVersion) {

		int minorVersion = (toInsert.getMinorVersion() + 1);
		int majorVersion = toInsert.getMajorVersion();

		if (newMajorVersion) {
			majorVersion++;
			minorVersion = 1;
		}

		// fetch ownerID
		int ownerID = DatabaseHelper.loginNameToID(loginName);

		// first insert into Requirement table
		String sqlInsertIntoRequirementUpdate = "INSERT INTO Requirement(Title, MajorVersion, MinorVersion, OwnerID, Requirement, Description, Rationale, Source, SupportingMaterials, FitCriterion, IsFrozen, LastModifiedAt) VALUES ('"
				+ toInsert.getTitle() + "', " + majorVersion + ", " + minorVersion + ", " + ownerID + ", "
				+ toInsert.getRqID() + ", '" + toInsert.getDescription() + "', '" + toInsert.getRationale() + "', '"
				+ toInsert.getSource() + "', '" + toInsert.getSupportingMaterials() + "', '"
				+ toInsert.getFitCriterion() + "', " + toInsert.getIsFrozen() + ", '" + new java.util.Date() + "')";

		DatabaseHelper.executeUpdate(sqlInsertIntoRequirementUpdate);

		String[] strArr = toInsert.getModules().split(",");

		ArrayList<Integer> moduleIDs = new ArrayList<>();

		for (String str : strArr) {

			moduleIDs.add(DatabaseHelper.getIDFromModule(str));
		}
		// then insert each (RqID,ModuleID)-Pair into table

		for (Integer moduleID : moduleIDs) {

			String sqlInsertIntoRequirementModuleUpdate = "INSERT INTO RequirementModule(RequirementID,ModuleID) VALUES("
					+ toInsert.getRqID() + "," + moduleID + ")";

			DatabaseHelper.executeUpdate(sqlInsertIntoRequirementModuleUpdate);
		}

		// let the controller know that sth. has changed
		triggerNotification(loginName);
	}

	public void insertRqIntoDatabase(ObservableList<String> modules, String title, String description, String rationale,
			String source, String userStories, String fitCriterion, String supportingMaterials, boolean isFrozen) {

		int minorVersion = 1;
		int majorVersion = 1;

		// fetch ownerID
		int ownerID = DatabaseHelper.loginNameToID(loginName);

		// fetch biggest RqID
		int rqCardIDInt = 1 + DatabaseHelper.getMaxXFromY("RequirementID", "Requirement");

		// convert ifFrozen boolean to int:
		int isFrozenInt = 0;
		if (isFrozen) {
			isFrozenInt = 1;
		}

		// first insert into Requirement table
		String sqlInsertIntoRequirementUpdate = "INSERT INTO Requirement(Title, MajorVersion, MinorVersion, OwnerID, Requirement, Description, Rationale, Source, SupportingMaterials, FitCriterion, IsFrozen, LastModifiedAt) VALUES ('"
				+ title + "', " + majorVersion + ", " + minorVersion + ", " + ownerID + ", " + rqCardIDInt + ", '"
				+ description + "', '" + rationale + "', '" + source + "', '" + supportingMaterials + "', '"
				+ fitCriterion + "', " + isFrozenInt + ", '" + new java.util.Date() + "')";

		DatabaseHelper.executeUpdate(sqlInsertIntoRequirementUpdate);

		ArrayList<Integer> moduleIDs = DatabaseHelper.getIDsFromModules(modules);

		// then insert each (RqID,ModuleID)-Pair into table

		for (Integer i : moduleIDs) {

			String sqlInsertIntoRequirementModuleUpdate = "INSERT INTO RequirementModule(RequirementID,ModuleID) VALUES("
					+ rqCardIDInt + "," + i + ")";

			DatabaseHelper.executeUpdate(sqlInsertIntoRequirementModuleUpdate);
		}

		// let the controller know that sth. has changed
		triggerNotification(loginName);
	}

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
	public RequirementCardSimple getOverviewDataFromSelectedRq(RequirementCardSimple selected) {

		return DatabaseHelper.IDToRequirementCardSimple(selected.getRqID());

	}

	public void getRequirements() {

		ArrayList<RequirementCardSimple> requirements = DatabaseHelper.getRequirements();

		observableArray.clear();

		for (RequirementCardSimple rqCard : requirements) {

			observableArray.add(rqCard);
		}
	}

	private void triggerNotification(Object message) {
		setChanged();
		notifyObservers(message);
	}

	public void newVoteSubmitted(String requirement, int[] selectedItems) {

		int reqIDInt = DatabaseHelper.rQTitleToID(requirement);
		int userIDInt = DatabaseHelper.loginNameToID(loginName);

		String query = "INSERT INTO Vote(RequirementID, UserID ,DescriptionPrecise , DescriptionUnderstandable ,DescriptionCorrect ,"
				+ "DescriptionComplete, DescriptionAtomic, RationalePrecise, RationaleUnderstandable, RationaleTraceable, "
				+ "RationaleComplete, RationaleConsistent, FitCriterionComplete, CreatedAt) VALUES (" + reqIDInt + ","
				+ userIDInt + "," + selectedItems[0] + "," + selectedItems[1] + "," + selectedItems[2] + ", "
				+ selectedItems[3] + ", " + selectedItems[4] + ", " + selectedItems[5] + "," + selectedItems[6] + ", "
				+ selectedItems[7] + ", " + selectedItems[8] + ", " + selectedItems[9] + "," + selectedItems[10] + ", '"
				+ new Timestamp(Calendar.getInstance().getTime().getTime()) + "')";

		DatabaseHelper.executeUpdate(query);
	}

	public SubmittedVoteSimple getVoteResults(int rqCardID) {

		return generateEverageVoteResult(DatabaseHelper.getVoteResultsFrom(rqCardID));
	}

	private SubmittedVoteSimple generateEverageVoteResult(ArrayList<SubmittedVoteSimple> allVoteResults) {

		double descPrecise = 0;
		double descUnderstandable = 0;
		double descCorrect = 0;
		double descComplete = 0;
		double descAtomic = 0;
		double ratPrecise = 0;
		double ratUnderstandable = 0;
		double ratTraceable = 0;
		double ratComplete = 0;
		double ratConsistent = 0;
		double fitCriterionComplete = 0;

		int descCompleteCounter = 0;
		int descCorrectCounter = 0;
		int descAtomicCounter = 0;
		int ratTraceableCounter = 0;
		int ratCompleteCounter = 0;
		int ratConsistentCounter = 0;
		int fitCriterionCompleteCounter = 0;

		int preciseAndUnderstandableCounter = 0;
		for (SubmittedVoteSimple vote : allVoteResults) {
			// calculate everage of all votes::

			descPrecise += vote.getDescriptionPrecise();
			descUnderstandable += vote.getDescriptionUnderstandable();

			ratPrecise += vote.getRationalePrecise();
			ratUnderstandable += vote.getDescriptionUnderstandable();

			if (vote.getDescriptionCorrect() == 1) {
				descCorrect += vote.getDescriptionCorrect();
				descCorrectCounter++;
			} else {
				descCorrectCounter++;
			}
			if (vote.getDescriptionComplete() == 1) {
				descComplete += vote.getDescriptionComplete();
				descCompleteCounter++;
			} else {
				descCompleteCounter++;
			}
			if (vote.getDescriptionAtomic() == 1) {
				descAtomic += vote.getDescriptionAtomic();
				descAtomicCounter++;
			} else {
				descAtomicCounter++;
			}
			if (vote.getRationaleTraceable() == 1) {
				ratTraceable += vote.getRationaleTraceable();
				ratTraceableCounter++;
			} else {
				ratTraceableCounter++;
			}
			if (vote.getRationaleComplete() == 1) {
				ratComplete += vote.getRationaleComplete();
				ratCompleteCounter++;
			} else {
				ratCompleteCounter++;
			}
			if (vote.getRationaleConsistent() == 1) {
				ratConsistent += vote.getRationaleConsistent();
				ratConsistentCounter++;
			} else {
				ratConsistentCounter++;
			}
			if (vote.getFitCriterionCorrect() == 1) {
				fitCriterionComplete += vote.getFitCriterionCorrect();
				fitCriterionCompleteCounter++;
			} else {
				fitCriterionCompleteCounter++;
			}

			preciseAndUnderstandableCounter++;
		}

		return new SubmittedVoteSimple(descPrecise / preciseAndUnderstandableCounter,
				descUnderstandable / preciseAndUnderstandableCounter, descCorrect / descCorrectCounter,
				descComplete / descCompleteCounter, descAtomic / descAtomicCounter,
				ratPrecise / preciseAndUnderstandableCounter, ratUnderstandable / preciseAndUnderstandableCounter,
				ratTraceable / ratTraceableCounter, ratComplete / ratCompleteCounter,
				ratConsistent / ratConsistentCounter, fitCriterionComplete / fitCriterionCompleteCounter);
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

		return DatabaseHelper.getModules();
	}

	public ObservableList<String> getTeams() {
		return DatabaseHelper.getTeams();
	}

	public boolean userAlreadySubscribed() {
		return DatabaseHelper.checkUserAlreadySubscribed(loginName);
	}

	public void letUserBeMemberOf(String team) {

		DatabaseHelper.subscribe(loginName, team);

	}

	public void letUserExitTeam(String team) {
		DatabaseHelper.exitXFromY(loginName, team);
	}

	public ArrayList<String> getTeamsUserIsSubscribed() {
		return DatabaseHelper.getTeamsUserIsSubscribed(loginName);
	}

	public void letUserExitAllTeams() {
		DatabaseHelper.exitUserFromAllTeams(loginName);
	}

	public ArrayList<Integer> getModulesFromRequirement(int id) {
		return DatabaseHelper.getModulesByRequirementID(id);
	}

	public ArrayList<Integer> getIDsFromModules(ObservableList<String> observableList) {
		return DatabaseHelper.getIDsFromModules(observableList);
	}

	public Integer getIDFromModule(String module) {
		return DatabaseHelper.getIDFromModule(module);
	}

	public ObservableList<String> getModulesByRqID(int rQID) {

		ObservableList<String> modules = FXCollections.observableArrayList();

		for (Integer i : DatabaseHelper.getModulesByRequirementID(rQID)) {

			modules.add(DatabaseHelper.moduleIDToName(i));
		}
		return modules;

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

	public ObservableList<String> getUserStories() {
		return DatabaseHelper.getUserStories();
	}

}
