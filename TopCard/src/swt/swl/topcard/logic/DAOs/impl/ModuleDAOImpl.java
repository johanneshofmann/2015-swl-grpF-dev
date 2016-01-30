package swt.swl.topcard.logic.DAOs.impl;

import java.util.Observable;

import javafx.collections.ObservableList;
import swt.swl.topcard.logic.Module;
import swt.swl.topcard.logic.DAOs.ModuleDAO;
import swt.swl.topcard.logic.impl.DatabaseHelper;
import swt.swl.topcard.logic.impl.ModuleImpl;

public class ModuleDAOImpl extends Observable implements ModuleDAO {

	private ObservableList<Module> observableArray;

	public ObservableList<Module> getObservableArray() {
		return this.observableArray;
	}

	public void selectModules() {

		ObservableList<String> modules = DatabaseHelper.getNameFrom("Module");

		// Clear observable array
		this.observableArray.clear();

		for (String moduleName : modules) {

			Module module = new ModuleImpl(DatabaseHelper.XNameToID("Module", moduleName), moduleName);

			this.observableArray.add(module);
		}
	}

	public boolean hasModule(String name) {

		return DatabaseHelper.checkExistent("Module", name);
	}

	public void insertModule(String moduleName) {

		DatabaseHelper.insertSimpleX("Module", moduleName);

		triggerNotification(moduleName);
	}

	public void deleteModuleFromDatabase(int ID) {

		DatabaseHelper.deleteXFromDatabaseByID("Module", "=", ID);
	}

	public void deleteModuleFromDatabase(String name) {

		DatabaseHelper.deleteXFromDatabaseByName("Module", name);
	}

	private void triggerNotification(Object message) {
		setChanged();
		notifyObservers(message);
	}
}
