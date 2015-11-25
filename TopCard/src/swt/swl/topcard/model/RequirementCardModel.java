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
			Statement stmt = conn.createStatement();
			Statement stmt1 = conn.createStatement();
			ResultSet result = stmt.executeQuery("select ID from User where LoginName='" + loginName + "'");
			ResultSet rQCardID = stmt1.executeQuery("select max(Requirement) from Requirement");
			int ownerID = 0;
			int rQCardIDInt = 0;
			if (result.next() && rQCardID.next()) {
				ownerID = result.getInt(1);
				rQCardIDInt = rQCardID.getInt(1);
			}

			// convert ifFrozen boolean to int:
			int isFrozenInt = 0;
			if (isFrozen) {
				isFrozenInt = 1;
			}

			String sqlInsert = "insert into Requirement(Title, MajorVersion, MinorVersion, Description, Rationale, Source, FitCriterion) values ('"
					+ title + "', " + 1 + ", " + 1 + ", " + description + "', '" + rationale + "', '" + source + "', '"
					+ fitCriterion + "', '" + supportingMaterials + "', " + isFrozenInt + ")";

			stmt.executeUpdate(sqlInsert);

			triggerNotification(loginName);

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
			while (resultset.next()) {
				observableArray.add(new RequirementCardSimple(resultset.getString(1)));

			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		triggerNotification(observableArray);
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
