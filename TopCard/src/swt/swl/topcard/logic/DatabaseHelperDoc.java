package swt.swl.topcard.logic;

import java.util.ArrayList;

import javafx.collections.ObservableList;
import swt.swl.topcard.logic.impl.RequirementCardSimpleImpl;

public interface DatabaseHelperDoc {

	int XNameToID(String string, String userName);

	ArrayList<Integer> getAllRQIDsFromUser(String userName);

	ArrayList<SubmittedVoteSimple> getVoteResultsFrom(Integer id);

	ObservableList<String> getNamesFromSource(String string);

	void executeUpdate(String sqlInsert);

	void deleteXFromDatabaseByName(String string, String loginName);

	ObservableList<String> getNameFrom(String string);

	boolean checkExistent(String string, String name);

	void insertSimpleX(String string, String moduleName);

	void deleteXFromDatabaseByID(String string, int iD);

	int getMaxXFromY(String string, String string2);

	ArrayList<Integer> getIDsFromX(String source, ObservableList<String> itemNames);

	RequirementCardSimpleImpl IDToRequirementCardSimple(int id);

	boolean checkUserAlreadySubscribed(String loginName);

}
