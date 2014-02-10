package main;

import java.util.ArrayList;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Point;
import org.newdawn.slick.geom.Vector2f;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.tiled.TiledMap;

import physics.Player;
import world.CollisionMap;
import world.Level;
import world.World;

public class GameState extends BasicGameState {
	
	private ArrayList<Level> levels;
	private World world;
	private Player player;

	@Override
	public void init(GameContainer container, StateBasedGame sbg)
			throws SlickException {
		this.levels = initLevels();
		this.world = new World(levels.get(0));
		this.player = world.getPlayer();
	}

	@Override
	public void update(GameContainer container, StateBasedGame sbg, int delta)
			throws SlickException {
		Input input = container.getInput();
		player.update(input, delta);
		world.update(input, delta);
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
	
	public ArrayList<Level> initLevels() throws SlickException {
		ArrayList<Level> levels = new ArrayList<Level>();
		
		Level ezpz = new Level("ezpz", new CollisionMap("resources/tilemaps/ezpz.tmx"), new Point(100, 50));
		levels.add(ezpz);
		
		return levels;
	}
	
}
