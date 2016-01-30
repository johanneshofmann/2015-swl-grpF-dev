package swt.swl.topcard;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import swt.swl.topcard.logic.ViewBuilder;
import swt.swl.topcard.logic.impl.ViewBuilderImpl;

public class MainAppImpl extends Application {

	public static Stage primaryStage;
	private Scene loginScene;
	private ViewBuilder viewBuilder;

	public void start(Stage primaryStage) {
		try {
			// Initialize primary stage
			MainAppImpl.primaryStage = primaryStage;
			MainAppImpl.primaryStage.setTitle("TopCard");

			// instanciate ViewBuilder

			viewBuilder = new ViewBuilderImpl();

			// Initialize root layout
			this.initRootLayout();

			setOnCloseRequest();

		} catch (Exception e) {

			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		launch(args);
	}

	public void initRootLayout() {

		viewBuilder.buildView("Login");

		System.out.println("Starting up Application ..\rSystem Time: " + new java.util.Date());
	}

	/**
	 * 
	 */
	private void setOnCloseRequest() {

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
}