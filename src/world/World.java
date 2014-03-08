package world;

import java.util.ArrayList;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.geom.Line;
import org.newdawn.slick.geom.Point;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.geom.Shape;
import org.newdawn.slick.geom.Vector2f;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.tiled.TiledMap;

import enums.GameRole;
import enums.PlayerState;
import util.WorldInput;

public class World {
	
	// fields
	private CollisionMap tileMap;
	private Player player;
	private Camera camera;
	
	// util
	private int tileSize;
	private float playerWidth;
	private float playerHeight;
	
 	public World(Level level) {
		this.tileMap = level.getTileMap();
		this.player = new Player(level.getPlayerSpawn());
		this.camera = new Camera(tileMap, player);
		
		this.tileSize = tileMap.getTileSize();
		playerWidth = player.getWidth();
		playerHeight = player.getHeight();
	}
	
	public void update(WorldInput input, float time) {
		player.update(input, time);
		correctPlayerPosition(time);
		camera.update();
	}
	 
	public void render(Graphics graphics) {
		camera.render(graphics);
	}
	
	public void correctPlayerPosition(float time) {
		Point correctedPosition = checkCollision(time);
		player.setPosition(correctedPosition);
	}
	
	public Point checkCollision(float time) {
		float oldX = player.getPosition().getX();
		float oldY = player.getPosition().getY();
		
		float velocityX = player.getVelocity().getX();
		float velocityY = player.getVelocity().getY();
		
		float newX = oldX + velocityX * time;
		float newY = oldY + velocityY * time;
		
		float finalX;
		float finalY;
				
		// have to compare x and y directions separately
		Float leftCollision = leftCollision(velocityX, newX, oldY);
		Float rightCollision = rightCollision(velocityX, newX, oldY);
		Float bottomCollision = bottomCollision(velocityY, oldX, newY);
		Float topCollision = topCollision(velocityY, oldX, newY);
		
		if(leftCollision != null) {
			finalX = leftCollision;
			player.setDx(0);
		}
		else if(rightCollision != null) {
			finalX = rightCollision;
			player.setDx(0);
		}
		else {
			finalX = newX;
		}
			
		if(bottomCollision != null) {
			finalY = bottomCollision;
			player.setDy(0);
			player.restoreJump();
		}
		else if(topCollision != null) {
			finalY = topCollision;
			player.setDy(0);
		}
		else {
			finalY = newY;
		}
		
		// set player state to change physics based on state
		if(bottomCollision != null){
			player.setState(PlayerState.FLOOR);
		}
		else if(leftCollision != null) {
			player.setState(PlayerState.LEFT_WALL);
			player.restoreJump();
		}
		else if(rightCollision != null) {
			player.setState(PlayerState.RIGHT_WALL);
			player.restoreJump();
		}
		else {
			player.setState(PlayerState.AIR);
			player.removeJump();
		}
		
		player.setPositionChanged(oldX != finalX || oldY != finalY);
		return new Point(finalX, finalY);
	}
	
	public Float leftCollision(float velocityX, float newX, float oldY) {
		if(velocityX > 0) return null;
		
		float leftX = newX - playerWidth / 2 - 2;
		float topY = oldY - playerHeight / 2;
		float bottomY = oldY + playerHeight / 2;
		
		Point topLeft = new Point(leftX, topY);
		Point bottomLeft = new Point(leftX, bottomY);
		
		boolean leftBlocked = tileMap.isBlocked(topLeft) || tileMap.isBlocked(bottomLeft);
		
		if(leftBlocked) {
			float adjustedPosition = ((int) leftX / tileSize) * tileSize + tileSize + player.getWidth() / 2 + 1; 
			return adjustedPosition;
		}
		else {
			return null;
		}
	}
	
	public Float rightCollision(float velocityX, float newX, float oldY) {
		if(velocityX < 0)  return null;
		
		float rightX = newX + playerWidth / 2;
		float topY = oldY - playerHeight / 2;
		float bottomY = oldY + playerHeight / 2;
		
		Point topRight = new Point(rightX, topY);
		Point bottomRight = new Point(rightX, bottomY);
		
		boolean rightBlocked = tileMap.isBlocked(topRight) || tileMap.isBlocked(bottomRight);
		
		if(rightBlocked) {
			float adjustedPosition = ((int) rightX / tileSize) * tileSize - player.getWidth() / 2;
			return adjustedPosition;
		}
		else {
			return null;
		}
	}
	
	public Float bottomCollision(float velocityY, float oldX, float newY) {
		if(velocityY < 0) return null;
		
		float leftX = oldX - playerWidth / 2;
		float rightX = oldX + playerWidth / 2 - 1;
		float bottomY = newY + playerHeight / 2 + 1;
		
		Point bottomLeft = new Point(leftX, bottomY);
		Point bottomRight = new Point(rightX, bottomY);
		
		boolean bottomBlocked = tileMap.isBlocked(bottomLeft) || tileMap.isBlocked(bottomRight);
		
		if(bottomBlocked) {
			float adjustedPosition = ((int) bottomY / tileSize) * tileSize - player.getHeight() / 2 - 1;
			return adjustedPosition;
		}
		else {
			return null;
		}
	}
	
	public Float topCollision(float velocityY, float oldX, float newY) {
		if(velocityY > 0) return null;
		
		float leftX = oldX - playerWidth / 2;
		float rightX = oldX + playerWidth / 2 - 1;
		float topY = newY - playerHeight / 2;
		
		Point topLeft = new Point(leftX, topY);
		Point topRight = new Point(rightX, topY);
		
		boolean topBlocked = tileMap.isBlocked(topLeft) || tileMap.isBlocked(topRight);
		
		if(topBlocked) {
			float adjustedPosition = ((int) topY / tileSize) * tileSize + tileSize + player.getHeight() / 2;
			return adjustedPosition;
		}
		else {
			return null;
		}
	}
	
}
