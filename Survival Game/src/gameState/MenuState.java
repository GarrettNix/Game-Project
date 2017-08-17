package gameState;

import org.lwjgl.input.Keyboard;

import entity.Background;

/**
 * 
 * @author Garrett
 * 
 * This is the menu state. It has a background image which is displayed behind
 * the objects on the menu (i.e. buttons, text). It is used to allow the player
 * to choose what to do in the game, such as start a new game, continue a game,
 * go to the help screen, or exit the game.
 *
 */

public class MenuState extends GameState {
	
	private Background bg;
	
	public MenuState(GameStateManager gsm) {
		this.gsm = gsm;
		bg = new Background("menu_placeholder", 0);
	}
	
	public void update() {
		System.out.println("updating menu");
		handleKeyInput();
		bg.update();
	}
	
	public void render() {
		bg.render();
	}
	
	/**
	 * For now, go to PlayStandard state when ENTER is pressed. Buttons will be
	 * added later.
	 */
	protected void handleKeyInput() {
		if (Keyboard.isKeyDown(Keyboard.KEY_RETURN)) gsm.setState(GameStateManager.PLAY_STATE);
	}
}