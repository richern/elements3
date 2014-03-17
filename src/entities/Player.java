package entities;

import java.util.HashMap;

import org.newdawn.slick.Animation;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.SpriteSheet;
import org.newdawn.slick.geom.Point;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.geom.Vector2f;

import enums.PlayerState;

public class Player {
	
	// The player does not set its position, just velocity
	// The world will check for collision and set position
	
	private static final float COLLISION_WIDTH = 60;
	private static final float COLLISION_HEIGHT = 90;
	private static final int SPRITE_WIDTH = 96;
	private static final int SPRITE_HEIGHT = 96;
	
	private static final float MAX_SPEED = 600;
	private static final float ACCELERATION = 1200;
	private static final float GRAVITY = 1500;
	private static final float MAX_FALL_SPEED = 1000;
	
	private static final float JUMP_SPEED = 600;
	private static final float MIN_JUMP_TIME = 50;
	private static final float MAX_JUMP_TIME = 250;
	
	private static final float MAX_WALL_TIME = 500; // time player is stuck to wall when moving in opposite direction
	private static final float WALL_JUMP_SPEED = MAX_SPEED / 2;
	private static final float WALL_GRAVITY = GRAVITY / 3;
	private static final float WALL_SLIDE_SPEED = MAX_FALL_SPEED / 5;
	
	private static final int animationSpeed = 50;
	
	private float x;
	private float y;
	private float dx;
	private float dy;
	private float ddx;
	
	boolean left;
	boolean right;
	boolean up;
	
	private boolean hasJump;
	private boolean jumping;
	private boolean jumpReleased;
	private float jumpTime;
	private float wallTime;
	
	private PlayerState state;
	
	private boolean positionChanged; // lets camera know to update
	
	Float leftCollision;
	Float rightCollision;
	Float bottomCollision;
	Float topCollision;
	
	SpriteSheet spritesheet;
	Animation idlingLeft;
	Animation idlingRight;
	Animation walkingLeft;
	Animation walkingRight;
	Animation jumpingLeft;
	Animation jumpingRight;
	Animation wallingLeft;
	Animation wallingRight;
	
	public Player(Point position) throws SlickException {
		this.x = position.getX();
		this.y = position.getY();
		this.dx = 0;
		this.dy = 0;
		
		this.left = false;
		this.right = false;
		this.up = false;
		
		this.hasJump = false;
		this.jumping = false;
		this.jumpReleased = false;
		this.jumpTime = 0;
		this.wallTime = 0;
		
		this.state = PlayerState.IDLE_RIGHT;
		
		this.positionChanged = false;
		
		spritesheet = new SpriteSheet("resources/spritesheets/player.png", SPRITE_WIDTH, SPRITE_HEIGHT);
		idlingLeft = new Animation(spritesheet, 2, 0, 2, 0, true, animationSpeed, true);
		idlingRight = new Animation(spritesheet, 4, 1, 4, 1, true, animationSpeed, true);
		walkingLeft = new Animation(spritesheet, 3, 0, 4, 0, true, animationSpeed, true);
		walkingRight = new Animation(spritesheet, 0, 2, 2, 2, true, animationSpeed, true);
		jumpingLeft = new Animation(spritesheet, 1, 0, 1, 0, true, animationSpeed, true);
		jumpingRight = new Animation(spritesheet, 3, 1, 3, 1, true, animationSpeed, true);
		wallingLeft = new Animation(spritesheet, 3, 2, 3, 2, true, animationSpeed, true);
		wallingRight = new Animation(spritesheet, 1, 1, 1, 1, true, animationSpeed, true);
	}
	
	public void update(HashMap<Integer, Boolean> input, float time) {
		boolean leftKey = input.get(Input.KEY_LEFT);
		boolean rightKey = input.get(Input.KEY_RIGHT);
		boolean upKey = input.get(Input.KEY_UP);
		
		left = left ^ leftKey;
		right = right ^ rightKey;
		up = up ^ upKey;
		
		if(left && right || !left && !right) idle(time);
		if(left) 	moveLeft(time);
		if(right) 	moveRight(time);
		if(up) 		jump(time);
		if(!up) 	notJumping(time);
		gravity(time);
		
		positionChanged = false;
	}
	
	public void update(Point playerPosition) {
		x = playerPosition.getX();
		y = playerPosition.getY();
	}
	
	public void idle(float time) {
		if(state.isOnGround()) {
			ddx = 0;
			dx = 0;
		}
	}

	public void moveLeft(float time) {
		if(state.isOnRightWall() && wallTime < MAX_WALL_TIME) {
			wallTime += time * 1000.0f;
		}
		else {
			wallTime = 0;
			float minVelocity = -MAX_SPEED;
			float newdx = dx > 0 && state.isOnGround() ? 0 : dx;
			ddx = -ACCELERATION;
	
			newdx += ddx * time;
			newdx = newdx < minVelocity ? minVelocity : newdx;
			
			dx = newdx;
		}
	}
	
