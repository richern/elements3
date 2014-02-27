package util;

import networking.GameRole;

import org.newdawn.slick.Input;

import states.PlayState;

// wrapper for slick.input

public class GameInput {
	
	public boolean left = false;
	public boolean right = false;
	public boolean up = false;
	public boolean enter = false;
	
	public void registerInput(Input input) {
		switch(PlayState.role) {
		case ALL:
			left = input.isKeyDown(Input.KEY_LEFT);
			right = input.isKeyDown(Input.KEY_RIGHT);
			up = input.isKeyDown(Input.KEY_SPACE);
			break;
		case LEFT:
			left = input.isKeyDown(Input.KEY_SPACE);
			break;
		case RIGHT:
			right = input.isKeyDown(Input.KEY_SPACE);
			break;
		case JUMP:
			up = input.isKeyDown(Input.KEY_SPACE);
			break;
		}
	}
	
}
