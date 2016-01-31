package swt.swl.topcard.model;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import swt.swl.topcard.logic.entitiy.RequirementCardSimple;
import swt.swl.topcard.logic.entity.impl.RequirementCardSimpleImpl;

public class TestReqiurementCardSimple {

	private RequirementCardSimple rqCard;

	public TestReqiurementCardSimple() {

		rqCard = new RequirementCardSimpleImpl.RQBuilderImpl().buildRQ();
	}

	@Test
	public void getTitleShouldReturnTitle() {

		String toCheck = rqCard.getTitle();

		assertEquals("title", toCheck);
	}

	@Test
	public void afterSettingTitleItShouldHaveChanged() {

		String newTitle = "newTitle";
		rqCard = new RequirementCardSimpleImpl.RQBuilderImpl().setTitle(newTitle).buildRQ();

		String toCheck = rqCard.getTitle();

		assertEquals(newTitle, toCheck);
	}

}
