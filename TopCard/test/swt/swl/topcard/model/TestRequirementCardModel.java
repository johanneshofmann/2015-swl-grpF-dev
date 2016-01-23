package swt.swl.topcard.model;

import static org.junit.Assert.assertEquals;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import javafx.collections.ObservableList;
import junit.framework.TestSuite;
import swt.swl.topcard.controller.RequirementCardController;
import swt.swl.topcard.logic.RequirementCardSimple;

public class TestRequirementCardModel {

	private RequirementCardModel rqModel;
	RequirementCardController controller;
	private String loginName;

	@Before
	public void init() {
		// controller needed for initializing observableArray
		controller = new RequirementCardController();
		rqModel = controller.getRqModel();

		loginName = "loginName";
	}

	@After
	public void end() {

	}

	@Test
	public void afterInsertRQCardIntoDatabaseItShouldContainIt() {

		String title = "title";
		String description = "description";
		String rationale = "rationale";
		ObservableList<String> source = null;
		ObservableList<String> userStories = null;
		String fitCriterion = "fitCriterion";
		String supportingMaterials = "supportingMaterials";
		boolean isFrozen = false;

		rqModel.insertRqIntoDatabase(null, title, description, rationale, source, userStories, fitCriterion,
				supportingMaterials);

		// 'call' update-Method:
		this.rqModel.updateRequirementsList();

		// TODO: @Test not correct set here:

		RequirementCardSimple addedRqCard = new RequirementCardSimple(0, title, 0, 0, 0, supportingMaterials, 0,
				supportingMaterials, supportingMaterials, supportingMaterials, supportingMaterials, supportingMaterials,
				supportingMaterials, supportingMaterials, 0, null, supportingMaterials);
		boolean inArray = false;
		for (RequirementCardSimple rq : rqModel.getObservableArray()) {
			if (rq.getTitle().equals(addedRqCard.getTitle())) {
				inArray = true;
			}
		}
		assertEquals(true, inArray);

		// delete after checking ..
		// TODO: cant simply delete now because of child row constraints, use
		// delete method with 3 parameters
		// DatabaseHelper.deleteRqFromDatabase(title);
	}

	@Test
	public void afterRemovingRqCardItShouldBeRemoved() {

		// first insert one :

		String title = "title";
		String description = "description";
		String rationale = "rationale";
		ObservableList<String> source = null;
		ObservableList<String> userStories = null;
		String fitCriterion = "fitCriterion";
		String supportingMaterials = "supportingMaterials";
		boolean isFrozen = false;

		rqModel.insertRqIntoDatabase(null, title, description, rationale, source, userStories, fitCriterion,
				supportingMaterials);
		// 'call' update-Method:
		this.rqModel.updateRequirementsList();

		// here also
		RequirementCardSimple addedRqCard = new RequirementCardSimple(0, title, 0, 0, 0, supportingMaterials, 0,
				supportingMaterials, supportingMaterials, supportingMaterials, supportingMaterials, supportingMaterials,
				supportingMaterials, supportingMaterials, 0, null, supportingMaterials);

		// TODO: cant simply delete now because of child row constraints, use
		// delete method with 3 parameters
		// DatabaseHelper.deleteRqFromDatabase(title);

		assertEquals(false, rqModel.getObservableArray().contains((RequirementCardSimple) addedRqCard));

	}

	public static junit.framework.Test suite() {
		return new TestSuite(TestRequirementCardModel.class);
	}
}