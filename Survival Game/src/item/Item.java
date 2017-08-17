package item;

import org.newdawn.slick.opengl.Texture;

/**
 * @author Garrett
 * 
 * This is the superclass for all types of INVENTORY items (NOT items on the ground).
 * It will have extensions for things like tools and weapons.
 */
public class Item {
	
	protected String[][] names = {
			{"oak wood", "oak plank", "stick", "rock", "reeds"}, // regular items
			{"makeshift axe", "makeshift pickaxe", "makeshift shovel", "stone axe", "stone pickake", "stone shovel"}, // tools
			{"makeshift sword", "stone sword"} // weapons
	};
	
	protected Texture[][] textures = {
			{}, // regular items
			{}, // tools
			{} // weapons
	};
	
	protected int id;
	protected String name;
	protected Texture texture;
	protected int quantity;
	protected boolean stackable;
	
	public Item(int id) {
		this.id = id;
		int r = id / 10;
		int c = id % 10;
		texture = textures[r][c];
	}
	
	public int getID() { return id; }
	public String getName() { return name; }
	public Texture getTexture() { return texture; }
	public int getQuantity() { return quantity; }
	public boolean canStack() { return stackable; }
	
	public void takeQuantity(int quantity) {
		this.quantity -= quantity;
	}
	
	public void stack(int amount) {
		this.quantity += amount;
	}
	
}