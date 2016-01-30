package swt.swl.topcard.model;

import static org.junit.Assert.assertEquals;

//import java.sql.Timestamp;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import javafx.collections.ObservableList;
import swt.swl.topcard.controller.RequirementCardController;
import swt.swl.topcard.controller.impl.RequirementCardControllerImpl;
import swt.swl.topcard.logic.impl.DatabaseHelper;
import swt.swl.topcard.logic.impl.RequirementCardSimpleImpl;
import swt.swl.topcard.logic.RequirementCardSimple;

public class TestRequirementCardModel {

	private RequirementCardModel rqModel;
	RequirementCardController controller;
	String loginName;

	// private String loginName;

	@Before
	public void init() {
		// controller needed for initializing observableArray
		controller = new RequirementCardControllerImpl();
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
		// int isFrozen = 0;
		// Timestamp createdAt = null;
		String name = "Bob";
		rqModel.setLoginName(name);
		// int rqID = 0;

		rqModel.insertRqIntoDatabase(null, title, description, rationale, source, userStories, fitCriterion,
				supportingMaterials);

		// 'call' update-Method:
		this.rqModel.updateRequirementsList();

		RequirementCardSimple addedRqCard = new RequirementCardSimpleImpl.RQBuilderImpl().buildRQ();

		boolean inArray = false;
		for (RequirementCardSimple rq : rqModel.getObservableArray()) {
			if (rq.getTitle().equals(addedRqCard.getTitle())) {
				inArray = true;
			}
		}
		assertEquals(true, inArray);

		// delete after checking ..
		DatabaseHelper.deleteRqFromDatabase(addedRqCard.getRqID(), addedRqCard.getMajorVersion(),
				addedRqCard.getMinorVersion());
	}

	@Test
	public void afterRemovingRqCardItShouldBeRemoved() {

		// first insert one :

		String title = "title";
		String description = "description";
		String rationale = "rationale";
		ObservableList<String> modules = null;
		ObservableList<String> source = null;
		ObservableList<String> userStories = null;
		String fitCriterion = "fitCriterion";
		String supportingMaterials = "supportingMaterials";
		// int isFrozen = 0;
		// Timestamp createdAt = null;
		String name = "Bob";
		rqModel.setLoginName(name);
		// int rqID = 0;

		rqModel.insertRqIntoDatabase(modules, title, description, rationale, source, userStories, fitCriterion,
				supportingMaterials);
		// 'call' update-Method:
		this.rqModel.updateRequirementsList();

		RequirementCardSimple addedRqCard = new RequirementCardSimpleImpl.RQBuilderImpl().buildRQ();
		DatabaseHelper.deleteRqFromDatabase(addedRqCard.getRqID(), addedRqCard.getMajorVersion(),
				addedRqCard.getMinorVersion());

		assertEquals(false, rqModel.getObservableArray().contains((RequirementCardSimple) addedRqCard));

	}

}