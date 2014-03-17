package menu.menus;

import main.Game;
import menu.Menu;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

public class TitleMenu extends Menu {
	
	float globalAlpha;
	float GLOBAL_ALPHA_RATE = 30;
	float GLOBAL_ALPHA_ACCELERATION = 40;
	
	float ALPHA_RATE = 128;
	Image backgroundArt;
	float alpha; //8 bit
	
	public TitleMenu() throws SlickException {
		super("title");
		globalAlpha = 0;
		backgroundArt = new Image("resources/menu/title.png");
		alpha = 0;
	}
	
	@Override
	public void update(GameContainer container, StateBasedGame sbg, int delta) throws SlickException {
		super.update(container, sbg, delta);
		if(globalAlpha < 256) {
			GLOBAL_ALPHA_RATE += GLOBAL_ALPHA_ACCELERATION * (delta / 1000f);
			globalAlpha += GLOBAL_ALPHA_RATE * (delta / 1000f);
		}
		
		if(alpha > 256) {
			alpha = 256;
			ALPHA_RATE = -Math.abs(ALPHA_RATE);
		}
		else if(alpha < 0) {
			alpha = 0;
			ALPHA_RATE = Math.abs(ALPHA_RATE);
		}
		alpha += ALPHA_RATE * (delta / 1000f);
	}
	
	@Override
	public void render(GameContainer container, StateBasedGame sbg, Graphics graphics) {
		float width = Game.TARGET_RESOLUTION.getWidth();
		float height = Game.TARGET_RESOLUTION.getHeight();
		float centerX = width / 2;
		
		graphics.drawImage(backgroundArt, 0, 0, new Color(256,256,256,(int) globalAlpha));
		
		String text = "Press Space or Enter";
		float x = centerX - font.getWidth(text) / 2;
		float y = height * 0.9f;
		font.drawString(x, y, text, new Color(22, 28, 32, (int) alpha));
	}

}