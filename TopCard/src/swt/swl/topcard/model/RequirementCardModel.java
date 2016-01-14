package swt.swl.topcard.model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Observable;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class RequirementCardModel extends Observable {

	// loginName is set when the registrationView is created.
	private String loginName;

	private int module0ID, module1ID, module2ID;

	private ObservableList<RequirementCardSimple> observableArray;

	public RequirementCardModel() {

		try {
			Class.forName("com.mysql.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

	public boolean checkUserName(String ownerName) {

		return (loginName.equals(ownerName) ? true : false);
	}

	public void insertEditedRqIntoDatabase() {

	}

	public void insertRqIntoDatabase(ObservableList<String> modules, String title, String description, String rationale,
			String source, String userStories, String fitCriterion, String supportingMaterials, boolean isFrozen) {

		int ownerID = 0, rqCardIDInt = 1, isFrozenInt = 0;

		// convert ifFrozen boolean to int:
		if (isFrozen) {
			isFrozenInt = 1;
		}

		String sqlSelectUserIDQuery = "SELECT ID FROM User WHERE LoginName='" + loginName + "'";
		String sqlSelectMaxRequirementQuery = "SELECT MAX(Requirement) FROM Requirement";
		String sqlInsertIntoRequirementUpdate = "INSERT INTO Requirement(Title, MajorVersion, MinorVersion, OwnerID, Requirement, Description, Rationale, Source, SupportingMaterials, FitCriterion, IsFrozen, LastModifiedAt) VALUES ('"
				+ title + "', " + 1 + ", " + 1 + ", " + ownerID + ", " + rqCardIDInt + ", '" + description + "', '"
				+ rationale + "', '" + source + "', '" + supportingMaterials + "', '" + fitCriterion + "', "
				+ isFrozenInt + ", '" + new java.util.Date() + "')";
		String sqlInsertIntoRequirementModuleUpdate = "INSERT INTO RequirementModule(RequirementID) SELECT ID FROM Requirement WHERE";

		// ,ModuleID,Module2ID,Module3ID
		// + "("+rqCardIDInt + "," + module0ID + "," + module1ID + "," +
		// module2ID + ")";
		// String sqlSelectModuleIDQuery = generateSelectModuleQuery(modules);

		try (Connection conn = DriverManager.getConnection("jdbc:mysql://db.swt.wiai.uni-bamberg.de/GroupF", "GroupF",
				"gruppe_f")) {

			// Statement which is used for executing all necesary queries
			Statement stmt = conn.createStatement();

			// first fetch ownerID

			ResultSet userID = stmt.executeQuery(sqlSelectUserIDQuery);

			if (userID.next()) {
				ownerID = userID.getInt(1);

				// to use Statement again need to close old ResultSet
				userID.close();
			}

			// fetch biggest RqID
			ResultSet rQCardID = stmt.executeQuery(sqlSelectMaxRequirementQuery);

			if (rQCardID.next()) {
				rqCardIDInt += rQCardID.getInt(1);
				rQCardID.close();
			}

			// need 2 Updates for the 2 Tables (Requirement &
			// Requirement_Module)

			// first insert into Requirement table
			stmt.executeUpdate(sqlInsertIntoRequirementUpdate);

			// then check module amount, allocate IDs and insert em into
			// Requirement_Module table
			initModuleIDs(stmt.executeQuery(sqlSelectModuleIDQuery));
			stmt.executeUpdate(sqlInsertIntoRequirementModuleUpdate);

			// let the controller know that sth. has changed
			triggerNotification(loginName);

		} catch (

		SQLException e) {
			e.printStackTrace();
		}
	}

	private void initModuleIDs(ResultSet moduleIDs) {

		try {
			if (moduleIDs.next()) {
				module0ID = moduleIDs.getInt(1);
			}
			if (moduleIDs.next()) {
				module1ID = moduleIDs.getInt(1);
			}
			if (moduleIDs.next()) {
				module2ID = moduleIDs.getInt(1);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	private String generateSelectModuleQuery(ObservableList<String> modules) {

		String sqlSelectModuleID = null;
		switch (modules.size()) {
		case 1:
			sqlSelectModuleID = "SELECT ID FROM Module WHERE Name='" + modules.get(0) + "'";
			break;
		case 2:
			sqlSelectModuleID = "SELECT ID FROM Module WHERE Name='" + modules.get(0) + "' OR Name='" + modules.get(1)
					+ "'";
			break;
		case 3:
			sqlSelectModuleID = "SELECT ID FROM Module WHERE Name='" + modules.get(0) + "' OR Name='" + modules.get(1)
					+ "' OR Name='" + modules.get(2) + "'";
			break;
		default:
			throw new IllegalArgumentException("Invalid amount of modules given. Amount was: " + modules.size());
		}
		return sqlSelectModuleID;
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

		try (Connection conn = DriverManager.getConnection("jdbc:mysql://db.swt.wiai.uni-bamberg.de/GroupF", "GroupF",
				"gruppe_f")) {

			Statement getRqCardData = conn.createStatement();
			Statement getOwnerName = conn.createStatement();

			ResultSet rQCardData = getRqCardData
					.executeQuery("SELECT * FROM Requirement WHERE Requirement =" + selected.getRqID());

			if (rQCardData.next()) {
				selected.setTitle((rQCardData.getString(2))); // Title

				// TODO: RQCardCModel: insert/ get ModulName:
				// selected.setmo

				selected.setMajorVersion(rQCardData.getInt(3)); // MajorVersion
				selected.setMinorVersion(rQCardData.getInt(4)); // MinorVersion
				ResultSet ownerName = getOwnerName
						.executeQuery("SELECT LoginName FROM User WHERE ID = " + rQCardData.getInt(5));
				if (ownerName.next()) {

					selected.setOwnerName(ownerName.getString(1)); // ownerName
				}
				selected.setRqID(rQCardData.getInt(6)); // rqID
				selected.setDescription(rQCardData.getString(7)); // Description
				selected.setRationale(rQCardData.getString(8)); // Rationale
				selected.setSource(rQCardData.getString(9)); // Source

				// TODO: RQCardCModel: insert / get UserStories :

				selected.setSupportingMaterials(rQCardData.getString(10)); // SupportingMaterials
				selected.setFitCriterion(rQCardData.getString(11)); // FitCriterion
				selected.setIsFrozen(rQCardData.getInt(12));// isFrozen
				selected.setCreatedAt(rQCardData.getTimestamp(13)); // CreatedAt
				selected.setLastModifiedAt(rQCardData.getString(14)); // LastModifiedAt

			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		if (!(selected.getMajorVersion() == 0)) {
			return selected;
		} else {
			throw new ClassFormatError("selected RqCard hasn't changed correctly");
		}
	}

	/**
	 *
	 *
	 * @param title
	 */
	public void deleteRqFromDatabase(String title) {
		try (Connection conn = DriverManager.getConnection("jdbc:mysql://db.swt.wiai.uni-bamberg.de/GroupF", "GroupF",
				"gruppe_f")) {

			Statement stmt$0 = conn.createStatement();
			Statement stmt$1 = conn.createStatement();

			ResultSet rqID = stmt$0.executeQuery("SELECT Requirement FROM Requirement WHERE Title='" + title + "'");
			int rqCardID = 0;
			while (rqID.next()) {
				rqCardID = rqID.getInt(1);
				stmt$1.executeUpdate("DELETE FROM Requirement WHERE Requirement= " + rqCardID);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void getRequirements() {
		try (Connection conn = DriverManager.getConnection("jdbc:mysql://db.swt.wiai.uni-bamberg.de/GroupF", "GroupF",
				"gruppe_f")) {
			Statement stmt = conn.createStatement();

			String allRequirements = "SELECT * FROM Requirement";
			ResultSet resultset = stmt.executeQuery(allRequirements);
			observableArray.clear();
			while (resultset.next()) {

				String ownerName = requestOwnerName(resultset.getInt(5));

				observableArray.add(new RequirementCardSimple(resultset.getInt(1), resultset.getString(2),
						resultset.getInt(3), resultset.getInt(4), resultset.getInt(5), ownerName, resultset.getInt(6),
						resultset.getString(7), resultset.getString(8), resultset.getString(9), resultset.getString(10),
						resultset.getString(11), resultset.getInt(12), resultset.getTimestamp(13),
						resultset.getString(14)));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	private void triggerNotification(Object message) {
		setChanged();
		notifyObservers(message);
	}

	public void getMyOrToVoteRequirements(boolean myRq) {
		try (Connection conn = DriverManager.getConnection("jdbc:mysql://db.swt.wiai.uni-bamberg.de/GroupF", "GroupF",
				"gruppe_f")) {
			Statement stmt = conn.createStatement();

			ResultSet ownerID = stmt.executeQuery("SELECT ID FROM User WHERE LoginName= '" + loginName + "'");
			int ownerIDInt = 0;
			while (ownerID.next()) {
				ownerIDInt = ownerID.getInt(1);
			}
			Statement stmt$2 = conn.createStatement();
			String sql = null;
			if (myRq) {
				sql = "SELECT * FROM Requirement WHERE OwnerID =" + ownerIDInt;
			} else {
				sql = "SELECT * FROM Requirement WHERE OwnerID !=" + ownerIDInt;
			}
			ResultSet requirements = stmt$2.executeQuery(sql);
			observableArray.clear();
			while (requirements.next()) {
				String ownerName = requestOwnerName(requirements.getInt(5));
				observableArray.add(new RequirementCardSimple(requirements.getInt(1), requirements.getString(2),
						requirements.getInt(3), requirements.getInt(4), requirements.getInt(5), ownerName,
						requirements.getInt(6), requirements.getString(7), requirements.getString(8),
						requirements.getString(9), requirements.getString(10), requirements.getString(11),
						requirements.getInt(12), requirements.getTimestamp(13), requirements.getString(14)));

			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void newVoteSubmitted(String requirement, String[] selectedItems) {

		try (Connection conn = DriverManager.getConnection("jdbc:mysql://db.swt.wiai.uni-bamberg.de/GroupF", "GroupF",
				"gruppe_f")) {
			Statement getReqID = conn.createStatement();

			ResultSet reqID = getReqID.executeQuery("SELECT ID FROM Requirement WHERE Title ='" + requirement + "'");
			int reqIDInt = 0;
			if (reqID.next()) {
				reqIDInt = reqID.getInt(1);
			}
			Statement getUserID = conn.createStatement();
			ResultSet ownerID = getUserID.executeQuery("SELECT ID FROM User WHERE LoginName= '" + loginName + "'");
			int userIDInt = 0;
			if (ownerID.next()) {
				userIDInt = ownerID.getInt(1);
			}
			//
			Statement insert = conn.createStatement();
			insert.executeUpdate(

					// TODO: RQCardModel: !!! change to int values: !!!

					"INSERT INTO Vote(RequirementID,UserID,DescriptionPrecise, DescriptionUnderstandable,DescriptionCorrect,DescriptionComplete,DescriptionAtomic, RationalePrecise, RationaleUnderstandable, RationaleTraceable, RationaleComplete,RationaleConsistent,CreatedAt)    VALUES ("
							+ reqIDInt + "," + userIDInt + "," + selectedItems[0] + "," + selectedItems[1] + ",'"
							+ selectedItems[2] + "', '" + selectedItems[3] + "', '" + selectedItems[4] + "', "
							+ selectedItems[5] + "," + selectedItems[6] + ", '" + selectedItems[7] + "', '"
							+ selectedItems[8] + "', '" + selectedItems[9] + "', '"
							+ new Timestamp(Calendar.getInstance().getTime().getTime()) + "')");

			// TODO: RQCardModel : !!! change to int values: !!!

		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

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
	public SubmittedVoteSimple getVoteResults(int rqCardID) {

		ArrayList<SubmittedVoteSimple> allVoteResults = new ArrayList<SubmittedVoteSimple>();

		try (Connection conn = DriverManager.getConnection("jdbc:mysql://db.swt.wiai.uni-bamberg.de/GroupF", "GroupF",
				"gruppe_f")) {
			Statement getVoteResults = conn.createStatement();
			String sql = "SELECT * FROM Vote WHERE RequirementID =" + rqCardID;
			ResultSet rqVote = getVoteResults.executeQuery(sql);

			while (rqVote.next()) {
				allVoteResults.add(new SubmittedVoteSimple(rqVote.getInt(4), rqVote.getInt(5), rqVote.getInt(6),
						rqVote.getInt(7), rqVote.getInt(8), rqVote.getInt(9), rqVote.getInt(10), rqVote.getInt(11),
						rqVote.getInt(12), rqVote.getInt(13), rqVote.getInt(14)));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return generateEverageVoteResult(allVoteResults);
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

		try (Connection conn = DriverManager.getConnection("jdbc:mysql://db.swt.wiai.uni-bamberg.de/GroupF", "GroupF",
				"gruppe_f")) {

			Statement stmt = conn.createStatement();

			String query = "SELECT Name FROM Module";

			ResultSet modulesSet = stmt.executeQuery(query);
			ObservableList<String> modules = FXCollections.observableArrayList();

			while (modulesSet.next()) {
				modules.add(modulesSet.getString(1));
			}
			return modules;

		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}

	}

	private String requestOwnerName(int ownerID) {

		String ownerName = null;

		try (Connection conn = DriverManager.getConnection("jdbc:mysql://db.swt.wiai.uni-bamberg.de/GroupF", "GroupF",
				"gruppe_f")) {

			Statement stmt = conn.createStatement();

			String getOwnerNameQuery = "SELECT LoginName FROM User WHERE ID=" + ownerID;

			ResultSet ownerNameContainer = stmt.executeQuery(getOwnerNameQuery);

			if (ownerNameContainer.next()) {
				ownerName = ownerNameContainer.getString(1);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return ownerName;
	}

}
