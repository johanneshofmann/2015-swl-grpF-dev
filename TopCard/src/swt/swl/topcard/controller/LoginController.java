package swt.swl.topcard.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import swt.swl.topcard.controller.logic.ViewBuilder;
import swt.swl.topcard.model._Model;

/**
 * Interface for {@link LoginControllerImpl}.
 * 
 * @author swt-041649
 *
 */
public interface LoginController {

	/**
	 * 
	 * Invoked when {@code LoginButton} is clicked. <br>
	 * <br>
	 * 
	 * Performs the following actions:
	 * <ul>
	 * <li>First calls the {@link Controller}-Interfaces' {@code checkEmpty()}
	 * -method,
	 * <li>checks if user name given in {@code LoginNameTextField} is in
	 * database(if user exists)
	 * <li>if (user exists) { println("LoginName accepted..") and open
	 * {@code RequirementCardView} }
	 * <li>else { show prompt to user and ask wheather he want to register,
	 * <li>if Yes { open {@code RegistrationView} },
	 * <li>if No { close prompt }
	 * </ul>
	 * 
	 * @param event
	 */
	void loginButtonClicked(ActionEvent event);

	/**
	 * 
	 * Simply calls the {@link LoginController}s'
	 * {@code createRegistrationView()-method} on which this view changing task
	 * is passed to the {@link ViewBuilder}.
	 * 
	 * @param event
	 */
	@FXML
	void registerButtonClicked(ActionEvent event);

	/**
	 * 
	 * TODO: javadoc
	 * 
	 * @param loginName
	 */
	void createRequirementCardView(String loginName);

	/**
	 * 
	 * Simple {@code Getter-Method}.
	 * 
	 * @param mainController
	 * @param loginScene
	 */
	Scene getRequirementCardViewScene();

	/**
	 * 
	 * Simple {@code Setter-Method}.
	 * 
	 * @param scene
	 */
	void setLoginScene(Scene scene);

	/**
	 * 
	 * Simple {@code Getter-Method}.
	 * 
	 * @returns {@code this.loginScene}
	 */
	Scene getLoginScene();

	/**
	 * 
	 * Simple {@code Getter-Method}.
	 * 
	 * @returns this.{@link Model}
	 */
	_Model getModel();

	/**
	 * 
	 * Combined {@code Setter-Method} for {@code RequirementCardController} and
	 * {@code loginScene}.
	 * 
	 * @param scene
	 */
	void setData(RequirementCardController mainController, Scene loginScene);
}
