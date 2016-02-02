package swt.swl.topcard.controller.logic;

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
	 * 
	 * TODO: javadoc
	 * 
	 */
	void preLoadScenes();

	/**
	 * 
	 * TODO: javadoc
	 * 
	 */
	void buildView(String string);

	/**
	 * 
	 * TODO: javadoc
	 * 
	 */
	void buildView(String view, RequirementCardSimple rq);

	/**
	 * 
	 * TODO: javadoc
	 * 
	 */
	Scene buildView(String view, String loginName);

	/**
	 * 
	 * TODO: javadoc
	 * 
	 */
	void setMainController(RequirementCardController mainController);

	/**
	 * 
	 * TODO: javadoc
	 * 
	 */
	public static void changeGUI(Scene scene) {

		ViewBuilderImpl.primaryStage.close();
		ViewBuilderImpl.primaryStage.setScene(scene);
		ViewBuilderImpl.primaryStage.show();
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

}