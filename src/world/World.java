package world;

import java.util.ArrayList;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.geom.Point;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.geom.Shape;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.tiled.TiledMap;

import physics.Body;

public class World {
	
	// physics
	private static final float GRAVITY = 150;
	
	// fields
	private Level level;
	private Body player;
	
 	public World(Level level) {
		this.level = level;
		int playerSize = 32;
		player = new Body(32, level.getPlayerSpawn(), 400, 200, 300, 1000);
	}
	
	public void update(GameContainer container, StateBasedGame sbg, int delta) {
		updatePlayerPosition(delta);
	}
	 
	public void render(GameContainer container, StateBasedGame sbg, Graphics graphics) {
		level.getTileMap().render(0, 0);
		graphics.draw(player.getShape());
	}
	
	public Body getPlayer() {
		return player;
	}
	
	public void updatePlayerPosition(int delta) {
		float time = delta / 1000.0f;
		float oldX = player.getPosition().getX();
		float oldY = player.getPosition().getY();
		float newX = oldX + player.getVelocity().getX() * time;
		float newY = oldY - player.getVelocity().getY() * time + GRAVITY * time;
		boolean[][] blocked = level.getBlocked();
		int tileSize = level.getTileMap().getTileHeight();
		
		if(blocked[((int)newX + 16) / tileSize][(int)oldY / tileSize] ||
				blocked[((int)newX - 16) / tileSize][(int)oldY / tileSize]) {
			newX = oldX;
		}
		if(blocked[(int)oldX / tileSize][((int)newY + 16) / tileSize] ||
				blocked[(int)oldX / tileSize][((int)newY - 16) / tileSize]) {
			newY = oldY;
		}
		player.setPosition(new Point(newX, newY));
	}
	
}
