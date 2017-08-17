package entity.plant;

import entity.Entity;
import tilemap.TileMap;

/**
 * 
 * @author Garrett
 * 
 * This is the superclass for all Plant entities.
 *
 */

public abstract class Plant extends Entity {
	
	protected int itemDrop;
	protected int minItems;
	protected int maxItems;
	protected int bestTool;
	protected int health;
	
	public Plant(TileMap tm) {
		super(tm);
	}
	
	public abstract void update();
	
	public int getItemDrop() { return itemDrop; }
	public int getMinItems() { return minItems; }
	public int getMaxItems() { return maxItems; }
	public int getBestTool() { return bestTool; }
	public int getHealth() { return health; }
	
}