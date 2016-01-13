package swt.swl.topcard.model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javafx.collections.ObservableList;

public class SearchHelper {

	private static ObservableList<RequirementCardSimple> observableArray;

	public static void search(ObservableList<RequirementCardSimple> observableList, String title, String owner,
			String fitCriterion, String source, String supportingMaterials) {
		// TODO: SearchModel: search() -> add unimplemented parts and test it

		observableArray = observableList;
		filterTitle(title);
		filterOwner(owner, title);
		filterFitCriterion(fitCriterion, title, owner);
		filterSource(source);
		filterSupportingMaterials(supportingMaterials);
	}

	private static void filterTitle(String title) {

		if (title == null) {
			return;
		}
		try (Connection conn = DriverManager.getConnection("jdbc:mysql://db.swt.wiai.uni-bamberg.de/GroupF", "GroupF",
				"gruppe_f")) {
			Statement stmt = conn.createStatement();

			ResultSet requirements = stmt.executeQuery("SELECT * FROM Requirement WHERE Title='" + title + "'");

			observableArray.clear();

			while (requirements.next()) {
				String ownerName = DatabaseHelper.requestOwnerName(requirements.getInt(5));
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

	private static void filterOwner(String owner, String title) {
		if (owner == null) {
			return;
		}
		try (Connection conn = DriverManager.getConnection("jdbc:mysql://db.swt.wiai.uni-bamberg.de/GroupF", "GroupF",
				"gruppe_f")) {
			Statement stmt = conn.createStatement();

			ResultSet r = stmt.executeQuery("SELECT ID FROM User WHERE LoginName = '" + owner + "'");
			int ownerID = 1111111;
			while (r.next()) {
				ownerID = r.getInt(1);

			}
			String sql = null;
			if (title == null) {
				sql = "SELECT * FROM Requirement WHERE OwnerID = " + ownerID;
			} else {
				sql = "SELECT * FROM Requirement WHERE OwnerID = " + ownerID + "AND Title = '" + title + "'";
			}
			ResultSet requirements = stmt.executeQuery(sql);
			while (requirements.next()) {
				String ownerName = DatabaseHelper.requestOwnerName(requirements.getInt(5));
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

	private static void filterFitCriterion(String fitCriterion, String title, String owner) {
		if (fitCriterion == null) {
			return;
		}
		try (Connection conn = DriverManager.getConnection("jdbc:mysql://db.swt.wiai.uni-bamberg.de/GroupF", "GroupF",
				"gruppe_f")) {
			Statement stmt = conn.createStatement();

			int ownerID = 1111111;
			if (owner != null) {

				Statement stmt$ = conn.createStatement();
				ResultSet r = stmt$.executeQuery("SELECT ID FROM User WHERE LoginName = '" + owner + "'");
				while (r.next()) {
					ownerID = r.getInt(1);
				}
			}
			String sql = null;
			if (owner == null && title != null) {
				sql = "SELECT * FROM Requirement WHERE Title = '" + title + "'AND FitCriterion= '" + fitCriterion + "'";
			} else if (owner == null && title == null) {
				sql = "SELECT * FROM Requirement WHERE FitCriterion = '" + fitCriterion + "'";
			} else if (owner != null && title == null) {
				sql = "SELECT * FROM Requirement WHERE FitCriterion='" + fitCriterion + "' AND OwnerID=" + ownerID;
			} else if (owner != null && title != null) {
				sql = "SELECT * FROM Requirement WHERE FitCriterion='" + fitCriterion + "' AND OwnerID=" + ownerID
						+ "and Title='" + title + "'";
			}
			ResultSet requirements = stmt.executeQuery(sql);
			observableArray.clear();
			while (requirements.next()) {
				String ownerName = DatabaseHelper.requestOwnerName(requirements.getInt(5));
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

	private static void filterSource(String source) {
		// TODO SearchModel: implement filterSource()
		if (source == null) {
			return;
		}
	}

	private static void filterSupportingMaterials(String supportingMaterials) {
		// TODO SearchModel: implement filterSupportingMaterials()
		if (supportingMaterials == null) {
			return;
		}

	}

	public ObservableList<RequirementCardSimple> getObservableArray() {
		return observableArray;
	}

	public void setObservableArray(ObservableList<RequirementCardSimple> observableArray) {
		SearchHelper.observableArray = observableArray;
	}
}
