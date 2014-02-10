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

import physics.Player;

public class World {
	
	// fields
	private Level level;
	private Player player;
	
 	public World(Level level) {
		this.level = level;
		player = new Player(level.getPlayerSpawn());
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
		CollisionMap tileMap = level.getTileMap();
		int tileSize = tileMap.getTileSize();		
		
		float width = player.getWidth();
		float height = player.getHeight();
		
		float oldX = player.getPosition().getX();
		float oldY = player.getPosition().getY();
		
		float velocityX = player.getVelocity().getX();
		float velocityY = player.getVelocity().getY();
		
		float newX = oldX + velocityX * time;
		float newY = oldY + velocityY * time;
		
		float finalX;
		float finalY;
				
		// have to compare x and y directions separately
		finalY = newY;
		
		float oldTopY = oldY - height / 2;
		float oldBottomY = oldY + height / 2;
		
		if(velocityX < 0) {
			float leftX = newX - width / 2;
			
			Point topLeft = new Point(leftX, oldTopY);
			Point bottomLeft = new Point(leftX, oldBottomY);
			
			boolean leftBlocked = tileMap.isBlocked(topLeft) || tileMap.isBlocked(bottomLeft);
			if(leftBlocked) {
				finalX = ((int) leftX / tileSize) * tileSize + tileSize + player.getWidth() / 2 + 1;
				player.setDx(0);
			}
			else {
				finalX = newX;
			}
		}
		else if(velocityX > 0) {
			float rightX = newX + width / 2;
			
			Point topRight = new Point(rightX, oldTopY);
			Point bottomRight = new Point(rightX, oldBottomY);
			
			boolean rightBlocked = tileMap.isBlocked(topRight) || tileMap.isBlocked(bottomRight);
			if(rightBlocked) {
				finalX = ((int) rightX / tileSize) * tileSize - player.getWidth() / 2;
				player.setDx(0);
			}
			else {
				finalX = newX;
			}
		}
		else {
			finalX = newX;
		}
		
		float oldLeftX = oldX - width / 2;
		float oldRightX = oldX + width / 2 - 1;
		if(velocityY > 0) {
			float bottomY = newY + height / 2;
			
			Point bottomLeft = new Point(oldLeftX, bottomY);
			Point bottomRight = new Point(oldRightX, bottomY);
			
			boolean bottomBlocked = tileMap.isBlocked(bottomLeft) || tileMap.isBlocked(bottomRight);
			if(bottomBlocked) {
				finalY = ((int) bottomY / tileSize) * tileSize - player.getHeight() / 2 - 1;
				player.setDy(0);
			}
			else {
				finalY = newY;
			}
		}
	
		return new Point(finalX, finalY);
	}
	
}
