package swt.swl.topcard.model.impl;

import java.util.Observable;

import javafx.collections.ObservableList;
import swt.swl.topcard.logic.Team;
import swt.swl.topcard.logic.impl.DatabaseHelperImpl;
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

		ObservableList<String> resultSet = DatabaseHelperImpl.getNameFrom("Team");

		// Clear observable array
		this.observableArray.clear();

		for (String team : resultSet) {
			ModuleImpl module = new ModuleImpl(DatabaseHelperImpl.XNameToID("Team", team), team);
			this.observableArray.add(module);
		}
	}

	public boolean hasTeam(String name) {

		return DatabaseHelperImpl.checkExistent("Team", name);
	}

	public void insertTeam(String teamName) {

		DatabaseHelperImpl.insertSimpleX("Team", teamName);

		triggerNotification(teamName);
	}

	private void triggerNotification(Object message) {

		setChanged();
		notifyObservers(message);
	}

	public void deleteTeamFromDatabase(int ID) {

		DatabaseHelperImpl.deleteXFromDatabaseByID("Team", ID);
	}

	public void deleteModuleFromDatabase(String name) {
		DatabaseHelperImpl.deleteXFromDatabaseByName("Team", name);
	}
}
