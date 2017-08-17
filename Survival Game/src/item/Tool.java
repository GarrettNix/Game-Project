package item;

public class Tool extends Item {
	
	public static final int AXE = 0;
	public static final int PICKAXE = 1;
	public static final int SHOVEL = 2;
	public static final int HOE = 3;
	
	protected int durability;
	protected int maxDurability;
	
	public Tool(int id) {
		super(id);
	}
	
}