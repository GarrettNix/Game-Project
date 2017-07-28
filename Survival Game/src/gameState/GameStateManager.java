package gameState;

/**
 * 
 * @author Garrett
 * 
 * This class controls the current state of the game (i.e. Menu, Help, In-Game).
 * It contains an array of GameStates, the core modes of the game, which it uses
 * to track what state the game is currently in. When its update() and render()
 * methods are called, it calls the corresponding method on the current GameState.
 * It is also used by the GameStates to switch states when specific actions are
 * made by the player, such as clicking on the "play" button on the menu.
 * 
 * For now, it is also responsible for using Render to load all necessary game assets
 * and supply them to their corresponding GameStates. For efficiency, asset loading will
 * be removed from this class and placed in each individual GameState, but for now we
 * will be working with simple assets so load times are irrelevant.
 *
 */

public class GameStateManager {
	
	private GameState[] gameStates;
	private int currentState;
	
	public static final int NUM_STATES = 2; // 2 for now, might increase in the future
	public static final int MENU_STATE = 0;
	public static final int PLAY_STATE = 1;
	
	
	/**
	 * 
	 * Initialize this GameStateManager and set the current state to MENU_STATE.
	 * This causes the game to start up on the menu screen.
	 * 
	 */
	public GameStateManager() {
		gameStates = new GameState[NUM_STATES];
		currentState = MENU_STATE;
		setState(currentState);
	}
	
	public void setState(int state) {
		unloadState(currentState);
		currentState = state;
		loadState(currentState);
	}
	
	/**
	 * 
	 * @param state
	 * 
	 * Load the GameState that corresponds to the value of
	 * state recieved.
	 */
	private void loadState(int state) {
		switch (state) {
			case MENU_STATE:
				gameStates[state] = new MenuState(this);
				break;
			case PLAY_STATE:
				gameStates[state] = new PlayStandard(this);
				break;
			default:
				gameStates[state] = new MenuState(this);
				break;
		}
	}
	
	/**
	 * 
	 * @param state
	 * 
	 * state is the ID for the current GameState to be unloaded from memory.
	 * It is generally passed the value of currentState when switching states,
	 * allowing the current state to be unloaded and the new state to be loaded
	 * instead.
	 * 
	 */
	private void unloadState(int state) {
		gameStates[state] = null;
	}
	
	public void update() {
		gameStates[currentState].update();
	}
	
	public void render() {
		gameStates[currentState].render();
	}
	
}