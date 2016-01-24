package swt.swl.topcard.logic.impl;

import swt.swl.topcard.logic.Team;

public class TeamImpl implements Team {
	private int id;
	private String name;

	public TeamImpl(int id, String text) {
		super();
		this.id = id;
		this.name = text;
	}

	public int getId() {
		return this.id;
	}

	public void setId(int value) {
		this.id = value;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String value) {
		this.name = value;
	}

}
