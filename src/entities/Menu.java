package entities;

import java.util.ArrayList;
import java.util.List;

public class Menu {
	private List<Item> menu = null;
	
	public Menu() {
		menu = new ArrayList<Item>();
	}
	
	public List<Item> getMenu() {
		return menu;
	}
	
	public void addItem(Item item) {
		
	}
}