	public void moveRight(float time) {
		if(state.isOnLeftWall() && wallTime < MAX_WALL_TIME) {
			wallTime += time * 1000.0f;
		}
		else {
			wallTime = 0;
			float maxVelocity = MAX_SPEED;
			float newdx = dx < 0 && state.isOnGround() ? 0 : dx;
			ddx = ACCELERATION;
			
			newdx += ddx * time;
			newdx = newdx > maxVelocity ? maxVelocity : newdx;
			
			dx = newdx;
		}
	}
	
	public void jump(float time) {
		if(hasJump && jumpReleased) {
			if(state.isOnLeftWall())
				dx = WALL_JUMP_SPEED;
			else if(state.isOnRightWall())
				dx = -WALL_JUMP_SPEED;
			jumpReleased = false;
			hasJump = false;
			jumping = true;
		}
		if(jumping) {
			if(jumpTime > MAX_JUMP_TIME) {
				jumping = false;
			}
			else {
				dy = -JUMP_SPEED;
				jumpTime += time * 1000.0f;
			}
		}
	}
	
	public void notJumping(float time) {
		jumpReleased = true;
		jumping = false;
	}
	
	public void gravity(float time) {
		boolean wall = state.isOnWall();
		float gravity = wall ? WALL_GRAVITY : GRAVITY;
		float maxFallSpeed = wall ? WALL_SLIDE_SPEED : MAX_FALL_SPEED;
		
		float newdy = dy + gravity * time;
		newdy = newdy > maxFallSpeed ? maxFallSpeed : newdy;
		dy = newdy;
	}
	
	public void updateState(Float leftCollision, Float rightCollision, Float bottomCollision, Float topCollision) {
		boolean noCollision = leftCollision == null && rightCollision == null
				&& bottomCollision == null && topCollision == null;
		if(noCollision)	{
			if(ddx > 0) 
				 state = PlayerState.JUMP_RIGHT;
			else state = PlayerState.JUMP_LEFT;
		}
		if(bottomCollision != null) {
			if(dx > 0)
				state = PlayerState.WALK_RIGHT;
			else if(dx < 0)
				state = PlayerState.WALK_LEFT;
			else if(state.isFacingRight())
				state = PlayerState.IDLE_RIGHT;
			else
				state = PlayerState.IDLE_LEFT;
		}
		else if(leftCollision != null) 
			state = PlayerState.WALL_LEFT;
		else if(rightCollision != null) 
			state = PlayerState.WALL_RIGHT;
	}
	
	public void render(Graphics graphics) {
		float topLeftX = x - SPRITE_WIDTH / 2;
		float topLeftY = y - SPRITE_HEIGHT / 2;

		Animation animation = new Animation();
		switch(state) {
		case IDLE_LEFT:
			animation = idlingLeft;
			break;
		case IDLE_RIGHT:
			animation = idlingRight;
			break;
		case WALK_LEFT:
			animation = walkingLeft;
			break;
		case WALK_RIGHT:
			animation = walkingRight;
			break;
		case JUMP_LEFT:
			animation = jumpingLeft;
			break;
		case JUMP_RIGHT:
			animation = jumpingRight;
			break;
		case WALL_LEFT:
			animation = wallingLeft;
			break;
		case WALL_RIGHT:
			animation = wallingRight;
			break;
		}
		
		graphics.drawAnimation(animation, topLeftX, topLeftY);
	}
		
	// getters & setters
	public float getWidth() {
		return COLLISION_WIDTH;
	}
	
	public float getHeight() {
		return COLLISION_HEIGHT;
	}
	
	public float getX() {
		return x;
	}
	
	public float getY() {
		return y;
	}
	
	public float getDx() {
		return dx;
	}
	
	public float getDy() {
		return dy;
	}
	
	public Point getPosition() {
		return new Point(x, y);
	}
	
	public void setX(float x) {
		if(this.x != x)
			positionChanged = true;
		this.x = x;
	}
	
	public void setY(float y) {
		if(this.y != y)
			positionChanged = true;
		this.y = y;
	}
	
	public void setDx(float dx) {
		this.dx = dx;
	}
	
	public void setDy(float dy) {
		this.dy = dy;
	}
	
	public Vector2f getVelocity() {
		return new Vector2f(dx, dy);
	}
	
	public Rectangle getRectangle() {
		float topLeftX = x - COLLISION_WIDTH / 2;
		float topLeftY = y - COLLISION_HEIGHT / 2;
		return new Rectangle(topLeftX, topLeftY, COLLISION_WIDTH, COLLISION_HEIGHT);
	}
	
	public void restoreJump() {
		if(!jumping) {
			hasJump = true;
			jumpTime = 0;
		}
	}
	
	public void removeJump() {
		hasJump = false;
	}
	
	public void setState(PlayerState state) {
		this.state = state;
	}
	
	public PlayerState getState() {
		return state;
	}
	
	public boolean isPositionChanged() {
		return positionChanged;
	}
 
}
