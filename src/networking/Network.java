package networking;

import networking.packets.*;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryonet.EndPoint;

import enums.GameRole;

public class Network {
	
	public static final int PORT = 8212;
	
	public static void register(EndPoint endPoint) {
		Kryo kryo = endPoint.getKryo();
		kryo.register(GameRole.class);
		kryo.register(GameInitPacket.class);
		kryo.register(ActionPacket.class);
		kryo.register(PlayerPacket.class);
	}

}
