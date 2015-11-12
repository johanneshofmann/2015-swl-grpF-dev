package swt.swl.topcard;

import javafx.application.Application;
import javafx.stage.Stage;
import swt.swl.topcard.controller.MainWindowController;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.fxml.FXMLLoader;


public class MainApp extends Application {
	//
	private Stage primaryStage;
	private Pane rootLayout;

	@Override
	public void start(Stage primaryStage) {
		try {
			//Initialize primary stage
			this.primaryStage = primaryStage;
			this.primaryStage.setTitle("TopCard");

			//Initialize root layout
			this.initRootLayout();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}

	//test

	public static void main(String[] args) {
		launch(args);
	}

	public void initRootLayout() {
		try
		{
			FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("view/UserLoginWindow.fxml"));
            rootLayout = (Pane) loader.load();

            Scene scene = new Scene(rootLayout);
            primaryStage.setScene(scene);
            primaryStage.show();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	public Stage getPrimaryStage() {
		return primaryStage;
	}

}
