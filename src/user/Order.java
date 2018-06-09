package user;

import java.util.HashSet;

import entities.Item;

public class Order {
	
	private int orderId;
	private int userId;
	private HashSet<Integer> orderedItems = null;
	
	public Order(int userId) {	
		this.userId = userId;
		orderedItems = new HashSet<Integer>();
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
	public HashSet<Integer> getOrderedItems() {
		return orderedItems;
	}
	
	
	// Individual Functions
	public void addItem(Item item) {	
		orderedItems.add(item.getItemId());
	}
	
	public void removeItem(Item item) {
		orderedItems.remove(item.getItemId());
	}
}
