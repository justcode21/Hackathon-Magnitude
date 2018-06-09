package user;

import java.util.HashSet;

import entities.Item;

public class Order {
	
	private int orderId;
	private int userId;
	private HashSet<Item> orderedItems = null;
	
	public Order(int userId) {	
		this.userId = userId;
		orderedItems = new HashSet<Item>();
	}
	
	// Getters and Setters
	public int getOrderId() {
		return orderId;
	}
	public void setOrderId(int orderId) {
		this.orderId = orderId;
	}
	public int getUserId() {
		return userId;
	}
	public HashSet<Item> getOrderedItems() {
		return orderedItems;
	}
	
	
	// Individual Functions
	public void addItem(Item item) {	
		item.setOrderId(orderId);
		orderedItems.add(item);
	}
	
	public void removeItem(Item item) {
		orderedItems.remove(item);
	}
}
