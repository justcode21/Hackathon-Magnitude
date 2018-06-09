package CafeteriaUserManagement;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.HashSet;
import java.util.Set;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

/**
 * Servlet implementation class ViewOrders
 */
@WebServlet("/ViewOrders")
public class ViewOrders extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ViewOrders() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//System.out.println("working");
		String cafeUserId = request.getParameter("loginId");
		String uparcafeId = null;
		uparcafeId = request.getParameter("cafeId");
		PrintWriter out = response.getWriter();
		String sql = "select cafeid from magnifood.cafe where cafeteriauser = ?";
		String sql2 = "select * from magnifood.order where cafeId = ?";
		//System.out.println(cafeId);
		JSONObject jo = new JSONObject();
		//System.out.println("boo");
		try {
			//System.out.println("boo2");
			Set<String> hash_set = new HashSet<String>();
			Class.forName("com.mysql.jdbc.Driver");
			Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/magnifood", "root", "yash1234");
			//System.out.println("oka");
			PreparedStatement ps = conn.prepareStatement(sql);
			System.out.println(ps.toString());
			System.out.println(cafeUserId);
			ps.setString(1, cafeUserId);
			System.out.println("hey" + ps.toString());
			ResultSet rs = ps.executeQuery();
			//System.out.println("plpl");
			JSONArray ja = new JSONArray();
			while(rs.next()) {
				String cafeIdHere = rs.getString(1);
				//System.out.println(cafeIdHere);
				PreparedStatement ps2 = conn.prepareStatement(sql2);
				ps2.setString(1, cafeIdHere);
				System.out.println(ps2.toString());
				ResultSet rs2 = ps2.executeQuery();
				while(rs2.next()){
					String orderID = rs2.getString(2);
					String userId = rs2.getString(3);
					String cafeID = rs2.getString(6);
					String status = rs2.getString(7);
					int prev = hash_set.size();
					hash_set.add(orderID);
					int newSize = hash_set.size();
					JSONObject jnew = new JSONObject();
					System.out.println(prev + " " +  newSize);
					if(prev != newSize && check(uparcafeId,cafeID)) {
						System.out.print("working");
						jnew.put("OrderId", orderID);
						jnew.put("UserId", userId);
						jnew.put("CafeId", cafeID);
						jnew.put("Status", status);
						ja.add(jnew);
					}
				}
			}
			jo.put("value" , ja);
			out.println(jo);
			out.flush();
		}catch(Exception e) {
			
		}
	}

	private boolean check(String uparcafeId, String cafeID) {
		if(uparcafeId == null)
			return true;
		if(uparcafeId.equals(cafeID))
			return true;
		return false;
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
