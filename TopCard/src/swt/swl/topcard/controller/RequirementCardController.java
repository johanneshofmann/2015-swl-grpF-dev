package swt.swl.topcard.controller;

import java.util.Observable;
import java.util.Observer;

import org.controlsfx.control.CheckComboBox;
import org.controlsfx.control.IndexedCheckModel;

import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import swt.swl.topcard.MainApp;
import swt.swl.topcard.logic.RequirementCardSimple;
import swt.swl.topcard.logic.TeamChangeListener;
import swt.swl.topcard.model.RequirementCardModel;

public class RequirementCardController implements Observer {

	private MainApp mainApp;
	private String loginName;
	private RequirementCardModel model;
	private LoginWindowController loginController;
	private RequirementCardController this$;
	private TeamChangeListener teamChangedListener;
	private boolean cancelTransaction;

	@FXML
	private Pane mainWindowPainLeft, mainWindowPainRight;
	@FXML
	private HBox menuListHBox;
	@FXML
	private Button searchRqButton;

	@FXML
	private ImageView startButton;
	@FXML
	private MenuItem createModuleButton, createNewRqButton, createNewTeam, createNewUserStory;
	@FXML
	private TableView<RequirementCardSimple> requirementCardsTable;

	private TableColumn<RequirementCardSimple, String> nameTableColumn, ownerTableColumn, teamTableColumn;

	private CheckComboBox<String> chooseTeamBox;
	// all Labels and ResultLabels
	@FXML
	private Label loginNameLabel, titleResultLabel, modulesResultLabel, descriptionResultLabel, rationaleResultLabel,
			sourceResultLabel, userstoriesResultLabel, supportingMaterialsResultLabel, fitCriterionResultLabel,
			frozenResultLabel;

	private ObservableList<RequirementCardSimple> observableList;

	public RequirementCardController() {

		this.observableList = FXCollections.observableArrayList();

		model = new RequirementCardModel();
		model.setObservableArray(this.observableList);
		model.addObserver(this);
		setThis$(this);
	}

	public void initializeFXNodes() {

		initChooseTeamComboBox();
		initTableView();

	}

	@FXML
	void startButtonClicked(MouseEvent event) {
		repaint();
	}

	public void repaint() {

		refreshList();
		refrechTeams();
		mainApp.getPrimaryStage().close();
		mainApp.getPrimaryStage().setScene(loginController.getRequirementCardViewScene());
		mainApp.getPrimaryStage().show();
	}

	private void refrechTeams() {

		clearChecks();

		for (String team : model.getTeamsUserIsSubscribed()) {
			checkTeam(team);
		}
	}

