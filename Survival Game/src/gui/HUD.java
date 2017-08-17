package gui;

import static helpers.Render.*;

import java.awt.Rectangle;

import org.newdawn.slick.Color;
import org.newdawn.slick.opengl.Texture;

import entity.Player;

/**
 * 
 * @author Garrett
 * 
 * This class is used for rendering the HUD on the screen. It draws
 * the health bar, inventory hotbar, etc.
 *
 */

public class HUD {
	
	private Player player;
	private float[] barLengths;
	
	private Rectangle rect;
	private Color rectangleColor;
	private Color[] barColors;
	private Color[] transparentColors;
	
	private Texture[] icons;
	
	public HUD(Player player) {
		this.player = player;
		barLengths = new float[4];
		rect = new Rectangle(5, 621, 200, 124);
		rectangleColor = new Color(230, 230, 230, 210);
		icons = new Texture[4];
		icons[0] = quickLoad("hud", "health_icon");
		icons[1] = quickLoad("hud", "energy_icon");
		icons[2] = quickLoad("hud", "food_icon");
		icons[3] = quickLoad("hud", "water_icon");
		barColors = new Color[4];
		barColors[0] = Color.red;
		barColors[1] = new Color(255, 216, 0);
		barColors[2] = new Color(145, 121, 0);
		barColors[3] = new Color(0, 219, 219);
		transparentColors = new Color[4];
		transparentColors[0] = new Color(255, 0, 0, 80);
		transparentColors[1] = new Color(255, 216, 0, 80);
		transparentColors[2] = new Color(145, 121, 0, 80);
		transparentColors[3] = new Color(0, 219, 219, 80);
	}
	
	/**
	 * This method takes the player's health relative to the max health and
	 * calculates the corresponding amount of the bar to fill in.
	 * It does the same for all the other bars, too.
	 */
	private void updateBars() {
		// update health bar
		barLengths[0] = (player.getHealth() / player.getMaxHealth()) * 150f;
		// update energy bar
		barLengths[1] = (player.getEnergy() / player.getMaxEnergy()) * 150f;
		// update food bar
		barLengths[2] = (player.getFood() / player.getMaxFood()) * 150f;
		// update water bar
		barLengths[3] = (player.getWater() / player.getMaxWater()) * 150f;
	}
	
	/**
	 * Renders the HUD onto the screen.
	 */
	public void render() {
		updateBars();
		// draw white background
		drawRect(rect, rectangleColor);
		// draw icons (health, food, etc)
		for (int i = 0; i < icons.length; i++) {
			if (icons[i] != null) {
				drawImage(icons[i], 15, 628 + i * 30);
			}
		}
		// draw transparent "empty" bars
		for (int i = 0; i < icons.length; i++) {
			drawRect(45, 628 + i * 30, 150, 20, transparentColors[i]);
		}
		// draw "full" portions of bars
		for (int i = 0; i < icons.length; i++) {
			drawRect(45, 628 + i * 30, barLengths[i], 20, barColors[i]);
		}
	}
	
}