package swt.swl.topcard.model;

import static org.junit.Assert.assertEquals;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

import org.junit.Before;
import org.junit.Test;

import swt.swl.topcard.logic.DAOs.ModuleDAO;
import swt.swl.topcard.logic.DAOs.impl.ModuleDAOImpl;

public class TestModuleModel {
	// private ObservableList<Module> observableArray;
	private ModuleDAO moduleModel;

	@Before

	public void setUp() {

	}

	public TestModuleModel() {
		moduleModel = new ModuleDAOImpl();
	}

	@Test
	public void testInsertModuleTest() {
		String name1 = "testModule";
		moduleModel.insertModule(name1);
		boolean toCheck = moduleModel.hasModule(name1);
		assertEquals(true, toCheck);
	}

	@Test
	public void testHasModuleShouldBeTrue() {
		String name1 = "testModule";
		moduleModel.insertModule(name1);
		boolean toCheck = moduleModel.hasModule(name1);
		assertEquals(true, toCheck);
	}

	@Test
	public void testHasModuleShouldBeFalse() {
		String name1 = "testModule";
		// moduleModel.insertModule(name1);
		boolean toCheck = moduleModel.hasModule(name1);
		assertEquals(false, toCheck);
	}

	@Test
	public void testDeleteModuleFromDatabase() {

		String name1 = "testModule";
		moduleModel.insertModule(name1);

		try (Connection conn = DriverManager.getConnection("jdbc:mysql://db.swt.wiai.uni-bamberg.de/GroupF", "GroupF",
				"gruppe_f")) {
			Statement stmt = conn.createStatement();

			String sqlInsert = "delete from Module where Name ='" + name1 + "'";
			stmt.executeUpdate(sqlInsert);

		} catch (SQLException e) {
			e.printStackTrace();
		}
		// moduleModel.deleteModuleFromDatabase(name1);
		boolean toCheck = moduleModel.hasModule(name1);
		assertEquals(false, toCheck);
	}

}