	@FXML
	void createNewRqButtonClicked(ActionEvent event) {

		try {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(getClass().getResource("/swt/swl/topcard/view/CreateRQCardView.fxml"));
			ScrollPane rootLayout = (ScrollPane) loader.load();
			((CreateRQCardController) loader.getController()).setData(this.model, this);
			((CreateRQCardController) loader.getController()).initFXNodes();
			Scene scene = new Scene(rootLayout);
			mainApp.getPrimaryStage().setScene(scene);
			mainApp.getPrimaryStage().show();

			event.consume();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@FXML
	void createModuleButtonClicked(ActionEvent event) {
		try {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(getClass().getResource("/swt/swl/topcard/view/CreateModuleView.fxml"));
			Pane rootLayout = (Pane) loader.load();
			((CreateModuleController) loader.getController()).setMainController(this);
			Scene scene = new Scene(rootLayout);
			mainApp.getPrimaryStage().setScene(scene);
			mainApp.getPrimaryStage().show();

			event.consume();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@FXML
	void createNewTeamClicked(ActionEvent event) {

		try {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(getClass().getResource("/swt/swl/topcard/view/CreateTeamView.fxml"));
			Pane rootLayout = (Pane) loader.load();
			((CreateTeamController) loader.getController()).setMainController(this);
			Scene scene = new Scene(rootLayout);
			mainApp.getPrimaryStage().setScene(scene);
			mainApp.getPrimaryStage().show();

			event.consume();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@FXML
	void createNewUserStoryClicked(ActionEvent event) {

		try {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(getClass().getResource("/swt/swl/topcard/view/CreateUserStoryView.fxml"));
			Pane rootLayout = (Pane) loader.load();
			((CreateUserStoryController) loader.getController()).setData(this);
			Scene scene = new Scene(rootLayout);
			mainApp.getPrimaryStage().setScene(scene);
			mainApp.getPrimaryStage().show();

			event.consume();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@FXML
	void searchRqButtonClicked(ActionEvent event) {

		try {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(getClass().getResource("/swt/swl/topcard/view/SearchRQCardView.fxml"));
			ScrollPane rootLayout = (ScrollPane) loader.load();
			((SearchRQCardController) loader.getController()).setData(this.model, this);
			Scene scene = new Scene(rootLayout);
			mainApp.getPrimaryStage().setScene(scene);
			mainApp.getPrimaryStage().show();

			event.consume();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void update(Observable o, Object update) {

		if (update != null) {
			if (update.toString().equals(loginName)) {
				repaint();
				return;
			}
		}
	}

	private void openVoteView(RequirementCardSimple rq) {

		try {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(getClass().getResource("/swt/swl/topcard/view/VoteRQCard.fxml"));
			ScrollPane rootLayout = (ScrollPane) loader.load();

			((VoteRqCardController) loader.getController()).setData(this.model, this, rq);

			Scene scene = new Scene(rootLayout);
			mainApp.getPrimaryStage().setScene(scene);
			mainApp.getPrimaryStage().show();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void openEditView(RequirementCardSimple rq) {

		try {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(getClass().getResource("/swt/swl/topcard/view/EditRqCardView.fxml"));
			ScrollPane rootLayout = (ScrollPane) loader.load();

			((EditRqCardController) loader.getController()).setData(this.model, this, rq);
			((EditRqCardController) loader.getController()).initializeFXNodes();

			Scene scene = new Scene(rootLayout);
			mainApp.getPrimaryStage().setScene(scene);
			mainApp.getPrimaryStage().show();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void initTableView() {
		ObservableList<TableColumn<RequirementCardSimple, ?>> columns = requirementCardsTable.getColumns();

		nameTableColumn = new TableColumn<>("Requirement Cards");
		nameTableColumn.setCellValueFactory(new PropertyValueFactory<RequirementCardSimple, String>("title"));

		ownerTableColumn = new TableColumn<>("Owner");
		ownerTableColumn.setCellValueFactory(new PropertyValueFactory<RequirementCardSimple, String>("ownerName"));

		teamTableColumn = new TableColumn<>("Modules");
		teamTableColumn.setCellValueFactory(new PropertyValueFactory<RequirementCardSimple, String>("modules"));

		columns.clear();

		columns.add(nameTableColumn);
		columns.add(ownerTableColumn);
		columns.add(teamTableColumn);

		refreshList();

		addEventHandlerToTableView();
	}

	private void refreshList() {
		this.model.getRequirements();
		requirementCardsTable.setItems(observableList);
	}

	private void initChooseTeamComboBox() {

		chooseTeamBox = new CheckComboBox<>(model.getTeams());

		menuListHBox.getChildren().add(chooseTeamBox);

		addEventHandlerToChooseTeamBox();
	}

	private void addEventHandlerToTableView() {
		requirementCardsTable.setOnMousePressed(new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent event) {
				if (requirementCardsTable.getSelectionModel().getSelectedItem() == null) {
					return;
				}
				if (event.isPrimaryButtonDown() && event.getClickCount() == 2) {

					RequirementCardSimple item = requirementCardsTable.getSelectionModel().getSelectedItem();

					if (model.checkUserName(item.getOwnerName())) {
						openEditView(item);
					} else {
						openVoteView(item);
					}
				} else {
					if (event.isPrimaryButtonDown() && event.getClickCount() == 1) {

						RequirementCardSimple item = requirementCardsTable.getSelectionModel().getSelectedItem();

						titleResultLabel.setText(item.getTitle());
						modulesResultLabel.setText(item.getModules());
						descriptionResultLabel.setText(item.getDescription());
						rationaleResultLabel.setText(item.getRationale());
						sourceResultLabel.setText(item.getSource());

						userstoriesResultLabel.setText(item.getUserStories());

						supportingMaterialsResultLabel.setText(item.getSupportingMaterials());
						fitCriterionResultLabel.setText(item.getFitCriterion());
						frozenResultLabel.setText(item.getIsFrozen() + "");
					}
				}
			}
		});
	}

	private void addEventHandlerToChooseTeamBox() {

		teamChangedListener = new TeamChangeListener(model, this);

		chooseTeamBox.getCheckModel().getCheckedItems()
				.addListener((ListChangeListener<? super String>) teamChangedListener);
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
		model.setLoginName(loginName);
		this.loginNameLabel.setText(loginName);
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
		return model;
	}

	public void setRqModel(RequirementCardModel rqModel) {
		this.model = rqModel;
	}

	public boolean getCancelTransaction() {
		return cancelTransaction;
	}

	public void setCancelTransaction(boolean cancelTransaction) {
		this.cancelTransaction = cancelTransaction;
	}

	public RequirementCardController getThis$() {
		return this$;
	}

	public void setThis$(RequirementCardController this$) {
		this.this$ = this$;
	}

	public void checkTeam(String team) {

		cancelTransaction = true;
		chooseTeamBox.getCheckModel().check(team);
		cancelTransaction = false;
	}

	public String getFirstTeam() {
		return chooseTeamBox.getCheckModel().getCheckedItems().get(0);
	}

	public void clearChecks() {

		IndexedCheckModel<String> checkModel = chooseTeamBox.getCheckModel();

		for (int i = 0; i < checkModel.getCheckedItems().size(); i++) {
			cancelTransaction = true;
			checkModel.clearCheck(checkModel.getCheckedItems().get(i));
			cancelTransaction = false;
		}
	}
}
