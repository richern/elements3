package networking.packets;

import networking.GameRole;

public class RolePacket {
	
	public GameRole role;
	
	public String toString() {
		return role.name();
	}

}
