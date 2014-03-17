package enums;

public enum GameState {
	MENU(0), CUTSCENE(1), PLAY(2);
	
	private int id;
	
	GameState(int id) {
		this.id = id;
	}
	
	public int getID() {
		return id;
	}
}
