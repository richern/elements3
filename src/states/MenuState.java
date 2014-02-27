package states;

import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.Callable;

import main.Game;
import menu.Action;
import menu.Menu;
import menu.Option;
import networking.GameClient;
import networking.GameRole;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

public class MenuState extends BasicGameState {
	
	static Menu currentMenu;
	static Menu rootMenu;
	static ArrayList<Option> options;
	static int currentOption;

	@Override
	public void init(GameContainer container, final StateBasedGame sbg)
			throws SlickException {
		Menu mainMenu = new Menu("main");
		
		Action singlePlayer = new Action("Single Player", new Callable<Void>() {
			public Void call() {
				PlayState.role = GameRole.ALL;
				sbg.enterState(GameState.PlayState.getID());
				return null;
			}
		});

		Action hostGame = new Action("Host Game", new Callable<Void>() {
			public Void call() {
				try {
					Game.startServer();
					Game.setClient(new GameClient("localhost", sbg));
				} catch (IOException e) {
					e.printStackTrace();
				}
				sbg.enterState(GameState.LobbyState.getID());
				System.out.println("Hosting game");
				return null;
			}
		});
		
		Action findGame = new Action("Find Game", new Callable<Void>() {
			public Void call() {
				try {
					Game.setClient(new GameClient("localhost", sbg));
				} catch (IOException e) {
					e.printStackTrace();
				}
				sbg.enterState(GameState.LobbyState.getID());
				System.out.println("Joining game");
				return null;
			}
		});
		
		mainMenu.addOption(singlePlayer);
		mainMenu.addOption(hostGame);
		mainMenu.addOption(findGame);
		rootMenu = mainMenu;
	}
	
	@Override
	public void enter(GameContainer container, StateBasedGame sbg) {
		setCurrentMenu(rootMenu);
	}
	
	@Override
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

	@Override
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
	
	@Override
	public int getID() {
		return GameState.MenuState.getID();
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
	
	public static void setCurrentMenu(Menu menu) {
		currentMenu = menu;
		options = menu.getOptions();
		currentOption = 0;
	}
	
}
