package menu.menus;

import menu.Menu;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

public class TitleMenu extends Menu{
	
	float ALPHA_RATE = 128;
	Image backgroundArt;
	float alpha; //8 bit
	
	public TitleMenu(String text) throws SlickException {
		super(text);
		backgroundArt = new Image("resources/menu/title.png");
		alpha = 0;
	}
	
	@Override
	public void update(GameContainer container, StateBasedGame sbg, int delta) throws SlickException {
		super.update(container, sbg, delta);
		if(alpha > 256) {
			alpha = 256;
			ALPHA_RATE = -Math.abs(ALPHA_RATE);
		}
		else if(alpha < 0) {
			alpha = 0;
			ALPHA_RATE = Math.abs(ALPHA_RATE);
		}
		alpha += ALPHA_RATE * (delta / 1000f);
		System.out.println(alpha);
	}
	
	@Override
	public void render(GameContainer container, StateBasedGame sbg, Graphics graphics) {
		graphics.drawImage(backgroundArt, 0, 0);
		
		String text = "Press Space or Enter";
		float x = container.getWidth() / 2 - font.getWidth(text) / 2;
		float y = container.getHeight() - 100;
		font.drawString(x, y, text, new Color(22, 28, 32, (int) alpha));
	}

}