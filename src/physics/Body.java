package physics;

import org.newdawn.slick.geom.Point;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.geom.Shape;

public class Body {
	
	private final float MAX_VELOCITY, ACCELERATION, JUMP_IMPULSE, MAX_JUMP_TIME;
	private Point position;
	private Vector velocity;
	private boolean hasJump;
	private float jumpTime;
	private float size;
	
	public Body(float size, Point position, float ACCELERATION, float MAX_VELOCITY, float JUMP_IMPULSE, float MAX_JUMP_TIME) {
		this.size = size;
		this.MAX_VELOCITY = MAX_VELOCITY;
		this.ACCELERATION = ACCELERATION;
		this.JUMP_IMPULSE = JUMP_IMPULSE;
		this.MAX_JUMP_TIME = MAX_JUMP_TIME;
		this.position = position;
		this.velocity = new Vector(0, 0);
		this.hasJump = true;
		jumpTime = 0;
	}
	
	public void idle(int delta) {
		velocity.setX(0);
	}

	public void moveLeft(int delta) {
		float time = delta / 1000.0f;
		float newX = velocity.getX() > 0 ? 0 : velocity.getX();
		newX -= ACCELERATION * time;
		if(newX < -Math.abs(MAX_VELOCITY)) {
			newX = -Math.abs(MAX_VELOCITY);
		}
		velocity.setX(newX);
	}
	
	public void moveRight(int delta) {
		float time = delta / 1000.0f;
		float newX = velocity.getX() < 0 ? 0 : velocity.getX();
		newX += ACCELERATION * time;
		if(newX > Math.abs(MAX_VELOCITY)) {
			newX = Math.abs(MAX_VELOCITY);
		}
		velocity.setX(newX);
	}
	
	public void jump(int delta) {
		if(hasJump) {
			hasJump = false;
			if(jumpTime == -1) {
				jumpTime = 0;
			}
			else if(jumpTime <= MAX_JUMP_TIME) {
				jumpTime += delta;
			}
			else {
				velocity.setY(0);
				return;
			}
			velocity.setY(JUMP_IMPULSE);
		}
	}
	
	public Point getPosition() {
		return position;
	}
	
	public void setPosition(Point p) {
		position = p;
	}
	
	public Vector getVelocity() {
		return velocity;
	}
	
	public void resetJump() {
		hasJump = true;
	}
	
	public Shape getShape() {
		return new Rectangle(getPosition().getX() - size / 2, 
				getPosition().getY() - size / 2, size, size);
	}
	
}
