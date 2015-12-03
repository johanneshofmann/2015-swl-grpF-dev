package swt.swl.topcard.controller;

import javafx.fxml.FXML;
import swt.swl.topcard.model.RequirementCardModel;

public class EditRqCardController {
	
	private RequirementCardModel model;
	private RequirementCardController mainController;
	private String toEdit;

	
	
	private void fillTextFields(){
		String[] data = model.getDataFromSelectedRq(toEdit);
		
	}
	public void setData(RequirementCardModel rqModel, RequirementCardController requirementCardController, String toEdit) {
		this.model = rqModel;
		this.mainController = requirementCardController;
		this.toEdit = toEdit;
	}
	
	@FXML
	public void SaveChangesButtonClicked(){
		
	}

}
