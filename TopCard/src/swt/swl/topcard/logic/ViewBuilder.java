package swt.swl.topcard.logic;

import javafx.scene.Scene;
import swt.swl.topcard.MainAppImpl;
import swt.swl.topcard.controller.RequirementCardController;

public interface ViewBuilder {

	void buildView(String view);

	void buildView(String view, RequirementCardSimple rq);

	void buildView(String view, String loginName);

	void setMainController(RequirementCardController mainController);

	public static void refreshView(Scene scene) {

		MainAppImpl.primaryStage.close();
		MainAppImpl.primaryStage.setScene(scene);
		MainAppImpl.primaryStage.show();
	}

}