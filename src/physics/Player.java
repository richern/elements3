package physics;

import org.newdawn.slick.Input;
import org.newdawn.slick.geom.Point;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.geom.Shape;
import org.newdawn.slick.geom.Vector2f;

public class Player {
	
	// The player can attempt to set its position, but ultimately the world will check and correct for collision
	
	private final float WIDTH = 21;
	private final float HEIGHT = 21;
	
	private final float MAX_SPEED = 600;
	private final float ACCELERATION = 1200;
	private final float GRAVITY = 1000;
	private final float MAX_FALL_SPEED = 1000;
	
	private final float JUMP_IMPULSE = 800;
	private final float MAX_JUMP_TIME = 1000;
	
	private float x;
	private float y;
	private float dx;
	private float dy;
	
	private boolean hasJump;
	
	public Player(Point position) {
		this.x = position.getX();
		this.y = position.getY();
		this.dx = 0;
		this.dy = 0;
		this.hasJump = true;
	}
	
	public void update(Input input, int delta) {
		float time = delta / 1000.0f;
		
		if(input.isKeyDown(Input.KEY_LEFT)) {
			moveLeft(time);
		}
		if(input.isKeyDown(Input.KEY_RIGHT)) {
			moveRight(time);
		}
		if(!input.isKeyDown(Input.KEY_LEFT) && !input.isKeyDown(Input.KEY_RIGHT)) {
			idle(time);
		}
		if(input.isKeyDown(Input.KEY_UP)) {
			jump(time);
		}
		
		gravity(time);
		
		x += dx * time;
		y += dy * time;
	}
	
	public void idle(float time) {
		dx = 0;
	}

	public void moveLeft(float time) {
		float minVelocity = -MAX_SPEED;
		float newdx = dx > 0 ? 0 : dx;
		
		newdx -= ACCELERATION * time;
		newdx = newdx < minVelocity ? minVelocity : newdx;
		
		dx = newdx;
	}
	
	public void moveRight(float time) {
		float maxVelocity = MAX_SPEED;
		float newdx = dx < 0 ? 0 : dx;
		
		newdx += ACCELERATION * time;
		newdx = newdx > maxVelocity ? maxVelocity : newdx;
		
		dx = newdx;
	}
	
	public void jump(float time) {
	}
	
	public void gravity(float time) {
		float newdy = dy + GRAVITY * time;
		newdy = newdy > MAX_FALL_SPEED ? MAX_FALL_SPEED : newdy;
		dy = newdy;
	}
	
	// getters & setters
	public float getWidth() {
		return WIDTH;
	}
	
	public float getHeight() {
		return HEIGHT;
	}
	
	public void setX(float x) {
		this.x = x;
	}
	
	public void setY(float y) {
		this.y = y;
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
	
	public Rectangle getRectangle() {
		float topLeftX = x - (WIDTH - 1) / 2;
		float topLeftY = y - (HEIGHT - 1) / 2;
		return new Rectangle(topLeftX, topLeftY, WIDTH, HEIGHT);
	}
	
}
