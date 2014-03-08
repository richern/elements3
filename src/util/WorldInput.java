package util;

import org.newdawn.slick.Input;

import enums.GameRole;
import states.PlayState;

// wrapper for slick.input

public class WorldInput extends GameInput {
	
	public boolean left = false;
	public boolean right = false;
	public boolean up = false;
	
	@SuppressWarnings("incomplete-switch")
	public void update(GameRole role, boolean pressed) {
		switch(role) {
		case LEFT:
			left = pressed;
			break;
		case RIGHT:
			right = pressed;
			break;
		case JUMP:
			up = pressed;
			break;
		}
	}
	
	//TODO:
	/*public void update(GameRole role, Input input) {
		if(role == GameRole.ALL) {
			left = input.isKeyDown(Input.KEY_LEFT);
			right = input.isKeyDown(Input.KEY_RIGHT);
			up = input.isKeyDown(Input.KEY_SPACE);
		}
		else {
			update(role, input.isKeyDown(Input.KEY_SPACE));
		}
	}*/
	
}
