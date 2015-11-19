package swt.swl.topcard.controller;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.Pane;
import swt.swl.topcard.MainApp;
import swt.swl.topcard.model.MainWindowModel;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;

public class MainWindowController {

	private MainWindowModel model;
	private MainApp mainApp;

	@FXML
	private Button start,search,createNewRq;
	@FXML
	private MenuItem toVote,myRqCards;
	@FXML
	private MenuButton showRQCards;

	public MainWindowController(){
		model = new MainWindowModel();
	}

	@FXML
	void startButtonClicked(ActionEvent event) {
		//TODO:
	}

	@FXML
	void createNewRqButtonClicked(ActionEvent event) {

		try {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(getClass().getResource("/swt/swl/topcard/view/CreateRQCardView.fxml"));
			Pane rootLayout = (Pane) loader.load();
			((CreateRQCardController) loader.getController()).setModel(this.model);
			Scene scene = new Scene(rootLayout);
			mainApp.getPrimaryStage().setScene(scene);
			mainApp.getPrimaryStage().show();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@FXML
	void searchButtonClicked(ActionEvent event) {
		//TODO:
	}

	@FXML
	void toVoteButtonClicked(ActionEvent event) {
		//TODO:
	}

	@FXML
	void myRqCardsButtonClicked(ActionEvent event) {
		// TODO:
	}

	public void setMainApp(MainApp mainApp) {
		this.mainApp = mainApp;
	}

	public MainWindowModel getModel() {
		return this.model;
	}
}
