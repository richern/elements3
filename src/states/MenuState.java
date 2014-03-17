 package states;

import java.io.IOException;
import java.util.concurrent.Callable;

import main.Game;
import menu.*;
import menu.menus.*;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

import enums.GameRole;
import enums.GameState;

public class MenuState extends BasicGameState {
	
	static Menu rootMenu;
	static Menu currentMenu;

	@Override
	public void init(GameContainer container, StateBasedGame sbg)
			throws SlickException {
		final Game game = (Game) sbg;
		final PlayState playState = game.getPlayState();
		
		Menu titleMenu = new TitleMenu("title");
		Menu mainMenu = new Menu("main");
		
		Action singlePlayer = new Action("Single Player", new Callable<Void>() {
			public Void call() {
				playState.setRole(GameRole.ALL);
				game.enterCutsceneState();
				return null;
			}
		});

		Action hostGame = new Action("Host Game", new Callable<Void>() {
			public Void call() {
				try {
					playState.setRole(GameRole.JUMP);
					game.startServer();
					game.enterPlayState();
				} catch (IOException e) {
					e.printStackTrace();
				}
				System.out.println("Hosting game");
				return null;
			}
		});
		
		Action findGame = new Action("Find Game", new Callable<Void>() {
			public Void call() {
				try {
					game.startClient("localhost");
				} catch (IOException e) {
					e.printStackTrace();
				}
				System.out.println("Joining game");
				return null;
			}
		});
		
		titleMenu.addOption(mainMenu);
		
		mainMenu.addOption(singlePlayer);
		mainMenu.addOption(hostGame);
		mainMenu.addOption(findGame);
		rootMenu = titleMenu;
	}
	
	@Override
	public void enter(GameContainer container, StateBasedGame sbg) {
		setCurrentMenu(rootMenu);
	}
	
	@Override
	public void update(GameContainer container, StateBasedGame sbg, int delta)
			throws SlickException {
		currentMenu.update(container, sbg, delta);
	}

	@Override
	public void render(GameContainer container, StateBasedGame sbg, Graphics graphics)
			throws SlickException {
		currentMenu.render(container, sbg, graphics);
	}
	
	@Override
	public int getID() {
		return GameState.MENU.getID();
	}
	
	public static void setCurrentMenu(Menu menu) {
		currentMenu = menu;
	}
	
}
