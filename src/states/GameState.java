package states;

public enum GameState {
	PlayState(0), MenuState(1), LobbyState(2), FindLobbyState(3);
	
	private int id;
	
	GameState(int id) {
		this.id = id;
	}
	
	public int getID() {
		return id;
	}
}
