package entities;

import java.util.HashMap;
import java.util.Map;

import org.json.simple.JSONObject;

import tools.HttpConnections;
import tools.IO;

public class Menu {
		
	public static void displayMenu(String type, String loginId) {
		Map<String, String>parameters = new HashMap<String, String>();
		parameters.put("loginId", loginId);
		JSONObject response = HttpConnections.sendRequestToServer(type, parameters);
		System.out.println(response.toString());
	}
	
	public static void addItems(String loginId) {
		Map<String, String>parameters = new HashMap<String, String>();
		
		System.out.println("ItemName: ");
		String itemName = IO.inputSource.nextLine();
		
		System.out.println("ItemCost: ");
		String itemCost = IO.inputSource.nextLine();
		parameters.put("loginId", loginId);
		parameters.put("itemName", itemName);
		parameters.put("itemCost", itemCost);
		JSONObject response = HttpConnections.sendRequestToServer("AddItems", parameters);
		System.out.println(response.toString());		
	}
	
	public static void updateMenu(String loginId) {
		Map<String, String>parameters = new HashMap<String, String>();
		
		System.out.println("ItemName: ");
		String oldItemName = IO.inputSource.nextLine();
		
		System.out.println("newItemName");
		String newItemName = IO.inputSource.nextLine();
		
		System.out.println("newItemCost: ");
		String newItemCost = IO.inputSource.nextLine();
		
		parameters.put("loginId", loginId);
		parameters.put("oldItemName", oldItemName);
		parameters.put("newItemName", newItemName);
		parameters.put("newItemCost", newItemCost);
		JSONObject response = HttpConnections.sendRequestToServer("EditItems", parameters);
		System.out.println(response.toString());
	}
	
	public static void removeItem(String loginId) {
		Map<String, String>parameters = new HashMap<String, String>();
		
		System.out.println("ItemName: ");
		String itemName = IO.inputSource.nextLine();
		
		parameters.put("loginId", loginId);
		parameters.put("itemName", itemName);
		JSONObject response = HttpConnections.sendRequestToServer("RemoveItems", parameters);
		System.out.println(response.toString());
	}
}
