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
}
