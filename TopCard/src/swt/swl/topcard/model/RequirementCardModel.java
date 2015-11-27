package swt.swl.topcard.model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Observable;
import javafx.collections.ObservableList;

public class RequirementCardModel extends Observable {
	private String loginName;
	private ObservableList<RequirementCardSimple> observableArray;
	// loginName is set when the registrationView is created.

	public RequirementCardModel() {
		try {
			Class.forName("com.mysql.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
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

			triggerNotification(this.loginName);

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
