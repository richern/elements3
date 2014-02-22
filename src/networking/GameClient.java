package networking;

import java.io.IOException;

import com.esotericsoftware.kryonet.Client;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;

public class GameClient extends Listener {
	
	private static Client client;
	
	public GameClient() throws IOException {
		client = new Client();
		client.addListener(this);
		client.start();
		client.connect(5000, "localhost", Network.PORT);
	}
	
	public void connected(Connection connection) {
		System.out.println("Client connected");
	}

}
