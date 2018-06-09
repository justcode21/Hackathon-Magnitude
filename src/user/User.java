package user;

import entities.Item;

public class User {
	
	private int userId;
	private String userName;
	private String userCompany;
	private String loginId;
	private String password;
	
	private boolean isLoggedIn;
	
	private Order userOrders = null;
	
	public User() {
		userName = null;
		userCompany = null;
		loginId = null;
		password = null;
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
	public String getUserCompany() {
		return userCompany;
	}
	public void setUserCompany(String userCompany) {
		this.userCompany = userCompany;
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
	public void addOrder(Item item) {
		userOrders.addItem(item);
	}
	
	public void deleteOrder(Item item){
		userOrders.removeItem(item);
	}
}
