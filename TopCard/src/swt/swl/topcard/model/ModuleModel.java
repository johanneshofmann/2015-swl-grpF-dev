package swt.swl.topcard.model;

import java.util.Observable;

import javafx.collections.ObservableList;
import swt.swl.topcard.logic.DatabaseHelper;
import swt.swl.topcard.logic.Module;

public class ModuleModel extends Observable {

	private ObservableList<Module> observableArray;

	public ObservableList<Module> getObservableArray() {
		return this.observableArray;
	}

	public void selectModules() {

		ObservableList<String> modules = DatabaseHelper.getNameFrom("Module");

		// Clear observable array
		this.observableArray.clear();

		for (String moduleName : modules) {

			Module module = new Module(DatabaseHelper.XNameToID("Module", moduleName), moduleName);

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

		DatabaseHelper.deleteXFromDatabaseByID("Module", ID);
	}

	public void deleteModuleFromDatabase(String name) {

		DatabaseHelper.deleteXFromDatabaseByName("Module", name);
	}

	private void triggerNotification(Object message) {
		setChanged();
		notifyObservers(message);
	}
}
