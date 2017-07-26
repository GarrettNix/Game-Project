package entity;

import static helpers.Render.HEIGHT;
import static helpers.Render.WIDTH;
import static helpers.Render.drawBackground;
import static helpers.Render.loadTexture;


import org.newdawn.slick.opengl.Texture;

import helpers.Render;

public class Background {
	
	private Texture image;
	
	private float x;
	private float y;
	private float dx;
	private float dy;
	
	public Background(String name, float dx) {
		image = loadTexture("res/backgrounds/" + name + ".png", "PNG");
		this.dx = dx;
	}
	
	public void setPosition(float x, float y) {
		this.x = x % Render.WIDTH;
		this.y = y % Render.HEIGHT;
	}
	
	public void setVector(float dx, float dy) {
		this.dx = dx;
		this.dy = dy;
	}
	
	public void update() {
		x += dx;
		x = x % WIDTH;
		y += dy;
		y = y % HEIGHT;
	}
	
	public void render() {
		drawBackground(image, x, y);
		if (x < 0) {
			drawBackground(image, x + WIDTH, y);
		}
		if (x > 0) {
			drawBackground(image, x - WIDTH, y);
		}
	}

	public float getX() {
		return x;
	}

	public float getY() {
		return y;
	}
}