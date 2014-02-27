package menu;

import java.util.ArrayList;

import states.MenuState;

public class Menu extends Option {
	
	ArrayList<Option> options;
	
	public Menu(String text) {
		super(text);
		options = new ArrayList<Option>();
	}
	
	public void addOption(Option o) {
		options.add(o);
	}
	
	public ArrayList<Option> getOptions() {
		return options;
	}

	@Override
	public void open() {
		MenuState.setCurrentMenu(this);
	}

}
