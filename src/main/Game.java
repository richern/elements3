package main;

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

public class Game extends StateBasedGame {

	public Game() {
		super("Elements 3");
	}

	@Override
	public void initStatesList(GameContainer arg0) throws SlickException {
		addState(new GameState());
	}
	
	public static void main(String[] args) throws SlickException {
		AppGameContainer app = new AppGameContainer(new Game());
		app.setDisplayMode(600, 400, false);
		app.start();
	}

}
