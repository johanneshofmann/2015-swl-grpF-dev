package swt.swl.topcard.model.impl;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import swt.swl.topcard.logic.RequirementCardSimple;
import swt.swl.topcard.model.SearchHelper;
import swt.swl.topcard.model.search.SearchOperator;
import swt.swl.topcard.model.search.VoteValue;

public class SearchHelperImpl implements SearchHelper {

	private static ObservableList<RequirementCardSimple> observableArray;

	public static void search(ObservableList<RequirementCardSimple> observableList, String title, String owner,
			String module, String description, String rationale, String source, String userStories,
			String supportingMaterials, String fitCriterion, Integer isFrozen, SearchOperator descPreciseOp,
			Integer descPrecise, SearchOperator descUnderstandableOp, Integer descUnderstandable, VoteValue descCorrect,
			VoteValue descComplete, VoteValue descAtomic, SearchOperator ratPreciseOp, Integer ratPrecise,
			SearchOperator ratUnderstableOp, Integer ratUnderstandable, VoteValue ratTraceable, VoteValue ratCorrect,
			VoteValue ratConsistent) {
		// TODO: SearchModel: search() -> add unimplemented parts and test it

		// Copy observableList
		ObservableList<RequirementCardSimple> requirements = FXCollections.observableArrayList(observableList);

		// First of all, retrieve all requirements
		// Do client-side filtering
		// 1. Filter
		if (title != null) {
			requirements.stream().filter(r -> (!r.getTitle().contains(title))).forEach(observableList::remove);
			requirements = FXCollections.observableArrayList(observableList);
		}
		if (owner != null) {
			// TODO: observableList.stream().filter( r -> (r.getOwnerID() !=
			// owner)).forEach(observableList::remove);
			// requirements = FXCollections.observableArrayList(observableList);
		}
		if (module != null) {
			observableList.stream().filter(r -> (!r.getModules().contains(module))).forEach(observableList::remove);
			requirements = FXCollections.observableArrayList(observableList);
		}
		if (description != null) {
			observableList.stream().filter(r -> (!r.getDescription().contains(description)))
					.forEach(observableList::remove);
			requirements = FXCollections.observableArrayList(observableList);
		}
		if (rationale != null) {
			observableList.stream().filter(r -> (!r.getRationale().contains(rationale)))
					.forEach(observableList::remove);
			requirements = FXCollections.observableArrayList(observableList);
		}
		if (source != null) {
			observableList.stream().filter(r -> (!r.getSource().contains(source))).forEach(observableList::remove);
			requirements = FXCollections.observableArrayList(observableList);
		}
		if (userStories != null) {
			observableList.stream().filter(r -> (!r.getSource().contains(userStories))).forEach(observableList::remove);
			requirements = FXCollections.observableArrayList(observableList);
		}
		if (supportingMaterials != null) {
			observableList.stream().filter(r -> (!r.getSupportingMaterials().contains(supportingMaterials)))
					.forEach(observableList::remove);
			requirements = FXCollections.observableArrayList(observableList);
		}
		if (fitCriterion != null) {
			observableList.stream().filter(r -> (!r.getFitCriterion().contains(fitCriterion)))
					.forEach(observableList::remove);
			requirements = FXCollections.observableArrayList(observableList);
		}
		/*
		 * TODO if(isFrozen != FrozenValue.ALL) { int intFrozen = (isFrozen ==
		 * FrozenValue.YES) ? 1 : 0; observableList.stream().filter(r ->
		 * (r.getIsFrozen() == intFrozen)).forEach(observableList::remove); }
		 */
		if (descPreciseOp != SearchOperator.ALL) {
			if (descPreciseOp == SearchOperator.EQUAL) {
				observableList.stream().filter(r -> (r.getSubmittedVote().getDescriptionPrecise() == descPrecise)).forEach(observableList::remove);
				requirements = FXCollections.observableArrayList(observableList);
			} else if (descPreciseOp == SearchOperator.LESS) {
				observableList.stream().filter(r -> (r.getSubmittedVote().getDescriptionPrecise() < descPrecise)).forEach(observableList::remove);
				requirements = FXCollections.observableArrayList(observableList);
			} else if (descPreciseOp == SearchOperator.GREATER) {
				observableList.stream().filter(r -> (r.getSubmittedVote().getDescriptionPrecise() > descPrecise)).forEach(observableList::remove);
				requirements = FXCollections.observableArrayList(observableList);
			}
		}
		if (descUnderstandableOp != SearchOperator.ALL) {
			if (descUnderstandableOp == SearchOperator.EQUAL) {
				observableList.stream().filter(r -> (r.getSubmittedVote().getDescriptionUnderstandable() == descUnderstandable)).forEach(observableList::remove);
				requirements = FXCollections.observableArrayList(observableList);
			} else if (descUnderstandableOp == SearchOperator.LESS) {
				observableList.stream().filter(r -> (r.getSubmittedVote().getDescriptionUnderstandable() < descUnderstandable)).forEach(observableList::remove);
				requirements = FXCollections.observableArrayList(observableList);
			} else if (descUnderstandableOp == SearchOperator.GREATER) {
				observableList.stream().filter(r -> (r.getSubmittedVote().getDescriptionUnderstandable() > descUnderstandable)).forEach(observableList::remove);
				requirements = FXCollections.observableArrayList(observableList);
			}
		}
		if (descCorrect != VoteValue.ALL) {
			observableList.stream().filter(r -> (!(r.getSubmittedVote().getDescriptionCorrect() == (double)descCorrect.getValue()))).forEach(observableList::remove);
			requirements = FXCollections.observableArrayList(observableList);
		}
		if (descComplete != VoteValue.ALL) {
			observableList.stream().filter(r -> (!(r.getSubmittedVote().getDescriptionComplete() == (double)descComplete.getValue()))).forEach(observableList::remove);
			requirements = FXCollections.observableArrayList(observableList);
		}
		if (descAtomic != VoteValue.ALL) {
			observableList.stream().filter(r -> (!(r.getSubmittedVote().getDescriptionAtomic() == (double)descAtomic.getValue()))).forEach(observableList::remove);
			requirements = FXCollections.observableArrayList(observableList);
		}
		if (ratPreciseOp != SearchOperator.ALL) {
			if (ratPreciseOp == SearchOperator.EQUAL) {
				observableList.stream().filter(r -> (r.getSubmittedVote().getRationalePrecise() == ratPrecise));
				requirements = FXCollections.observableArrayList(observableList);
			} else if (ratPreciseOp == SearchOperator.LESS) {
				observableList.stream().filter(r -> (r.getSubmittedVote().getRationalePrecise() < ratPrecise));
				requirements = FXCollections.observableArrayList(observableList);
			} else if (ratPreciseOp == SearchOperator.GREATER) {
				observableList.stream().filter(r -> (r.getSubmittedVote().getRationalePrecise() > ratPrecise));
				requirements = FXCollections.observableArrayList(observableList);
			}
		}
		if (ratUnderstableOp != SearchOperator.ALL) {
					if (ratUnderstableOp == SearchOperator.EQUAL) {
				observableList.stream().filter(r -> (r.getSubmittedVote().getRationaleUnderstandable() == ratUnderstandable));
				requirements = FXCollections.observableArrayList(observableList);
			} else if (ratUnderstableOp == SearchOperator.LESS) {
				observableList.stream().filter(r -> (r.getSubmittedVote().getRationaleUnderstandable() < ratUnderstandable));
				requirements = FXCollections.observableArrayList(observableList);
			} else if (ratUnderstableOp == SearchOperator.GREATER) {
				observableList.stream().filter(r -> (r.getSubmittedVote().getRationaleUnderstandable() > ratUnderstandable));
				requirements = FXCollections.observableArrayList(observableList);
			}
		}
		if (ratTraceable != null) {
			observableList.stream().filter(r -> (!(r.getSubmittedVote().getRationaleTraceable() == (double)ratTraceable.getValue()))).forEach(observableList::remove);
			requirements = FXCollections.observableArrayList(observableList);
		}
		if (ratCorrect != VoteValue.ALL) {
			observableList.stream().filter(r -> (!(r.getSubmittedVote().getRationaleComplete() == (double)ratCorrect.getValue()))).forEach(observableList::remove);
			requirements = FXCollections.observableArrayList(observableList);
		}
		if (ratConsistent != VoteValue.ALL) {
			observableList.stream().filter(r -> (!(r.getSubmittedVote().getRationaleConsistent() == (double)ratConsistent.getValue()))).forEach(observableList::remove);
			requirements = FXCollections.observableArrayList(observableList);
		}

		observableArray = observableList;
	}

