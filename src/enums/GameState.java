package enums;

public enum GameState {
	PLAY(0), MENU(1);
	
	private int id;
	
	GameState(int id) {
		this.id = id;
	}
	
	public int getID() {
		return id;
	}
}
