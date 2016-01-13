package AllTests;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import junit.framework.Test;
import junit.framework.TestSuite;
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
// TODO: alle klassen die fertig sind unten einfuegen
// (in der klasse muss eine Suite() Methode erstellt werden die abgeaendert aus
// den anderen Klassen rauskopiert werden kann)
@SuiteClasses({ TestLoginModel.class, TestLoginModel.class, TestCreateRQCardController.class })
public class AllTests {
	/**
	 * Returns a <code>TestSuite</code> instance that contains all the declared
	 * <code>TestCase</code> to run.
	 * 
	 * @return a <code>TestSuite</code> instance.
	 */
	public static Test suite() {
		final TestSuite suite = new TestSuite("All Tests");

		suite.addTest(TestLoginModel.suite());
		suite.addTest(TestModuleModel.suite());
		suite.addTest(TestCreateRQCardController.suite());
		suite.addTest(TestLoginWindowController.suite());
		suite.addTest(TestRegistrationController.suite());
		suite.addTest(TestRequirementCardController.suite());
		suite.addTest(TestSearchRQCardController.suite());
		suite.addTest(TestReqiurementCardSimple.suite());
		suite.addTest(TestRequirementCardModel.suite());

		return suite;
	}

	/**
	 * Launches all the tests with a text mode test runner.
	 * 
	 * @param args
	 *            ignored
	 */
	public static final void main(String[] args) {
		junit.textui.TestRunner.run(AllTests.suite());
	}
}
