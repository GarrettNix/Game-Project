package entity;

import org.newdawn.slick.opengl.Texture;

import tilemap.TileMap;

import static helpers.Render.*;

/**
 * 
 * @author Garrett
 * 
 * The Player class contains all data related to the player's avatar.
 * It is used for updating the player's position, actions, etc.
 *
 */

public class Player extends Entity {
	
	private Texture texture;
	
	private boolean sprinting;
	
	public Player(TileMap tm) {
		super(tm);
		texture = quickLoad("entities", "player_placeholder");
		width = 40;
		height = 64;
	}
	
	public void setSprinting(boolean b) { sprinting = b; }
	
	public void update() {
		if (left) {
			if (sprinting) x -= 3;
			else x -=2;
		}
		if (right) {
			if (sprinting) x += 3;
			else x += 2;
		}
		if (up) {
			if (sprinting) y -= 3;
			else y -= 2;
		}
		if (down) {
			if (sprinting) y += 3;
			else y += 2;
		}
		// keep player on the map
		if (x < 20) x = 20;
		if (x > 63980) x = 63980;
		if (y < 64) y = 64;
		if (y > 64000) y = 64000;
	}
	
	public void render() {
		setMapPosition();
		drawImage(texture, x + xmap - width / 2, y + ymap - height);
	}
}