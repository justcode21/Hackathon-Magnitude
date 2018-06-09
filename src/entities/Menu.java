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
		String itemName = IO.inputSource.nextLine();
		String itemCost = IO.inputSource.nextLine();
		parameters.put("loginId", loginId);
		parameters.put("itemName", itemName);
		parameters.put("itemCost", itemCost);
		JSONObject response = HttpConnections.sendRequestToServer("AddItems", parameters);
		System.out.println(response.toString());		
	}
}
