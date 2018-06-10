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
		String sql7 = "select cafeid from magnifood.cafe";
		try {
			Class.forName("com.mysql.jdbc.Driver");
			Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/magnifood", "root", "yash1234");
			PreparedStatement ps4 = conn.prepareStatement(sql4);
			//PreparedStatement ps7 = conn.prepareStatement(sql7);
			//ResultSet rs7 = ps7.executeQuery();
			//while(rs7.next()) {
				//String dbCafeId = rs7.getString(1);
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
				if(n == m && dbCafeId!=null) {
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
							System.out.println(key + " " + value);
							mtp.put(key, value);
						}
						Boolean flag = false;
						int executed = 0;
						//System.out.println("here1");
						PreparedStatement ps3 = conn.prepareStatement(sql3);
						ps3.setString(1, dbCafeId);
						System.out.println(ps3.toString());
						ResultSet rs3 = ps3.executeQuery();
						while(rs3.next()) {
							String val = mtp.get(rs3.getString(2));
							if(val == null)
								continue;
							//System.out.println(key + " "+ value);
							int available = Integer.parseInt(rs3.getString(1));
							int toBeUsed = Integer.parseInt(mtp.get(rs3.getString(2)));
							if(available < toBeUsed) {
								flag = true;
							}
							else {
								executed++;
							}
						}
						System.out.println("flag is : " + flag);
						System.out.println("executed : " + executed);
						if(!flag && executed == mtp.size()) {
							ps.setString(5, dbCafeId);
							ps.setString(6, "ordered pending");
							ps.executeUpdate();
							try {
								processOrder(conn,generatedOrderID,itemIds[i]);
							} catch (Exception e) {
								e.printStackTrace();
							}
							ResultSet rsx = ps3.executeQuery();
							while(rsx.next()) {
								String id = rsx.getString(2);
								if(mtp.get(id) == null)
									continue;
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
							jz.put("Orderid Status","out of stock");
							ja.add(jz);
							
						}
					}
					JSONObject jo = new JSONObject();
					jo.put("OrderID", generatedOrderID);
					jo.put("values", ja);
					pw.println(jo);
					pw.flush();
				}else if(n==m) {
					String sql8 = "select ingredientid,quantity from magnifood.ingredients_items where itemid = ?";
					String sql9 = "select ingredientquantity,id from magnifood.ingredients where ingredientid = ?";
					PreparedStatement ps8 = conn.prepareStatement(sql8);
					PreparedStatement ps9 = conn.prepareStatement(sql9);
					ArrayList<String>al = new ArrayList<String>();
					ArrayList<String>quantityPerItemList = new ArrayList<String>();
					for(int i=0;i<itemIds.length;i++) {
						String itemId = itemIds[i];
						String quantityNeeded = quantity[i];
						ps.setString(3, itemIds[i]);
						ps.setString(4, quantity[i]);
						ps8.setString(1, itemId);
						ResultSet rs8 = ps8.executeQuery();
						boolean cannotMake = false;
						while(rs8.next()) {
							String ingredientId = rs8.getString(1);
							String quantityPerItem = rs8.getString(2);
							ps9.setString(1, ingredientId);
							ResultSet rs9 = ps9.executeQuery();
							boolean flag = false;
							String id = null;
							String temp = null;
							while(rs9.next()) {
								int ingredientQuantityAvailable = Integer.parseInt(rs9.getString(1));
								int ingredientQuantityNeeded = Integer.parseInt(quantityPerItem) * Integer.parseInt(quantityNeeded);
								if(ingredientQuantityAvailable > ingredientQuantityNeeded) {
									flag = true;
									id = rs9.getString(2);
									temp = quantityPerItem;
								}
							}
							if(!flag) {
								cannotMake = true;
								break;
							}
							al.add(id);
							quantityPerItemList.add(temp);
						}
						if(cannotMake) {
							PreparedStatement ps5 = conn.prepareStatement(sql5);
							ps5.setString(1, "out of stock");
							ps5.setString(2, itemIds[i]);
							ps5.executeUpdate();
							JSONObject jo = new JSONObject();
							jo.put("OrderId", "-1");
							pw.print(jo);
							pw.flush();
							return;
						}
					}
					for(int i=0;i<itemIds.length;i++) {
						String id = al.get(i);
						String sql10 = "select ingredientquantity from ingredients where id = ?";
						PreparedStatement ps10 = conn.prepareStatement(sql10);
						ps10.setString(1, id);
						System.out.println(ps10.toString());
						ResultSet rs10 = ps10.executeQuery();
						String dbQuantity = null;
						while(rs10.next()) {
							dbQuantity = rs10.getString(1);
						}
						int newQuantity = Integer.parseInt(dbQuantity) - ( Integer.parseInt(quantity[i]) * Integer.parseInt(quantityPerItemList.get(i)) );
						PreparedStatement ps11 = conn.prepareStatement(sql6);
						ps11.setString(1, Integer.toString(newQuantity));
						ps11.setString(2, id);
						ps11.executeUpdate();
						ps.setString(5, dbCafeId);
						ps.setString(6, "ordered pending");
						ps.executeUpdate();
						try {
							processOrder(conn,generatedOrderID,itemIds[i]);
						} catch (Exception e) {
							e.printStackTrace();
						}
						JSONObject jo = new JSONObject();
						jo.put("orderId",generatedOrderID);
						ja.add(jo);
					}
					JSONObject jnew = new JSONObject();
					jnew.put("values",ja);
					System.out.println("done");
					pw.print(jnew);
					pw.flush();
				}
				else {
					JSONObject jo = new JSONObject();
					jo.put("OrderID", "-1");
					pw.println(jo);
					pw.flush();
				}
			}
			
		//}
	catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void processOrder(Connection conn,String generatedOrderID, String string) throws Exception{
		
		String sql = "select id from magnifood.order where itemid = ? and orderid = ?";
		PreparedStatement ps = conn.prepareStatement(sql);
		ps.setString(1, string);
		ps.setString(2, generatedOrderID);
		String rowId = null;
		ResultSet rs = ps.executeQuery();
		while(rs.next()) {
			rowId = rs.getString(1);
		}
		System.out.println(rowId);
		ProcessOrders process = new ProcessOrders(rowId);
		process.start();
		System.out.println("sent for update" + generatedOrderID);
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
