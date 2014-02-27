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
import com.esotericsoftware.kryonet.Server;

import states.*;
import util.Dimension;
import world.CollisionMap;
import world.Level;
import world.CollisionMap.TileNotSquareException;

public class Game extends StateBasedGame {
	
	static final Dimension TARGET_RESOLUTION = new Dimension(1920, 1080);
	static GameServer server = null;
	static GameClient client = null;
	
	public Game() {
		super("Elements 3");
	}

	@Override
	public void initStatesList(GameContainer container) throws SlickException {
		//container.getGraphics().scale(0.5f,0.5f);
		addState(new MenuState());
		addState(new LobbyState());
		addState(new PlayState());
	}
	
	public static Dimension getTargetResolution() {
		return TARGET_RESOLUTION;
	}
	
	public static void startServer() throws IOException {
		server = new GameServer();
	}
	
	public static GameServer getServer() {
		return server;
	}
	
	public static void setClient(GameClient c) {
		client = c;
	}
	
	public static GameClient getClient() {
		return client;
	}
	
	public static boolean isHost() {
		return server != null;
	}
	
	public static void main(String[] args) throws SlickException {
		AppGameContainer app = new AppGameContainer(new Game());
		app.setDisplayMode(640, 360, false);
		app.setAlwaysRender(true);
		app.start();
	}

}
