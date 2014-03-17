package entities;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Point;
import org.newdawn.slick.geom.Rectangle;

import enums.KeyType;

public class Key {
	
	final float WIDTH = 64;
	final float HEIGHT = 96;
	
	Image image;
	float x;
	float y;
	
	public Key(Point spawn, KeyType keyType) throws SlickException {
		this.x = spawn.getX();
		this.y = spawn.getY();
		
		switch(keyType) {
		case AIR:
			image = new Image("resources/keys/airkey.png");
			break;
		case WATER:
			image = new Image("resources/keys/waterkey.png");
			break;
		case FIRE:
			image = new Image("resources/keys/firekey.png");
			break;
		}
	}
	
	public void update(float time) {
		
	}
	
	public void render(Graphics graphics) {
		float topLeftX = this.x - WIDTH / 2;
		float topLeftY = this.y - HEIGHT / 2;
		
		graphics.drawImage(image, topLeftX, topLeftY);
	}

	public Rectangle getRectangle() {
		float topLeftX = this.x - WIDTH / 2;
		float topLeftY = this.y - HEIGHT / 2;
		
		return new Rectangle(topLeftX, topLeftY, WIDTH, HEIGHT);
	}
	
}
