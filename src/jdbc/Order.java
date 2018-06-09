package jdbc;

import java.io.IOException;
import java.io.PrintWriter;
import java.security.SecureRandom;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Random;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

/**
 * Servlet implementation class Order
 */
@WebServlet("/Order")
public class Order extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Order() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request,response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		PrintWriter pw = response.getWriter();
		String sql = "insert into magnifood.order(OrderId,UserId,ItemId,Quantity) values(?,?,?,?)";
		String UserId = request.getParameter("userId");
		//String OrderId = request.getParameter("orderId");
		String[] itemIds = request.getParameter("itemId").split(",");
		String[] quantity = request.getParameter("quantity").split(",");
		
		try {
			Class.forName("com.mysql.jdbc.Driver");
			Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/magnifood", "root", "yash1234");
			PreparedStatement ps = conn.prepareStatement(sql);
			
			ps.setString(1, getOrderId());
			ps.setString(2, UserId);
			int n = itemIds.length;
			int m = quantity.length;
			
			if(n == m) {
				for(int i=0;i<n;i++) {
					ps.setString(3, itemIds[i]);
					ps.setString(4, quantity[i]);
					ps.executeUpdate();
				}
			}
			else {
				
			}
			
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private String getOrderId() {
		String phone = "123456789";
		Long seed = Long.valueOf(phone) * 1000 + System.currentTimeMillis() % 1000L;
		return generateOrder(seed);
 	}

	private String generateOrder(Long seed) {
		Random random = new SecureRandom();
	    random.setSeed(seed);
	    int CODE_LENGTH = 8;

	    Long randomLong = random.nextLong();
	    String pw =  Long.toString(randomLong).substring(1, CODE_LENGTH+1); // 
	    return pw ; 
	}

}
