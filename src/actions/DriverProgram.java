package actions;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.Scanner;

import constants.Constants;
import entities.Cafeteria;
import entities.Menu;
import jdk.internal.org.xml.sax.InputSource;
import tools.LoginOptions;

public class DriverProgram {

	private static Scanner inputSource = new Scanner(System.in);
	public static int userFlag = -1;
	
	private static void resetUserFlag() {
		userFlag = -1;
	}
	
	public static void printScreen(String fileName) {
		String documentToRead = "src/" + fileName + ".txt";
		try (BufferedReader br = new BufferedReader(new FileReader(documentToRead))) {
			String line = null;
			while ((line = br.readLine()) != null) {
				System.out.println(line);
			}
		}
		catch(Exception e) {
			e.printStackTrace();		
		}
	}
	
	public static void cafeteriaUserLogin(String currentLoginId) {
		while(true) {
			printScreen("CafeteriaUserOptions");
			int choice = inputSource.nextInt();
			if(choice ==  Constants.cafeteriaUserOperations.ADD_CAFE.ordinal())
				Cafeteria.addCafe(currentLoginId);
			else if(choice == Constants.cafeteriaUserOperations.UPDATE_CAFE.ordinal())
				Cafeteria.updateCafe(currentLoginId);
			else if(choice == Constants.cafeteriaUserOperations.REMOVE_CAFE.ordinal())
				Cafeteria.removeCafe(currentLoginId);
			else if(choice == Constants.cafeteriaUserOperations.PRINT_CAFE.ordinal())
				Order.printAllOrders("ViewCafe", currentLoginId, 0);
			else if(choice == Constants.cafeteriaUserOperations.PRINT_ORDER_PER_CAFE.ordinal())
				Order.printAllOrders("ViewOrders", currentLoginId, 1);
			else if(choice == Constants.cafeteriaUserOperations.PRINT_ALL_ORDERS.ordinal())
				Order.printAllOrders("ViewOrders", currentLoginId, 0);
			else if(choice == Constants.cafeteriaUserOperations.ADD_INGRIDIENT.ordinal())
				Cafeteria.addIngridient(currentLoginId);
			else break;
		}
		resetUserFlag();
	}
	
	public static void cafeUserLogin(String currentLoginId) {
		while(true) {
			printScreen("CafeUserOptions");
			int choice = inputSource.nextInt();
			if(choice == Constants.cafeUserOperations.ADD_ITEM.ordinal())
				Menu.addItems(currentLoginId);
			else if(choice == Constants.cafeUserOperations.UPDATE_ITEM.ordinal())
				Menu.updateMenu(currentLoginId);
			else if(choice ==  Constants.cafeUserOperations.REMOVE_ITEM.ordinal())
				Menu.removeItem(currentLoginId);
			else if(choice ==  Constants.cafeUserOperations.PRINT_ORDERS.ordinal())
				Order.printAllOrders("ViewItems", currentLoginId, 0);
			else break;
		}
		resetUserFlag();
	}
	
	public static void normalUserLogin(String currentLoginId) {
		while(true) {
			printScreen("NormalUserOptions");
			int choice = inputSource.nextInt();
			if(choice == Constants.normanUserOperations.GET_MENU.ordinal())
				Menu.displayMenu("Items", currentLoginId);
			else if(choice == Constants.normanUserOperations.GIVE_ORDER.ordinal())
				Order.getOrders(currentLoginId);
			else break;
		}
		resetUserFlag();
	}
	
	public static void loginUser() {
		String currentLoginId = LoginOptions.validateUserLogin();
		while(true) {
			if(userFlag == Constants.userTypes.NORMAL_USER.ordinal())
				normalUserLogin(currentLoginId);
			else if(userFlag == Constants.userTypes.CAFE_USER.ordinal())
				cafeUserLogin(currentLoginId);
			else if(userFlag == Constants.userTypes.CAFETERIA_USER.ordinal())
				cafeteriaUserLogin(currentLoginId);
			else break;
		}
		resetUserFlag();
	}
	
	public static void regesterUser() {
		LoginOptions.createNewUser();
	}
	
	public static void main(String[] args) throws Exception{
		
		while(true){
			printScreen("Home");
			int choice = inputSource.nextInt();
			if(choice == Constants.loginOptions.REGISTER.ordinal())
				regesterUser();
			else if(choice == Constants.loginOptions.LOGIN.ordinal()) 
				loginUser();	
			else 
				break;
		}
		resetUserFlag();
	}
}
