package states;

import java.io.IOException;

import main.Game;
import networking.GameClient;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

public class FindLobbyState extends BasicGameState {
	
	String hostIP;

	@Override
	public void init(GameContainer arg0, StateBasedGame arg1)
			throws SlickException {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void update(GameContainer container, StateBasedGame sbg, int delta)
			throws SlickException {
		Input input = container.getInput();
		boolean enter = input.isKeyDown(Input.KEY_ENTER);
		
		if(enter) {
			try {
				Game.setClient(new GameClient("localhost", sbg));
			} catch (IOException e) {
				e.printStackTrace();
			}
			sbg.enterState(GameState.LobbyState.getID());
		}
	}

	@Override
	public void render(GameContainer arg0, StateBasedGame arg1, Graphics arg2)
			throws SlickException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public int getID() {
		return GameState.FindLobbyState.getID();
	}

}
