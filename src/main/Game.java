package main;


import java.io.IOException;

import networking.GameClient;
import networking.GameServer;

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Point;
import org.newdawn.slick.state.StateBasedGame;

import com.esotericsoftware.kryonet.Client;
import com.esotericsoftware.kryonet.EndPoint;
import com.esotericsoftware.kryonet.Listener;
import com.esotericsoftware.kryonet.Server;

import enums.GameState;
import states.*;
import util.Dimension;
import world.CollisionMap;
import world.Level;
import world.CollisionMap.TileNotSquareException;

public class Game extends StateBasedGame {
	
	public static final Dimension TARGET_RESOLUTION = new Dimension(1920, 1080);
	public static Listener network;
	
	public Game() {
		super("Elements 3");
	}

	@Override
	public void initStatesList(GameContainer container) throws SlickException {
		addState(new MenuState());
		addState(new PlayState());
	}
	
	public void startServer() throws IOException {
		network = new GameServer(this);
	}
	
	public void startClient(String host) throws IOException {
		network = new GameClient(host, this);
	}
	
	public void enterPlayState() {
		enterState(GameState.PLAY.getID());
	}
	
	public PlayState getPlayState() {
		return (PlayState) getState(GameState.PLAY.getID());
	}
	
	public boolean isSinglePlayer() {
		return network == null;
	}
	
	public boolean isHost() {
		return network instanceof GameServer;
	}
	
	public boolean isClient() {
		return network instanceof GameClient;
	}
	
	public Listener getNetwork() {
		return network;
	}
	
	public static void main(String[] args) throws SlickException {
		AppGameContainer app = new AppGameContainer(new Game());
		
		app.setDisplayMode(640, 360, false);
		//app.setDisplayMode(1920, 1080, true);
		app.setAlwaysRender(true);
		app.start();
	}

}
