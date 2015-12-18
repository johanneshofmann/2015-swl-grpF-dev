package swt.swl.topcard;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import swt.swl.topcard.controller.LoginWindowController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.layout.Pane;
import javafx.geometry.Rectangle2D;

public class MainApp extends Application {

	private Stage primaryStage;
	private Pane rootLayout;
	private Scene loginScene;

	public void start(Stage primaryStage) {
		try {
			// Initialize primary stage
			this.primaryStage = primaryStage;
			this.primaryStage.setTitle("TopCard");
			
			/*
			Rectangle2D primaryScreenBounds = Screen.getPrimary().getVisualBounds();
			
			//set Stage boundaries to visible bounds of the main screen
			primaryStage.setX(primaryScreenBounds.getMinX());
			primaryStage.setY(primaryScreenBounds.getMinY());
			primaryStage.setWidth(primaryScreenBounds.getWidth());
			primaryStage.setHeight(primaryScreenBounds.getHeight());
			*/
			
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
			loginScene = new Scene(rootLayout);
			primaryStage.setScene(loginScene);
			primaryStage.show();
			System.out.println(new java.util.Date());
			System.out.println("MainApp: maybe ownerName is not set in some cases...?");
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
