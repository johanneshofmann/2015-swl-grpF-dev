package swt.swl.topcard.model;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import swt.swl.topcard.controller.RequirementCardController;

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
		String source = "source";
		String userStories = "userStories";
		String fitCriterion = "fitCriterion";
		String supportingMaterials = "supportingMaterials";
		boolean isFrozen = false;

		rqModel.insertRQIntoDatabase(title, description, rationale, source, userStories, fitCriterion,
				supportingMaterials, isFrozen);

		// 'call' update-Method:
		this.rqModel.getRequirements();
		
		RequirementCardSimple addedRqCard = new RequirementCardSimple(title);
		boolean inArray = false;
		for(RequirementCardSimple rq : rqModel.getObservableArray()){
			if(rq.equals(addedRqCard)){
				inArray=true;
			}
		}
		assertEquals(true, inArray);

		// delete after checking ..
		rqModel.deleteRqFromDatabase(title);
	}

	@Test
	public void afterRemovingRqCardItShouldBeRemoved() {

		// first insert one :

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
		// 'call' update-Method:
		this.rqModel.getRequirements();

		RequirementCardSimple addedRqCard = new RequirementCardSimple(title);

		rqModel.deleteRqFromDatabase(title);

		assertEquals(false, rqModel.getObservableArray().contains((RequirementCardSimple) addedRqCard));

	}
}