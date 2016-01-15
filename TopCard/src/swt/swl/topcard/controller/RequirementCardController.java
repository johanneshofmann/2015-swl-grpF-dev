package swt.swl.topcard.controller;

import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

import org.controlsfx.control.CheckComboBox;

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
import swt.swl.topcard.logic.Team;
import swt.swl.topcard.model.RequirementCardModel;

public class RequirementCardController implements Observer {

	private MainApp mainApp;
	private String loginName;
	private RequirementCardModel rqModel;
	private LoginWindowController loginController;
	private RequirementCardController this$;

	@FXML
	private Pane mainWindowPainLeft, mainWindowPainRight;
	@FXML
	private HBox menuListHBox;
	@FXML
	private Button searchRqButton;

	@FXML
	private ImageView startButton;
	@FXML
	private MenuItem createModuleButton, createNewRqButton, createNewTeam;
	@FXML
	private TableView<RequirementCardSimple> requirementCardsTable;

	private TableColumn<RequirementCardSimple, String> ownerTableColumn, nameTableColumn;

	private CheckComboBox<String> chooseTeamBox;
	// all Labels and ResultLabels
	@FXML
	private Label loginNameLabel, titleResultLabel, modulesResultLabel, descriptionResultLabel, rationaleResultLabel,
			sourceResultLabel, userstoriesResultLabel, supportingMaterialsResultLabel, fitCriterionResultLabel,
			frozenResultLabel;

	private ObservableList<RequirementCardSimple> observableList;

	public RequirementCardController() {

		this.observableList = FXCollections.observableArrayList();

		rqModel = new RequirementCardModel();
		rqModel.setObservableArray(this.observableList);
		rqModel.addObserver(this);
	}

	public void initializeFXNodes() {

		initChooseTeamComboBox();
		initTableView();

	}

	public void repaint() {
		mainApp.getPrimaryStage().close();
		mainApp.getPrimaryStage().setScene(loginController.getRequirementCardViewScene());
		mainApp.getPrimaryStage().show();
	}

	@FXML
	void startButtonClicked(MouseEvent event) {
		initializeFXNodes();
		mainApp.getPrimaryStage().close();
		mainApp.getPrimaryStage().setScene(loginController.getRequirementCardViewScene());
		mainApp.getPrimaryStage().show();
	}

