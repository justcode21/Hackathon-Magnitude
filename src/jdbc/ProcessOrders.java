package jdbc;

import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.util.Random;
import java.sql.Connection;

public class ProcessOrders extends Thread{

	private String Id;
	
	public ProcessOrders(String Id) {
		this.Id = Id;
	}
	
	private String getAId() {
		return this.Id;
	}


	public void execute() {
		run();
	}
	
	public void run() {
		try {
			Class.forName("com.mysql.jdbc.Driver");
			Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/magnifood", "root", "yash1234");
			String sql = "UPDATE magnifood.order SET Status=? WHERE Id=?";
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setString(2, getAId());
			Random r = new Random();
			int totalTimeToProcess = r.nextInt(50) + 50;
			int first  = (int) (totalTimeToProcess * 0.1);
			int second = (int) (totalTimeToProcess * 0.7);
			boolean done1 = false;
			boolean done2 = false;
			ps.setString(1, "Placed");
			ps.executeUpdate();
			for(int i=0;i<totalTimeToProcess;i++) {
				if(i > first && i < second && done1 == false) {
					done1 = true;
					ps.setString(1, "Processing");
					ps.executeUpdate();
				}
				else if(i > second && done2 == false) {
					done2 = true;
					ps.setString(1, "Out for delivery");
					ps.executeUpdate();
				}
				Thread.sleep(100);
				
			}
			ps.setString(1, "Delivery");
			ps.executeUpdate();
		}catch(Exception e) {
			e.printStackTrace();
		}
	}

}
