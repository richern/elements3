package states;

import java.util.HashMap;
	
import main.Game;
import networking.GameClient;
import networking.GameServer;

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
import util.GlobalInput;
import world.CollisionMap;
import world.Level;
import world.Player;
import world.World;
	
public class PlayState extends BasicGameState {
	
	World world;
	GameRole role;
	Listener network;
	HashMap<Integer, Boolean> playerInput;
	HashMap<Integer, Boolean> globalInput;

	@Override
	public void init(GameContainer container, StateBasedGame sbg)
			throws SlickException {
		try {
			world = new World(new Level("camera_test", new CollisionMap("resources/tilemaps/96.tmx"), new Point(384, 1440)));
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		playerInput = new HashMap<Integer, Boolean>();
		globalInput = new HashMap<Integer, Boolean>();
		initInput(playerInput);
		initInput(globalInput);
	}

	@SuppressWarnings("incomplete-switch")
	@Override
	public void update(GameContainer container, StateBasedGame sbg, int delta)
			throws SlickException {
		Game game = (Game) sbg;
		float time = delta / 1000.0f;
		
		if(game.isHost()) {
			boolean spaceKey = playerInput.get(Input.KEY_SPACE);
			Player player = world.getPlayer();
						
			if(spaceKey) translateAction(role);
			world.update(globalInput, time);
			
			if(player.isPositionChanged()) {
				((GameServer) network).sendPlayerPacket();
			}
		}
		else if(game.isClient()) {
			boolean spaceKey = playerInput.get(Input.KEY_SPACE);
			
			if(spaceKey) {
				((GameClient) network).sendActionPacket();
			}
			
			world.update(time);
		}
		else {
			globalInput.putAll(playerInput);
			world.update(globalInput, time);
		}
		
		resetInput();
	}
	
	@Override
	public void enter(GameContainer container, StateBasedGame game) {
		network = ((Game) game).getNetwork();
	}
	
	@Override
	public void render(GameContainer container, StateBasedGame sbg, Graphics graphics)
			throws SlickException {
		//graphics.scale((float) 640/1920, (float) 360/1080);
		world.render(graphics);
	}
	
	@Override
	public void keyPressed(int key, char c) {
		if(playerInput.containsKey(key)) {
			playerInput.put(key, true); 
		}
	}
	
	@Override
	public void keyReleased(int key, char c) {
		if(playerInput.containsKey(key)) {
			playerInput.put(key, true); 
		}
	}
	
	@Override
	public int getID() {
		return GameState.PLAY.getID();
	}
	
	public void translateAction(GameRole role) {
		switch(role){
		case LEFT:
			globalInput.put(Input.KEY_LEFT, true);
			break;
		case RIGHT:
			globalInput.put(Input.KEY_RIGHT, true);
			break;
		case JUMP:
			globalInput.put(Input.KEY_UP, true);
			break;
		}
	}
	
	public void initInput(HashMap<Integer, Boolean> input) {
		input.put(Input.KEY_LEFT, false);
		input.put(Input.KEY_RIGHT, false);
		input.put(Input.KEY_UP, false);
		input.put(Input.KEY_SPACE, false);
		input.put(Input.KEY_ESCAPE, false);
	}
	
	public void resetInput() {
		for(Integer key : playerInput.keySet()) {
			playerInput.put(key, false);
		}
		for(Integer key : globalInput.keySet()) {
			globalInput.put(key, false);
		}
	}
	
	public void setRole(GameRole role) {
		this.role = role;
	}
	
	public World getWorld() {
		return world;
	}
}
