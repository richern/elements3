package world;

import java.util.ArrayList;
import java.util.HashMap;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Line;
import org.newdawn.slick.geom.Point;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.geom.Shape;
import org.newdawn.slick.geom.Vector2f;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.tiled.TiledMap;

import enums.GameRole;
import enums.PlayerState;
import util.GlobalInput;

public class World {
	
	// fields
	private Level level;
	private Player player;
	private Camera camera;
	
	// util
	private CollisionMap tileMap;
	private int tileSize;
	private Point oldPlayerPosition;
	private float playerWidth;
	private float playerHeight;
	
 	public World(Level level) throws SlickException {
 		this.level = level;
		this.tileMap = level.getTileMap();
		this.player = new Player(level.getPlayerSpawn());
		this.camera = new Camera(tileMap, player);
		
		this.tileSize = tileMap.getTileSize();
		playerWidth = player.getWidth();
		playerHeight = player.getHeight();
	}
	
	public void update(HashMap<Integer, Boolean> input, float time) {
		oldPlayerPosition = player.getPosition();
		checkCollision();
		player.update(input, time);
		camera.update();
	}
	
	public void update(Point playerPosition) {
		oldPlayerPosition = player.getPosition();
		player.update(playerPosition);
		camera.update();
	}
	
	public void update(float time) {
		oldPlayerPosition = player.getPosition();
		checkCollision();
		camera.update();
	}
	 
	public void render(Graphics graphics) {
		int offsetX = camera.getOffsetX();
		int offsetY = camera.getOffsetY();
		
		tileMap.render(offsetX, offsetY);
		graphics.translate(offsetX, offsetY);
		player.render(graphics);
		graphics.draw(player.getRectangle());
	}
	
	public void checkCollision() {
		float velocityX = player.getDx();
		float velocityY = player.getDy();
		float oldX = oldPlayerPosition.getX();
		float oldY = oldPlayerPosition.getY();
		float newX = player.getX();
		float newY = player.getY();
		
		Float leftCollision   = velocityX > 0 ? null : leftCollision(newX, oldY);
		Float rightCollision  = velocityX < 0 ? null : rightCollision(newX, oldY);
		Float bottomCollision = velocityY < 0 ? null : bottomCollision(oldX, newY);
		Float topCollision 	  = velocityY > 0 ? null : topCollision(oldX, newY);
		Collision collision = new Collision(leftCollision, rightCollision, topCollision, bottomCollision);
		
		notifyCollision(collision);
	}
	
	public void notifyCollision(Collision collision) {
		player.handleCollisions(collision);
	}
	
	private Float leftCollision(float newX, float oldY) {
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
	
	private Float rightCollision(float newX, float oldY) {
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
	
	private Float bottomCollision(float oldX, float newY) {
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
	
	private Float topCollision(float oldX, float newY) {
		float leftX = oldX - playerWidth / 2;
		float rightX = oldX + playerWidth / 2 - 1;
		float topY = newY - playerHeight / 2 - 1;
		
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
	
	public Player getPlayer() {
		return player;
	}
	
}
