package swt.swl.topcard.controller;

import java.util.Observable;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import swt.swl.topcard.MainApp;
import swt.swl.topcard.model._Model;

/**
 * 
 * @author swt-041649
 *
 */
public interface LoginController {

	void loginButtonClicked(ActionEvent event);

	@FXML
	void registerButtonClicked(ActionEvent event);

	void update(Observable o, Object message);

	void setData(RequirementCardController mainController, Scene loginScene);

	MainApp getMainApp();

	Scene getRequirementCardViewScene();

	void setLoginScene(Scene scene);

	Scene getLoginScene();

	_Model getModel();
}
