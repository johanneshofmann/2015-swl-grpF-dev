package swt.swl.topcard.controller.logic;

import java.io.IOException;
import java.util.HashMap;

import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TabPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import swt.swl.topcard.controller.Controller;
import swt.swl.topcard.controller.CreateModuleController;
import swt.swl.topcard.controller.CreateRequirementCardController;
import swt.swl.topcard.controller.CreateTeamController;
import swt.swl.topcard.controller.CreateUserStoryController;
import swt.swl.topcard.controller.EditRequirementCardController;
import swt.swl.topcard.controller.LoginController;
import swt.swl.topcard.controller.RegistrationController;
import swt.swl.topcard.controller.RequirementCardController;
import swt.swl.topcard.controller.SearchRequirementCardController;
import swt.swl.topcard.controller.ShowDiagramController;
import swt.swl.topcard.controller.VoteRequirementCardController;
import swt.swl.topcard.logic.DAOs.mvc.impl.ControllerDAOImpl;
import swt.swl.topcard.logic.entitiy.RequirementCardSimple;
import swt.swl.topcard.logic.exception.TopCardException;

public class ViewBuilderImpl implements ViewBuilder {

	protected static Stage primaryStage;

	private FXMLLoader loader;
	private RequirementCardController mainController;

	private HashMap<String, Scene> systemScenes = new HashMap<>();

	private final String[] scenes = { "Login", "CreateRequirementCard", "SearchRequirementCard", "ShowDiagram",
			"CreateModule", "CreateTeam", "CreateUserStory" }; // length=7

	public ViewBuilderImpl(RequirementCardController mainController) {

		setMainController(mainController);

		preLoadScenes();
	}

	public ViewBuilderImpl() {
	}

	static {
		primaryStage = new Stage();
		primaryStage.setTitle("TopCard");
		setOnCloseRequest();
	}

	public void preLoadScenes() {

		for (String view : scenes) {

			systemScenes.put(view, generateScene(view));
			System.err.println("scene added: " + view);
		}
	}

