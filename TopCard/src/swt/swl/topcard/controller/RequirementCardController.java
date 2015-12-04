package swt.swl.topcard.controller;

import java.util.Observable;
import java.util.Observer;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import swt.swl.topcard.MainApp;
import swt.swl.topcard.model.RequirementCardModel;
import swt.swl.topcard.model.RequirementCardSimple;

public class RequirementCardController implements Observer {
	private MainApp mainApp;
	private String loginName;
	private RequirementCardModel rqModel;
	private LoginWindowController loginController;

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

	public RequirementCardController() {
		rqModel = new RequirementCardModel();
		this.observableList = FXCollections.observableArrayList();
		rqModel.setObservableArray(this.observableList);
		rqModel.addObserver(this);
	}

	public void initialize() {
		// Create TableColumnFactory
		this.requirementCards.setCellValueFactory(new PropertyValueFactory<>("Title"));
		this.rqModel.getRequirements();
		requirementCardsTable.setItems(observableList);
		addEventHandlerToTableView();
	}

	private void addEventHandlerToTableView() {
		requirementCardsTable.setOnMousePressed(new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent event) {
				if (event.isPrimaryButtonDown() && event.getClickCount() == 2) {
					String rqTitle = requirementCardsTable.getSelectionModel().getSelectedItem().getTitle();
					openRequirement(rqTitle);
				}
			}
		});
	}

	public void repaint() {
		mainApp.getPrimaryStage().setScene(loginController.getRequirementCardViewScene());
		mainApp.getPrimaryStage().show();
	}

	@FXML
	void startButtonClicked(ActionEvent event) {
		initialize();
		mainApp.getPrimaryStage().setScene(loginController.getRequirementCardViewScene());
		mainApp.getPrimaryStage().show();
	}

	@FXML
	void createNewRqButtonClicked(ActionEvent event) {

		try {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(getClass().getResource("/swt/swl/topcard/view/CreateRQCardView.fxml"));
			Pane rootLayout = (Pane) loader.load();
			((CreateRQCardController) loader.getController()).setData(this.rqModel, this);
			Scene scene = new Scene(rootLayout);
			mainApp.getPrimaryStage().setScene(scene);
			mainApp.getPrimaryStage().show();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@FXML
	void searchButtonClicked(ActionEvent event) {

		try {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(getClass().getResource("/swt/swl/topcard/view/SearchRQCardView.fxml"));
			Pane rootLayout = (Pane) loader.load();
			((SearchRQCardController) loader.getController()).setData(this.rqModel, this);
			Scene scene = new Scene(rootLayout);
			mainApp.getPrimaryStage().setScene(scene);
			mainApp.getPrimaryStage().show();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@FXML
	void toVoteButtonClicked(ActionEvent event) {
		rqModel.getMyOrToVoteRequirements(false);
	}

	@FXML
	void myRqCardsButtonClicked(ActionEvent event) {
		rqModel.getMyOrToVoteRequirements(true);
	}

	/**
	 *
	 */
	@Override
	public void update(Observable o, Object update) {

		if (update != null) {
			if (update.toString().equals(loginName)) {
				startButtonClicked(new ActionEvent());
				System.out.println("Insert complete!");
			}
		}
	}

	/**
	 *
	 * @param rqTitle
	 */
	private void openRequirement(String rqTitle) {
		try {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(getClass().getResource("/swt/swl/topcard/view/VoteOrEditRqCardView.fxml"));
			Pane rootLayout = (Pane) loader.load();
			((VoteOrEditRqCardController) loader.getController()).setData(this.rqModel, this, rqTitle);
			((VoteOrEditRqCardController) loader.getController()).initializeNodes(rqTitle);
			Scene scene = new Scene(rootLayout);
			mainApp.getPrimaryStage().setScene(scene);
			mainApp.getPrimaryStage().show();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 *
	 * @param loginName
	 * @param mainApp
	 * @param loginWindowController
	 */
	public void setData(String loginName, MainApp mainApp, LoginWindowController loginWindowController) {
		this.loginName = loginName;
		this.mainApp = mainApp;
		this.loginController = loginWindowController;
		rqModel.setLoginName(loginName);

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

	public RequirementCardModel getRqModel() {
		return rqModel;
	}

	public void setRqModel(RequirementCardModel rqModel) {
		this.rqModel = rqModel;
	}

}
