package networking;

import java.io.IOException;
import java.util.ArrayList;

import networking.packets.GameStartPacket;
import networking.packets.InputPacket;
import networking.packets.RolePacket;

import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import com.esotericsoftware.kryonet.Server;

public class GameServer extends Listener {
	
	private static Server server;
	private static final int PORT = Network.PORT;
	ArrayList<GameRole> roles = new ArrayList<GameRole>();
	
	public GameServer() throws IOException {
		roles.add(GameRole.LEFT);
		roles.add(GameRole.RIGHT);
		roles.add(GameRole.JUMP);
		server = new Server();
		connect();
	}
	
	public void connect() throws IOException {
		server.bind(PORT);
		Network.register(server);
		server.addListener(this);
		server.start();
		System.out.println("Server started on " + PORT);
	}
	
	public void connected(Connection connection) {
		RolePacket rolePacket = new RolePacket();
		rolePacket.role = roles.remove(0);
		connection.sendTCP(rolePacket);
		if(roles.size() == 0) {
			System.out.println("Sending GameStart packet");
			server.sendToAllTCP(new GameStartPacket());
		}
		System.out.println("Connected: " + connection.getID());
	}
	
	public void received(Connection connection, Object packet) {
		int connectionId = connection.getID();
		if(packet instanceof InputPacket) {
			server.sendToAllExceptTCP(connectionId, packet);
		}
	}
	
	public void disconnected(Connection connection) {
		System.out.println("Disconnected: " + connection.getID());
	}

}
