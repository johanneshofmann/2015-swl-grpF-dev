package swt.swl.topcard.model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Observable;
import javafx.collections.ObservableList;

public class RequirementCardModel extends Observable {

	// loginName is set when the registrationView is created.
	private String loginName;

	private ObservableList<RequirementCardSimple> observableArray;

	public RequirementCardModel() {
		try {
			Class.forName("com.mysql.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

	public boolean checkUserName(String requirement) {
		try (Connection conn = DriverManager.getConnection("jdbc:mysql://db.swt.wiai.uni-bamberg.de/GroupF", "GroupF",
				"gruppe_f")) {

			Statement stmt = conn.createStatement();
			Statement stmt$1 = conn.createStatement();

			ResultSet equals = stmt.executeQuery("Select OwnerID from Requirement where Title = '" + requirement + "'");
			String result = null;
			if (equals.next()) {
				ResultSet IDToLoginName = stmt$1
						.executeQuery("Select LoginName from User where ID = " + equals.getInt(1));
				if (IDToLoginName.next()) {
					result = IDToLoginName.getString(1);
				}
			}
			if (result != null) {
				return (loginName.equals(result) ? true : false);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

	public void insertRQIntoDatabase(String title, String description, String rationale, String source,
			String userStories, String fitCriterion, String supportingMaterials, boolean isFrozen) {
		try (Connection conn = DriverManager.getConnection("jdbc:mysql://db.swt.wiai.uni-bamberg.de/GroupF", "GroupF",
				"gruppe_f")) {

			Statement stmt$0 = conn.createStatement();
			Statement stmt$1 = conn.createStatement();

			ResultSet userID = stmt$0.executeQuery("select ID from User where LoginName='" + loginName + "'");
			ResultSet rQCardID = stmt$1.executeQuery("select max(Requirement) from Requirement");
			int ownerID = 0;
			int rqCardIDInt = 0;
			if (userID.next() && rQCardID.next()) {
				ownerID = userID.getInt(1);
				rqCardIDInt = rQCardID.getInt(1);
			}

			// convert ifFrozen boolean to int:
			int isFrozenInt = 0;
			if (isFrozen) {
				isFrozenInt = 1;
			}

			String sqlInsert = "insert into Requirement(Title, MajorVersion, MinorVersion, OwnerID, Requirement, Description, Rationale, Source, SupportingMaterials, FitCriterion, IsFrozen) values ('"
					+ title + "', " + 1 + ", " + 1 + ", " + ownerID + ", " + rqCardIDInt + ", '" + description + "', '"
					+ rationale + "', '" + source + "', '" + supportingMaterials + "', '" + fitCriterion + "', "
					+ isFrozenInt + ")";

			stmt$0.executeUpdate(sqlInsert);

			triggerNotification(loginName);

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Stores all Data for showing an overview from a selected Requirement Card
	 * in a String[] and returns it.<br>
	 *
	 * @param selected,
	 *            the selected Item from the RqCardTableView
	 * @returns String[] containing all data of a selected requirement,
	 *          including <br>
	 *          <ul>
	 *          <li>[0]OwnerName,</li>
	 *          <li>[1]ModuleName</li>
	 *          <li>[2]Requirement(number)</li>
	 *          <li>[3]Description,</li>
	 *          <li>[4]Rationale,</li>
	 *          <li>[5]Source,</li>
	 *          <li>[6]UserStories</li>
	 *          <li>[7]SupportingMaterials,</li>
	 *          <li>[8]FitCriterion,</li>
	 *          <li>[9]IsFrozen,</li>
	 *          <li>[10]CreatedAt</li>
	 *          <li>[11]LastUpdatedAt</li>
	 *          </ul>
	 */
	public String[] getOverviewDataFromSelectedRq(String selected) {

		String[] selectedItemValues = new String[12];

		try (Connection conn = DriverManager.getConnection("jdbc:mysql://db.swt.wiai.uni-bamberg.de/GroupF", "GroupF",
				"gruppe_f")) {

			Statement getRqCardData = conn.createStatement();
			Statement getOwnerName = conn.createStatement();

			ResultSet rQCardData = getRqCardData.executeQuery(
					"Select OwnerID,ModulName,Requirement,Description,Rationale,Source,UserStories,SupportingMaterials,FitCriterion,IsFrozen,CreatedAt, LastUpdatedAt from Requirement where Title = '"
							+ selected + "'");

			if (rQCardData.next()) {
				selectedItemValues[0] = "" + rQCardData.getInt(1); // ownerID

				int ownerID = Integer.parseInt(selectedItemValues[0]);
				ResultSet ownerName = getOwnerName.executeQuery("Select LoginName from User where ID = " + ownerID);
				if (ownerName.next()) {
					selectedItemValues[0] = ownerName.getString(1); // ownerName
				}
				selectedItemValues[1] = rQCardData.getString(2); // ModulName
				selectedItemValues[2] = "" + rQCardData.getInt(3); // Requirement[Number]
				selectedItemValues[3] = rQCardData.getString(4); // Description
				selectedItemValues[4] = rQCardData.getString(5); // Rationale
				selectedItemValues[5] = rQCardData.getString(6); // Source
				selectedItemValues[6] = rQCardData.getString(7); // UserStories
				selectedItemValues[7] = rQCardData.getString(8); // SupportingMaterials
				selectedItemValues[8] = rQCardData.getString(9); // FitCriterion
				selectedItemValues[9] = "" + rQCardData.getInt(10);// isFrozen
				selectedItemValues[10] = rQCardData.getString(11); // CreatedAt
				selectedItemValues[11] = rQCardData.getString(12); // LastModifiedAt
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return selectedItemValues;
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

			ResultSet rqID = stmt$0.executeQuery("select Requirement from Requirement where Title='" + title + "'");
			int rqCardID = 0;
			while (rqID.next()) {
				rqCardID = rqID.getInt(1);
				stmt$1.executeUpdate("delete from Requirement where Requirement= " + rqCardID);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void getRequirements() {
		try (Connection conn = DriverManager.getConnection("jdbc:mysql://db.swt.wiai.uni-bamberg.de/GroupF", "GroupF",
				"gruppe_f")) {
			Statement stmt = conn.createStatement();

			String allRequirements = "select Title from Requirement";
			ResultSet resultset = stmt.executeQuery(allRequirements);
			observableArray.clear();
			while (resultset.next()) {
				observableArray.add(new RequirementCardSimple(resultset.getString(1)));
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

			ResultSet ownerID = stmt.executeQuery("select ID from User where LoginName= '" + loginName + "'");
			int ownerIDInt = 0;
			while (ownerID.next()) {
				ownerIDInt = ownerID.getInt(1);
			}
			Statement stmt$2 = conn.createStatement();
			String sql = null;
			if (myRq) {
				sql = "Select Title from Requirement where OwnerID =" + ownerIDInt;
			} else {
				sql = "Select Title from Requirement where OwnerID !=" + ownerIDInt;
			}
			ResultSet requirements = stmt$2.executeQuery(sql);
			observableArray.clear();
			while (requirements.next()) {
				observableArray.add(new RequirementCardSimple(requirements.getString(1)));

			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void search(String title, String owner, String fitCriterion, String source, String supportingMaterials) {
		// TODO:

		filterTitle(title);
		filterOwner(owner, title);
		filterFitCriterion(fitCriterion, title, owner);
		filterSource(source);
		filterSupportingMaterials(supportingMaterials);

	}

	private void filterSupportingMaterials(String supportingMaterials) {
		// TODO Auto-generated method stub
		if (supportingMaterials == null) {
			return;
		}

	}

	private void filterSource(String source) {
		// TODO Auto-generated method stub
		if (source == null) {
			return;
		}
	}

	private void filterFitCriterion(String fitCriterion, String title, String owner) {
		if (fitCriterion == null) {
			return;
		}
		try (Connection conn = DriverManager.getConnection("jdbc:mysql://db.swt.wiai.uni-bamberg.de/GroupF", "GroupF",
				"gruppe_f")) {
			Statement stmt = conn.createStatement();

			int ownerID = 1111111;
			if (owner != null) {

				Statement stmt$ = conn.createStatement();
				ResultSet r = stmt$.executeQuery("Select ID from User where LoginName = '" + owner + "'");
				while (r.next()) {
					ownerID = r.getInt(1);
				}
			}
			String sql = null;
			if (owner == null && title != null) {
				sql = "select Title from Requirement where Title = '" + title + "'and FitCriterion= '" + fitCriterion
						+ "'";
			} else if (owner == null && title == null) {
				sql = "select Title from Requirement where FitCriterion = '" + fitCriterion + "'";
			} else if (owner != null && title == null) {
				sql = "select Title from Requirement where FitCriterion='" + fitCriterion + "' and OwnerID=" + ownerID;
			} else if (owner != null && title != null) {
				sql = "select Title from Requirement where FitCriterion='" + fitCriterion + "' and OwnerID=" + ownerID
						+ "and Title='" + title + "'";
			}
			ResultSet res = stmt.executeQuery(sql);
			observableArray.clear();
			while (res.next()) {
				observableArray.add(new RequirementCardSimple(res.getString(1)));
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	private void filterOwner(String owner, String title) {
		if (owner == null) {
			return;
		}
		try (Connection conn = DriverManager.getConnection("jdbc:mysql://db.swt.wiai.uni-bamberg.de/GroupF", "GroupF",
				"gruppe_f")) {
			Statement stmt = conn.createStatement();

			ResultSet r = stmt.executeQuery("Select ID from User where LoginName = '" + owner + "'");
			int ownerID = 1111111;
			while (r.next()) {
				ownerID = r.getInt(1);

			}
			String sql = null;
			if (title == null) {
				sql = "select Title from Requirement where OwnerID = " + ownerID;
			} else {
				sql = "select Title from Requirement where OwnerID = " + ownerID + "and Title = '" + title + "'";
			}
			ResultSet res = stmt.executeQuery(sql);
			while (res.next()) {
				observableArray.add(new RequirementCardSimple(res.getString(1)));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	private void filterTitle(String title) {

		if (title == null) {
			return;
		}
		try (Connection conn = DriverManager.getConnection("jdbc:mysql://db.swt.wiai.uni-bamberg.de/GroupF", "GroupF",
				"gruppe_f")) {
			Statement stmt = conn.createStatement();

			ResultSet res = stmt.executeQuery("select Title from Requirement where Title='" + title + "'");

			observableArray.clear();

			while (res.next()) {
				observableArray.add(new RequirementCardSimple(res.getString(1)));
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
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
}
