package swt.swl.topcard.controller;

import javafx.scene.control.Button;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import swt.swl.topcard.MainApp;
import swt.swl.topcard.model.LoginModel;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;




public class MainWindowController {
	
	private MainApp mainApp;
	
	@FXML
	private Button start;
	@FXML
	private Button search;
	@FXML
	private Button createNewRq;
	@FXML
	private MenuItem toVote;
	@FXML
	private MenuItem myRqCards;
	@FXML
	private MenuButton showRQCards;
   
	@FXML
	void startButtonClicked(ActionEvent event){
	   
	}
	@FXML
	void createNewRqButtonClicked(ActionEvent event){
	   
	}
	@FXML
	void searchButtonClicked(ActionEvent event){
	   
	}
	@FXML
	void toVoteButtonClicked(ActionEvent event){
	   
	}
	@FXML
	void myRqCardsButtonClicked(ActionEvent event){
	   
	}
	
	public void setMainApp(MainApp mainApp) {
		this.mainApp = mainApp;
	}
	
}
