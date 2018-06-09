package entities;

import java.util.HashMap;
import java.util.Map;

import org.json.simple.JSONObject;

import tools.HttpConnections;
import tools.IO;

public class Cafeteria {

	public static void removeCafe(String loginId) {
		Map<String, String>parameters = new HashMap<String, String>();
		
		System.out.println("CafeName: ");
		String cafeName = IO.inputSource.nextLine();
		
		parameters.put("loginId", loginId);
		parameters.put("cafeName", cafeName);
		JSONObject response = HttpConnections.sendRequestToServer("RemoveCafe", parameters);
		System.out.println(response.toString());
	}
	
	public static void updateCafe(String loginId) {
		Map<String, String>parameters = new HashMap<String, String>();
		
		System.out.println("CafeName: ");
		String oldCafeName = IO.inputSource.nextLine();
		
		System.out.println("NewCafeName: ");
		String newCafeName = IO.inputSource.nextLine();
		
		parameters.put("loginId", loginId);
		parameters.put("oldCafeName", oldCafeName);
		parameters.put("newCafeName", newCafeName);
		JSONObject response = HttpConnections.sendRequestToServer("EditCafe", parameters);
		System.out.println(response.toString());
	}
	
	public static void addCafe(String loginId) {
		Map<String, String>parameters = new HashMap<String, String>();
		
		System.out.println("CafeName: ");
		String cafeName = IO.inputSource.nextLine();
		
		System.out.println("CafeManagerName: ");
		String cafeUserName = IO.inputSource.nextLine();
		
		parameters.put("loginId", loginId);
		parameters.put("cafeName", cafeName);
		parameters.put("cafeUserName", cafeUserName);
		
		JSONObject response = HttpConnections.sendRequestToServer("AddCafe", parameters);
		System.out.println(response.toString());
		
	}
	
}
