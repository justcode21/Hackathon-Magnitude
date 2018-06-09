package actions;

import java.util.HashMap;
import java.util.Map;

import org.json.simple.JSONObject;

import tools.HttpConnections;
import tools.IO;

public class Order {
	
	// Individual Functions	
	public static void getOrders(String loginId) {
		
		System.out.println("Order: ");
		String currentOrder = IO.inputSource.nextLine();
		
		System.out.println("Quntatiy: ");
		String quantity = IO.inputSource.nextLine();
		
		Map<String, String>parameters = new HashMap<String, String>();
		parameters.put("loginId", loginId);
		parameters.put("itemId", currentOrder);
		parameters.put("quantity", quantity);
		
		JSONObject response = HttpConnections.sendRequestToServer("Order", parameters);
		System.out.println(response.toString());
	}
		
	public static void printAllOrders(String type, String loginId, int flag) {
		Map<String, String>parameters = new HashMap<String, String>();
		parameters.put("loginId", loginId);
		if(flag == 1) {
			System.out.println("CafeId: ");
			int cafeId = IO.inputSource.nextInt();
			parameters.put("cafeId", Integer.toString(cafeId));
		}
		JSONObject response = HttpConnections.sendRequestToServer(type, parameters);
		System.out.println(response.toString());		
	}
}
