package states;

import java.util.ArrayList;
import java.util.HashMap;

import main.Game;
import networking.GameServer;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

import com.esotericsoftware.kryonet.Listener;

import enums.GameState;

public class CutsceneState extends BasicGameState {
	
	final int FADE_TIME = 1000;
	final int MIN_SCENE_TIME = 2000;
	
	HashMap<String, ArrayList<Image>> cutscenes;
	ArrayList<Image> activeCutscene;
	int activeScene;
	int sceneTime;
	int alpha;
	boolean ending;

	@Override
	public void init(GameContainer arg0, StateBasedGame arg1)
			throws SlickException {
		cutscenes = new HashMap<String, ArrayList<Image>>();
		
		ArrayList<Image> introScene = new ArrayList<Image>();
		introScene.add(new Image("resources/cutscenes/introduction/intro1.png"));
		introScene.add(new Image("resources/cutscenes/introduction/intro2.png"));
		introScene.add(new Image("resources/cutscenes/introduction/intro3.png"));
		introScene.add(new Image("resources/cutscenes/introduction/intro4.png"));
		
		ArrayList<Image> endingScene = new ArrayList<Image>();
		endingScene.add(new Image("resources/cutscenes/ending/end1.png"));
		endingScene.add(new Image("resources/cutscenes/ending/end2.png"));
		endingScene.add(new Image("resources/cutscenes/ending/end3.png"));
		endingScene.add(new Image("resources/cutscenes/ending/end4.png"));
		
		cutscenes.put("intro", introScene);
		cutscenes.put("ending", endingScene);
		
		activateCutscene("intro");
	}
	
	@Override
	public void update(GameContainer container, StateBasedGame sbg, int delta)
			throws SlickException {
		Game game = (Game) sbg;
		
		sceneTime += delta;
		Input input = container.getInput();
		boolean nextScene = input.isKeyPressed(Input.KEY_SPACE)
				|| input.isKeyPressed(Input.KEY_ENTER);
		
		if(nextScene && minSceneTime()) {
			nextScene(game);
		}
	}

	@Override
	public void render(GameContainer container, StateBasedGame game, Graphics graphics)
			throws SlickException {
		graphics.scale((float) container.getWidth() / 1920f, (float) container.getHeight() / 1080f);
		graphics.drawImage(activeCutscene.get(activeScene), 0, 0);
	}
	
	public void nextScene(Game game) {
		if(activeScene < activeCutscene.size() - 1) {
			sceneTime = 0;
			activeScene++;
		}
		else {
			if(ending) {
				System.exit(0);
			}
			else game.enterPlayState();	
			
		}
	}
	
	public void activateCutscene(String name) {
		ending = name.equals("ending");
		activeCutscene = cutscenes.get(name);
		activeScene = 0;
		sceneTime = 0;
		alpha = 0;
	}
	
	public boolean minSceneTime() {
		return sceneTime >= MIN_SCENE_TIME;
	}

	@Override
	public int getID() {
		return GameState.CUTSCENE.getID();
	}
	
	
	
}
