package networking;

import util.GameInput;
import networking.packets.GameStartPacket;
import networking.packets.InputPacket;
import networking.packets.RolePacket;
import networking.packets.TestPacket;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryonet.EndPoint;

public class Network {
	
	public static final int PORT = 8212;
	
	public static void register(EndPoint endPoint) {
		Kryo kryo = endPoint.getKryo();
		kryo.register(GameInput.class);
		kryo.register(GameRole.class);
		kryo.register(RolePacket.class);
		kryo.register(GameStartPacket.class);		
		kryo.register(InputPacket.class);
		kryo.register(TestPacket.class);
	}

}
