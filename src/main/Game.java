package main;


import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Point;
import org.newdawn.slick.state.StateBasedGame;

import world.CollisionMap;
import world.Level;
import world.CollisionMap.TileNotSquareException;

public class Game extends StateBasedGame {
	
	private static final Dimension TARGET_RESOLUTION = new Dimension(1920, 1080);
	
	public Game() {
		super("Elements 3");
	}

	@Override
	public void initStatesList(GameContainer container) throws SlickException {
		//container.getGraphics().scale(0.5f,0.5f);
		try {
			addState(new GameState(new Level("camera_test", new CollisionMap("resources/tilemaps/camera_test.tmx"), new Point(32, 576))));
		}
		catch(TileNotSquareException e) {
			e.printStackTrace();
		}
	}
	
	public static Dimension getTargetResolution() {
		return TARGET_RESOLUTION;
	}
	
	public static void main(String[] args) throws SlickException {
		AppGameContainer app = new AppGameContainer(new Game());
		app.setDisplayMode(640, 360, false);
		app.setAlwaysRender(true);
		app.start();
	}

}
