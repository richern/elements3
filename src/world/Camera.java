package world;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.geom.Rectangle;

import main.Game;

public class Camera {
	
	// camera attempts to center player, locks when out of bounds
	
	private static final float CAM_WIDTH = Game.TARGET_RESOLUTION.getWidth();;
	private static final float CAM_HEIGHT = Game.TARGET_RESOLUTION.getHeight();;
	
	private final float TILEMAP_WIDTH;
	private final float TILEMAP_HEIGHT;
	private final float PADDING_X;
	private final float PADDING_Y;
	
	private float camX;
	private float camY;
	
	private Player player;
	
	public Camera(CollisionMap tileMap, Player player) {
		this.TILEMAP_WIDTH = tileMap.getWidth() * tileMap.getTileSize();
		this.TILEMAP_HEIGHT = tileMap.getHeight() * tileMap.getTileSize();
		this.PADDING_X = (CAM_WIDTH - TILEMAP_WIDTH) / 2;
		this.PADDING_Y = (CAM_HEIGHT - TILEMAP_HEIGHT) / 2;
		
		this.player = player;
	}
	
	public void update() {
		if(!player.isPositionChanged()) return;
		setCenter();
		adjust();
	}
		
	public void setCenter() {
		float playerX = player.getPosition().getX();
		float playerY = player.getPosition().getY();
		
		camX = playerX - CAM_WIDTH / 2;
		camY = playerY - CAM_HEIGHT / 2;
		
	}
	
	public void adjust() {
		Rectangle viewport = new Rectangle(camX, camY, CAM_WIDTH, CAM_HEIGHT);
		
		if(PADDING_X >= 0) {
			camX = -PADDING_X;
		}
		else {
			float minX = viewport.getMinX();
			float maxX = viewport.getMaxX();
			
			if(minX < 0) {
				camX = 0;
			}
			else if(maxX > TILEMAP_WIDTH) {
				camX = TILEMAP_WIDTH - CAM_WIDTH;
			}
		}
		
		if(PADDING_Y >= 0) {
			camY = -PADDING_Y;
		}
		else {
			float minY = viewport.getMinY();
			float maxY = viewport.getMaxY();
			
			if(minY < 0) {
				camY = 0;
			}
			else if(maxY > TILEMAP_HEIGHT) {
				camY = TILEMAP_HEIGHT - CAM_HEIGHT;
			}
		}
	}
	
	public int getOffsetX() {
		return (int) -camX;
	}
	
	public int getOffsetY() {
		return (int) -camY;
	}
	
	public float getCamWidth() {
		return CAM_WIDTH;
	}
	
	public float getCamHeight() {
		return CAM_HEIGHT;
	}
	
}
