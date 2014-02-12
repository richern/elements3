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

public class World {
	
	// fields
	private Level level;
	private Player player;
	private CollisionMap tileMap;
	private int tileSize;
	private float playerWidth;
	private float playerHeight;
	
 	public World(Level level) {
		this.level = level;
		this.player = new Player(level.getPlayerSpawn());
		this.tileMap = level.getTileMap();
		this.tileSize = level.getTileMap().getTileSize();
		this.playerWidth = player.getWidth();
		this.playerHeight = player.getHeight();
	}
	
	public void update(Input input, int delta) {
		updatePlayerPosition(input, delta);
	}
	 
	public void render(Graphics graphics) {
		level.getTileMap().render(0, 0);
		graphics.draw(player.getRectangle());
	}
	
	public Player getPlayer() {
		return player;
	}
	
	public void updatePlayerPosition(Input input, int delta) {
		float time = delta / 1000.0f;
		Point newPosition = checkCollision(time);
		player.setPosition(newPosition);
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
		
		if(leftCollision != null && bottomCollision == null) {
			player.setState(PlayerState.WALL);
		}
		else {
			player.setState(PlayerState.INIT);
		}
	
		return new Point(finalX, finalY);
	}
	
	public Float leftCollision(float velocityX, float newX, float oldY) {
		if(velocityX > 0) return null;
		
		float leftX = newX - playerWidth / 2 - 1;
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
		float bottomY = newY + playerHeight / 2;
		
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
