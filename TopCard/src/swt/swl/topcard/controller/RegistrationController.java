package swt.swl.topcard.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import swt.swl.topcard.model.LoginModel;

/**
 * 
 * @author swt-041649
 *
 */
public interface RegistrationController {

	@FXML
	void cancelButtonClicked(ActionEvent event);

	@FXML
	void register(ActionEvent event);

	LoginModel getModel();

	void setData(LoginController loginController, LoginModel model);

}
