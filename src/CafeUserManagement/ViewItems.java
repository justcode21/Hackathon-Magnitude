package CafeUserManagement;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

/**
 * Servlet implementation class ViewItems
 */
@WebServlet("/ViewItems")
public class ViewItems extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ViewItems() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String sql = "select cafeId from magnifood.cafe where cafeuserid = ?";
		String sql2 = "select itemid from magnifood.menu where cafeid = ?";
		String sql3 = "select * from magnifood.item where itemid = ?";
		
		String cafeUserId = request.getParameter("loginId");
		PrintWriter out = response.getWriter();
		JSONObject jo = new JSONObject();
		try {
			Class.forName("com.mysql.jdbc.Driver");
			Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/magnifood", "root", "yash1234");
			PreparedStatement ps = conn.prepareStatement(sql);
			PreparedStatement ps2 = conn.prepareStatement(sql2);
			PreparedStatement ps3 = conn.prepareStatement(sql3);
			ps.setString(1, cafeUserId);
			ResultSet rs = ps.executeQuery();
			JSONArray ja = new JSONArray();
			int count = 0;
			while(rs.next()) {
				String cafeId = rs.getString(1);
				ps2.setString(1, cafeId);
				ResultSet rs2 = ps2.executeQuery();
				while(rs2.next()) {
					String itemId = rs2.getString(1);
					ps3.setString(1, itemId);
					ResultSet rs3 = ps3.executeQuery();
					
					while(rs3.next()) {
						JSONObject jnew = new JSONObject();
						jnew.put("ItemId", rs3.getObject(1));
						jnew.put("ItemName", rs3.getObject(2));
						jnew.put("ItemCost", rs3.getObject(3));
						ja.add(jnew);
						count++;
					}
				}
			}
			jo.put("count", count);
			jo.put("values", ja);
			out.println(jo);
			out.flush();
		}catch(Exception e) {
			
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
