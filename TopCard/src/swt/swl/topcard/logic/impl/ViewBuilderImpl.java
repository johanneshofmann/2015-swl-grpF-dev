package swt.swl.topcard.logic.impl;

import java.io.IOException;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TabPane;
import javafx.scene.layout.Pane;
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
import swt.swl.topcard.logic.ViewBuilder;

public class ViewBuilderImpl implements ViewBuilder {

	private FXMLLoader loader;
	private RequirementCardController mainController;

	public void buildView(String view) {

		try {

			loader = new FXMLLoader();
			loader.setLocation(getClass().getResource("/swt/swl/topcard/view/" + view + "View.fxml"));

			Scene scene = null;

			switch (view) {

			case "Login":

				// load content in pane and put pane in scene:

				scene = new Scene((Pane) loader.load());

				((LoginController) loader.getController()).setData(mainController, scene);

				break;

			case "CreateModule":

				scene = new Scene((Pane) loader.load());
				((CreateModuleController) loader.getController()).setMainController(mainController);

				break;

			case "CreateTeam":

				scene = new Scene((Pane) loader.load());
				((CreateTeamController) loader.getController()).setMainController(mainController);

				break;

			case "CreateUserStory":

				scene = new Scene((Pane) loader.load());
				((CreateUserStoryController) loader.getController()).setMainController(mainController);

				break;

			case "CreateRequirementCard":

				scene = new Scene((ScrollPane) loader.load());
				((CreateRequirementCardController) loader.getController()).setData(mainController.getRqModel(),
						mainController);
				((CreateRequirementCardController) loader.getController()).initFXNodes();

				break;

			case "SearchRequirementCard":

				scene = new Scene((ScrollPane) loader.load());
				((SearchRequirementCardController) loader.getController()).setData(mainController.getRqModel(),
						mainController);

				break;

			default:
				throw new IllegalArgumentException(
						"Invalid value for input parameter 'view'. Given value was: " + view);

			}

			ViewBuilder.refreshView(scene);

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public void buildView(String view, RequirementCardSimple rq) {

		try {

			loader = new FXMLLoader();
			loader.setLocation(getClass().getResource("/swt/swl/topcard/view/" + view + "View.fxml"));

			Scene scene = null;

			switch (view) {

			case "EditRequirementCard":

				scene = new Scene((ScrollPane) loader.load());

				((EditRequirementCardController) loader.getController()).setData(mainController.getRqModel(),
						mainController, rq);
				((EditRequirementCardController) loader.getController()).initializeFXNodes();

				break;

			case "VoteRequirementCard":

				scene = new Scene((ScrollPane) loader.load());

				((VoteRequirementCardController) loader.getController()).setData(mainController.getRqModel(),
						mainController, rq);

				break;

			default:
				throw new IllegalArgumentException(
						"Invalid value for input parameter 'view'. Given value was: " + view);
			}

			ViewBuilder.refreshView(scene);

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public void buildView(String view, String loginName) {

		loader = new FXMLLoader();
		loader.setLocation(getClass().getResource("/swt/swl/topcard/view/" + view + "View.fxml"));

		if (loginName.equals("ShowDiagram")) {

			try {
				Scene scene = new Scene((TabPane) loader.load());

				ViewBuilder.refreshView(scene);

			} catch (IOException e) {
				e.printStackTrace();
			}

			((ShowDiagramController) loader.getController()).setData(mainController, loginName);

		} else {
			throw new IllegalArgumentException("Invalid value for input parameter 'view'. Given value was: " + view);

		}
	}

	public void setMainController(RequirementCardController mainController) {
		this.mainController = mainController;
	}

}
