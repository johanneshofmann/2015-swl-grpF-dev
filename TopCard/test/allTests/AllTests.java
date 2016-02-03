package allTests;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import swt.swl.topcard.model.TestLoginModel;
import swt.swl.topcard.model.TestModuleModel;
import swt.swl.topcard.model.TestReqiurementCardSimple;
import swt.swl.topcard.model.TestRequirementCardModel;

@RunWith(Suite.class)
@SuiteClasses({ TestLoginModel.class, TestModuleModel.class, TestReqiurementCardSimple.class,
		TestRequirementCardModel.class })
public class AllTests {

}
