package states;

import main.Game;
import networking.GameClient;
import networking.GameRole;
import networking.packets.InputPacket;
import networking.packets.TestPacket;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Point;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

import util.GameInput;
import world.CollisionMap;
import world.Level;
import world.World;
	
public class PlayState extends BasicGameState {
	
	static World world;
	public static GameRole role;
	public static GameInput input;

	@Override
	public void init(GameContainer container, StateBasedGame sbg)
			throws SlickException {
		input = new GameInput();
		try {
			setWorld(new Level("camera_test", new CollisionMap("resources/tilemaps/camera_test.tmx"), new Point(32, 576)));
		}
		catch(Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void update(GameContainer container, StateBasedGame sbg, int delta)
			throws SlickException {
		input.registerInput(container.getInput());
		if(role != GameRole.ALL) GameClient.sendInputPacket(input, delta);
		world.update(input, delta);
	}
	
	@Override
	public void render(GameContainer container, StateBasedGame sbg, Graphics graphics)
			throws SlickException {
		graphics.scale((float) 640/1920, (float) 360/1080);
		world.render(graphics);
	}
	
	public static void setWorld(Level level) {
		world = new World(level);
	}
	
	public static World getWorld() {
		return world;
	}
	
	@Override
	public int getID() {
		return GameState.PlayState.getID();
	}
	
}
