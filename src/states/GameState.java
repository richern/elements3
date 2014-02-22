package states;

public enum GameState {
	PlayState(0), MenuState(1);
	
	private int id;
	
	GameState(int id) {
		this.id = id;
	}
	
	public int getID() {
		return id;
	}
}
