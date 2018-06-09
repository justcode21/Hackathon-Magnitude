package entities;

public class Item {
	
	private int itemId;
	private int itemName;
	private double itemCost;

	public Item() {
		
		itemId = -1;
	}
	
	// Getters and Setters
	public int getItemId() {
		return itemId;
	}
	public void setItemId(int itemId) {
		this.itemId = itemId;
	}
	public int getItemName() {
		return itemName;
	}
	public void setItemName(int itemName) {
		this.itemName = itemName;
	}
	public double getItemCost() {
		return itemCost;
	}
	public void setItemCost(double itemCost) {
		this.itemCost = itemCost;
	}
}
