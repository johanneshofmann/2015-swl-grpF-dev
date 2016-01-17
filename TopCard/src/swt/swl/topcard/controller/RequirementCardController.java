package swt.swl.topcard.controller;

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
import javafx.scene.control.ButtonType;
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
		setThis$(this);
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

		columns.clear();
		requirementCardsTable.setEditable(true);

		columns.add(nameTableColumn);
		columns.add(ownerTableColumn);

		this.rqModel.getRequirements();
		requirementCardsTable.setItems(observableList);

		addEventHandlerToTableView();
	}

	private void initChooseTeamComboBox() {

		chooseTeamBox = new CheckComboBox<>();

		ObservableList<String> teams = rqModel.getTeams();

		chooseTeamBox.getItems().clear();

		for (String team : teams) {

			chooseTeamBox.getItems().add(team);
		}
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

		ArrayList<String> teamsFromUser = new ArrayList<>();

		for (String team : rqModel.getTeamsUserIsSubscribed()) {

			teamsFromUser.add(team);
			chooseTeamBox.getCheckModel().check(team);
		}

		chooseTeamBox.getCheckModel().getCheckedItems().addListener(new ListChangeListener<String>() {

			@Override
			public void onChanged(javafx.collections.ListChangeListener.Change<? extends String> c) {

				ObservableList<String> selectedTeams = chooseTeamBox.getCheckModel().getCheckedItems();

				int selectedSize = selectedTeams.size();
				int actualSize = teamsFromUser.size();

				if (rqModel.userAlreadySubscribed()) {

					// if team was added..
					if (selectedSize > actualSize) {

						for (String team : selectedTeams) {

							if (!teamsFromUser.contains(team.toString())) {

								openConfirmationAlert(team);
							}
						}
					}

					// if team was removed ..
					if (selectedSize < actualSize) {

						for (String team : teamsFromUser) {

							if (!selectedTeams.contains(team.toString())) {

								String textRow1, textRow2;

								if (actualSize <= 1) {

									textRow1 = "You're about to leave the only team you're joined.";
									textRow2 = "Really leave team? ";

								} else {

									textRow1 = "You're about to leave team " + team + ", ";
									textRow2 = "Really leave team? ";
								}

								String[] leftButtonConfig = { "true", "Leave" };
								String[] rightButtonConfig = { "false", "-not displayed-" };

								openSubscribeConfirmationDialogueView(this$, team, textRow1, textRow2, leftButtonConfig,
										rightButtonConfig);
							}
						}
					}
				} else {
					// simply let user enter team..
					rqModel.letUserBeMemberOf(selectedTeams.get(0));
					new Alert(AlertType.INFORMATION, "You are now member of the team " + selectedTeams.get(0) + ".")
							.showAndWait();
				}
			}

			private void openConfirmationAlert(String team) {

				Alert confirmationAlert = new Alert(AlertType.CONFIRMATION,
						"You've already subscribed on a developer team, /t subscribe on new team AND your old team(s) OR only on the new one ? ");

				confirmationAlert.getButtonTypes().set(0, new ButtonType("Cancel"));
				confirmationAlert.getButtonTypes().set(1, new ButtonType("On all"));
				confirmationAlert.getButtonTypes().add(new ButtonType("Only on " + team));

				confirmationAlert.showAndWait();

				if (confirmationAlert.getResult().getText().equals("Cancel")) {

					restoreChangedTeam(team, true);

					confirmationAlert.close();

				} else if (confirmationAlert.getResult().getText().equals("On all")) {

					rqModel.letUserBeMemberOf(team);

					confirmationAlert.close();

					new Alert(AlertType.INFORMATION, "On all teams subscribed.").showAndWait();

				} else if (confirmationAlert.getResult().getText().equals("Only on " + team)) {

					chooseTeamBox.getCheckModel().clearChecks();
					rqModel.letUserExitAllTeams();

					restoreChangedTeam(team, false);
					rqModel.letUserBeMemberOf(team);

					confirmationAlert.close();

					new Alert(AlertType.INFORMATION, "You exited all teams but the one you have chosen.").showAndWait();
				}
				// String textRow1 = "You've already subscribed
				// on a developer team, ";
				// String textRow2 = "subscribe on new team AND
				// your old team(s) OR only on the new one ? ";
				//
				// String[] leftButtonConfig = { "true", "On
				// all" };
				// String[] rightButtonConfig = { "true", "Only
				// on " + team };
				//
				// openSubscribeConfirmationDialogueView(this$,
				// team, textRow1, textRow2, leftButtonConfig,
				// rightButtonConfig);

			}

			private void openSubscribeConfirmationDialogueView(RequirementCardController mainController,
					String changedTeam, String textRow1, String textRow2, String[] leftButtonConfig,
					String[] rightButtonConfig) {

				try {
					FXMLLoader loader = new FXMLLoader();
					loader.setLocation(
							getClass().getResource("/swt/swl/topcard/view/SubscribeConfirmationDialogueView.fxml"));
					Pane rootLayout = (Pane) loader.load();
					((SubscribeConfirmationDialogueController) loader.getController()).setData(rqModel, mainController,
							changedTeam, textRow1, textRow2, leftButtonConfig, rightButtonConfig);
					Scene scene = new Scene(rootLayout);
					mainApp.getPrimaryStage().setScene(scene);
					mainApp.getPrimaryStage().show();

				} catch (Exception e) {
					e.printStackTrace();
				}
			}

		});
	}

	public void restoreChangedTeam(String changedTeam, boolean wasAdded) {

		IndexedCheckModel<String> checkModel = chooseTeamBox.getCheckModel();
		ObservableList<String> selectedTeams;

		if (wasAdded) {

			checkModel.clearCheck(changedTeam);

			// @Test:
			selectedTeams = checkModel.getCheckedItems();

			if (selectedTeams.contains(changedTeam)) {

				throw new IllegalStateException("Selected teams should not contain changed team! ");
			}

		} else {

			checkModel.check(changedTeam);

			// @Test:
			selectedTeams = checkModel.getCheckedItems();

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
