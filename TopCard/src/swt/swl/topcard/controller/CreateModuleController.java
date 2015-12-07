package swt.swl.topcard.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import swt.swl.topcard.model.ModuleModel;

public class CreateModuleController {

	private ModuleModel model;
	
	@FXML
	private Button createButton;
	
	@FXML
	private Button cancelButton;
	
	@FXML
	private TextField moduleNameTextField;
	
	@FXML
	protected void create()
	{
		// TODO
		// 0. Check whether a string has been entered in the textfield
		// 1. Check whether module with the name exists
		// 2. Add module to the database
	}
	
	@FXML
	protected void cancel()
	{
		//TODO close
	}
	
	
}
