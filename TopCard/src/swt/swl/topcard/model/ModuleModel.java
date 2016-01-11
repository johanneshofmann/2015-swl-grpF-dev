package swt.swl.topcard.model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Observable;

import javafx.collections.ObservableList;

public class ModuleModel extends Observable{

	private ObservableList<Module> observableArray;
	
	public ObservableList<Module> getObservableArray()
	{
		return this.observableArray;
	}
	
	public ModuleModel()
	{
		
	}
	
	public void selectModules()
	{
		//Clear observable array
		this.observableArray.clear();
		
		String query = "SELECT * FROM Module;";
		
		ResultSet resultSet = DatabaseHelper.executeQuery(query);
		try {
			while(resultSet.next())
			{
				Module module = new Module(resultSet.getInt(1), resultSet.getString(1));
				this.observableArray.add(module);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public Boolean hasModule(String text)
	{
		String query = "SELECT * FROM Module WHERE Text=?";
		ResultSet resultSet = DatabaseHelper.executeQuery(query, text);
		return ResultSetUtil.contains(resultSet, 1, text);
	}
	
	public void insertModule(String text)
	{
		String query = "INSERT INTO Module(ID) VALUES (?);";
		DatabaseHelper.executeUpdate(query, text);
		triggerNotification(text);
	}
	
	private void triggerNotification(Object message) {
		setChanged();
		notifyObservers(message); 
	}
	
	public void deleteModuleFromDatabase(int ID){

		try (Connection conn = DriverManager.getConnection("jdbc:mysql://db.swt.wiai.uni-bamberg.de/GroupF", "GroupF",
				"gruppe_f")) {
			Statement stmt = conn.createStatement();

			String sqlInsert = "delete from Module where ID =" + ID ;
			stmt.executeUpdate(sqlInsert);

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	public void deleteModuleFromDatabase(String Name){

		try (Connection conn = DriverManager.getConnection("jdbc:mysql://db.swt.wiai.uni-bamberg.de/GroupF", "GroupF",
				"gruppe_f")) {
			Statement stmt = conn.createStatement();

			String sqlInsert = "delete from Module where Name =" + Name ;
			stmt.executeUpdate(sqlInsert);

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
