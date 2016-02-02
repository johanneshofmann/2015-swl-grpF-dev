package swt.swl.topcard.logic.DAOs.mvc.impl;

import java.util.HashMap;

import swt.swl.topcard.logic.DAOs.mvc.ModelDAO;
import swt.swl.topcard.model._Model;

public class ModelDAOImpl implements ModelDAO {

	public static HashMap<String, _Model> models = new HashMap<>();

	public void addModel(String name, _Model model) {

		models.put(name, model);
	}

}
