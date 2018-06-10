package jdbc;

import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.util.Random;
import java.sql.Connection;

public class ProcessOrders extends Thread{

	private String Id;
	private String userId;
	private String orderId;
	
	public ProcessOrders(String userId,String orderId,String Id) {
		this.Id = Id;
		this.userId = userId;
		this.orderId = orderId;
	}
	
	private String getAId() {
		return this.Id;
	}
	
	private String getUserId() {
		return this.userId;
	}
	
	private String getOrderId() {
		return this.orderId;
	}


	public void execute() {
		run();
	}
	
	public void run() {
		try {
			Class.forName("com.mysql.jdbc.Driver");
			Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/magnifood", "root", "yash1234");
			String sql = "UPDATE magnifood.order SET Status=? WHERE Id=?";
			String sql2 = "INSERT INTO magnifood.usernotification (UserId, Mess1, Mess2, Mess3, Mess4, OrderId) VALUES (?, ?, ?, ?, ?, ?)";
			PreparedStatement ps = conn.prepareStatement(sql);
			PreparedStatement ps2 = conn.prepareStatement(sql2);
			ps.setString(2, getAId());
			Random r = new Random();
			int totalTimeToProcess = r.nextInt(50) + 50;
			int first  = (int) (totalTimeToProcess * 0.1);
			int second = (int) (totalTimeToProcess * 0.7);
			boolean done1 = false;
			boolean done2 = false;
			ps.setString(1, "Placed");
			String mess1 = Long.toString(System.currentTimeMillis());
			String mess2 = "";
			String mess3 = "";
			String mess4 = "";
			ps.executeUpdate();
			for(int i=0;i<totalTimeToProcess;i++) {
				if(i > first && i < second && done1 == false) {
					done1 = true;
					ps.setString(1, "Processing");
					mess2 = Long.toString(System.currentTimeMillis());
					ps.executeUpdate();
				}
				else if(i > second && done2 == false) {
					done2 = true;
					ps.setString(1, "Out for delivery");
					mess3 = Long.toString(System.currentTimeMillis());
					ps.executeUpdate();
				}
				Thread.sleep(100);
				
			}
			mess4 = Long.toString(System.currentTimeMillis());
			ps.setString(1, "Delivery");
			ps.executeUpdate();
			
			ps2.setString(1, getUserId());
			ps2.setString(2, mess1);
			ps2.setString(3, mess2);
			ps2.setString(4, mess3);
			ps2.setString(5, mess4);
			ps2.setString(6, orderId);
			ps2.executeUpdate();
		}catch(Exception e) {
			e.printStackTrace();
		}
	}

}
