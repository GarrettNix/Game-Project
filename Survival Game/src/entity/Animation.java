package entity;

import org.newdawn.slick.opengl.Texture;

public class Animation {
	
	private Texture[] frames;
	private int currentFrame;
	private int frameUpdateTimer;
	private int timer;
	private boolean playedOnce;
	
	public Animation() {
		frames = new Texture[0];
		frameUpdateTimer = -1;
		timer = 0;
		currentFrame = 0;
		playedOnce = false;
	}
	
	public void setFrames(Texture[] frames, int timer) {
		this.frames = frames;
		this.frameUpdateTimer = timer;
		currentFrame = 0;
		playedOnce = false;
	}
	
	public void update() {
		if (frameUpdateTimer == -1) return;
		else {
			timer++;
			if (timer >= frameUpdateTimer) {
				currentFrame++;
				if (currentFrame >= frames.length) {
					currentFrame = 0;
					playedOnce = true;
				}
				timer = 0;
			}
		}
	}
	
	public boolean hasPlayedOnce() {
		return playedOnce;
	}
	
	public Texture getFrame() {
		return frames[currentFrame];
	}
	
}