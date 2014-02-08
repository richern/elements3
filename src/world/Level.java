package world;

import java.util.ArrayList;

import org.newdawn.slick.geom.Point;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.geom.Vector2f;
import org.newdawn.slick.tiled.TiledMap;

public class Level {
	
	private String id;
	private TiledMap tileMap;
	private boolean[][] blocked;
	private Point playerSpawn;
	
	public Level(String id, TiledMap tileMap, Point playerSpawn) {
		this.id = id;
		this.tileMap = tileMap;
		this.blocked = initBlocked();
		this.playerSpawn = playerSpawn;
	}
	
	public boolean[][] initBlocked() {
		int numCols = tileMap.getWidth();
		int numRows = tileMap.getHeight();
		blocked = new boolean[numCols][numRows];
		
		for(int col = 0; col < numCols; col++) {
			for(int row = 0; row < numRows; row++) {
				int tileId = tileMap.getTileId(col, row, 0);
				String val = tileMap.getTileProperty(tileId, "blocked", "false");
				if(val.equals("true")) {
					blocked[col][row] = true;
				}
			}
		}
		
		System.out.println("blocked: " + blocked.length + ":" + blocked[0].length);
		
		return blocked;
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
	
	public Point getPlayerSpawn() {
		return playerSpawn;
	}
	
}
