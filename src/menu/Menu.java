package menu;

import java.awt.Font;
import java.io.InputStream;
import java.util.ArrayList;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.TrueTypeFont;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.util.ResourceLoader;

import states.MenuState;

public class Menu extends Option {
	
	protected TrueTypeFont font;
	protected ArrayList<Option> options;
	protected int currentOption;
	protected Input input;
	
	public Menu(String text) {
		super(text);
		importFont();
		options = new ArrayList<Option>();
		currentOption = 0;
		input = null;
	}
	
	public void importFont() {
		try {
			InputStream inputStream	= ResourceLoader.getResourceAsStream("resources/fonts/Caracteres L1.ttf");
			Font awtFont = Font.createFont(Font.TRUETYPE_FONT, inputStream);
			awtFont = awtFont.deriveFont(48f); // set font size
			font = new TrueTypeFont(awtFont, false);
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public void addOption(Option o) {
		options.add(o);
	}
	
	public void update(GameContainer container, StateBasedGame sbg, int delta)
			throws SlickException {
		input = container.getInput();
		boolean up = input.isKeyPressed(Input.KEY_UP) || input.isKeyPressed(Input.KEY_RIGHT);
		boolean down = input.isKeyPressed(Input.KEY_DOWN) || input.isKeyPressed(Input.KEY_LEFT);
		boolean enter = input.isKeyPressed(Input.KEY_ENTER) || input.isKeyPressed(Input.KEY_SPACE);
		
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
			graphics.setColor(Color.white);
			String text = options.get(i).getText();
			if(i == currentOption) {
				graphics.setColor(Color.yellow);
			}
			graphics.drawString(text, x, y);
			y += 30;
		}
		graphics.setColor(Color.white);
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
