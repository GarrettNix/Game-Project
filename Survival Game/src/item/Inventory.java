package item;

public class Inventory {
	
	private Item[][] inventory;
	private Item[] hotbar;
	
	public Inventory() {
		inventory = new Item[3][9];
		hotbar = new Item[6];
	}
	
	/**
	 * Pick up an item from the ground. If it can stack, find a stack that has
	 * enough free space to add this item. If none exists, place remaining quantity
	 * in an empty space.
	 * If it can't stack, find an empty slot to place this item. If none exists,
	 * cancel.
	 * 
	 * return:
	 * -1 if item cannot be placed in inventory
	 * amt remaining if only some of the item can be placed in inventory
	 * -2 if pickup is successful
	 */
	public int pickUp(Item item) {
		if (item.canStack()) {
			// check stack in hotbar
			for (int c = 0; c < hotbar.length; c++) {
				if (hotbar[c] == null) continue; // nothing here
				if (hotbar[c].getID() == item.getID()) { // same item, check stacking
					int spaceAvailable = 99 - hotbar[c].getQuantity();
					if (item.getQuantity() > spaceAvailable) { // too many for one stack
						hotbar[c].stack(spaceAvailable);
						item.takeQuantity(spaceAvailable);
						// there is some quantity left, so run this again to check for more slots/stacks
						pickUp(item);
					}
					else { // enough room, success
						hotbar[c].stack(item.getQuantity());
						return -2;
					}
				}
				else continue; // different items
			}
			// if this is reached, no stack in hotbar, so check for stack in inventory
			for (int r = 0; r < inventory.length; r++) {
				for (int c = 0; c < inventory[r].length; c++) {
					if (inventory[r][c] == null) continue; // nothing here
					if (inventory[r][c].getID() == item.getID()) { // same item, check stacking
						int spaceAvailable = 99 - inventory[r][c].getQuantity();
						if (item.getQuantity() > spaceAvailable) { // too many for one stack
							inventory[r][c].stack(spaceAvailable);
							item.takeQuantity(spaceAvailable);
							// there is some quantity left, so run this again to check for more slots/stacks
							pickUp(item);
						}
					}
					else continue; // different items
				}
			}
			// if this point is reached, no stack is available, so check for empty slots in hotbar
			for (int c = 0; c < hotbar.length; c++) {
				if (hotbar[c] == null) { // empty slot yay
					hotbar[c] = item;
					return -2;
				}
			}
			// if this point is reached, no slots left in hotbar, so check for empty slots in inventory
			for (int r = 0; r < inventory.length; r++) {
				for (int c = 0; c < inventory[r].length; c++) {
					if (inventory[r][c] == null) {
						inventory[r][c] = item;
						return -2; // empty slot yay
					}
				}
			}
			// if this point is reached, nowhere in the inventory may this item be inserted, return -1
			return -1;
		}
		else {
			// check hotbar
			for (int c = 0; c < hotbar.length; c++) {
				if (hotbar[c] == null) { // slot found
					hotbar[c] = item;
					return -2;
				}
			}
			// if this code is reached, hotbar scan failed. check inventory
			for (int r = 0; r < inventory.length; r++) {
				for (int c = 0; c < inventory[r].length; c++) {
					if (inventory[r][c] == null) { // slot found
						inventory[r][c] = item;
						return -2;
					}
				}
			}
			// if this code is reached, there is no slot. return -1
			return -1;
		}
	}
}