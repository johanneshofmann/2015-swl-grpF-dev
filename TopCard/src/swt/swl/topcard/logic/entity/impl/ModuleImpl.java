package swt.swl.topcard.logic.entity.impl;

import swt.swl.topcard.logic.entitiy.Module;

public class ModuleImpl implements Module {
	private int id;
	private String name;

	public ModuleImpl(int id, String name) {
		super();
		this.id = id;
		this.name = name;
	}

	// ===============================
	// === Getters and Setters =======
	// ===============================

	public int getId() {
		return this.id;
	}

	public void setId(int value) {
		this.id = value;
	}

	public String getText() {
		return this.name;
	}

	public void setText(String value) {
		this.name = value;
	}
}
