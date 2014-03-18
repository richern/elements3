	package networking;

import java.io.IOException;
import java.net.InetAddress;

import org.newdawn.slick.SlickException;

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
	
	public GameClient(Game game) throws IOException {
		this.game = game;
		client = new Client();
		InetAddress address = client.discoverHost(Network.PORT, Network.PORT);
		//this.host = address.toString().replace("/", "");
		this.host = "localhost";
		connect();
	}
	
	public void connect() throws IOException {
		Network.register(client);
		client.addListener(this);
		client.start();
		client.connect(5000, host, Network.PORT, Network.PORT);
	}
	
	public void connected(Connection connection) {
		System.out.println("Client connected");
	}
	
	public void received(Connection connection, Object packet) {
		if(packet instanceof PlayerPacket) {
			World world = game.getPlayState().getWorld();
			PlayerPacket playerPacket = (PlayerPacket) packet;

			world.update(playerPacket.x, playerPacket.y, playerPacket.ddx);
		}
		else if(packet instanceof NextLevelPacket) {
			try {
				game.getPlayState().nextLevel(game);
			} catch (SlickException e) {
				e.printStackTrace();
			}
		}
	}
	
	public void sendActionPacket(boolean pressed) {
		ActionPacket packet = new ActionPacket();
		packet.pressed = pressed;
		client.sendTCP(packet);
	}

}
