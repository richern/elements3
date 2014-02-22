package states;

import java.awt.Dimension;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Point;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

import world.CollisionMap;
import world.Level;
import world.World;
	
public class PlayState extends BasicGameState {
	
	private Level level;
	private World world;
	
	public PlayState() {
		try {
			setWorld(new Level("camera_test", new CollisionMap("resources/tilemaps/camera_test.tmx"), new Point(32, 576)));
		}
		catch(Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void init(GameContainer container, StateBasedGame sbg)
			throws SlickException {}

	@Override
	public void update(GameContainer container, StateBasedGame sbg, int delta)
			throws SlickException {
		Input input = container.getInput();
		world.update(input, delta);
	}
	
	@Override
	public void render(GameContainer container, StateBasedGame sbg, Graphics graphics)
			throws SlickException {
		graphics.scale((float) 640/1920, (float) 360/1080);
		world.render(graphics);
	}
	
	public void setWorld(Level level) {
		this.world = new World(level);
	}

	@Override
	public int getID() {
		return 0;
	}
	
}
