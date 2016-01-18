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

		rqModel = new RequirementCardModel();
		rqModel.setObservableArray(this.observableList);
		rqModel.addObserver(this);
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
			((VoteRqCardController) loader.getController()).setData(this.rqModel, this, rq);
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
		this.rqModel.getRequirements();
		requirementCardsTable.setItems(observableList);
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

					if (rqModel.checkUserName(item.getOwnerName())) {
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

						// TODO: RQCardController: implement all requirements to
						// make possible:
						// userstoriesResultLabel.setText(item.getUserStories());

						supportingMaterialsResultLabel.setText(item.getSupportingMaterials());
						fitCriterionResultLabel.setText(item.getFitCriterion());
						frozenResultLabel.setText(item.getIsFrozen() + "");
					}
				}
			}
		});
	}

	private void addEventHandlerToChooseTeamBox() {

		chooseTeamBox.getCheckModel().getCheckedItems().addListener(new ListChangeListener<String>() {

			ArrayList<String> teamsFromUser = initComboBoxAndGetTeamsFromUser();

			@Override
			public void onChanged(javafx.collections.ListChangeListener.Change<? extends String> c) {

				ObservableList<String> selectedTeams = chooseTeamBox.getCheckModel().getCheckedItems();

				int selectedSize = selectedTeams.size();
				int actualSize = teamsFromUser.size();

				if (rqModel.userAlreadySubscribed()) {

					// if team was added..
					if (selectedSize > actualSize) {

						String teamStr = null;

						for (String team : selectedTeams) {

							if (!teamsFromUser.contains(team.toString())) {

								rqModel.letUserBeMemberOf(team);
								teamStr = team;
							}
						}
						new Alert(AlertType.INFORMATION, "You are now member of the team " + teamStr + ".")
								.showAndWait();
					}

					// if team was removed ..
					if (selectedSize < actualSize) {
						Alert removeConfirmation = null;
						String teamStr = null;
						for (String team : teamsFromUser) {

							if (!selectedTeams.contains(team.toString())) {
								teamStr = team;

								if (actualSize <= 1) {

									String textRow = "You're about to leave the only team you're joined. Really leave team?";

									removeConfirmation = new Alert(AlertType.CONFIRMATION, textRow);

									removeConfirmation.getButtonTypes().set(0, new ButtonType("Cancel"));
									removeConfirmation.getButtonTypes().set(1, new ButtonType("Leave"));

								} else {
									rqModel.letUserExitTeam(team);
									removeConfirmation = new Alert(AlertType.INFORMATION, "Left team " + team + ".");
								}
							}
						}
						removeConfirmation.showAndWait();
						if (removeConfirmation.getResult().getText().toString().equals("Leave")) {
							rqModel.letUserExitTeam(teamStr);
							new Alert(AlertType.INFORMATION, "Left team " + teamStr + ".").showAndWait();
						} else {
							removeConfirmation.close();
						}
					}
				} else {
					// simply let user enter team..
					rqModel.letUserBeMemberOf(selectedTeams.get(0));
					new Alert(AlertType.INFORMATION, "You are now member of the team " + selectedTeams.get(0) + ".")
							.showAndWait();
				}
			}
		});
	}

	private ArrayList<String> initComboBoxAndGetTeamsFromUser() {

		ArrayList<String> teamsFromUser = new ArrayList<>();

		for (String team : rqModel.getTeamsUserIsSubscribed()) {

			teamsFromUser.add(team);
			chooseTeamBox.getCheckModel().check(team);
		}
		return teamsFromUser;
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
