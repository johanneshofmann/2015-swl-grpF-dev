package swt.swl.topcard.model;

public class Module {
	private int id;
	private String text;
	
	public Module(int id, String text)
	{
		super();
		this.id = id;
		this.text = text;
	}
	
	public int getId()
	{
		return this.id;
	}
	
	public void setId(int value)
	{
		this.id = value;
	}
	
	public String getText()
	{
		return this.text;
	}
	
	public void setText(String value)
	{
		this.text = value;
	}
}
