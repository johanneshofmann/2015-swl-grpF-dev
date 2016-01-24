package swt.swl.topcard.controller;

import java.util.Observable;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import swt.swl.topcard.MainApp;

/**
 * 
 * @author swt-041649
 *
 */
public interface LoginController {

	void loginButtonClicked(ActionEvent event);

	@FXML
	void registerButtonClicked(ActionEvent event);

	void createRegistrationView();

	public void createRequirementCardView(String loginName);

	void update(Observable o, Object message);

	void setData(MainApp mainApp, Scene loginScene);

	MainApp getMainApp();

	Scene getRequirementCardViewScene();

	void setLoginScene(Scene scene);

	Scene getLoginScene();
}
