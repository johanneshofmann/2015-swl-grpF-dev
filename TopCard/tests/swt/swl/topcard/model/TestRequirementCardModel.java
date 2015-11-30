package swt.swl.topcard.model;

import static org.junit.Assert.*;

import org.junit.Test;

public class TestRequirementCardModel {

	private RequirementCardModel rqModel;

	public TestRequirementCardModel() {

		rqModel = new RequirementCardModel();
	}

	@Test
	public void afterInsertRQCardIntoDatabaseItShouldContainIt() {

		String title = "title";
		String description = "description";
		String rationale = "rationale";
		String source = "source";
		String userStories = "userStories";
		String fitCriterion = "fitCriterion";
		String supportingMaterials = "supportingMaterials";
		boolean isFrozen = false;

		rqModel.insertRQIntoDatabase(title, description, rationale, source, userStories, fitCriterion,
				supportingMaterials, isFrozen);
		RequirementCardSimple addedRqCard = new RequirementCardSimple(title);

		assertEquals(true, rqModel.getObservableArray().contains(addedRqCard));

		// delete after checking ..
		afterRemovingRqCardItShouldBeRemoved(title);
	}

	@Test
	public void afterRemovingRqCardItShouldBeRemoved(String title) {

		rqModel.deleteRqFromDatabase(title);
		RequirementCardSimple addedRqCard = new RequirementCardSimple(title);

		assertEquals(false, rqModel.getObservableArray().contains(addedRqCard));
	}
}
