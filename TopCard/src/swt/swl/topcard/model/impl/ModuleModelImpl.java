package swt.swl.topcard.model.impl;

import java.util.Observable;

import javafx.collections.ObservableList;
import swt.swl.topcard.logic.impl.DatabaseHelperImpl;
import swt.swl.topcard.logic.impl.ModuleImpl;
import swt.swl.topcard.model.ModuleModel;

public class ModuleModelImpl extends Observable implements ModuleModel {

	private ObservableList<ModuleImpl> observableArray;

	public ObservableList<ModuleImpl> getObservableArray() {
		return this.observableArray;
	}

	public void selectModules() {

		ObservableList<String> modules = DatabaseHelperImpl.getNameFrom("Module");

		// Clear observable array
		this.observableArray.clear();

		for (String moduleName : modules) {

			ModuleImpl module = new ModuleImpl(DatabaseHelperImpl.XNameToID("Module", moduleName), moduleName);

			this.observableArray.add(module);
		}
	}

	public boolean hasModule(String name) {

		return DatabaseHelperImpl.checkExistent("Module", name);
	}

	public void insertModule(String moduleName) {

		DatabaseHelperImpl.insertSimpleX("Module", moduleName);

		triggerNotification(moduleName);
	}

	public void deleteModuleFromDatabase(int ID) {

		DatabaseHelperImpl.deleteXFromDatabaseByID("Module", ID);
	}

	public void deleteModuleFromDatabase(String name) {

		DatabaseHelperImpl.deleteXFromDatabaseByName("Module", name);
	}

	private void triggerNotification(Object message) {
		setChanged();
		notifyObservers(message);
	}
}
