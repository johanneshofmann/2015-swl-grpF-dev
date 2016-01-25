package swt.swl.topcard.model;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import swt.swl.topcard.logic.RequirementCardSimple;
import swt.swl.topcard.logic.impl.RequirementCardSimpleImpl;

public class TestReqiurementCardSimple {

	private RequirementCardSimple rqCard;

	public TestReqiurementCardSimple() {

		rqCard = new RequirementCardSimpleImpl(0, "title", 0, 0, 0, null, 0, null, null, null, null, null, null, null, 0, null, null);
	}

	@Test
	public void getTitleShouldReturnTitle() {

		String toCheck = rqCard.getTitle();

		assertEquals("title", toCheck);
	}

	@Test
	public void afterSettingTitleItShouldHaveChanged() {

		String newTitle = "newTitle";
		rqCard.setTitle(newTitle);

		String toCheck = rqCard.getTitle();

		assertEquals(newTitle, toCheck);
	}

}
