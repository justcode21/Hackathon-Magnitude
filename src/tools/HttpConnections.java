package tools;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Map;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;


public class HttpConnections {

	public static String parametersToString(Map<String, String>parameters) {
		String parameterString = "";
		for (Map.Entry<String,String> entry : parameters.entrySet()) 
			parameterString += entry.getKey() + "=" + entry.getValue() + "&";
		return parameterString.substring(0, parameterString.length() - 1);
	}
	
	public static JSONObject sendRequestToServer(String type, Map<String, String>parameters) {
		
		String urlString = "http://10.54.3.47:8080/MagniFoods-jsp/" + type + "?" + parametersToString(parameters);
		System.out.println(urlString);
		StringBuffer response = new StringBuffer();
		JSONObject jsonResponse = null;
		try {
			URL obj = new URL(urlString);
			HttpURLConnection con = (HttpURLConnection) obj.openConnection();
			con.setRequestMethod("GET");
			int responseCode = con.getResponseCode();
			System.out.println(responseCode);
			if (responseCode == HttpURLConnection.HTTP_OK) { // success
				BufferedReader in = new BufferedReader(new InputStreamReader(
						con.getInputStream()));
				String inputLine;

				while ((inputLine = in.readLine()) != null) {
					response.append(inputLine);
				}
				in.close();
			} else {
				System.out.println("GET request not worked");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
			System.out.println(response.toString());
			jsonResponse = (JSONObject) new JSONParser().parse(response.toString());
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return jsonResponse;
	}
	
}
