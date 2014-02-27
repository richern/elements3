package networking;

import java.io.IOException;
import java.net.InetSocketAddress;

import org.newdawn.slick.state.StateBasedGame;

import states.GameState;
import states.PlayState;
import util.GameInput;
import networking.packets.GameStartPacket;
import networking.packets.InputPacket;
import networking.packets.RolePacket;

import com.esotericsoftware.kryonet.Client;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;

public class GameClient extends Listener {
	
	static Client client;
	static InetSocketAddress hostIP;
	StateBasedGame sbg;
	
	
	public GameClient(String host, StateBasedGame sbg) throws IOException {
		this.sbg = sbg;
		client = new Client();
		Network.register(client);
		client.addListener(this);
		client.start();
		client.connect(5000, host, Network.PORT);
	}
	
	public void connected(Connection connection) {
		hostIP = connection.getRemoteAddressTCP();
		System.out.println("Client connected to " + hostIP);
	}
	
	public void received(Connection connection, Object packet) {
		if(packet instanceof RolePacket) {
			PlayState.role = ((RolePacket) packet).role;
			System.out.println("received role packet " + ((RolePacket) packet).toString());
		}
		else if(packet instanceof GameStartPacket) {
			sbg.enterState(GameState.PlayState.getID());
		}
		else if(packet instanceof InputPacket) {
			InputPacket inputPacket = (InputPacket) packet;
			PlayState.input = inputPacket.input;
			int delta = inputPacket.delta;
			PlayState.getWorld().update(PlayState.input, delta);
		}
	}
	
	public static void sendInputPacket(GameInput input, int delta) {
		InputPacket inputPacket = new InputPacket();
		inputPacket.input = input;
		inputPacket.delta = delta;
		client.sendTCP(inputPacket);
	}
	
	public static InetSocketAddress getHostIP() {
		return hostIP;
	}
	
	public static Client getClient() {
		return client;
	}

}
