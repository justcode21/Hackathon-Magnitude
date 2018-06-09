package actions;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.Scanner;

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
			if(choice == 1)
				Cafeteria.addCafe(currentLoginId);
			else if(choice == 2)
				Cafeteria.updateCafe(currentLoginId);
			else if(choice == 3)
				Cafeteria.removeCafe(currentLoginId);
			else if(choice == 4)
				Order.printAllOrders("ViewCafe", currentLoginId, 0);
			else if(choice == 5)
				Order.printAllOrders("ViewOrders", currentLoginId, 1);
			else if(choice == 6)
				Order.printAllOrders("ViewOrders", currentLoginId, 0);
			else break;
		}
		resetUserFlag();
	}
	
	public static void cafeUserLogin(String currentLoginId) {
		while(true) {
			printScreen("CafeUserOptions");
			int choice = inputSource.nextInt();
			if(choice == 1)
				Menu.addItems(currentLoginId);
			else if(choice == 2)
				Menu.updateMenu(currentLoginId);
			else if(choice == 3)
				Menu.removeItem(currentLoginId);
			else if(choice == 4)
				Order.printAllOrders("ViewItems", currentLoginId, 0);
			else break;
		}
		resetUserFlag();
	}
	
	public static void normalUserLogin(String currentLoginId) {
		while(true) {
			printScreen("NormalUserOptions");
			int choice = inputSource.nextInt();
			if(choice == 1)
				Menu.displayMenu("Items", currentLoginId);
			else if(choice == 2)
				Order.getOrders(currentLoginId);
			else break;
		}
		resetUserFlag();
	}
	
	public static void loginUser() {
		String currentLoginId = LoginOptions.validateUserLogin();
		while(true) {
			if(userFlag == 0)
				normalUserLogin(currentLoginId);
			else if(userFlag == 1)
				cafeUserLogin(currentLoginId);
			else if(userFlag == 2)
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
			if(choice == 1)
				regesterUser();
			else if(choice == 2) 
				loginUser();	
			else 
				break;
		}
		resetUserFlag();
	}
}
