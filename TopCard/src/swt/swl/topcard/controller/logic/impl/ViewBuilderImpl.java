package swt.swl.topcard.controller.logic.impl;

import java.io.IOException;
import java.util.HashMap;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TabPane;
import javafx.scene.layout.Pane;
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
import swt.swl.topcard.controller.logic.ViewBuilder;
import swt.swl.topcard.logic.DAOs.mvc.impl.ControllerDAOImpl;
import swt.swl.topcard.logic.entitiy.RequirementCardSimple;
import swt.swl.topcard.logic.exception.TopCardException;

public class ViewBuilderImpl implements ViewBuilder {

	private FXMLLoader loader;
	private RequirementCardController mainController;

	private HashMap<String, Scene> systemScenes;

	private final String[] scenes = { "Login", "CreateRequirementCard", "SearchRequirementCard", "ShowDiagram",
			"CreateTeam", "CreateTeam", "CreateUserStory" }; // length=7

	public ViewBuilderImpl(RequirementCardController mainController) {

		setMainController(mainController);

		buildSystemScenes();
	}

	private void buildSystemScenes() {

		systemScenes = new HashMap<>();

		for (String view : scenes) {

			systemScenes.put(view, generateScene(view));
			System.out.println("scene added: " + view);
		}
	}

	private Scene generateScene(String view) {

		System.out.println("input: " + view);
		try {

			loader = new FXMLLoader();
			loader.setLocation(getClass().getResource("/swt/swl/topcard/view/" + view + "View.fxml"));

			switch (view) {

			case "RequirementCard":

				loader.load();

				ControllerDAOImpl.controllers.put("RequirementCard", loader.getController());

			case "Login":

				// load content in pane and put pane in scene:

				Scene scene = new Scene((Pane) loader.load());

				LoginController loginController = (LoginController) loader.getController();

				loginController.setData(mainController, scene);
				ControllerDAOImpl.controllers.put("Login", (Controller) loginController);

				return scene;

			case "CreateModule":

				Pane createModule = (Pane) loader.load();

				CreateModuleController moduleController = (CreateModuleController) loader.getController();
				moduleController.setMainController(mainController);
				ControllerDAOImpl.controllers.put("CreateModule", (Controller) moduleController);

				return new Scene(createModule);

			case "CreateTeam":

				Pane createTeam = (Pane) loader.load();

				CreateTeamController teamController = (CreateTeamController) loader.getController();
				teamController.setMainController(mainController);
				ControllerDAOImpl.controllers.put("CreateTeam", (Controller) teamController);

				return new Scene(createTeam);

			case "CreateUserStory":

				Pane createStory = (Pane) loader.load();

				CreateUserStoryController userStoryController = (CreateUserStoryController) loader.getController();
				userStoryController.setMainController(mainController);
				ControllerDAOImpl.controllers.put("CreateUserStory", (Controller) userStoryController);
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
				ControllerDAOImpl.controllers.put("CreateRequirementCard", (Controller) createRqController);

				return new Scene(createRqPane);

			case "SearchRequirementCard":

				ScrollPane searchPane = (ScrollPane) loader.load();

				SearchRequirementCardController searchController = (SearchRequirementCardController) loader
						.getController();
				searchController.setData(mainController.getRqModel(), mainController);
				ControllerDAOImpl.controllers.put("SearchRequirementCard", (Controller) searchController);

				return new Scene(searchPane);

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

		System.out.println(view);
		
		if (systemScenes == null) {
			throw new IllegalAccessError("scenes null");
		}
		if (!systemScenes.containsKey(view)) {
			System.out.println("key '+" + view + "' not present.");
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
				ControllerDAOImpl.controllers.put("EditRequirementCard", (Controller) editController);

				break;

			case "VoteRequirementCard":

				scene = new Scene((ScrollPane) loader.load());

				VoteRequirementCardController voteController = (VoteRequirementCardController) loader.getController();
				voteController.setData(mainController.getRqModel(), mainController, rq);
				ControllerDAOImpl.controllers.put("VoteRequirementCard", (Controller) voteController);

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

				ViewBuilder.changeGUI(scene);

			} catch (IOException e) {
				e.printStackTrace();
			}

			RegistrationController registrationController = (RegistrationController) loader.getController();
			registrationController.setData(loginController, loginController.getModel());
			ControllerDAOImpl.controllers.put("RegistrationController", (Controller) registrationController);

		} else {
			throw new IllegalArgumentException("Invalid value for input parameter 'view'. Given value was: " + view);

		}
	}

	// for show diagram / line chart / pie chart diagram view :

	public void buildView(String view, String loginName) {

		loader = new FXMLLoader();
		loader.setLocation(getClass().getResource("/swt/swl/topcard/view/" + view + "View.fxml"));

		if (loginName.equals("ShowDiagram")) {

			try {
				Scene scene = new Scene((TabPane) loader.load());

				ViewBuilder.changeGUI(scene);

			} catch (IOException e) {
				e.printStackTrace();
			}

			ShowDiagramController diagramController = (ShowDiagramController) loader.getController();
			diagramController.setData(mainController, loginName);
			ControllerDAOImpl.controllers.put("ShowDiagramController", (Controller) diagramController);

		} else {
			throw new IllegalArgumentException("Invalid value for input parameter 'view'. Given value was: " + view);

		}
	}

	public void setMainController(RequirementCardController mainController) {
		this.mainController = mainController;
	}

	// for requirement card view:

	@Override
	public void buildView(String view, String loginName, LoginController loginController) {

		if (view.equals("RequirementCard")) {

			((RequirementCardController) ControllerDAOImpl.controllers.get(view)).setData(loginName, loginController);

		} else {
			throw new IllegalArgumentException(
					"Invalid input value for parameter 'view'. Given value was: " + view + ".");
		}
	}

}
