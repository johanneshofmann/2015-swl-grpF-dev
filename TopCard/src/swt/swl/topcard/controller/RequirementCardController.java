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
import swt.swl.topcard.logic.eventHandler.TeamChangeListener;
import swt.swl.topcard.model.RequirementCardModel;

public class RequirementCardController implements Observer {

	private MainApp mainApp;
	private String loginName;
	private RequirementCardModel model;
	private LoginController loginController;
	private RequirementCardController this$;
	private TeamChangeListener teamChangedListener;
	private boolean cancelTransaction;

	@FXML
	private Pane mainWindowPainLeft, mainWindowPainRight;
	@FXML
	private HBox menuListHBox;
	@FXML
	private Button searchRqButton, logoutButton;

	@FXML
	private ImageView startButton;
	@FXML
	private MenuItem createModuleButton, createNewRqButton, createNewTeam, createNewUserStory;
	@FXML
	private TableView<RequirementCardSimple> requirementCardsTable;

	private TableColumn<RequirementCardSimple, String> nameTableColumn, ownerTableColumn, modulesTableColumn;

	private CheckComboBox<String> chooseTeamBox;
	// all Labels and ResultLabels
	@FXML
	private Label loginNameLabel, titleResultLabel, modulesResultLabel, descriptionResultLabel, rationaleResultLabel,
			sourceResultLabel, userstoriesResultLabel, supportingMaterialsResultLabel, fitCriterionResultLabel,
			frozenResultLabel;

	private ObservableList<RequirementCardSimple> observableList;

	private FXMLLoader loader;

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
	void createNewRequirementButtonClicked(ActionEvent event) {

		xButtonClicked(event, "CreateRequirementCard", null);
	}

	@FXML
	void createModuleButtonClicked(ActionEvent event) {
		xButtonClicked(event, "CreateModule", null);
	}

	@FXML
	void createNewTeamClicked(ActionEvent event) {

		xButtonClicked(event, "CreateTeam", null);
	}

	@FXML
	void createNewUserStoryClicked(ActionEvent event) {

		xButtonClicked(event, "CreateUserStory", null);
	}

	@FXML
	void searchRqButtonClicked(ActionEvent event) {

		xButtonClicked(event, "SearchRequirementCard", null);
	}

	private void openVoteView(RequirementCardSimple rq) {

		xButtonClicked(new ActionEvent(), "VoteRequirementCard", rq);
	}

	private void openEditView(RequirementCardSimple rq) {

		xButtonClicked(new ActionEvent(), "EditRequirementCard", rq);
	}

	private void xButtonClicked(ActionEvent event, String operation, RequirementCardSimple rq) {

		try {
			loader = new FXMLLoader();
			loader.setLocation(getClass().getResource("/swt/swl/topcard/view/" + operation + "View.fxml"));

			Scene scene = null;

			switch (operation) {

			case "CreateModule":

				Pane createModuleView = (Pane) loader.load();
				((CreateModuleController) loader.getController()).setMainController(this);

				scene = new Scene(createModuleView);

				break;

			case "CreateTeam":

				Pane createTeamView = (Pane) loader.load();
				((CreateTeamController) loader.getController()).setMainController(this);

				scene = new Scene(createTeamView);

				break;

			case "CreateUserStory":

				Pane createUserStoryView = (Pane) loader.load();
				((CreateUserStoryController) loader.getController()).setMainController(this);

				scene = new Scene(createUserStoryView);

				break;

			case "CreateRequirementCard":

				ScrollPane createRequirementCardView = (ScrollPane) loader.load();
				((CreateRequirementCardController) loader.getController()).setData(this.model, this);
				((CreateRequirementCardController) loader.getController()).initFXNodes();

				scene = new Scene(createRequirementCardView);

				break;

			case "SearchRequirementCard":

				ScrollPane searchRequirementCardView = (ScrollPane) loader.load();
				((SearchRequirementCardController) loader.getController()).setData(this.model, this);

				scene = new Scene(searchRequirementCardView);

				break;

			case "EditRequirementCard":

				ScrollPane editRequirementCardView = (ScrollPane) loader.load();

				((EditRequirementCardController) loader.getController()).setData(this.model, this, rq);
				((EditRequirementCardController) loader.getController()).initializeFXNodes();

				scene = new Scene(editRequirementCardView);

				break;

			case "VoteRequirementCard":

				ScrollPane voteRequirementCardView = (ScrollPane) loader.load();

				((VoteRequirementCardController) loader.getController()).setData(this.model, this, rq);

				scene = new Scene(voteRequirementCardView);

				break;
			}

			mainApp.getPrimaryStage().setScene(scene);
			mainApp.getPrimaryStage().show();

			event.consume();

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	@FXML
	void logoutButtonClicked(ActionEvent event) {

		mainApp.getPrimaryStage().close();
		mainApp.getPrimaryStage().setScene(loginController.getLoginScene());
		mainApp.getPrimaryStage().show();
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

	private void initTableView() {
		ObservableList<TableColumn<RequirementCardSimple, ?>> columns = requirementCardsTable.getColumns();

		nameTableColumn = new TableColumn<>("Requirement Cards");
		nameTableColumn.setCellValueFactory(new PropertyValueFactory<RequirementCardSimple, String>("title"));

		ownerTableColumn = new TableColumn<>("Owner");
		ownerTableColumn.setCellValueFactory(new PropertyValueFactory<RequirementCardSimple, String>("ownerName"));

		modulesTableColumn = new TableColumn<>("Modules");
		modulesTableColumn.setCellValueFactory(new PropertyValueFactory<RequirementCardSimple, String>("modules"));

		columns.clear();

		columns.add(nameTableColumn);
		columns.add(ownerTableColumn);
		columns.add(modulesTableColumn);

		refreshList();

		addEventHandlerToTableView();
	}

	private void refreshList() {
		this.model.updateRequirementsList();
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

				RequirementCardSimple item = requirementCardsTable.getSelectionModel().getSelectedItem();

				if (event.isPrimaryButtonDown() && event.getClickCount() == 2) {

					if (model.checkUserName(item.getOwnerName())) {
						openEditView(item);
					} else {
						openVoteView(item);
					}
				} else {
					if (event.isPrimaryButtonDown() && event.getClickCount() == 1) {

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
	public void setData(String loginName, MainApp mainApp, LoginController loginWindowController) {

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
