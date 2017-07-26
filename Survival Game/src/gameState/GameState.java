package gameState;

public abstract class GameState {
	
	protected GameStateManager gsm;
	
	public abstract void update();
	public abstract void render();
	
}