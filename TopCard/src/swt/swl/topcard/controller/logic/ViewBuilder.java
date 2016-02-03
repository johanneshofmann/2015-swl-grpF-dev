package swt.swl.topcard.controller.logic;

import java.util.HashMap;

import javafx.scene.Scene;
import swt.swl.topcard.controller.LoginController;
import swt.swl.topcard.controller.RequirementCardController;
import swt.swl.topcard.logic.entitiy.RequirementCardSimple;

/**
 * 
 * Singleton, that generates, manages and provides a central cache for the
 * TopCard-views
 * 
 * @author -steve-
 *
 */
public interface ViewBuilder {

	/**
	 * Pre-Loads a few scenes of TopCard Application.
	 * 
	 */
	void preLoadScenes();

	/**
	 * 
	 * Changes to the view matching the given String.
	 */
	void buildView(String string);

	/**
	 * 
	 * Creates a View.
	 */
	void buildView(String view, RequirementCardSimple rq);

	/**
	 * Creates a View.
	 * 
	 */
	Scene buildView(String view, String loginName);

	/**
	 * Sets the Main-Controller-Instance of the ViewBuilder.
	 */
	void setMainController(RequirementCardController mainController);

	/**
	 * Performs a well-known operation in JavaFX Applications:
	 * 
	 * <ul>
	 * <li>close PrimaryStage
	 * <li>change Scene on PrimaryStage
	 * <li>shop PrimaryStage again
	 * </ul>
	 * 
	 */
	public static void changeGUI(Scene scene) {

		ViewBuilderImpl.INSTANCE.getPrimaryStage().close();
		ViewBuilderImpl.INSTANCE.getPrimaryStage().setScene(scene);
		ViewBuilderImpl.INSTANCE.getPrimaryStage().show();
	}

	/**
	 * 
	 * One of the ViewBuilders' buildView()-methods. Generates
	 * RequirementCardView and -Controller and stores them in the specific
	 * DAO(Controllers)/HashMap(Views)
	 * 
	 * @param string
	 * @param loginName
	 * @param loginController
	 */
	void buildView(String string, String loginName, LoginController loginController);

	/**
	 * 
	 * Static method, that executes all configuration operations for the
	 * ViewBuilder Singleton
	 */
	void configureYourself();

	HashMap<String, Scene> getSystemScenes();

}