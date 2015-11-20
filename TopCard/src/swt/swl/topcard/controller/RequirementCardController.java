package swt.swl.topcard.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.Pane;
import swt.swl.topcard.MainApp;

public class RequirementCardController {
	private MainApp mainApp;
	private String loginName;
	@FXML
	private Pane mainWindowPainLeft, mainWindowPainRight;
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
	private TableView<String> requirementCardsTable;
	@FXML
	private TableColumn<String, String> requirementCards;

	@FXML
	void startButtonClicked(ActionEvent event) {
		try {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(getClass().getResource("/swt/swl/topcard/view/RequirementCardView.fxml"));
			Pane rootLayout = (Pane) loader.load();
			Scene scene = new Scene(rootLayout);
			mainApp.getPrimaryStage().setScene(scene);
			mainApp.getPrimaryStage().show();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
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
}
