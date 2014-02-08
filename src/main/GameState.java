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
import world.Level;
import world.World;

public class GameState extends BasicGameState {
	
	private World world;
	private ArrayList<Level> levels;

	@Override
	public void init(GameContainer container, StateBasedGame sbg)
			throws SlickException {
		initLevels();
		world = new World(levels.get(0));
	}

	@Override
	public void update(GameContainer container, StateBasedGame sbg, int delta)
			throws SlickException {
		Input input = container.getInput();
		transferInputToPlayer(input, world.getPlayer(), delta);
		world.update(container, sbg, delta);
	}
	
	@Override
	public void render(GameContainer container, StateBasedGame sbg, Graphics graphics)
			throws SlickException {
		world.render(container, sbg, graphics);
	}

	@Override
	public int getID() {
		return 0;
	}
	
	public void initLevels() throws SlickException {
		levels = new ArrayList<Level>();
		Level ezpz = new Level("ezpz", new TiledMap("resources/tilemaps/ezpz.tmx"), new Point(100, 96));
		levels.add(ezpz);
	}
	
	public void transferInputToPlayer(Input input, Player player, int delta) {
		if(input.isKeyDown(Input.KEY_LEFT)) {
			player.moveLeft(delta);
		}
		if(input.isKeyDown(Input.KEY_RIGHT)) {
			player.moveRight(delta);
		}
		if(!input.isKeyDown(Input.KEY_LEFT) && !input.isKeyDown(Input.KEY_RIGHT)) {
			player.idle(delta);
		}
		if(input.isKeyDown(Input.KEY_UP)) {
			player.jump(delta);
		}
	}
	
}
