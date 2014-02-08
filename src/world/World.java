package world;

import java.util.ArrayList;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.geom.Line;
import org.newdawn.slick.geom.Point;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.geom.Shape;
import org.newdawn.slick.geom.Vector2f;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.tiled.TiledMap;

import physics.Player;
import physics.Rectangle2;

public class World {
	
	// physics
	private static final float GRAVITY = 150;
	
	// fields
	private Level level;
	private Player player;
	
 	public World(Level level) {
		this.level = level;
		player = new Player(level.getPlayerSpawn());
	}
	
	public void update(GameContainer container, StateBasedGame sbg, int delta) {
		updatePlayerPosition(delta);
	}
	 
	public void render(GameContainer container, StateBasedGame sbg, Graphics graphics) {
		level.getTileMap().render(0, 0);
		graphics.draw(player.getShape().toRectangle());
	}
	
	public Player getPlayer() {
		return player;
	}
	
	public void updatePlayerPosition(int delta) {
		float time = delta / 1000.0f;
		
		float oldX = player.getPosition().getX();
		float oldY = player.getPosition().getY();
		
		float velocityX = player.getVelocity().getX();
		float velocityY = player.getVelocity().getY();
		
		// d1 = d0 + v*t
		float newX = oldX + velocityX * time;
		float newY = oldY - (velocityY - GRAVITY) * time;
		
		Point newPosition = checkCollision(new Point(newX, newY));
		player.setPosition(newPosition);
	}
	
	public Point checkCollision(Point position) {
		Point newPosition = position;
		int tileSize = level.getTileMap().getTileHeight();
		boolean[][] blocked = level.getBlocked();
		Rectangle2 rect = player.getShape();
		
		Line leftEdge = rect.getLeftEdge();
		Line rightEdge = rect.getRightEdge();
		Line bottomEdge = rect.getBottomEdge();
		
		checkLeftCollision(leftEdge, blocked, tileSize, newPosition);
		checkBottomCollision(bottomEdge, blocked, tileSize, newPosition);
		checkRightCollision(rightEdge, blocked, tileSize, newPosition);
		
		return newPosition;
	}
	
	public void checkLeftCollision(Line bottomEdge, boolean[][] blocked, int tileSize, Point newPosition) {
		float pixelX = bottomEdge.getCenterX();
		float pixelY1 = bottomEdge.getMinY();
		float pixelY2 = bottomEdge.getMaxY();
		
		int tileX = (int) (pixelX / tileSize);
		int tileY1 = (int) (pixelY1 / tileSize);
		int tileY2 = (int) (pixelY2 / tileSize);
		
		for(int tileY = tileY1; tileY <= tileY2; tileY++) {
			if(blocked[tileX][tileY]) {
				float tileRight = tileX * tileSize + tileSize + player.getRadius();
				newPosition.setX(tileRight);
			}
		}
	}
	
	public void checkRightCollision(Line rightEdge, boolean[][] blocked, int tileSize, Point newPosition) {
		float pixelX = rightEdge.getCenterX();
		float pixelY1 = rightEdge.getMinY();
		float pixelY2 = rightEdge.getMaxY();
		
		int tileX = (int) (pixelX / tileSize);
		int tileY1 = (int) (pixelY1 / tileSize);
		int tileY2 = (int) (pixelY2 / tileSize);
		
		for(int tileY = tileY1; tileY <= tileY2; tileY++) {
			if(blocked[tileX][tileY]) {
				float tileRight = tileX * tileSize - player.getRadius() - 1 ;
				newPosition.setX(tileRight);
			}
		}
	}
	
	public void checkBottomCollision(Line bottomEdge, boolean[][] blocked, int tileSize, Point newPosition) {
		float pixelX1 = bottomEdge.getMinX();
		float pixelX2 = bottomEdge.getMaxX();
		float pixelY = bottomEdge.getCenterY();
		
		int tileX1 = (int) (pixelX1 / tileSize);
		int tileX2 = (int) (pixelX2 / tileSize);
		int tileY = (int) (pixelY / tileSize);
		
		for(int tileX = tileX1; tileX <= tileX2; tileX++) {
			if(blocked[tileX][tileY]) {
				float tileTop = tileY * tileSize - player.getRadius();
				newPosition.setY(tileTop);
			}
		}
	}
	
}
