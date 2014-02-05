package world;

import java.util.ArrayList;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.tiled.TiledMap;

import physics.Body;

public class World {
	
	// physics
	private static final float GRAVITY = 0.4f;
	
	// fields
	private Level level;
	private Body player;
	private ArrayList<Rectangle> collisionBoxes;
	
 	public World(Level level, Body player) {
		this.level = level;
		this.player = player;
		this.collisionBoxes = initCollisionBoxes();
	}
	
	public void update() {
		
	}
	
	public void render(Graphics graphics) {
		level.getTileMap().render(0, 0);
		for(Rectangle r : collisionBoxes) {
			graphics.draw(r);
		}
	}
	
	public ArrayList<Rectangle> initCollisionBoxes() {
		ArrayList<Rectangle> collisionBoxes = new ArrayList<Rectangle>();
		TiledMap tileMap = level.getTileMap();
		boolean[][] blocked = level.getBlocked();
		int numCols = blocked.length;
		int numRows = blocked[0].length;
		int tileSize = tileMap.getTileHeight(); // assumes height == width
		
		for(int col = 0; col < numCols; col++) {
			for(int row = 0; row < numRows; row++) {
				if(blocked[col][row]) {
					collisionBoxes.add(new Rectangle(col * tileSize, row * tileSize, tileSize, tileSize));
				}
			}
		}
		
		return collisionBoxes;
	}
	
}
