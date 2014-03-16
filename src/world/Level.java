package world;

import org.newdawn.slick.geom.Point;

public class Level {
	
	private int id;
	private String name;
	private CollisionMap tileMap;
	
	public Level(int id, String name, CollisionMap tileMap) {
		this.id = id;
		this.name = name;
		this.tileMap = tileMap;
	}
	
	public int getId() {
		return id;
	}
	
	public Point getPlayerSpawn() {
		return tileMap.getPlayerSpawn();
	}
	
	public String getName() {
		return name;
	}
	
	public CollisionMap getTileMap() {
		return tileMap;
	}
	
}
