package main;

import java.awt.Dimension;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

import world.Level;
import world.World;
	
public class GameState extends BasicGameState {
	
	private Level level;
	private World world;
	
	public GameState(Level level) {
		this.level = level;
	}

	@Override
	public void init(GameContainer container, StateBasedGame sbg)
			throws SlickException {
		this.world = new World(level);
	}

	@Override
	public void update(GameContainer container, StateBasedGame sbg, int delta)
			throws SlickException {
		Input input = container.getInput();
		world.update(input, delta);
	}
	
	@Override
	public void render(GameContainer container, StateBasedGame sbg, Graphics graphics)
			throws SlickException {
		container.getGraphics().scale((float) 640/1920, (float) 360/1080);
		world.render(graphics);
	}

	@Override
	public int getID() {
		return 0;
	}
	
}
