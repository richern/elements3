package world;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Point;
import org.newdawn.slick.tiled.TiledMap;

import enums.KeyType;

public class Level {
	
	String name;
	TiledMap collisionMap;
	TiledMap artMap;
	Image background;
	
	int tileSize;
	Point playerSpawn;
	Point keySpawn;
	KeyType keyType;
	
	public Level(String name, String collisionMap, String artMap, String background,
			Point playerSpawn, Point keySpawn, KeyType keyType) throws SlickException{
		this.collisionMap = new TiledMap(collisionMap);
		this.artMap = new TiledMap(artMap);
		this.background = new Image(background);
		
		System.out.println(this.collisionMap.getHeight() * this.collisionMap.getTileHeight());
		System.out.println(this.artMap.getHeight() * this.artMap.getTileHeight());
		
		this.tileSize = this.collisionMap.getTileWidth();
		this.name = name;
		this.playerSpawn = playerSpawn;
		this.keySpawn = keySpawn;
		this.keyType = keyType;
	}
	
	// util
	public boolean isBlocked(Point point) throws TileMapOutOfBoundsException {
		int x = (int) (point.getX() / tileSize);
		int y = (int) (point.getY() / tileSize);
		try {
			int tileId = collisionMap.getTileId(x, y, 0);
			boolean isBlocked = collisionMap.getTileProperty(tileId, "blocked", "false").equals("true");
			return isBlocked;
		} catch(java.lang.ArrayIndexOutOfBoundsException e) {
			throw new TileMapOutOfBoundsException(x, y);
		}
	}
	
	// slick
	public void render(Graphics graphics, int offsetX, int offsetY) {
		graphics.drawImage(background, 0, 0);
		artMap.render(offsetX, offsetY, 1);
		artMap.render(offsetX, offsetY, 2);
	}
	
	// primitive getters/setters
	public int getWidth() {
		return artMap.getWidth();
	}
	
	public int getHeight() {
		return artMap.getHeight();
	}
	
	public int getTileSize() {
		return tileSize;
	}
	
	public Point getPlayerSpawn() {
		return playerSpawn;
	}
	
	public Point getKeySpawn() {
		return keySpawn;
	}
	
	public KeyType getKeyType() {
		return keyType;
	}
	
	public class TileMapOutOfBoundsException extends Exception {
		private static final long serialVersionUID = 1L;
		public TileMapOutOfBoundsException(int x, int y) {
			super("Out of bounds at " + x + ":" + y);
		}
	}
		
}
