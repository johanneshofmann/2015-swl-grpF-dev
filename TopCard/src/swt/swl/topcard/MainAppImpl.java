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
	public synchronized void start(Stage primaryStage) {

		// instanciate ViewBuilder

		preLoadApplication();

		vB = ViewBuilderImpl.INSTANCE;

		vB.configureYourself();

		this.initLoginView();
	}

	// TODO: remove after finishing..
	private static void preLoadApplication() {

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

		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	public void initLoginView() {

		vB.buildView("Login");

		System.out.println("Starting up Application at System Time: " + new java.util.Date());
	}

}