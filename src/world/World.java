package world;

import java.util.HashMap;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Point;

import entities.Player;

public class World {
	
	// fields
	private Level level;
	private Player player;
	private Camera camera;
	
	// util
	private int tileSize;
	private float playerWidth;
	private float playerHeight;
	
 	public World(Level level) throws SlickException {
 		this.level = level;
		this.player = new Player(level.getPlayerSpawn());
		this.camera = new Camera(level, player);
		
		this.tileSize = level.getTileSize();
		playerWidth = player.getWidth();
		playerHeight = player.getHeight();
	}
	
	public void update(HashMap<Integer, Boolean> input, float time) {
		player.update(input, time);
		checkCollision(time);
		camera.update();
	}
	
	public void update(Point playerPosition) {
		player.update(playerPosition);
		camera.update();
	}
	
	public void update(float time) {
		checkCollision(time);
		camera.update();
	}
	 
	public void render(Graphics graphics) {
		int offsetX = camera.getOffsetX();
		int offsetY = camera.getOffsetY();
		
		level.render(offsetX, offsetY);
		graphics.translate(offsetX, offsetY);
		player.render(graphics);
		graphics.draw(player.getRectangle());
	}
	
	public void checkCollision(float time) {
		float velocityX = player.getDx();
		float velocityY = player.getDy();
		float oldX 		= player.getX();
		float oldY 		= player.getY();
		float newX 		= oldX + velocityX * time;
		float newY 		= oldY + velocityY * time;
		
		Float leftCollision   = velocityX > 0 ? null : leftCollision(newX, oldY);
		Float rightCollision  = velocityX < 0 ? null : rightCollision(newX, oldY);
		Float bottomCollision = velocityY < 0 ? null : bottomCollision(oldX, newY, time);
		Float topCollision 	  = velocityY > 0 ? null : topCollision(oldX, newY);
		
		if(bottomCollision != null) {
			player.restoreJump();
			player.setDy(0);
			player.setY(bottomCollision);
		}
		else if(topCollision != null) { 
			player.setDy(0);
			player.setY(topCollision);
		}
		else {
			player.removeJump();
			player.setY(newY);
		}
		
		if(leftCollision != null) {
			player.restoreJump();
			player.setDx(0);
			player.setX(leftCollision);
		}
		else if(rightCollision != null) {
			player.restoreJump();
			player.setDx(0);
			player.setX(rightCollision);
		}
		else {
			player.setX(newX);
		}
		
		player.updateState(leftCollision, rightCollision, bottomCollision, topCollision);
	}
	
	private Float leftCollision(float newX, float oldY) {
		float leftX = newX - playerWidth / 2 - 2;
		float topY = oldY - playerHeight / 2;
		float bottomY = oldY + playerHeight / 2;
		
		Point topLeft = new Point(leftX, topY);
		Point bottomLeft = new Point(leftX, bottomY);
		
		boolean leftBlocked = level.isBlocked(topLeft) || level.isBlocked(bottomLeft);
		
		if(leftBlocked) {
			float adjustedPosition = ((int) leftX / tileSize) * tileSize + tileSize + player.getWidth() / 2 + 1;
			return adjustedPosition;
		}
		else {
			return null;
		}
	}
	
	private Float rightCollision(float newX, float oldY) {
		float rightX = newX + playerWidth / 2;
		float topY = oldY - playerHeight / 2;
		float bottomY = oldY + playerHeight / 2;
		
		Point topRight = new Point(rightX, topY);
		Point bottomRight = new Point(rightX, bottomY);
		
		boolean rightBlocked = level.isBlocked(topRight) || level.isBlocked(bottomRight);
		
		if(rightBlocked) {
			float adjustedPosition = ((int) rightX / tileSize) * tileSize - player.getWidth() / 2;
			return adjustedPosition;
		}
		else {
			return null;
		}
	}
	
	private Float bottomCollision(float oldX, float newY, float time) {
		float leftX = oldX - playerWidth / 2;
		float rightX = oldX + playerWidth / 2 - 1;
		float bottomY = newY + playerHeight / 2 + 1;
		
		Point bottomLeft = new Point(leftX, bottomY);
		Point bottomRight = new Point(rightX, bottomY);
		
		boolean bottomBlocked = level.isBlocked(bottomLeft) || level.isBlocked(bottomRight);
		
		if(bottomBlocked) {
			float adjustedPosition = ((int) bottomY / tileSize) * tileSize - player.getHeight() / 2 - 1;
			return adjustedPosition;
		}
		else {
			return null;
		}
	}
	
	private Float topCollision(float oldX, float newY) {
		float leftX = oldX - playerWidth / 2;
		float rightX = oldX + playerWidth / 2 - 1;
		float topY = newY - playerHeight / 2;
		
		Point topLeft = new Point(leftX, topY);
		Point topRight = new Point(rightX, topY);
		
		boolean topBlocked = level.isBlocked(topLeft) || level.isBlocked(topRight);
		
		if(topBlocked) {
			float adjustedPosition = ((int) topY / tileSize) * tileSize + tileSize + player.getHeight() / 2;
			return adjustedPosition;
		}
		else {
			return null;
		}
	}
	
	public Player getPlayer() {
		return player;
	}
	
}
