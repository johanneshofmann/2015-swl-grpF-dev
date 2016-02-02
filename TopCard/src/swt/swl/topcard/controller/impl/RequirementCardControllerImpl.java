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
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
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
import swt.swl.topcard.controller.Controller;
import swt.swl.topcard.controller.LoginController;
import swt.swl.topcard.controller.RequirementCardController;
import swt.swl.topcard.controller.ShowDiagramController;
import swt.swl.topcard.controller.logic.ViewBuilder;
import swt.swl.topcard.controller.logic.ViewBuilderImpl;
import swt.swl.topcard.logic.DAOs.mvc.impl.ControllerDAOImpl;
import swt.swl.topcard.logic.DAOs.mvc.impl.ModelDAOImpl;
import swt.swl.topcard.logic.entitiy.RequirementCardSimple;
import swt.swl.topcard.model._Model;
import swt.swl.topcard.logic.eventHandler.TeamChangeListener;
import swt.swl.topcard.logic.eventHandler.impl.TeamChangeListenerImpl;
import swt.swl.topcard.model.RequirementCardModel;
import swt.swl.topcard.model._impl.RequirementCardModelImpl;

public class RequirementCardControllerImpl implements Observer, Controller, RequirementCardController {

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

	private ViewBuilder viewBuilder;

	public RequirementCardControllerImpl() {

		this.observableList = FXCollections.observableArrayList();

		model = new RequirementCardModelImpl();
		ModelDAOImpl.models.put("RequirementCard", (_Model) model);

		model.setObservableArray(this.observableList);

		registerOnModel();

		setMainController(this);

		viewBuilder = new ViewBuilderImpl(this);
		viewBuilder.setMainController(this);
	}

	public void initializeFXNodes() {
		System.out.println("inti FX Nodes (@RQC.)");

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

		ViewBuilder.changeGUI(loginController.getRequirementCardViewScene());
	}

	private void refrechTeams() {

		clearChecks();

		for (String team : model.getTeamsUserIsSubscribed()) {
			checkTeam(team);
		}
	}

	@FXML
	void createNewRequirementButtonClicked(ActionEvent event) {

		viewBuilder.buildView("CreateRequirementCard");

		event.consume();
	}

	@FXML
	void createModuleButtonClicked(ActionEvent event) {

		viewBuilder.buildView("CreateModule");

		event.consume();
	}

	@FXML
	void createNewTeamClicked(ActionEvent event) {

		viewBuilder.buildView("CreateTeam");

		event.consume();
	}

	@FXML
	void createNewUserStoryClicked(ActionEvent event) {

		viewBuilder.buildView("CreateUserStory");

		event.consume();
	}

	@FXML
	void searchRqButtonClicked(ActionEvent event) {

		viewBuilder.buildView("SearchRequirementCard");

		event.consume();
	}

	private void openVoteView(RequirementCardSimple rq) {

		viewBuilder.buildView("VoteRequirementCard", rq);
	}

	private void openEditView(RequirementCardSimple rq) {

		viewBuilder.buildView("EditRequirementCard", rq);
	}

	@FXML
	void logoutButtonClicked(ActionEvent event) {

		cancel(event);
	}

	@FXML
	void showDiagramButtonClicked(ActionEvent event) {

		viewBuilder.buildView("ShowDiagram");
		ShowDiagramController controller = (ShowDiagramController) ControllerDAOImpl.controllers.get("ShowDiagram");
		controller.setData(this, loginName);
	}

	@Override
	public void update(Observable o, Object bool) {

		if (bool != null) {

			if ((boolean) bool == true || (boolean) bool == false) {

				repaint();
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
	 * @param loginController
	 */
	public void setData(String loginName, LoginController loginController) {

		if (loginName == null || loginName.isEmpty() || loginController == null) {
			new Alert(AlertType.WARNING, "Given arguments are null or empty.");
		}

		this.loginName = loginName;
		this.loginController = loginController;
		model.setLoginName(loginName);
		this.loginNameLabel.setText(loginName);
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

	@Override
	public void setMainController(RequirementCardController requirementCardController) {

		this.this$ = requirementCardController;
	}

	@Override
	public void cancel(ActionEvent event) {

		ViewBuilder.changeGUI(loginController.getLoginScene());

		event.consume();
	}

	@Override
	public void checkEmpty() {
		// not really necessary here, validation takes place in each method for
		// its own..
	}

	@Override
	public void registerOnModel() {
		((Observable) model).addObserver(this);
	}
}
