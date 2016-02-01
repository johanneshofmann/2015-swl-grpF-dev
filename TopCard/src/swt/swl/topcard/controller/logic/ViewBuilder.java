package swt.swl.topcard.controller.logic;

import javafx.scene.Scene;
import swt.swl.topcard.controller.LoginController;
import swt.swl.topcard.controller.RequirementCardController;
import swt.swl.topcard.logic.entitiy.RequirementCardSimple;

public interface ViewBuilder {

	void preLoadScenes();

	void buildView(String string);

	void buildView(String view, RequirementCardSimple rq);

	Scene buildView(String view, String loginName);

	void setMainController(RequirementCardController mainController);

	public static void changeGUI(Scene scene) {

		ViewBuilderImpl.primaryStage.close();
		ViewBuilderImpl.primaryStage.setScene(scene);
		ViewBuilderImpl.primaryStage.show();
	}

	void buildView(String string, String loginName, LoginController loginController);
}