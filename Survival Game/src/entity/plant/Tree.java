package entity.plant;

import org.newdawn.slick.opengl.Texture;

import tilemap.TileMap;

public abstract class Tree extends Plant {
	
	protected int stage;
	protected boolean fullGrown;
	
	protected Texture[] stages;
	
	public Tree(TileMap tm) {
		super(tm);
	}
	
	public abstract void update();
	
}