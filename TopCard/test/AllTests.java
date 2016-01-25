import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import swt.swl.topcard.controller.TestCreateRQCardController;
import swt.swl.topcard.controller.TestLoginWindowController;
import swt.swl.topcard.controller.TestRegistrationController;
import swt.swl.topcard.controller.TestRequirementCardController;
import swt.swl.topcard.controller.TestSearchRQCardController;
import swt.swl.topcard.model.TestLoginModel;
import swt.swl.topcard.model.TestModuleModel;
import swt.swl.topcard.model.TestReqiurementCardSimple;
import swt.swl.topcard.model.TestRequirementCardModel;

@RunWith(Suite.class)
@SuiteClasses({ TestLoginModel.class, TestModuleModel.class, TestReqiurementCardSimple.class,
		TestRequirementCardModel.class, TestCreateRQCardController.class, TestLoginWindowController.class,
		TestRegistrationController.class, TestRequirementCardController.class, TestSearchRQCardController.class })
public class AllTests {

}
