package swt.swl.topcard;
	
import javafx.application.Application;
import javafx.stage.Stage;
import swt.swl.topcard.controller.CardOverviewController;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.fxml.FXMLLoader;


public class MainApp extends Application {
	
	private Stage primaryStage;
	private BorderPane rootLayout;
	
	@Override
	public void start(Stage primaryStage) {
		try {
			//Initialize primary stage
			this.primaryStage = primaryStage;
			this.primaryStage.setTitle("TopCard");
			
			//Initialize root layout
			this.initRootLayout();
			
			//Show CardOverview;
			this.showCardOverview();
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
            loader.setLocation(MainApp.class.getResource("view/RootLayout.fxml"));
            rootLayout = (BorderPane) loader.load();
            
            Scene scene = new Scene(rootLayout);
            primaryStage.setScene(scene);
            primaryStage.show();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	
	public void showCardOverview()
	{
		try
		
		
		{
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("view/CardOverview.fxml"));
            AnchorPane cardOverview = (AnchorPane) loader.load();

            rootLayout.setCenter(cardOverview);
            
            CardOverviewController controller = loader.getController();
            //controller.setMainApp(this);
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
}
