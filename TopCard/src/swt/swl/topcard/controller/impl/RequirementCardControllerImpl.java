package swt.swl.topcard.controller.impl;

import java.util.ArrayList;
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
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TabPane;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import swt.swl.topcard.MainApp;
import swt.swl.topcard.controller.Controller;
import swt.swl.topcard.controller.CreateModuleController;
import swt.swl.topcard.controller.CreateRequirementCardController;
import swt.swl.topcard.controller.CreateTeamController;
import swt.swl.topcard.controller.CreateUserStoryController;
import swt.swl.topcard.controller.EditRequirementCardController;
import swt.swl.topcard.controller.LoginController;
import swt.swl.topcard.controller.RequirementCardController;
import swt.swl.topcard.controller.SearchRequirementCardController;
import swt.swl.topcard.controller.ShowDiagramController;
import swt.swl.topcard.controller.VoteRequirementCardController;
import swt.swl.topcard.logic.RequirementCardSimple;
import swt.swl.topcard.logic.eventHandler.TeamChangeListener;
import swt.swl.topcard.logic.eventHandler.impl.TeamChangeListenerImpl;
import swt.swl.topcard.model.RequirementCardModel;
import swt.swl.topcard.model.impl.RequirementCardModelImpl;

public class RequirementCardControllerImpl implements Observer, Controller, RequirementCardController {

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
	private Button searchRqButton, logoutButton, helpButton;

	@FXML
	private ImageView startButton;
	@FXML
	private MenuItem createModuleButton, createNewRqButton, createNewTeam, createNewUserStory;
	@FXML
	private TableView<RequirementCardSimple> requirementCardsTable;

	private TableColumn<RequirementCardSimple, String> nameTableColumn, ownerTableColumn, modulesTableColumn;
	private ArrayList<String> frozenRequirementCards;

	private CheckComboBox<String> chooseTeamBox;
	// all Labels and ResultLabels
	@FXML
	private Label loginNameLabel;

	@FXML
	private TextArea titleTextArea, modulesTextArea, descriptionTextArea, rationaleTextArea, sourceTextArea,
			userStoriesTextArea, supportingMaterialsTextArea, fitCriterionTextArea;

	private ObservableList<RequirementCardSimple> observableList;

	private FXMLLoader loader;

	public RequirementCardControllerImpl() {

		this.observableList = FXCollections.observableArrayList();

		model = new RequirementCardModelImpl();
		model.setObservableArray(this.observableList);
		((Observable) model).addObserver(this);
		setMainController(this);
	}

	public void initializeFXNodes() {

		initChooseTeamComboBox();
		initTableView();
		addEventHandlerToTableView();
	}

	@FXML
	void startButtonClicked(MouseEvent event) {
		repaint();
	}

	@FXML
	void helpButtonClicked(ActionEvent event) {
		/*
		 * String pfad =
		 * "/afs/swt.wiai.uni-bamberg.de/users/home.swt-040350/GIT Johannes/2015-swl-grpF-dev/TopCard/src/swt/swl/topcard/view/mozilla.pdf"
		 * ; // "swt/swl/topcard/view/mozilla.pdf"; try {
		 * Desktop.getDesktop().open(new File(pfad)); } catch (IOException e) {
		 * e.printStackTrace(); }
		 */
	}

	public void repaint() {

		initTableView();
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

			case "ShowDiagram":

				TabPane diagramPane = (TabPane) loader.load();

				((ShowDiagramController) loader.getController()).setData(this, loginName);

				scene = new Scene(diagramPane);

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
		cancel(event);
	}

	@FXML
	void showDiagramButtonClicked(ActionEvent event) {
		xButtonClicked(event, "ShowDiagram", null);
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
		frozenRequirementCards = new ArrayList<>();

		nameTableColumn.setCellValueFactory(new PropertyValueFactory<RequirementCardSimple, String>("title"));
		nameTableColumn.setCellFactory(column -> {
			return new TableCell<RequirementCardSimple, String>() {
				@Override
				protected void updateItem(String item, boolean empty) {

					super.updateItem(item, empty);

					if (item == null || empty) {

						setText(null);
						setStyle("");

					} else {
						// color all frozen requirement cards:
						setText(item);
						if (model.isFrozen(item)) {

							setTextFill(Color.SKYBLUE);
							frozenRequirementCards.add(item);
						}
					}
				}
			};
		});

		ownerTableColumn = new TableColumn<>("Owner");
		ownerTableColumn.setCellValueFactory(new PropertyValueFactory<RequirementCardSimple, String>("ownerName"));

		modulesTableColumn = new TableColumn<>("Modules");
		modulesTableColumn.setCellValueFactory(new PropertyValueFactory<RequirementCardSimple, String>("modules"));

		columns.clear();

		columns.add(nameTableColumn);
		columns.add(ownerTableColumn);
		columns.add(modulesTableColumn);

		refreshList();
	}

	private void refreshList() {

		model.updateRequirementsList();

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

					if (model.loginNameEqualsOwnerName(item.getOwnerName())) {
						openEditView(item);
					} else {
						if (frozenRequirementCards.contains((String) item.getTitle())) {
							new Alert(AlertType.INFORMATION,
									"As this requirement card is frozen,it is not voteable at the moment.")
											.showAndWait();
						} else if (model.userAlreadyVoted(item.getID())) {
							new Alert(AlertType.INFORMATION,
									"You already submitted your vote on that requirement card.").showAndWait();
						} else {
							openVoteView(item);
						}
					}
				} else {
					if (event.isPrimaryButtonDown() && event.getClickCount() == 1) {

						titleTextArea.setText(item.getTitle());
						modulesTextArea.setText(item.getModules());
						descriptionTextArea.setText(item.getDescription());
						rationaleTextArea.setText(item.getRationale());
						sourceTextArea.setText(item.getSource());

						userStoriesTextArea.setText(item.getUserStories());

						supportingMaterialsTextArea.setText(item.getSupportingMaterials());
						fitCriterionTextArea.setText(item.getFitCriterion());
					}
				}
			}
		});
	}

	@SuppressWarnings("unchecked")
	private void addEventHandlerToChooseTeamBox() {

		teamChangedListener = new TeamChangeListenerImpl(model, this);

		chooseTeamBox.getCheckModel().getCheckedItems().addListener((ListChangeListener<String>) teamChangedListener);
	}

	/**
	 *
	 * @param loginName
	 * @param mainApp
	 * @param loginWindowController
	 */
	public void setData(String loginName, MainApp mainApp, LoginController loginWindowController) {

		if (loginName == null || loginName.isEmpty() || mainApp == null || loginWindowController == null) {
			new Alert(AlertType.WARNING, "Given arguments are null or empty.");
		}

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

	@Override
	public void setMainController(RequirementCardController requirementCardController) {
		this.this$ = requirementCardController;
	}

	@Override
	public void cancel(ActionEvent event) {

		mainApp.getPrimaryStage().close();
		mainApp.getPrimaryStage().setScene(loginController.getLoginScene());
		mainApp.getPrimaryStage().show();
	}

	@Override
	public void checkEmpty() {
		// not really necessary here, validation takes place in each method for
		// its own..
	}
}