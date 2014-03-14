package enums;

public enum PlayerState {
	
	IDLE_LEFT, IDLE_RIGHT,
	WALK_LEFT, WALK_RIGHT,
	JUMP_LEFT, JUMP_RIGHT,
	WALL_LEFT, WALL_RIGHT;
	
	public boolean isFacingLeft() {
		return this == PlayerState.IDLE_LEFT || this == PlayerState.WALK_LEFT ||
				this == PlayerState.JUMP_LEFT || this == PlayerState.WALL_LEFT;
	}
	
	public boolean isFacingRight() {
		return this == PlayerState.IDLE_RIGHT || this == PlayerState.WALK_RIGHT ||
				this == PlayerState.JUMP_RIGHT || this == PlayerState.WALL_RIGHT;
	}
	
	public boolean isOnFloor() {
		return this == PlayerState.IDLE_LEFT || this == PlayerState.IDLE_RIGHT ||
				this == PlayerState.WALK_LEFT || this == PlayerState.WALK_RIGHT;
	}
	
	public boolean isInAir() {
		return this == PlayerState.JUMP_LEFT || this == PlayerState.JUMP_RIGHT;
	}
	
	public boolean isOnLeftWall() {
		return this == PlayerState.WALL_LEFT;
	}
	
	public boolean isOnRightWall() {
		return this == PlayerState.WALL_RIGHT;
	}
	
}