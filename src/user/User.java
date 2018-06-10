package user;

import actions.Order;
import entities.Item;
import entities.Menu;
import tools.IO;

import java.util.HashSet;


public class User {
	
	private int userId;
	private String userName;
	private String loginId;
	private String password;
	
	public User(){
		getDetails();
	}
	// Getters and Setters
	public int getUserId() {
		return userId;
	}
	public String getUserName() {
		return userName;
	}
	public String getLoginId() {
		return loginId;
	}
	public String getPassword() {
		return password;
	}
	
	
	// Individual Functions	
	public void getDetails(){
		System.out.println("Name: ");
		userName = IO.inputSource.nextLine();
		
		System.out.println("LoginId: ");
		loginId = IO.inputSource.nextLine();
		
		System.out.println("Password: ");
		password = IO.inputSource.nextLine();
	
	}
}
