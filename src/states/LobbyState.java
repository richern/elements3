package states;

import java.net.InetAddress;
import java.net.UnknownHostException;

import main.Game;
import networking.GameServer;
import networking.GameClient;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

public class LobbyState extends BasicGameState {
	
	@Override
	public void init(GameContainer arg0, StateBasedGame arg1)
			throws SlickException {
	}
	
	@Override
	public void enter(GameContainer container, StateBasedGame sbg) {
	}
	
	@Override
	public void update(GameContainer arg0, StateBasedGame arg1, int arg2)
			throws SlickException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void render(GameContainer container, StateBasedGame sbg, Graphics graphics)
			throws SlickException {
		int x = 30;
		int y = 30;
		graphics.setColor(Color.white);
		String hostIP = GameClient.getHostIP().toString();
		graphics.drawString(hostIP, x, y);
		
		y = 60;
		
	}

	@Override
	public int getID() {
		return GameState.LobbyState.getID();
	}

}
