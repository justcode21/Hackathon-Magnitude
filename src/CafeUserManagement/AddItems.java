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

import org.json.simple.JSONObject;

/**
 * Servlet implementation class AddItems
 */
@WebServlet("/AddItems")
public class AddItems extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AddItems() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String cafeUserId = request.getParameter("loginId");
		String itemName = request.getParameter("itemName");
		String itemCost = request.getParameter("itemCost");
		PrintWriter out = response.getWriter();
		String sql = "select cafeid from magnifood.cafe where cafeuserid = ?";
		String sql2 = "insert into magnifood.menu(cafeid,itemid) values(?,?)";
		String sql3 = "insert into magnifood.item(itemname,itemcost) values(?,?)";
		String sql4 = "select itemid from magnifood.item where itemname = ?";
		try {
			Class.forName("com.mysql.jdbc.Driver");
			Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/magnifood", "root", "yash1234");
			PreparedStatement ps3 = conn.prepareStatement(sql3);
			ps3.setString(1, itemName);
			ps3.setString(2, itemCost);
			ps3.executeUpdate();
			
			PreparedStatement ps4 = conn.prepareStatement(sql4);
			ps4.setString(1, itemName);
			String dbItemId = null;
			ResultSet rs = ps4.executeQuery();
			while(rs.next()) {
				dbItemId = rs.getString(1);
			}
			
			PreparedStatement ps1 = conn.prepareStatement(sql);
			ps1.setString(1, cafeUserId);
			String dbCafeId = null;
			ResultSet rs2 = ps1.executeQuery();
			while(rs2.next()) {
				dbCafeId = rs2.getString(1);
			}
			
			PreparedStatement ps2 = conn.prepareStatement(sql2);
			ps2.setString(1, dbCafeId);
			ps2.setString(2, dbItemId);
			ps2.executeUpdate();
			
			JSONObject jo = new JSONObject();
			jo.put("value", "okay");
		}catch(Exception e) {
			e.printStackTrace();
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
