package entities;

import java.util.HashMap;
import java.util.Map;

import org.json.simple.JSONObject;

import tools.HttpConnections;

public class Menu {
		
	public static void displayMenu(String loginId) {
		Map<String, String>parameters = new HashMap<String, String>();
		parameters.put("loginId", loginId);
		JSONObject response = HttpConnections.sendRequestToServer("Items", parameters);
		System.out.println(response.toString());
	}
}
