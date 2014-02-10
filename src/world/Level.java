package world;

import java.util.ArrayList;

import org.newdawn.slick.geom.Point;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.geom.Vector2f;
import org.newdawn.slick.tiled.TiledMap;

public class Level {
	
	private String id;
	private CollisionMap tileMap;
	private Point playerSpawn;
	
	public Level(String id, CollisionMap tileMap, Point playerSpawn) {
		this.id = id;
		this.tileMap = tileMap;
		this.playerSpawn = playerSpawn;
	}
		
	public String getId() {
		return id;
	}
	
	public CollisionMap getTileMap() {
		return tileMap;
	}
	
	public Point getPlayerSpawn() {
		return playerSpawn;
	}
	
}
