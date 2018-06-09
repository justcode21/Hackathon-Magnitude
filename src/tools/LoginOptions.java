package tools;

import java.util.HashMap;
import java.util.Map;

import org.json.simple.JSONObject;

import user.User;

public class LoginOptions {
	
	public static void createNewUser() {
		User newUser = new User();
		Map<String, String>parameters = new HashMap<String, String>();
		parameters.put("userName", newUser.getUserName());
		parameters.put("loginId", newUser.getLoginId());
		parameters.put("password", newUser.getPassword());
		
		JSONObject response = HttpConnections.sendRequestToServer("Registration", parameters);
		System.out.println(response.toString());
	}
	
	public static String validateUserLogin() {
		String loginId, password;
		
		System.out.println("loginId: ");
		loginId = IO.inputSource.nextLine();
		
		System.out.println("password: ");
		password = IO.inputSource.nextLine();
		
		Map<String, String>parameters = new HashMap<String, String>();
		parameters.put("loginId", loginId);
		parameters.put("password", password);
		JSONObject response = HttpConnections.sendRequestToServer("Login", parameters);
		if(response.get("loggedInStatus").equals("true"))
			return loginId;
		return null;
	}

}
