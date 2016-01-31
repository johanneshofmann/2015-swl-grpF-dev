package swt.swl.topcard;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import swt.swl.topcard.controller.RequirementCardController;
import swt.swl.topcard.controller.logic.impl.ViewBuilderImpl;
import swt.swl.topcard.controller.logic.ViewBuilder;
import swt.swl.topcard.logic.DAOs.mvc.impl.ControllerDAOImpl;

public class MainAppImpl extends Application {

	public static Stage primaryStage;

	private static ViewBuilder viewBuilder;

	public void start(Stage primaryStage) {
		try {
			// Initialize primary stage
			MainAppImpl.primaryStage = primaryStage;
			MainAppImpl.primaryStage.setTitle("TopCard");

			// instanciate ViewBuilder

			// maybe preloader could be cool here :)

			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(getClass().getResource("/swt/swl/topcard/view/RequirementCardView.fxml"));
			loader.load();
			ControllerDAOImpl.controllers.put("RequirementCard", loader.getController());

			viewBuilder = new ViewBuilderImpl(
					(RequirementCardController) ControllerDAOImpl.controllers.get("RequirementCard"));
			viewBuilder.buildView("Login");

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

		System.out.println("Starting up Application at System Time: " + new java.util.Date());
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