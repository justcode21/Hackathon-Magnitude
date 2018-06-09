package actions;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.Scanner;

import entities.Menu;
import tools.LoginOptions;

public class DriverProgram {
	
	public static void printPhaseOptions(int phase) {
		String documentToRead = "src/Phase" + Integer.toString(phase) + ".txt";
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
	
	public static void main(String[] args) throws Exception{
		
		int currentPhase = 1, choice;
		String currentLoginId = null;
		Scanner inputSource = new Scanner(System.in);
		while(true){
			printPhaseOptions(currentPhase);
			choice = inputSource.nextInt();
			if(choice == 1)
				LoginOptions.createNewUser();
			else if(choice == 2) {
				currentLoginId = LoginOptions.validateUserLogin();
			}
			else if(choice == 3) {
				Menu.displayMenu(currentLoginId);
			}
			else if(choice == 4) {
				Order.getOrders(currentLoginId);
			}
				
		}
	}
}
