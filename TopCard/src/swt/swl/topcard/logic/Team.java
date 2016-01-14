package swt.swl.topcard.logic;

public class Team {
	private int id;
	private String name;

	public Team(int id, String text) {
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
