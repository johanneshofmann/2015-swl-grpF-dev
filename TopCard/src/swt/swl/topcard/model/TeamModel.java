package swt.swl.topcard.model;

import java.util.Observable;

import javafx.collections.ObservableList;
import swt.swl.topcard.logic.DatabaseHelper;
import swt.swl.topcard.logic.Module;

public class TeamModel extends Observable {

	private ObservableList<Module> observableArray;

	public ObservableList<Module> getObservableArray() {
		return this.observableArray;
	}

	public TeamModel() {
	}

	public void selectTeams() {

		ObservableList<String> resultSet = DatabaseHelper.getNameFrom("Team");

		// Clear observable array
		this.observableArray.clear();

		for (String team : resultSet) {
			Module module = new Module(DatabaseHelper.XNameToID("Team", team), team);
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

		DatabaseHelper.deleteXFromDatabaseByID("Team", ID);
	}

	public void deleteModuleFromDatabase(String name) {
		DatabaseHelper.deleteXFromDatabaseByName("Team", name);
	}
}
