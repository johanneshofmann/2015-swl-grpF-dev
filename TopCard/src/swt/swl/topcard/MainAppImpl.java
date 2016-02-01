package swt.swl.topcard;

import javafx.application.Application;
import javafx.stage.Stage;
import swt.swl.topcard.controller.RequirementCardController;
import swt.swl.topcard.controller.logic.ViewBuilder;
import swt.swl.topcard.controller.logic.ViewBuilderImpl;
import swt.swl.topcard.logic.DAOs.mvc.impl.ControllerDAOImpl;

public class MainAppImpl extends Application {

	public static ViewBuilder vB;

	public static void main(String[] args) {
		launch(args);
	}

	// maybe preloader could be cool here :)
	public synchronized void start(Stage primaryStage) {

		// instanciate ViewBuilder
		try {

			System.out.println("starting..\r");

			for (int i = 0; i < 10; i++) {

				Thread.sleep(300);
				System.out.print(". ");
			}
			for (int i = 0; i < 10; i++) {
				Thread.sleep(200);
				System.out.print(".");
			}
			System.out.println("\r\r");

		} catch (

		InterruptedException e)

		{
			e.printStackTrace();
		}

		vB = new ViewBuilderImpl();

		String requirementCard = "RequirementCard";

		vB.buildView(requirementCard, null, null);

		RequirementCardController controller = (RequirementCardController) ControllerDAOImpl.controllers
				.get(requirementCard);

		controller.initializeFXNodes();

		vB.setMainController(controller);

		vB.preLoadScenes();

		this.initLoginView();

	}

	public void initLoginView() {

		vB.buildView("Login");

		System.out.println("Starting up Application at System Time: " + new java.util.Date());
	}

}