package states;

import main.Game;
import networking.GameClient;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Point;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

import com.esotericsoftware.kryonet.Listener;

import enums.GameRole;
import enums.GameState;
import util.PlayerInput;
import util.WorldInput;
import world.CollisionMap;
import world.Level;
import world.World;
	
public class PlayState extends BasicGameState {
	
	World world;
	GameRole role;
	Listener network;
	PlayerInput input;

	@Override
	public void init(GameContainer container, StateBasedGame sbg)
			throws SlickException {
		try {
			world = new World(new Level("camera_test", new CollisionMap("resources/tilemaps/camera_test.tmx"), new Point(32, 576)));
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		network = ((Game) sbg).getNetwork();
		input = new PlayerInput();
	}

	@Override
	public void update(GameContainer container, StateBasedGame sbg, int delta)
			throws SlickException {
		Game game = (Game) sbg;
		float time = delta / 1000.0f;
		
		if(game.isHost()) {
			input.register(Input input);
			world.update(input, time);
		}
		else if(game.isClient()) {
			
		}
		else {
			input.update(role, container.getInput());
			world.update(input, time);
		}
		
	}
	
	@Override
	public void render(GameContainer container, StateBasedGame sbg, Graphics graphics)
			throws SlickException {
		graphics.scale((float) 640/1920, (float) 360/1080);
		world.render(graphics);
	}
	
	@Override
	public int getID() {
		return GameState.PlayState.getID();
	}
	
	public void setRole(GameRole role) {
		this.role = role;
	}
	
}
