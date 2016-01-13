package swt.swl.topcard.model;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import junit.framework.TestSuite;

public class TestReqiurementCardSimple {

	private RequirementCardSimple rqCard;

	public TestReqiurementCardSimple() {

		rqCard = new RequirementCardSimple("title");
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

	public static junit.framework.Test suite() {
		return new TestSuite(TestReqiurementCardSimple.class);
	}
}
