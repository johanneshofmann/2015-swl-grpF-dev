package swt.swl.topcard.logic.DAOs.impl;

import java.util.Observable;

import javafx.collections.ObservableList;
import swt.swl.topcard.logic.DAOs.TeamDAO;
import swt.swl.topcard.logic._impl.DatabaseHelper;
import swt.swl.topcard.logic.entity.impl.ModuleImpl;

public class TeamDAOImpl extends Observable implements TeamDAO {

	private ObservableList<ModuleImpl> observableArray;

	public ObservableList<ModuleImpl> getObservableArray() {
		return this.observableArray;
	}

	public TeamDAOImpl() {
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
