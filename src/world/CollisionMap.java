package world;

import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Point;
import org.newdawn.slick.tiled.TiledMap;
public class CollisionMap extends TiledMap {
	
	private int tileSize;
	private Point playerSpawn;

	public CollisionMap(String ref) throws SlickException, TileNotSquareException {
		super(ref);
		if(getTileHeight() == getTileWidth())
			this.tileSize = getTileHeight();			
		else
			throw new TileNotSquareException();
		playerSpawn = pixelCoords(findTileProperty("playerSpawn"));
	}
	
	@Override
	public void render(int x, int y) {
		render(x, y, 0);
	}
	
	
	public Point findTileProperty(String property) {
		Point tileLocation = null;
		
		for(int col = 0; col < getWidth(); col++) {
			for(int row = 0; row < getHeight(); row++) {
				Point point = new Point(col, row);
				boolean isPlayerSpawn = getTileProperty(property, point, true);
				if(isPlayerSpawn) {
					tileLocation = new Point(col, row);
				}
			}
		}
		
		return tileLocation;
	}
	
	public boolean getTileProperty(String property, Point p, boolean tileCoords) {
		float divisor = tileCoords ? 1 : tileSize;
		int col = (int) (p.getX() / divisor);
		int row = (int) (p.getY() / divisor);
		int tileId = getTileId(col, row, 0);
		String val = getTileProperty(tileId, property, "false");
		return val.equals("true");
	}
	
	public Point pixelCoords(Point tileCoords) {
		return new Point(tileCoords.getX() * tileSize + tileSize / 2, 
				tileCoords.getY() * tileSize + tileSize / 2);
	}
	
	/*public boolean isBlocked(Point p) {
		// expects pixel coordinates
		return getTileProperty("blocked", p, false);
	}*/
	
	public boolean isBlocked(Point p) {
		// expects pixel coordinates
		int col = (int) p.getX() / tileSize;
		int row = (int) p.getY() / tileSize;
		int tileId = getTileId(col, row, 0);
		String val = getTileProperty(tileId, "blocked", "false");
		return val.equals("true");
	}
	
	
	public int getTileSize() {
		return tileSize;
	}
	
	public Point getPlayerSpawn() {
		return playerSpawn;
	}

	@SuppressWarnings("serial")
	public class TileNotSquareException extends Exception {
		public TileNotSquareException() {
			super("Tile height does not equal tile width");
		}
	}
	
}
