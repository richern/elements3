package audio;

import java.io.IOException;

import org.newdawn.slick.openal.AudioLoader;
import org.newdawn.slick.util.ResourceLoader;



public class Audio {

	public final int INTRO = 0;
	public final int WATER = 1;
	public final int FIRE = 2;
	public final int WIND = 3;
	public final int WALK = 4;
	public final int JUMP = 5;
	
	
	private org.newdawn.slick.openal.Audio sTrack;
	
	public Audio(int audio) throws IOException{
		sTrack = AudioLoader.getAudio("WAV",ResourceLoader.getResourceAsStream("resources/audio/intro.wav"));
		if(audio == WATER) sTrack = AudioLoader.getAudio("WAV",ResourceLoader.getResourceAsStream("resources/audio/water.wav"));
		if(audio == FIRE) sTrack = AudioLoader.getAudio("WAV",ResourceLoader.getResourceAsStream("resources/audio/fire.wav"));
		if(audio == WIND) sTrack = AudioLoader.getAudio("WAV",ResourceLoader.getResourceAsStream("resources/audio/wind.wav"));
		if(audio == WALK) sTrack = AudioLoader.getAudio("WAV",ResourceLoader.getResourceAsStream("resources/audio/walk_run.wav"));
		if(audio == JUMP) sTrack = AudioLoader.getAudio("WAV",ResourceLoader.getResourceAsStream("resources/audio/jump.wav"));
	}
	
	public void playMusic(){
		sTrack.playAsMusic(1.0f, 1.0f,true);
	}
	
	public void stop(){
		sTrack.stop();
	}
	
	public void playEffect(){
		sTrack.playAsSoundEffect(1.0f, 1.0f,true);
	}
}
