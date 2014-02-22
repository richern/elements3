package networking;

import java.io.IOException;

import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import com.esotericsoftware.kryonet.Server;

public class GameServer extends Listener {
	
	private static Server server;
	private static final int PORT = Network.PORT;
	
	public GameServer() throws IOException {
		server = new Server();
	}
	
	public void connect() throws IOException {
		server.bind(PORT);
		server.addListener(this);
		server.start();
	}
	
	public void connected(Connection connection) {
		System.out.println("Connected: " + connection.getID());
	}
	
	public void disconnected(Connection connection) {
		System.out.println("Disconnected: " + connection.getID());
	}

}
