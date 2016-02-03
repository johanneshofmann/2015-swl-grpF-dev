package swt.swl.topcard.logic._impl;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import swt.swl.topcard.logic.SearchHelper;
import swt.swl.topcard.logic.entitiy.RequirementCardSimple;
import swt.swl.topcard.model.enums.SearchOperator;
import swt.swl.topcard.model.enums.VoteValue;

public class SearchHelperImpl implements SearchHelper {

	private static ObservableList<RequirementCardSimple> observableArray;

	public static void search(ObservableList<RequirementCardSimple> observableList, String title, String owner,
			String module, String description, String rationale, String source, String userStories,
			String supportingMaterials, String fitCriterion, Integer isFrozen, SearchOperator descPreciseOp,
			Integer descPrecise, SearchOperator descUnderstandableOp, Integer descUnderstandable, VoteValue descCorrect,
			VoteValue descComplete, VoteValue descAtomic, SearchOperator ratPreciseOp, Integer ratPrecise,
			SearchOperator ratUnderstableOp, Integer ratUnderstandable, VoteValue ratTraceable, VoteValue ratCorrect,
			VoteValue ratConsistent) {

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
		if (descPreciseOp != SearchOperator.ALL) {
			if (descPreciseOp == SearchOperator.EQUAL) {
				observableList.stream().filter(r -> (r.getSubmittedVote().getDescriptionPrecise() == descPrecise))
						.forEach(observableList::remove);
				requirements = FXCollections.observableArrayList(observableList);
			} else if (descPreciseOp == SearchOperator.LESS) {
				observableList.stream().filter(r -> (r.getSubmittedVote().getDescriptionPrecise() < descPrecise))
						.forEach(observableList::remove);
				requirements = FXCollections.observableArrayList(observableList);
			} else if (descPreciseOp == SearchOperator.GREATER) {
				observableList.stream().filter(r -> (r.getSubmittedVote().getDescriptionPrecise() > descPrecise))
						.forEach(observableList::remove);
				requirements = FXCollections.observableArrayList(observableList);
			}
		}
		if (descUnderstandableOp != SearchOperator.ALL) {
			if (descUnderstandableOp == SearchOperator.EQUAL) {
				observableList.stream()
						.filter(r -> (r.getSubmittedVote().getDescriptionUnderstandable() == descUnderstandable))
						.forEach(observableList::remove);
				requirements = FXCollections.observableArrayList(observableList);
			} else if (descUnderstandableOp == SearchOperator.LESS) {
				observableList.stream()
						.filter(r -> (r.getSubmittedVote().getDescriptionUnderstandable() < descUnderstandable))
						.forEach(observableList::remove);
				requirements = FXCollections.observableArrayList(observableList);
			} else if (descUnderstandableOp == SearchOperator.GREATER) {
				observableList.stream()
						.filter(r -> (r.getSubmittedVote().getDescriptionUnderstandable() > descUnderstandable))
						.forEach(observableList::remove);
				requirements = FXCollections.observableArrayList(observableList);
			}
		}
		if (descCorrect != VoteValue.ALL) {
			observableList.stream()
					.filter(r -> (!(r.getSubmittedVote().getDescriptionCorrect() == (double) descCorrect.getValue())))
					.forEach(observableList::remove);
			requirements = FXCollections.observableArrayList(observableList);
		}
		if (descComplete != VoteValue.ALL) {
			observableList.stream()
					.filter(r -> (!(r.getSubmittedVote().getDescriptionComplete() == (double) descComplete.getValue())))
					.forEach(observableList::remove);
			requirements = FXCollections.observableArrayList(observableList);
		}
		if (descAtomic != VoteValue.ALL) {
			observableList.stream()
					.filter(r -> (!(r.getSubmittedVote().getDescriptionAtomic() == (double) descAtomic.getValue())))
					.forEach(observableList::remove);
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
				observableList.stream()
						.filter(r -> (r.getSubmittedVote().getRationaleUnderstandable() == ratUnderstandable));
				requirements = FXCollections.observableArrayList(observableList);
			} else if (ratUnderstableOp == SearchOperator.LESS) {
				observableList.stream()
						.filter(r -> (r.getSubmittedVote().getRationaleUnderstandable() < ratUnderstandable));
				requirements = FXCollections.observableArrayList(observableList);
			} else if (ratUnderstableOp == SearchOperator.GREATER) {
				observableList.stream()
						.filter(r -> (r.getSubmittedVote().getRationaleUnderstandable() > ratUnderstandable));
				requirements = FXCollections.observableArrayList(observableList);
			}
		}
		if (ratTraceable != null) {
			observableList.stream()
					.filter(r -> (!(r.getSubmittedVote().getRationaleTraceable() == (double) ratTraceable.getValue())))
					.forEach(observableList::remove);
			requirements = FXCollections.observableArrayList(observableList);
		}
		if (ratCorrect != VoteValue.ALL) {
			observableList.stream()
					.filter(r -> (!(r.getSubmittedVote().getRationaleComplete() == (double) ratCorrect.getValue())))
					.forEach(observableList::remove);
			requirements = FXCollections.observableArrayList(observableList);
		}
		if (ratConsistent != VoteValue.ALL) {
			observableList.stream()
					.filter(r -> (!(r.getSubmittedVote()
							.getRationaleConsistent() == (double) ratConsistent.getValue())))
					.forEach(observableList::remove);
			requirements = FXCollections.observableArrayList(observableList);
		}

		observableArray = observableList;
	}

	public ObservableList<RequirementCardSimple> getObservableArray() {
		return observableArray;
	}

	public void setObservableArray(ObservableList<RequirementCardSimple> observableArray) {
		SearchHelperImpl.observableArray = observableArray;
	}

}
