package menu;

public abstract class Option {
	
	protected String text;
	
	public Option(String text) {
		this.text = text;
	}
	
	public String getText() {
		return text;
	}
	
	public abstract void open();

}
