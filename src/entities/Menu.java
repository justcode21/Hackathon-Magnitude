package entities;

import java.util.HashSet;

public class Menu {
	private HashSet<Integer> menu = null;
	
	public Menu() {
		menu = new HashSet<Integer>();
	}
	
	public HashSet<Integer> getMenu() {
		return menu;
	}
	
	public void addItem(Item item) {
		menu.add(item.getItemId());
	}
	
	public static void displayMenu() {
		// Print all the item details from the database, using the itemId 
		/*for(int item : menu) 
			System.out.println(item);*/
	}
}