	// private static void filterTitle(String title) {
	//
	// if (title == null) {
	// return;
	// }
	// try (Connection conn =
	// DriverManager.getConnection("jdbc:mysql://db.swt.wiai.uni-bamberg.de/GroupF",
	// "GroupF",
	// "gruppe_f")) {
	// Statement stmt = conn.createStatement();
	//
	// ResultSet requirements = stmt.executeQuery("SELECT * FROM Requirement
	// WHERE Title='" + title + "'");
	//
	// observableArray.clear();
	//
	// while (requirements.next()) {
	// String ownerName = DatabaseHelper.IDToLoginName(requirements.getInt(5));
	// observableArray.add(new RequirementCardSimple(requirements.getInt(1),
	// requirements.getString(2),
	// requirements.getInt(3), requirements.getInt(4), requirements.getInt(5),
	// ownerName,
	// requirements.getInt(6),
	// DatabaseHelper.generateModulesString(requirements.getInt(1)),
	// requirements.getString(7), requirements.getString(8),
	// requirements.getString(9),
	// requirements.getString(10), requirements.getString(11),
	// requirements.getInt(12),
	// requirements.getTimestamp(13), requirements.getString(14)));
	// }
	//
	// } catch (SQLException e) {
	// e.printStackTrace();
	// }
	// }

