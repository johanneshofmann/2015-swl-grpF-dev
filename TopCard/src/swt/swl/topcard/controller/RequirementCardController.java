package swt.swl.topcard.controller;

import java.util.Observable;
import java.util.Observer;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;
import swt.swl.topcard.MainApp;
import swt.swl.topcard.model.LoginModel;
import swt.swl.topcard.model.RequirementCardModel;
import swt.swl.topcard.model.RequirementCardSimple;

public class RequirementCardController implements Observer {
	private MainApp mainApp;
	private String loginName;
	private RequirementCardModel rqModel;

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
	private TableView<RequirementCardSimple> requirementCardsTable;
	@FXML
	private TableColumn<String, String> requirementCards;
	
	private ObservableList<RequirementCardSimple> observableList;

	public RequirementCardController(){
		rqModel = new RequirementCardModel();
		this.observableList = FXCollections.observableArrayList();
		rqModel.setObservableArray(this.observableList);
		rqModel.addObserver(this);
	}
	
	public void initialize()
	{
		//Create TableColumnFactory
		this.requirementCards.setCellValueFactory(new PropertyValueFactory<>("Title"));
		requirementCardsTable.setItems(observableList);
		this.rqModel.getRequirements();
	}

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
	
	public void setMainApp(MainApp mainApp) {
		this.mainApp = mainApp;
	}
	public String getLoginName() {
		return loginName;
	}

	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}

	@Override
	public void update(Observable o, Object arg) {
		// TODO Auto-generated method stub
		
	}
}
