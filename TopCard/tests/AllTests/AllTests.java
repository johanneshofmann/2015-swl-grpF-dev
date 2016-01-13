package AllTests;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import junit.framework.Test;
import junit.framework.TestSuite;
import swt.swl.topcard.model.TestLoginModel;
import swt.swl.topcard.model.TestModuleModel;

@RunWith(Suite.class)
// TODO: alle klassen die fertig sind unten einfuegen
// (in der klasse muss eine Suite() Methode erstellt werden die abgeaendert aus
// den anderen Klassen rauskopiert werden kann)
@SuiteClasses({ TestLoginModel.class, TestLoginModel.class })
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
