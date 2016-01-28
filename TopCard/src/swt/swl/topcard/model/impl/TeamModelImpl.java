package swt.swl.topcard.model.impl;

import java.util.Observable;

import javafx.collections.ObservableList;
import swt.swl.topcard.logic.impl.DatabaseHelper;
import swt.swl.topcard.logic.impl.ModuleImpl;
import swt.swl.topcard.model.TeamModel;

public class TeamModelImpl extends Observable implements TeamModel {

	private ObservableList<ModuleImpl> observableArray;

	public ObservableList<ModuleImpl> getObservableArray() {
		return this.observableArray;
	}

	public TeamModelImpl() {
	}

	public void selectTeams() {

		ObservableList<String> resultSet = DatabaseHelper.getNameFrom("Team");

		// Clear observable array
		this.observableArray.clear();

		for (String team : resultSet) {
			ModuleImpl module = new ModuleImpl(DatabaseHelper.XNameToID("Team", team), team);
			this.observableArray.add(module);
		}
	}

	public boolean hasTeam(String name) {

		return DatabaseHelper.checkExistent("Team", name);
	}

	public void insertTeam(String teamName) {

		DatabaseHelper.insertSimpleX("Team", teamName);

		triggerNotification(teamName);
	}

	private void triggerNotification(Object message) {

		setChanged();
		notifyObservers(message);
	}

	public void deleteTeamFromDatabase(int ID) {

		DatabaseHelper.deleteXFromDatabaseByID("Team", "=", ID);
	}

	public void deleteModuleFromDatabase(String name) {
		DatabaseHelper.deleteXFromDatabaseByName("Team", name);
	}
}