package swt.swl.topcard;

import javafx.application.Application;
import javafx.stage.Stage;
import swt.swl.topcard.controller.logic.ViewBuilder;
import swt.swl.topcard.controller.logic.ViewBuilderImpl;

public class MainAppImpl extends Application implements MainApp {

	public static ViewBuilder vB;

	public static void main(String[] args) {
		launch(args);
	}

	// maybe preloader could be cool here :)
	@Override
	public synchronized void start(Stage primaryStage) {

		// instanciate ViewBuilder

		MainApp.drawDots();

		vB = ViewBuilderImpl.INSTANCE;

		vB.configureYourself();

		this.initLoginView();
	}

	public void initLoginView() {

		vB.buildView("Login");

		System.out.println("Starting up Application at System Time: " + new java.util.Date());
	}
}