	/**
	 * 
	 */
	private static void setOnCloseRequest() {

		primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>() {

			@Override
			public void handle(WindowEvent event) {

				Alert exitConfirmation = new Alert(AlertType.CONFIRMATION, "Exit programm?");
				exitConfirmation.showAndWait();
				ButtonType choice = exitConfirmation.getResult();
				if (choice == ButtonType.OK) {
					Platform.exit();
				} else {
					event.consume();
					primaryStage.show();
				}
			}
		});
	}

	// for requirement card view:
	@Override
	public void buildView(String view, String loginName, LoginController loginController) {

		if (view.equals("RequirementCard")) {

			// instanciate loader
			loader = new FXMLLoader();

			// set location
			loader.setLocation(getClass().getResource("/swt/swl/topcard/view/" + view + "View.fxml"));

			try {

				// load RequirementCardView & put it into systemScenes
				systemScenes.put(view, new Scene((Pane) loader.load()));

				ControllerDAOImpl.controllers.put(view, loader.getController());

				((RequirementCardController) ControllerDAOImpl.controllers.get(view)).initializeFXNodes();

				// put controller in controllerDAO
				ControllerDAOImpl.controllers.put(view, loader.getController());

				this.mainController = loader.getController();

			} catch (IOException e) {
				e.printStackTrace();
			}

		} else {
			// thow exception, if invalid view parameter
			throw new IllegalArgumentException(
					"Invalid input value for parameter 'view'. Given value was: " + view + ".");
		}
	}

	private Scene generateScene(String view) {

		System.out.println("input: " + view);

		try {

			loader = new FXMLLoader();

			loader.setLocation(getClass().getResource("/swt/swl/topcard/view/" + view + "View.fxml"));

			switch (view) {

			case "RequirementCard":

				Scene scene = loader.load();

				ControllerDAOImpl.controllers.put(view, loader.getController());

				return scene;

			case "Login":

				// load content in pane and put pane in scene:

				Scene loginScene = new Scene((Pane) loader.load());

				LoginController loginController = (LoginController) loader.getController();

				loginController.setData(mainController, loginScene);

				ControllerDAOImpl.controllers.put(view, (Controller) loginController);

				return loginScene;

			case "CreateModule":

				Pane createModule = (Pane) loader.load();

				CreateModuleController moduleController = (CreateModuleController) loader.getController();

				moduleController.setMainController(mainController);

				ControllerDAOImpl.controllers.put(view, (Controller) moduleController);

				return new Scene(createModule);

			case "CreateTeam":

				Pane createTeam = (Pane) loader.load();

				CreateTeamController teamController = (CreateTeamController) loader.getController();

				teamController.setMainController(mainController);

				ControllerDAOImpl.controllers.put(view, (Controller) teamController);

				return new Scene(createTeam);

			case "CreateUserStory":

				Pane createStory = (Pane) loader.load();

				CreateUserStoryController userStoryController = (CreateUserStoryController) loader.getController();

				userStoryController.setMainController(mainController);

				ControllerDAOImpl.controllers.put(view, (Controller) userStoryController);

				return new Scene(createStory);

			case "CreateRequirementCard":

				ScrollPane createRqPane = (ScrollPane) loader.load();

				CreateRequirementCardController createRqController = (CreateRequirementCardController) loader
						.getController();

				if (mainController == null) {

					throw new TopCardException("main controller null");
				}

				createRqController.setData(mainController.getRqModel(), mainController);

				createRqController.initFXNodes();

				ControllerDAOImpl.controllers.put(view, (Controller) createRqController);

				return new Scene(createRqPane);

			case "SearchRequirementCard":

				ScrollPane searchPane = (ScrollPane) loader.load();

				SearchRequirementCardController searchController = (SearchRequirementCardController) loader
						.getController();

				searchController.setData(mainController.getRqModel(), mainController);

				ControllerDAOImpl.controllers.put(view, (Controller) searchController);

				return new Scene(searchPane);

			case "ShowDiagram":

				return buildView(view, "");

			}
		} catch (Exception e) {

			System.err.println(e.getClass().getName());

			System.err.println(
					"\r\r---------------AN EXCEPTION OCCURED DURING THIS PROCESS--------------\r\r----------------------PRINTING STACK TRACE-------------------------- \r \r");
			e.printStackTrace();

			System.err.println(
					"\r\r----------------------------END OF EXCEPTION STACKTRACE----------------------------\r\r");
		}

		throw new IllegalArgumentException("Invalid value for input parameter 'view'. Given value was: " + view + ".");
	}

	// simply return one of the pre-loaded views
	public void buildView(String view) {

		System.err.println("fetching scene '" + view + "Scene'.");

		if (systemScenes == null) {
			throw new IllegalAccessError("scenes null");
		}
		if (!systemScenes.containsKey(view)) {
			System.err.println("key '" + view + "' not present.");
		}

		Scene scene = systemScenes.get(view);
		ViewBuilder.changeGUI(scene);
	}

	// Views that need to be instanciated dinymically (as some fields are filled
	// with Data from Database by initializing..)
	public void buildView(String view, RequirementCardSimple rq) {

		try {

			loader = new FXMLLoader();
			loader.setLocation(getClass().getResource("/swt/swl/topcard/view/" + view + "View.fxml"));

			Scene scene = null;

			switch (view) {

			case "EditRequirementCard":

				scene = new Scene((ScrollPane) loader.load());

				EditRequirementCardController editController = (EditRequirementCardController) loader.getController();
				editController.setData(mainController.getRqModel(), mainController, rq);
				editController.initializeFXNodes();
				ControllerDAOImpl.controllers.put(view, (Controller) editController);

				break;

			case "VoteRequirementCard":

				scene = new Scene((ScrollPane) loader.load());

				VoteRequirementCardController voteController = (VoteRequirementCardController) loader.getController();
				voteController.setData(mainController.getRqModel(), mainController, rq);
				ControllerDAOImpl.controllers.put(view, (Controller) voteController);

				break;

			default:
				throw new IllegalArgumentException(
						"Invalid value for input parameter 'view'. Given value was: " + view);
			}

			ViewBuilder.changeGUI(scene);

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	// for registration view:
	public void buildView(String view, LoginController loginController) {

		loader = new FXMLLoader();
		loader.setLocation(getClass().getResource("/swt/swl/topcard/view/" + view + "View.fxml"));

		if (view.equals("Registration")) {

			try {

				Scene scene = new Scene((Pane) loader.load());
				systemScenes.put(view, scene);
				ViewBuilder.changeGUI(scene);

			} catch (IOException e) {
				e.printStackTrace();
			}

			RegistrationController registrationController = (RegistrationController) loader.getController();
			registrationController.setData(loginController, loginController.getModel());
			ControllerDAOImpl.controllers.put(view, (Controller) registrationController);

		} else {
			throw new IllegalArgumentException("Invalid value for input parameter 'view'. Given value was: " + view);

		}
	}

	// for show diagram / line chart / pie chart diagram view :
	public Scene buildView(String view, String loginName) {

		loader = new FXMLLoader();

		loader.setLocation(getClass().getResource("/swt/swl/topcard/view/" + view + "View.fxml"));

		Scene scene = null;

		if (view.equals("ShowDiagram")) {

			try {

				scene = new Scene((TabPane) loader.load());

			} catch (IOException e) {

				e.printStackTrace();
			}

			ShowDiagramController diagramController = (ShowDiagramController) loader.getController();

			System.out.println("setting loginName to null in diagramController..");
			diagramController.setData(mainController, null);

			ControllerDAOImpl.controllers.put(view, (Controller) diagramController);

		} else {

			throw new IllegalArgumentException("Invalid value for input parameter 'view'. Given value was: " + view);
		}

		return scene;
	}

	public void setMainController(RequirementCardController mainController) {
		this.mainController = mainController;
	}

}
