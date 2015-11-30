package swt.swl.topcard.controller;

import org.junit.Test;
import static org.mockito.Mockito.*;

import javafx.event.ActionEvent;

public class TestCreateRQCardController {

	private RequirementCardController mainController;

	public TestCreateRQCardController() {
		mainController = mock(RequirementCardController.class);
	}
	@Test
	void testcloseWindow() {
		
		mainController.startButtonClicked(new ActionEvent());
		verify(mainController).startButtonClicked(new ActionEvent());
	}
}
