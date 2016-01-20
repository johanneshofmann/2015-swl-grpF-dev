package swt.swl.topcard.logic;

public class Module {
	private int id;
	private String name;

	public Module(int id, String name) {
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
