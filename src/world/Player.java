package world;

import org.newdawn.slick.Input;
import org.newdawn.slick.geom.Point;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.geom.Shape;
import org.newdawn.slick.geom.Vector2f;

public class Player {
	
	// The player can attempt to set its position, but ultimately the world will check and correct for collision
	
	private final float WIDTH = 22;
	private final float HEIGHT = 22;
	
	private final float MAX_SPEED = 600;
	private final float ACCELERATION = 1200;
	private final float GRAVITY = 1500;
	private final float MAX_FALL_SPEED = 1000;
	
	private final float JUMP_SPEED = 400;
	private final float MIN_JUMP_TIME = 50;
	private final float MAX_JUMP_TIME = 200;
	
	private final float MAX_WALL_TIME = 500;
	private final float WALL_JUMP_SPEED = MAX_SPEED / 2;
	private final float WALL_GRAVITY = GRAVITY / 3;
	private final float WALL_SLIDE_SPEED = MAX_FALL_SPEED / 5;
	
	private float x;
	private float y;
	private float dx;
	private float dy;
	
	private boolean hasJump;
	private boolean jumping;
	private boolean jumpReleased;
	private float jumpTime;
	private float wallTime;
	
	private PlayerState state;
	
	public Player(Point position) {
		this.x = position.getX();
		this.y = position.getY();
		this.dx = 0;
		this.dy = 0;
		
		this.hasJump = false;
		this.jumping = false;
		this.jumpReleased = false;
		this.jumpTime = 0;
		this.wallTime = 0;
		
		this.state = PlayerState.INIT;
	}
	
	public void update(Input input, int delta) {
		float time = delta / 1000.0f;
		boolean left = input.isKeyDown(Input.KEY_LEFT);
		boolean right = input.isKeyDown(Input.KEY_RIGHT);
		boolean up = input.isKeyDown(Input.KEY_UP) || input.isKeyDown(Input.KEY_SPACE);
		
		if(left && right || !left && !right) idle(time);
		if(left) moveLeft(time);
		if(right) moveRight(time);
		if(up) jump(time);
		if(!up) notJumping(time);
				
		gravity(time);
	}
	
	public void idle(float time) {
		if(state == PlayerState.FLOOR) {
			dx = 0;
		}
	}

	public void moveLeft(float time) {
		if(state == PlayerState.RIGHT_WALL && wallTime < MAX_WALL_TIME) {
			wallTime += time * 1000.0f;
		}
		else {
			wallTime = 0;
			float minVelocity = -MAX_SPEED;
			float newdx = dx > 0 && state == PlayerState.FLOOR ? 0 : dx;
			
			newdx -= ACCELERATION * time;
			newdx = newdx < minVelocity ? minVelocity : newdx;
			
			dx = newdx;
		}
	}
	
	public void moveRight(float time) {
		if(state == PlayerState.LEFT_WALL && wallTime < MAX_WALL_TIME) {
			wallTime += time * 1000.0f;
		}
		else {
			wallTime = 0;
			float maxVelocity = MAX_SPEED;
			float newdx = dx < 0 && state == PlayerState.FLOOR ? 0 : dx;
			
			newdx += ACCELERATION * time;
			newdx = newdx > maxVelocity ? maxVelocity : newdx;
			
			dx = newdx;
		}
	}
	
	public void jump(float time) {
		if(hasJump && jumpReleased) {
			dy = -JUMP_SPEED;
			if(state == PlayerState.LEFT_WALL) {
				dx = WALL_JUMP_SPEED;
			}
			else if(state == PlayerState.RIGHT_WALL) {
				dx = -WALL_JUMP_SPEED;
			}
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
		boolean wall = state == PlayerState.LEFT_WALL || state == PlayerState.RIGHT_WALL;
		float gravity = wall ? WALL_GRAVITY : GRAVITY;
		float maxFallSpeed = wall ? WALL_SLIDE_SPEED : MAX_FALL_SPEED;
		
		float newdy = dy + gravity * time;
		newdy = newdy > maxFallSpeed ? maxFallSpeed : newdy;
		dy = newdy;
	}
	
	// getters & setters
	public float getWidth() {
		return WIDTH;
	}
	
	public float getHeight() {
		return HEIGHT;
	}
	
	public Point getPosition() {
		return new Point(x, y);
	}
	
	public void setPosition(Point p) {
		x = p.getX();
		y = p.getY();
	}
	
	public Vector2f getVelocity() {
		return new Vector2f(dx, dy);
	}
	
	public void setDx(float dx) {
		this.dx = dx;
	}
	
	public void setDy(float dy) {
		this.dy = dy;
	}
	
	public Rectangle getRectangle() {
		float topLeftX = x - WIDTH / 2;
		float topLeftY = y - HEIGHT / 2;
		return new Rectangle(topLeftX, topLeftY, WIDTH, HEIGHT);
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
 
}
