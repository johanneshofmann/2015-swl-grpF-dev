package swt.swl.topcard;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import swt.swl.topcard.controller.LoginWindowController;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.layout.Pane;
import javafx.fxml.FXMLLoader;

public class MainApp extends Application {

	private Stage primaryStage;
	private Pane rootLayout;

	public void start(Stage primaryStage) {
		try {
			// Initialize primary stage
			this.primaryStage = primaryStage;
			this.primaryStage.setTitle("TopCard");

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
		try {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(MainApp.class.getResource("view/UserLoginWindow.fxml"));
			rootLayout = (Pane) loader.load();
			((LoginWindowController) loader.getController()).setMainApp(this);
			Scene scene = new Scene(rootLayout);
			primaryStage.setScene(scene);
			primaryStage.show();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

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

	public Stage getPrimaryStage() {
		return primaryStage;
	}
}
