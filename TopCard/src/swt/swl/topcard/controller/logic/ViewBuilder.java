package swt.swl.topcard.controller.logic;

import javafx.scene.Scene;
import swt.swl.topcard.MainAppImpl;
import swt.swl.topcard.controller.LoginController;
import swt.swl.topcard.controller.RequirementCardController;
import swt.swl.topcard.logic.entitiy.RequirementCardSimple;

public interface ViewBuilder {

	void buildView(String string);

	void buildView(String view, RequirementCardSimple rq);

	void buildView(String view, String loginName);

	void setMainController(RequirementCardController mainController);

	public static void changeGUI(Scene scene) {

		MainAppImpl.primaryStage.close();
		MainAppImpl.primaryStage.setScene(scene);
		MainAppImpl.primaryStage.show();
	}

	void buildView(String string, String loginName, LoginController loginController);
}