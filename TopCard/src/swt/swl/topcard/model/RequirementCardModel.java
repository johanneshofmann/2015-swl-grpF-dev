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
			String userStories, String fitCriterion) {
		try (Connection conn = DriverManager.getConnection("jdbc:mysql://db.swt.wiai.uni-bamberg.de/GroupF", "GroupF",
				"gruppe_f")) {
			Statement stmt = conn.createStatement();
		//	int ownerID = stmt.executeQuery("calculate owner ID ..?");

			String sqlInsert = "insert into Requirement(ID,Title,Description,Rationale,Source,FitCriterion) values (" + 1 + ", '"
					+ title + "', '" + description + "', '" + rationale + "', '" + source + "');";
			stmt.executeUpdate(sqlInsert);

			triggerNotification(loginName);

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public void getRequirements(){
		try (Connection conn = DriverManager.getConnection("jdbc:mysql://db.swt.wiai.uni-bamberg.de/GroupF", "GroupF",
				"gruppe_f")) {
			Statement stmt = conn.createStatement();

			String allRequirements = "select Title from Requirement";
			ResultSet resultset = stmt.executeQuery(allRequirements);
			while (resultset.next()) {
				observableArray.add(new RequirementCardSimple(resultset.getString(0)));
				
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
