package networking;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import util.WorldInput;
import main.Game;
import networking.packets.*;

import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import com.esotericsoftware.kryonet.Server;

import enums.GameRole;
import enums.GameState;

public class GameServer extends Listener {
	
	Game game;
	Server server;
	final int PORT = Network.PORT;
	Map<GameRole, Integer> rolesToConnections = new HashMap<GameRole, Integer>();
	WorldInput input = new WorldInput();
	
	public GameServer(Game game) throws IOException {
		initializeMap();
		this.game = game;
		this.server = new Server();
		connect();
	}
	
	public void connect() throws IOException {
		server.bind(PORT);
		Network.register(server);
		server.addListener(this);
		server.start();
		System.out.println("Server started on " + PORT);
	}
	
	public void initializeMap() {
		rolesToConnections.put(GameRole.LEFT, null);
		rolesToConnections.put(GameRole.RIGHT, null);
	}
	
	public void connected(Connection connection) {
		for(Map.Entry<GameRole, Integer> rtc : rolesToConnections.entrySet()) {
			GameRole role = rtc.getKey();
			Integer connectionId = rtc.getValue();
			
			if(connectionId == null) {
				rolesToConnections.put(role, connectionId);
				GameInitPacket gameInitPacket = new GameInitPacket();
				gameInitPacket.role = role;
				connection.sendTCP(gameInitPacket);
			}
		}
	}
	
	public void received(Connection connection, Object packet) {
	}
	
	public void disconnected(Connection connection) {
		
		System.out.println("Disconnected: " + connection.getID());
	}

}
