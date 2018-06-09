package user;

import java.util.Scanner;

import org.json.simple.JSONObject;

import actions.Order;
import entities.Item;
import entities.Menu;
import tools.HttpConnections;
import tools.IO;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;


public class User {
	
	private int userId;
	private String userName;
	private String loginId;
	private String password;
	
	private boolean isLoggedIn;
	
	private Order userOrders = null;

	public static HashSet<Integer>UserId = new HashSet<Integer>();
	
	public User(){
		getDetails();
		isLoggedIn = false;
		userOrders = new Order(userId);
	}
	
	// Getters and Setters
	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getLoginId() {
		return loginId;
	}
	public void setLoginId(String loginId) {
		this.loginId = loginId;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
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
	
	public void loginUser(){
		
	}
	
	public void addOrder(Item item) {
		userOrders.addItem(item);
	}
	
	public void deleteOrder(Item item){
		userOrders.removeItem(item);
	}
	
	public void viewMenuItems() {
		Menu.displayMenu();
	}
}
