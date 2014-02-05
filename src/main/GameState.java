package main;

import java.util.ArrayList;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.tiled.TiledMap;

import physics.Body;
import world.Level;
import world.World;

public class GameState extends BasicGameState {
	
	private World world;
	private Body player;
	private ArrayList<Level> levels;

	@Override
	public void init(GameContainer container, StateBasedGame sbg)
			throws SlickException {
		initLevels();
		world = new World(levels.get(0), null);
	}

	@Override
	public void update(GameContainer container, StateBasedGame sbg, int delta)
			throws SlickException {
		world.update();
	}
	
	@Override
	public void render(GameContainer container, StateBasedGame sbg, Graphics graphics)
			throws SlickException {
		world.render(graphics);
	}

	@Override
	public int getID() {
		return 0;
	}
	
	public void initLevels() throws SlickException {
		levels = new ArrayList<Level>();
		Level ezpz = new Level("ezpz", new TiledMap("resources/tilemaps/ezpz.tmx"));
		levels.add(ezpz);
	}
	
}
