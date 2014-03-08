package menu;

import java.util.ArrayList;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

import states.MenuState;

public class Menu extends Option {
	
	protected ArrayList<Option> options = new ArrayList<Option>();
	protected int currentOption = 0;
	
	public Menu(String text) {
		super(text);
	}
	
	public void addOption(Option o) {
		options.add(o);
	}
	
	public void update(GameContainer container, StateBasedGame sbg, int delta)
			throws SlickException {
		Input input = container.getInput();
		boolean up = input.isKeyPressed(Input.KEY_UP);
		boolean down = input.isKeyPressed(Input.KEY_DOWN);
		boolean enter = input.isKeyPressed(Input.KEY_ENTER);
		
		if(up && down) {}
		else if(up) {
			decrementOption();
		}
		else if(down) {
			incrementOption();
		}
		else if(enter) {
			selectOption();
		}
	}
	
	public void render(GameContainer container, StateBasedGame sbg, Graphics graphics)
			throws SlickException {
		int x = 30;
		int y = 30;
		
		for(int i=0; i < options.size(); i++) {
			String text = options.get(i).getText();
			if(i == currentOption) {
				graphics.setColor(Color.yellow);
			}
			else {
				graphics.setColor(Color.white);
			}
			graphics.drawString(text, x, y);
			y += 30;
		}
	}
	
	public ArrayList<Option> getOptions() {
		return options;
	}
	
	public void decrementOption() {
		currentOption = currentOption == 0 ? options.size() - 1 : currentOption - 1; 
	}
	
	public void incrementOption() {
		currentOption = currentOption == options.size() - 1 ? 0 : currentOption + 1;
	}
	
	public void selectOption() {
		options.get(currentOption).open();
	}

	@Override
	public void open() {
		MenuState.setCurrentMenu(this);
	}

}
