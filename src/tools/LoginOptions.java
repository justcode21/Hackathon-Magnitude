package tools;

import java.util.HashMap;
import java.util.Map;

import org.json.simple.JSONObject;

import user.User;

public class LoginOptions {
	
	public static void createNewUser() {
		User newUser = new User();
		User.UserId.add(newUser.getUserId());
		Map<String, String>mymap = new HashMap<String, String>();
		mymap.put("userName", newUser.getUserName());
		mymap.put("loginId", newUser.getLoginId());
		mymap.put("password", newUser.getPassword());
		
		JSONObject response = HttpConnections.sendRequestToServer("Registration", mymap);
		newUser.setUserId(Integer.parseInt(response.get("registeredStatus").toString()));
	}
	
	public static void validateUserLogin() {
		String loginId, password;
		loginId = IO.inputSource.nextLine();
		password = IO.inputSource.nextLine();
		Map<String, String>mymap = new HashMap<String, String>();
		mymap.put("loginId", loginId);
		mymap.put("password", password);
		JSONObject response = HttpConnections.sendRequestToServer("Login", mymap);
		System.out.println(response.get("loggedInStatus"));			
	}

}
