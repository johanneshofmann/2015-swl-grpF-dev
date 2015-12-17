package swt.swl.topcard.controller;

import org.junit.Test;
import static org.mockito.Mockito.*;

import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

import static org.junit.Assert.*;

public class TestCreateRQCardController {

	private RequirementCardController mainController;

	public TestCreateRQCardController() {
		mainController = mock(RequirementCardController.class);
	}
	
	@Test
	public void alertAfterCloseWindow(){
		Alert al = new Alert(AlertType.CONFIRMATION, "");
	//	assertEquals(al, CreateRQCardController.closeWindow(ActionEvent event);
		}

}
