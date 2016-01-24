package swt.swl.topcard.model;

import java.sql.Date;
import java.sql.Timestamp;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Observable;

import javafx.collections.ObservableList;
import swt.swl.topcard.logic.DatabaseHelper;
import swt.swl.topcard.logic.RequirementCardSimple;
import swt.swl.topcard.logic.SubmittedVoteSimple;

public class RequirementCardModel extends Observable {

	// loginName is set when the registrationView is created.
	private String loginName;

	private ObservableList<RequirementCardSimple> observableArray;

	public RequirementCardModel() {
		createLastModifiedAtString();

	}

	public boolean loginNameEqualsOwnerName(String ownerName) {

		return (loginName.equals(ownerName) ? true : false);
	}

	public synchronized void insertEditedRqIntoDatabase(ObservableList<String> modules, String title, int rqID,
			int majorVersion, int minorVersion, String description, String rationale, ObservableList<String> source,
			ObservableList<String> userStories, String fitCriterion, String supportingMaterials, boolean isFrozen,
			Timestamp createdAt) {

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
		triggerNotification(loginName);
	}

	public synchronized void insertRqIntoDatabase(ObservableList<String> modules, String title, String description,
			String rationale, ObservableList<String> source, ObservableList<String> userStories, String fitCriterion,
			String supportingMaterials) {

		int minorVersion = 0;
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
		triggerNotification(loginName);
	}

	private void insertAllPairsIntoEachTable(int rqID, ObservableList<String> modules,
			ObservableList<String> userStories, ObservableList<String> source) {

		// insert each (RqID,ModuleID)-Pair,
		insertEachRqIDXIDPairIntoDatabase("Module", modules, rqID);

		// each (RqID,UserStoryID)-Pair ..
		insertEachRqIDXIDPairIntoDatabase("UserStory", userStories, rqID);

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

		return DatabaseHelper.IDToRequirementCardSimple(selected.getID());

	}

	public void updateRequirementsList() {

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

		return generateEverageVoteResult(DatabaseHelper.getVoteResultsFrom(rqCardID));
	}

	private Object[] generateEverageVoteResult(ArrayList<SubmittedVoteSimple> allVoteResults) {

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

		int counter = 0;

		for (SubmittedVoteSimple vote : allVoteResults) {

			counter++;

			// calculate everage of all votes:

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

		DecimalFormat getDecimal = new DecimalFormat("#0.00");

		Object[] objArr = {
				new SubmittedVoteSimple(
						Double.parseDouble(getDecimal.format(descPrecise / preciseAndUnderstandableCounter)),
						Double.parseDouble(getDecimal.format(descUnderstandable / preciseAndUnderstandableCounter)),
						Double.parseDouble(getDecimal.format(descCorrect / descCorrectCounter)),
						Double.parseDouble(getDecimal.format(descComplete / descCompleteCounter)),
						Double.parseDouble(getDecimal.format(descAtomic / descAtomicCounter)),
						Double.parseDouble(getDecimal.format(ratPrecise / preciseAndUnderstandableCounter)),
						Double.parseDouble(getDecimal.format(ratUnderstandable / preciseAndUnderstandableCounter)),
						Double.parseDouble(getDecimal.format(ratTraceable / ratTraceableCounter)),
						Double.parseDouble(getDecimal.format(ratComplete / ratCompleteCounter)),
						Double.parseDouble(getDecimal.format(ratConsistent / ratConsistentCounter)),
						Double.parseDouble(getDecimal.format(fitCriterionComplete / fitCriterionCompleteCounter))),
				counter };

		return objArr;
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

	public void deleteRequirement(String rqID, String majorVersion, String minorVersion) {
		DatabaseHelper.deleteRqFromDatabase(Integer.parseInt(rqID), Integer.parseInt(majorVersion),
				Integer.parseInt(minorVersion));

		// let the controller know that sth. has changed
		triggerNotification(loginName);
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

}
