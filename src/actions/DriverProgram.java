package actions;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.Scanner;

import entities.Menu;
import jdk.internal.org.xml.sax.InputSource;
import tools.LoginOptions;

public class DriverProgram {
	
	private static Scanner inputSource = new Scanner(System.in);
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
	
	public static void regesterUser() {
		LoginOptions.createNewUser();
	}
	
	public static void loginUser() {
		String currentLoginId = LoginOptions.validateUserLogin();
		while(true) {
			printScreen("LoginOptions");
			int choice = inputSource.nextInt();
			if(choice == 1)
				Menu.displayMenu(currentLoginId);
			else if(choice == 2)
				Order.getOrders(currentLoginId);
			else break;
		}
	}
	
	public static void main(String[] args) throws Exception{
		
		while(true){
			printScreen("Home");
			int choice = inputSource.nextInt();
			if(choice == 1)
				regesterUser();
			else if(choice == 2) 
				loginUser();	
			else break;
		}
	}
}
