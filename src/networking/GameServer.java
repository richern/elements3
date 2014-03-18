package networking;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import states.PlayState;
import main.Game;
import networking.packets.*;

import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import com.esotericsoftware.kryonet.Server;

import entities.Player;
import enums.GameRole;

public class GameServer extends Listener {
	
	Game game;
	Server server;
	final int PORT = Network.PORT;
	Map<Integer, GameRole> rolesToConnections = new HashMap<Integer, GameRole>();
	ArrayList<GameRole> roles = new ArrayList<GameRole>();
	
	public GameServer(Game game) throws IOException {
		initRoles();
		initMap();
		this.game = game;
		this.server = new Server();
		connect();
	}
	
	public void connect() throws IOException {
		server.bind(PORT, PORT);
		Network.register(server);
		server.addListener(this);
		server.start();
		System.out.println("Server started on " + PORT);
	}
	
	public void initRoles() {
		roles.add(GameRole.LEFT);
		roles.add(GameRole.RIGHT);
	}
	
	public void initMap() {
		
	}
	
	public void connected(Connection connection) {
		GameRole role = roles.remove(0);
		int connectionId = connection.getID();
		rolesToConnections.put(connectionId, role);
	}
	
	public void received(Connection connection, Object packet) {
		PlayState playState = game.getPlayState();
		int connectionId = connection.getID();
		
		if(packet instanceof ActionPacket) {
			GameRole role = rolesToConnections.get(connectionId);
			boolean pressed = ((ActionPacket) packet).pressed;
			playState.translateAction(role, pressed);
		}
	}
	
	public void disconnected(Connection connection) {
		System.out.println("Disconnected: " + connection.getID());
	}
	
	public void sendPlayerPacket() {
		Player player = game.getPlayState().getWorld().getPlayer();
		float x = player.getX();
		float y = player.getY();
		float ddx = player.getDdx();
		
		PlayerPacket playerPacket = new PlayerPacket();
		playerPacket.x = x;
		playerPacket.y = y;
		playerPacket.ddx = ddx;
 		server.sendToAllTCP(playerPacket);
	}
	
	public void sendNextLevelPacket() {
		NextLevelPacket packet = new NextLevelPacket();
		server.sendToAllTCP(packet);
	}
}