	@FXML
	void createNewRqButtonClicked(ActionEvent event) {

		try {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(getClass().getResource("/swt/swl/topcard/view/CreateRQCardView.fxml"));
			ScrollPane rootLayout = (ScrollPane) loader.load();
			((CreateRQCardController) loader.getController()).setData(this.rqModel, this);
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
	void searchRqButtonClicked(ActionEvent event) {

		try {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(getClass().getResource("/swt/swl/topcard/view/SearchRQCardView.fxml"));
			ScrollPane rootLayout = (ScrollPane) loader.load();
			((SearchRQCardController) loader.getController()).setData(this.rqModel, this);
			Scene scene = new Scene(rootLayout);
			mainApp.getPrimaryStage().setScene(scene);
			mainApp.getPrimaryStage().show();

			event.consume();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@FXML
	void toVoteButtonClicked(ActionEvent event) {
		rqModel.getMyOrToVoteRequirements(false);
		nameTableColumn.setText("Requirement Cards to vote");

		event.consume();

	}

	@FXML
	void myRqCardsButtonClicked(ActionEvent event) {
		rqModel.getMyOrToVoteRequirements(true);
		nameTableColumn.setText("My Requirement Cards");

		event.consume();

	}

	@FXML
	void subscribeButtonClicked(ActionEvent event) {
		// TODO: RequirementCardController: implement subscribeButtonClicked()

	}

	/**
	 *
	 */
	@Override
	public void update(Observable o, Object update) {

		if (update != null) {
			if (update.toString().equals(loginName)) {
				repaint();
			}
		}
	}

	private void openVoteView(RequirementCardSimple rq) {
		try {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(getClass().getResource("/swt/swl/topcard/view/VoteRQCard.fxml"));
			ScrollPane rootLayout = (ScrollPane) loader.load();
			((VoteRqCardController) loader.getController()).setData(this.rqModel, this, rq);
			Scene scene = new Scene(rootLayout);
			mainApp.getPrimaryStage().setScene(scene);
			mainApp.getPrimaryStage().show();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 *
	 * @param rqTitle
	 */
	private void openEditView(RequirementCardSimple rq) {
		try {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(getClass().getResource("/swt/swl/topcard/view/EditRqCardView.fxml"));
			ScrollPane rootLayout = (ScrollPane) loader.load();
			((EditRqCardController) loader.getController()).setData(this.rqModel, this, rq);
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

		// TODO: RequirementCardController: owner is only visible by clicking
		// exactly on the owner table column.

		columns.clear();
		requirementCardsTable.setEditable(true);

		columns.add(nameTableColumn);
		columns.add(ownerTableColumn);

		this.rqModel.getRequirements();
		requirementCardsTable.setItems(observableList);

		addEventHandlerToTableView();
	}

	private void initChooseTeamComboBox() {

		ObservableList<String> teams = rqModel.getTeams();

		chooseTeamBox.getItems().clear();

		for (String team : teams) {

			chooseTeamBox.getItems().add(team);
		}
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
					item = rqModel.getOverviewDataFromSelectedRq(item);

					if (rqModel.checkUserName(item.getOwnerName())) {
						openEditView(item);
					} else {
						openVoteView(item);
					}
				} else {
					if (event.isPrimaryButtonDown() && event.getClickCount() == 1) {
						RequirementCardSimple item = requirementCardsTable.getSelectionModel().getSelectedItem();
						item = rqModel.getOverviewDataFromSelectedRq(item);

						titleResultLabel.setText(item.getTitle());
						descriptionResultLabel.setText(item.getDescription());
						rationaleResultLabel.setText(item.getRationale());
						sourceResultLabel.setText(item.getSource());
						// TODO: RQCardController: implement all requirements
						// for UserStories part
						// of the application
						userstoriesResultLabel.setText("");
						supportingMaterialsResultLabel.setText(item.getSupportingMaterials());
						fitCriterionResultLabel.setText(item.getFitCriterion());
						frozenResultLabel.setText(item.getIsFrozen() + "");
					}
				}
			}
		});
	}

	private void addEventHandlerToChooseTeamBox() {

		chooseTeamBox = new CheckComboBox<>();

		ArrayList<String> teamsFromUser = new ArrayList<>();

		for (Team team : rqModel.getTeamsUserIsSubscribed()) {

			teamsFromUser.add(team.getName());
			chooseTeamBox.getCheckModel().check(team.getName());
		}

		chooseTeamBox.getCheckModel().getCheckedItems().addListener(new ListChangeListener<String>() {

			@Override
			public void onChanged(javafx.collections.ListChangeListener.Change<? extends String> c) {

				ObservableList<String> selectedTeams = chooseTeamBox.getCheckModel().getCheckedItems();

				if (rqModel.userAlreadySubscribed()) {

					// TODO: @Steve: see wheather and if yes what team has
					// changed.

					ArrayList<String> addedTeams = new ArrayList<>();
					ArrayList<String> removedTeams = new ArrayList<>();

					int selectedSize = selectedTeams.size();
					int actualSize = teamsFromUser.size();

					int maxSize = Math.max(selectedSize, actualSize);

					for (int i = 0; i < maxSize; i++) {

						if (i < selectedSize) {

						}

						// if (!teamsFromUser.contains(team)) {
						// addedTeams.add(team);
						// }
					}
					for (String team : selectedTeams) {

						if (!teamsFromUser.contains(team)) {
							addedTeams.add(team);
						}
					}
					openSubscribeConfirmationDialogueView(this$, addedTeams);
				}
			}

			private boolean teamAdded(ArrayList<String> teamsFromUser, ObservableList<String> selectedTeams) {

				if (teamsFromUser.size() < selectedTeams.size()) {

					return true;
				} else {
					return false;
				}
			}

			private boolean teamRemoved(ArrayList<String> teamsFromUser, ObservableList<String> selectedTeams) {

				if (teamsFromUser.size() > selectedTeams.size()) {

					return true;
				}
				return false;
			}

			private void openSubscribeConfirmationDialogueView(RequirementCardController mainController,
					ArrayList<String> newTeams) {

				try {
					FXMLLoader loader = new FXMLLoader();
					loader.setLocation(getClass().getResource("/swt/swl/topcard/view/EditRqCardView.fxml"));
					Pane rootLayout = (Pane) loader.load();
					((SubscribeConfirmationDialogueController) loader.getController()).setData(rqModel, mainController,
							newTeams);
					Scene scene = new Scene(rootLayout);
					mainApp.getPrimaryStage().setScene(scene);
					mainApp.getPrimaryStage().show();

				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
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
		return rqModel;
	}

	public void setRqModel(RequirementCardModel rqModel) {
		this.rqModel = rqModel;
	}

	public RequirementCardController getThis$() {
		return this$;
	}

	public void setThis$(RequirementCardController this$) {
		this.this$ = this$;
	}

}
