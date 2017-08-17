package helpers;

import org.newdawn.slick.opengl.Texture;

/**
 * 
 * @author Garrett
 * 
 * This class acts as a bank for textures to be loaded into and accessesed by entities in the game.
 * A TextureBank object is generated upon loading the game and will load textures needed for rendering
 * things like plants, animals, item drops, etc.
 * 
 * All textures loaded in this class will be public, so it will not be necessary for any getter methods
 * to be used as other objects can simply access them by name.
 *
 */

public class TextureBank {
	
	public Texture[] oakTreeStages;
	public Texture[] oakTreeFullStages;
	
	public TextureBank() {
		System.out.println("Texture bank initializing...");
		loadTextures();
		System.out.println("Texture bank loaded.");
	}
	
	private void loadTextures() {
		
	}
	
}