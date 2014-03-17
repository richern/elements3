	package networking;

import java.io.IOException;

import org.newdawn.slick.geom.Point;

import world.World;
import main.Game;
import networking.packets.*;

import com.esotericsoftware.kryonet.Client;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;

public class GameClient extends Listener {
	
	Game game;
	Client client;
	String host;
	
	public GameClient(String host, Game game) throws IOException {
		this.game = game;
		this.host = host;
		client = new Client();
		connect();
	}
	
	public void connect() throws IOException {
		Network.register(client);
		client.addListener(this);
		client.start();
		client.connect(5000, host, Network.PORT);
	}
	
	public void connected(Connection connection) {
		System.out.println("Client connected");
	}
	
	public void received(Connection connection, Object packet) {
		if(packet instanceof GameInitPacket) {
			game.enterPlayState();
		}
		else if(packet instanceof PlayerPacket) {
			World world = game.getPlayState().getWorld();
			PlayerPacket playerPacket = (PlayerPacket) packet;
			Point position = new Point(playerPacket.x, playerPacket.y);
			
			world.update(position);
			System.out.println("world updated");
		}
	}
	
	public void sendActionPacket() {
		client.sendTCP(new ActionPacket());
		System.out.println("sent action packet");
	}

}
