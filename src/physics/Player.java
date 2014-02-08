package physics;

import org.newdawn.slick.geom.Point;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.geom.Shape;
import org.newdawn.slick.geom.Vector2f;

public class Player {
	
	private final float SIZE = 32;
	private final float MAX_VELOCITY = 600;
	private final float ACCELERATION = 1200;
	private final float JUMP_IMPULSE = 800;
	private final float MAX_JUMP_TIME = 1000;
	
	private Point position;
	private Vector2f velocity;
	private boolean hasJump;
	
	public Player(Point position) {
		this.position = position;
		this.velocity = new Vector2f(0, 0);
		this.hasJump = true;
	}
	
	public void idle(int delta) {
		float y = velocity.getY();
		velocity.set(0, y);
	}

	public void moveLeft(int delta) {
		float time = delta / 1000.0f;
		float y = velocity.getY();
		float newX = velocity.getX() > 0 ? 0 : velocity.getX();
		newX -= ACCELERATION * time;
		if(newX < -Math.abs(MAX_VELOCITY)) {
			newX = -Math.abs(MAX_VELOCITY);
		}
		velocity.set(newX, y);
	}
	
	public void moveRight(int delta) {
		float time = delta / 1000.0f;
		float y = velocity.getY();
		float newX = velocity.getX() < 0 ? 0 : velocity.getX();
		newX += ACCELERATION * time;
		if(newX > Math.abs(MAX_VELOCITY)) {
			newX = Math.abs(MAX_VELOCITY);
		}
		velocity.set(newX, y);
	}
	
	public void jump(int delta) {
	}
	
	public Point getPosition() {
		return position;
	}
	
	public void setPosition(Point p) {
		position = p;
	}
	
	public Vector2f getVelocity() {
		return velocity;
	}
	
	public void resetJump() {
		hasJump = true;
	}
	
	public Rectangle2 getShape() {
		return new Rectangle2(position, SIZE, SIZE);
	}
	
	public float getSize() {
		return SIZE;
	}
	
	public float getRadius() {
		return SIZE / 2;
	}
	
}
