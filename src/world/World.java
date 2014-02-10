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
		Point newPosition = checkCollision();
		player.setPosition(newPosition);
	}
	
	public Point checkCollision() {
		float newX = player.getPosition().getX();
		float newY = player.getPosition().getY();
		CollisionMap tileMap = level.getTileMap();
		int tileSize = tileMap.getTileSize();
		Rectangle rect = player.getRectangle();
		
		float minX = rect.getMinX();
		float maxX = rect.getMaxX();
		float minY = rect.getMinY();
		float maxY = rect.getMaxY();
		
		Point topLeft = new Point(minX, minY);
		Point topRight = new Point(maxX, minY);
		Point bottomLeft = new Point(minX, maxY);
		Point bottomRight = new Point(maxX, maxY);
		
		boolean isTopLeftBlocked = tileMap.isBlocked(topLeft);
		boolean isTopRightBlocked = tileMap.isBlocked(topRight);
		boolean isBottomLeftBlocked = tileMap.isBlocked(bottomLeft);
		boolean isBottomRightBlocked = tileMap.isBlocked(bottomRight);
		
		boolean isTopBlocked = isTopLeftBlocked || isTopRightBlocked;
		boolean isRightBlocked = isTopRightBlocked || isBottomRightBlocked;
		boolean isBottomBlocked = isBottomLeftBlocked || isBottomRightBlocked;
		boolean isLeftBlocked = isTopLeftBlocked || isBottomLeftBlocked;
		
		if(isBottomBlocked) {
			newY = ((int) maxY / tileSize) * tileSize - player.getHeight() / 2 - 1;
		}
		if(isTopLeftBlocked) {
			newX = ((int) minX / tileSize) * tileSize + tileSize + player.getWidth() / 2;
		}
		
		return new Point(newX, newY);
	}
}
