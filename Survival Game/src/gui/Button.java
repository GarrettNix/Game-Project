package gui;

import java.awt.Point;
import java.awt.Rectangle;

import org.lwjgl.input.Mouse;
import org.newdawn.slick.opengl.Texture;

import helpers.Render;
import static helpers.Render.drawImage;

/**
 * 
 * @author Garrett
 * 
 * A Button is used to represent an area on the screen that the user
 * may click on to interact with the game.
 * 
 * It has 2 textures, a default texture and a hoverTexture. The default
 * texture is displayed whenever the mouse is outside this button's specified
 * area. The hoverTexture is displayed whenever the mouse is inside the
 * specified area. This gives the button an interactive feel. However, if
 * you want the button to remain static whether the mouse is over it or not,
 * simply pass it two of the same texture and nothing will change (visually).
 *
 */

public class Button {
	
	private Texture texture;
	private Texture hoverTexture;
	private boolean hover;
	private int x, y, width, height;
	
	public Button(Texture texture, Texture hoverTexture, int x, int y, int width, int height) {
		this.texture = texture;
		this.hoverTexture = hoverTexture;
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
	}

	public Button(Texture texture, Texture hoverTexture, int x, int y) {
		this.texture = texture;
		this.hoverTexture = hoverTexture;
		this.x = x;
		this.y = y;
		this.width = texture.getImageWidth();
		this.height = texture.getImageHeight();
	}
	
	/**
	 * Check if the mouse is inside this button's specified area. Change
	 * the value of isHover accordingly.
	 */
	public void update() {
		Point p = new Point(Mouse.getX(), Render.HEIGHT - Mouse.getY() - 1);
		Rectangle r = new Rectangle(x, y, width, height);
		if (r.contains(p)) hover = true;
		else hover = false;
	}
	
	/**
	 * @return true if the mouse is inside this button's area.
	 * @return false if it is not.
	 */
	public boolean isHover() {
		return hover;
	}
	
	/**
	 * Draw this button's current texture on the screen.
	 */
	public void render() {
		if (hover) drawImage(hoverTexture, x, y, width, height);
		else drawImage(texture, x, y, width, height);
	}
}