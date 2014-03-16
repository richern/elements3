package world;

import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Point;
import org.newdawn.slick.tiled.TiledMap;

public class Level extends TiledMap {
	
	int id;
	String name;
	
	int tileSize;
	Point playerSpawn;
	
	public Level(int id, String name, String map) throws SlickException, TileNotSquareException {
		super(map);
		
		if(getTileWidth() == getTileHeight())
			 tileSize = getTileWidth();
		else throw new TileNotSquareException();
		
		this.id = id;
		this.name = name;
		this.playerSpawn = findPlayerSpawn(); 
	}
	
	// construct	
	public Point findPlayerSpawn() {
		return null;
	}
	
	// util
	public boolean isBlocked(Point point) {
		int x = (int) (point.getX() / tileSize);
		int y = (int) (point.getY() / tileSize);
		int tileId = getTileId(x, y, 0);
		boolean isBlocked = getTileProperty(tileId, "blocked", "false").equals("true");
		return isBlocked;
	}
	
	// slick
	public void render(int offsetX, int offsetY) {
		super.render(offsetX, offsetY);
	}
	
	// primitive getters/setters
	public int getId() {
		return id;
	}
	
	public int getTileSize() {
		return tileSize;
	}
	
	public Point getPlayerSpawn() {
		//TODO:return playerSpawn;
		return new Point(6.5f*tileSize, 15.5f*tileSize);
	}
	
	@SuppressWarnings("serial")
	public class TileNotSquareException extends Exception {
		public TileNotSquareException() {
			super("Tile height does not equal tile width");
		}
	}
		
}
