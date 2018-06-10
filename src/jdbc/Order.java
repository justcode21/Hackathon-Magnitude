package jdbc;

import java.io.IOException;
import java.io.PrintWriter;
import java.security.SecureRandom;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
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

import com.sun.org.apache.xpath.internal.operations.Bool;

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
		String sql = "insert into magnifood.order(OrderId,UserId,ItemId,Quantity,CafeId,Status) values(?,?,?,?,?,?)";
		String UserId = request.getParameter("loginId");
		//String OrderId = request.getParameter("orderId");
		String[] itemIds = request.getParameter("itemId").split(",");
		String[] quantity = request.getParameter("quantity").split(",");
		String cafeName = request.getParameter("cafeName");
		
		System.out.println(request.getParameter("itemId"));
		System.out.println(request.getParameter("quantity"));
		
		String sql2 = "select ingredientid,quantity from magnifood.ingredients_items where itemid = ?";
		String sql3 = "select ingredientquantity,id from magnifood.ingredients where cafeid = ?";
		String sql4 = "select cafeid from magnifood.cafe where cafename = ?";
		String sql5 = "UPDATE magnifood.item SET OutOfStock=? WHERE itemId=?";
		String sql6 = "UPDATE magnifood.ingredients SET ingredientquantity=? WHERE id=?";
		
		try {
			Class.forName("com.mysql.jdbc.Driver");
			Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/magnifood", "root", "yash1234");
			PreparedStatement ps4 = conn.prepareStatement(sql4);
			ps4.setString(1, cafeName);
			String dbCafeId = null;
			ResultSet rs4 = ps4.executeQuery();
			while(rs4.next()) {
				dbCafeId = rs4.getString(1);
			}
			System.out.println("cafe id " + dbCafeId);
			PreparedStatement ps = conn.prepareStatement(sql);
			String generatedOrderID = getOrderId();
			ps.setString(1, generatedOrderID);
			ps.setString(2, UserId);
			int n = itemIds.length;
			int m = quantity.length;
			JSONArray ja = new JSONArray();
			if(n == m) {
				for(int i=0;i<n;i++) {
					ps.setString(3, itemIds[i]);
					ps.setString(4, quantity[i]);
					System.out.println(ps.toString());
					PreparedStatement ps2 = conn.prepareStatement(sql2);
					ps2.setString(1, itemIds[i]);
					ResultSet rs2 = ps2.executeQuery();
					Map<String,String> mtp = new HashMap<String,String>();
					while(rs2.next()) {
						String key = (rs2.getString(1));
						String value = (Integer.toString(Integer.parseInt(rs2.getString(2)) * Integer.parseInt(quantity[i])));
						mtp.put(key, value);
					}
					Boolean flag = false;
					System.out.println("here1");
					PreparedStatement ps3 = conn.prepareStatement(sql3);
					ps3.setString(1, dbCafeId);
					ResultSet rs3 = ps3.executeQuery();
					while(rs3.next()) {
						int available = Integer.parseInt(rs3.getString(1));
						int toBeUsed = Integer.parseInt(mtp.get(rs3.getString(2)));
						if(available < toBeUsed) {
							flag = true;
						}
					}
					System.out.println("flag is : " + flag);
					if(!flag) {
						ps.setString(5, dbCafeId);
						ps.setString(6, "ordered confirmed");
						ps.executeUpdate();
						ResultSet rsx = ps3.executeQuery();
						while(rsx.next()) {
							String id = rsx.getString(2);
							String newQuantity = Integer.toString(Integer.parseInt(rsx.getString(1)) - Integer.parseInt(mtp.get(id)));
							PreparedStatement ps6 = conn.prepareStatement(sql6);
							ps6.setString(1, newQuantity);
							ps6.setString(2, id);
							ps6.executeUpdate();
						}
						JSONObject jx = new JSONObject();
						jx.put("Orderid Status","confirmed");
						ja.add(jx);
					}
					else {
						ps.setString(5, dbCafeId);
						ps.setString(6,"out of stock");
						ps.executeUpdate();
						PreparedStatement ps5 = conn.prepareStatement(sql5);
						ps5.setString(1, "out of stock");
						ps5.setString(2, itemIds[i]);
						ps5.executeUpdate();
						JSONObject jz = new JSONObject();
						jz.put("Orderid Status","confirmed");
						ja.add(jz);
						
					}
				}
				JSONObject jo = new JSONObject();
				jo.put("OrderID", generatedOrderID);
				jo.put("values", ja);
				pw.println(jo);
				pw.flush();
			}
			else {
				JSONObject jo = new JSONObject();
				jo.put("OrderID", "-1");
				pw.println(jo);
				pw.flush();
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
