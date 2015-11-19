package swt.swl.topcard.controller;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.Pane;
import swt.swl.topcard.MainApp;
import swt.swl.topcard.model.LoginModel;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;

public class MainWindowController {

	private MainApp mainApp;
	private String loginName;

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
	void startButtonClicked(ActionEvent event) {

	}

	@FXML
	void createNewRqButtonClicked(ActionEvent event) {
		
		try {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(getClass().getResource("/swt/swl/topcard/view/CreateRQCardView.fxml"));
			Pane rootLayout = (Pane) loader.load();
			((MainWindowController) loader.getController()).setLoginName(loginName);
			Scene scene = new Scene(rootLayout);
			mainApp.getPrimaryStage().setScene(scene);
			mainApp.getPrimaryStage().show();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@FXML
	void searchButtonClicked(ActionEvent event) {

	}

	@FXML
	void toVoteButtonClicked(ActionEvent event) {

	}

	@FXML
	void myRqCardsButtonClicked(ActionEvent event) {

	}

	public void setMainApp(MainApp mainApp) {
		this.mainApp = mainApp;
	}

	public String getLoginName() {
		return loginName;
	}

	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}
}
