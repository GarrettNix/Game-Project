package gameState;

import static helpers.Render.HEIGHT;
import static helpers.Render.WIDTH;

import org.lwjgl.input.Keyboard;

import entity.Player;
import tilemap.TileMap;

/**
 * 
 * @author Garrett
 * 
 * This is the default game mode. It contains the tilemap and entities. It is
 * responsible for game logic, such as updating entities.
 *
 */

public class PlayStandard extends GameState {
	
	private TileMap tilemap;
	
	private Player player;
	
	
	
	public PlayStandard(GameStateManager gsm) {
		this.gsm = gsm;
		
		init();
	}
	
	private void init() {
		tilemap = new TileMap(64);
		player = new Player(tilemap);
		player.setPosition(32000, 32000);
		tilemap.setPosition(WIDTH / 2 - player.getX(), HEIGHT / 2 - player.getY());
	}
	
	public void update() {
		handleKeyInput();
		player.update();
		tilemap.setPosition(WIDTH / 2 - player.getX(), HEIGHT / 2 - player.getY());
	}
	
	public void render() {
		tilemap.render();
		player.render();
	}
	
	protected void handleKeyInput() {
		player.setUp(Keyboard.isKeyDown(Keyboard.KEY_W));
		player.setDown(Keyboard.isKeyDown(Keyboard.KEY_S));
		player.setLeft(Keyboard.isKeyDown(Keyboard.KEY_A));
		player.setRight(Keyboard.isKeyDown(Keyboard.KEY_D));
		player.setSprinting(Keyboard.isKeyDown(Keyboard.KEY_LSHIFT));
	}
}