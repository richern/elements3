package states;

import java.util.ArrayList;
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
import enums.KeyType;
import world.Level;
import world.Level.TileMapOutOfBoundsException;
import world.World;
import world.World.KeyException;
	
public class PlayState extends BasicGameState {
	
	ArrayList<Level> levels;
	int currentLevel;
	World world;
	GameRole role;
	Listener network;
	HashMap<Integer, Boolean> playerInput;
	HashMap<Integer, Boolean> globalInput;
	
	boolean keyPressed = false;

	@Override
	public void init(GameContainer container, StateBasedGame sbg)
			throws SlickException {
		levels = new ArrayList<Level>();
		levels.add(new Level("96", "resources/tilemaps/airCMap.tmx", 
				"resources/tilemaps/airAMap.tmx", 
				"resources/backgrounds/Air.png",
				//new Point(23.5f * 96, 5.5f * 96),
				new Point(1.5f * 96, 29.5f * 96),
				new Point(21.5f * 96, 6.5f * 96), KeyType.AIR));

		currentLevel = 0;
		world = new World(levels.get(currentLevel));
		playerInput = new HashMap<Integer, Boolean>();
		globalInput = new HashMap<Integer, Boolean>();
		initInput(playerInput);
		initInput(globalInput);
	}

	@Override
	public void update(GameContainer container, StateBasedGame sbg, int delta)
			throws SlickException {
		Game game = (Game) sbg;
		float time = delta / 1000.0f;
		
		if(game.isHost()) {
			boolean spaceKey = playerInput.get(Input.KEY_SPACE);
						
			if(spaceKey) translateAction(role);
			try {
				world.update(globalInput, time);
			} catch(Exception e) {
				if(e instanceof TileMapOutOfBoundsException)
					world.reset();
				else if(e instanceof KeyException) {
					nextLevel(game);
					((GameServer) network).sendNextLevelPacket();
				}
			}
			
			((GameServer) network).sendPlayerPacket();
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
			try {
				world.update(globalInput, time);
			} catch(Exception e) {
				if(e instanceof TileMapOutOfBoundsException)
					world.reset();
				else if(e instanceof KeyException)
					nextLevel(game);
			}
		}
		
		resetInput();
	}
	
	@Override
	public void enter(GameContainer container, StateBasedGame game) throws SlickException {
		network = ((Game) game).getNetwork();
		world = new World(levels.get(currentLevel));
	}
	
	@Override
	public void render(GameContainer container, StateBasedGame sbg, Graphics graphics)
			throws SlickException {
		graphics.scale((float) container.getWidth() / 1920f, (float) container.getHeight() / 1080f);
		world.render(graphics);
	}
	
	@Override
	public void keyPressed(int key, char c) {
		keyPressed = true;
		if(playerInput.containsKey(key)) {
			playerInput.put(key, true); 
		}
	}
	
	@Override
	public void keyReleased(int key, char c) {
		if(!keyPressed) return;
		if(playerInput.containsKey(key)) {
			playerInput.put(key, true); 
		}
	}
	
	@Override
	public int getID() {
		return GameState.PLAY.getID();
	}
	
	public void nextLevel(Game game) throws SlickException {
		keyPressed = false;
		if(currentLevel >= levels.size() - 1) {
			game.getCutsceneState().activateCutscene("ending");
			game.enterCutsceneState();
		}
		else {			
			world = new World(levels.get(++currentLevel));
		}
	}
	
	public void resetWorld() throws SlickException {
		keyPressed = false;
		world = new World(levels.get(currentLevel));
	}
	
	@SuppressWarnings("incomplete-switch")
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
