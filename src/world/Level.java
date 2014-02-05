package world;

import org.newdawn.slick.tiled.TiledMap;

public class Level {
	
	private String id;
	private TiledMap tileMap;
	private boolean[][] blocked;
	
	public Level(String id, TiledMap tileMap) {
		this.id = id;
		this.tileMap = tileMap;
		initBlocked();
	}
	
	private void initBlocked() {
		blocked = new boolean[tileMap.getWidth()][tileMap.getHeight()];
		for(int col = 0; col < tileMap.getWidth(); col++) {
			for(int row = 0; row < tileMap.getHeight(); row++) {
				int tileId = tileMap.getTileId(col, row, 0);
				String value = tileMap.getTileProperty(tileId, "blocked", "false");
				if(value.equals("true")) {
					blocked[col][row] = true;
				}
			}
		}
	}
	
	public String getId() {
		return id;
	}
	
	public TiledMap getTileMap() {
		return tileMap;
	}
	
	public boolean[][] getBlocked() {
		return blocked;
	}
	
}
