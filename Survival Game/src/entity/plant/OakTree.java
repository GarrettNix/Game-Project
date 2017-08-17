package entity.plant;

import tilemap.TileMap;

public class OakTree extends Tree {
	
	private int apples;
	private int maxApples;
	
	private int appleTimer;
	
	public OakTree(TileMap tm) {
		super(tm);
		health = 10;
		itemDrop = 0;
		apples = 0;
		maxApples = 4;
		width = 80;
		height = 128;
	}
	
	public void update() {
		if (fullGrown && apples < maxApples) {
			appleTimer++;
			if (appleTimer >= 18000) {
				appleTimer = 0;
				apples++;
			}
		}
	}
	
	public void render() {
		if (fullGrown) {
			
		}
		else {
			
		}
	}
}