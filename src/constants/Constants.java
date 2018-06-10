package constants;

public class Constants {

	public static enum loginOptions{NULL, REGISTER, LOGIN};
	
	public static enum userTypes{NORMAL_USER, CAFE_USER, CAFETERIA_USER};
	
	public static enum normanUserOperations{NULL, GET_MENU, GIVE_ORDER};
	
	public static enum cafeUserOperations{NULL, ADD_ITEM, UPDATE_ITEM, REMOVE_ITEM, PRINT_ORDERS};
	
	public static enum cafeteriaUserOperations{NULL, ADD_CAFE, UPDATE_CAFE, REMOVE_CAFE, PRINT_CAFE, PRINT_ORDER_PER_CAFE, PRINT_ALL_ORDERS, ADD_INGRIDIENT};
	
}
