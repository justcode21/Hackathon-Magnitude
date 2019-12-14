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
 * Servlet implementation class RemoveItems
 */
@WebServlet("/RemoveItems")
public class RemoveItems extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public RemoveItems() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		PrintWriter out = response.getWriter();
		String cafeUserId = request.getParameter("loginId");
		String itemName = null;
		itemName = request.getParameter("itemName");
		
		String sql = "delete from magnifood.item WHERE itemId=?";
		String sql2 = "delete from magnifood.menu where itemId = ?";
		String sql3 = "select itemid from magnifood.item where itemname = ?";
		
		try {
			Class.forName("com.mysql.jdbc.Driver");
			Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/magnifood", "root", "yash1234");
			
			PreparedStatement ps3 = conn.prepareStatement(sql3);
			ps3.setString(1, itemName);
			ResultSet rs3 = ps3.executeQuery();
			String dbItemId = null;
			while(rs3.next()) {
				dbItemId = rs3.getString(1);
			}
			
			PreparedStatement ps2 = conn.prepareStatement(sql2);
			ps2.setString(1, dbItemId);
			ps2.executeUpdate();
			
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setString(1, dbItemId);
			ps.executeUpdate();
			
			JSONObject jo = new JSONObject();
			if(dbItemId == null)
				jo.put("value", "false");
			else
				jo.put("value", "done");
			out.println(jo);
			out.flush();
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
