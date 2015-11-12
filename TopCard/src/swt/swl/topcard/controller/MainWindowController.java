package swt.swl.topcard.controller;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import swt.swl.topcard.MainApp;



public class MainWindowController {
    public MainApp mainApp;
    private Pane rootLayout;
    private String loginName;
    
    public MainWindowController(String loginName) {
    	this.loginName=loginName;
    	try{
			FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("view/MainWindowView.fxml"));
            rootLayout = (Pane) loader.load();

            Scene scene = new Scene(rootLayout);
            mainApp.getPrimaryStage().setScene(scene);
            mainApp.getPrimaryStage().show();
		}catch(Exception e){
			e.printStackTrace();
		}
	}
}
