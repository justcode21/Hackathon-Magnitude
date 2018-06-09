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
 * Servlet implementation class EditItems
 */
@WebServlet("/EditItems")
public class EditItems extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public EditItems() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		PrintWriter out = response.getWriter();
		String cafeUserId = request.getParameter("loginId");
		String oldItemName = null;
		String newItemName = null;
		String newItemCost = null;
		
		oldItemName = request.getParameter("oldItemName");
		newItemName = request.getParameter("newItemName");
		newItemCost = request.getParameter("newItemCost");
		
		String sql = "UPDATE magnifood.item SET itemName=?,itemCost=? WHERE itemId=?";
		String sql2 = "select itemid from magnifood.item where itemname = ?";
		
		try {
			Class.forName("com.mysql.jdbc.Driver");
			Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/magnifood", "root", "yash1234");
			PreparedStatement ps2 = conn.prepareStatement(sql2);
			ps2.setString(1, oldItemName);
			ResultSet rs2 = ps2.executeQuery();
			String dbItemId = null;
			while(rs2.next()) {
				dbItemId = rs2.getString(1);
			}
			
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setString(1, newItemName);
			ps.setString(2, newItemCost);
			ps.setString(3, dbItemId);
			
			System.out.println(ps.toString());
			
			ps.executeUpdate();
			JSONObject jo = new JSONObject();
			jo.put("value", "done");
			out.println(jo);
			out.flush();
		}catch(Exception e) {
			e.printStackTrace();
		}
		
		
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
