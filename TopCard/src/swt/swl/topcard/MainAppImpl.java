package swt.swl.topcard;

import javafx.application.Application;
import javafx.stage.Stage;
import swt.swl.topcard.controller.LoginController;
import swt.swl.topcard.controller.logic.ViewBuilder;
import swt.swl.topcard.controller.logic.ViewBuilderImpl;
import swt.swl.topcard.logic.DAOs.mvc.impl.ControllerDAOImpl;

public class MainAppImpl extends Application implements MainApp {

	public static ViewBuilder vB;

	public static void main(String[] args) {
		launch(args);
	}

	// maybe preloader could be cool here :)
	@Override
	public synchronized void start(Stage primaryStage) {

		// instanciate ViewBuilder

		// MainApp.drawDots();

		vB = ViewBuilderImpl.INSTANCE;

		vB.configureYourself();

		this.initLoginView();
	}

	public void initLoginView() {

		String key = "Login";
		vB.buildView(key);

		((LoginController) ControllerDAOImpl.controllers.get(key))
				.setScenes(vB.getSystemScenes().get("RequirementCard"));

		System.out.println("Starting up Application at System Time: " + new java.util.Date());
	}
}