	// private static void filterOwner(String owner, String title) {
	// if (owner == null) {
	// return;
	// }
	// try (Connection conn =
	// DriverManager.getConnection("jdbc:mysql://db.swt.wiai.uni-bamberg.de/GroupF",
	// "GroupF",
	// "gruppe_f")) {
	// Statement stmt = conn.createStatement();
	//
	// ResultSet r = stmt.executeQuery("SELECT ID FROM User WHERE LoginName = '"
	// + owner + "'");
	// int ownerID = 1111111;
	// while (r.next()) {
	// ownerID = r.getInt(1);
	//
	// }
	// String sql = null;
	// if (title == null) {
	// sql = "SELECT * FROM Requirement WHERE OwnerID = " + ownerID;
	// } else {
	// sql = "SELECT * FROM Requirement WHERE OwnerID = " + ownerID + "AND Title
	// = '" + title + "'";
	// }
	// ResultSet requirements = stmt.executeQuery(sql);
	// while (requirements.next()) {
	// String ownerName = DatabaseHelper.IDToLoginName(requirements.getInt(5));
	// observableArray.add(new RequirementCardSimple(requirements.getInt(1),
	// requirements.getString(2),
	// requirements.getInt(3), requirements.getInt(4), requirements.getInt(5),
	// ownerName,
	// requirements.getInt(6),
	// DatabaseHelper.generateModulesString(requirements.getInt(1)),
	// requirements.getString(7), requirements.getString(8),
	// requirements.getString(9),
	// requirements.getString(10), requirements.getString(11),
	// requirements.getInt(12),
	// requirements.getTimestamp(13), requirements.getString(14)));
	// }
	// } catch (SQLException e) {
	// e.printStackTrace();
	// }
	// }
	//
	// private static void filterFitCriterion(String fitCriterion, String title,
	// String owner) {
	//
	// if (fitCriterion == null) {
	// return;
	// }
	// try (Connection conn =
	// DriverManager.getConnection("jdbc:mysql://db.swt.wiai.uni-bamberg.de/GroupF",
	// "GroupF",
	// "gruppe_f")) {
	// Statement stmt = conn.createStatement();
	//
	// int ownerID = 1111111;
	// if (owner != null) {
	//
	// Statement stmt$ = conn.createStatement();
	// ResultSet r = stmt$.executeQuery("SELECT ID FROM User WHERE LoginName =
	// '" + owner + "'");
	// while (r.next()) {
	// ownerID = r.getInt(1);
	// }
	// }
	// String sql = null;
	// if (owner == null && title != null) {
	// sql = "SELECT * FROM Requirement WHERE Title = '" + title + "'AND
	// FitCriterion= '" + fitCriterion + "'";
	// } else if (owner == null && title == null) {
	// sql = "SELECT * FROM Requirement WHERE FitCriterion = '" + fitCriterion +
	// "'";
	// } else if (owner != null && title == null) {
	// sql = "SELECT * FROM Requirement WHERE FitCriterion='" + fitCriterion +
	// "' AND OwnerID=" + ownerID;
	// } else if (owner != null && title != null) {
	// sql = "SELECT * FROM Requirement WHERE FitCriterion='" + fitCriterion +
	// "' AND OwnerID=" + ownerID
	// + "and Title='" + title + "'";
	// }
	// ResultSet requirements = stmt.executeQuery(sql);
	// observableArray.clear();
	// while (requirements.next()) {
	// String ownerName = DatabaseHelper.IDToLoginName(requirements.getInt(5));
	// observableArray.add(new RequirementCardSimple(requirements.getInt(1),
	// requirements.getString(2),
	// requirements.getInt(3), requirements.getInt(4), requirements.getInt(5),
	// ownerName,
	// requirements.getInt(6),
	// DatabaseHelper.generateModulesString(requirements.getInt(1)),
	// requirements.getString(7), requirements.getString(8),
	// requirements.getString(9),
	// requirements.getString(10), requirements.getString(11),
	// requirements.getInt(12),
	// requirements.getTimestamp(13), requirements.getString(14)));
	// }
	// } catch (SQLException e) {
	// e.printStackTrace();
	// }
	// }
	//
	// private static void filterSource(String source) {
	// // TODO SearchModel: implement filterSource()
	// if (source == null) {
	// return;
	// }
	// }
	//
	// private static void filterSupportingMaterials(String supportingMaterials)
	// {
	// // TODO SearchModel: implement filterSupportingMaterials()
	// if (supportingMaterials == null) {
	// return;
	// }
	//
	// }

	public ObservableList<RequirementCardSimple> getObservableArray() {
		return observableArray;
	}

	public void setObservableArray(ObservableList<RequirementCardSimple> observableArray) {
		SearchHelperImpl.observableArray = observableArray;
	}

}