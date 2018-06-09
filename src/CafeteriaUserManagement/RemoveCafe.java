package CafeteriaUserManagement;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.simple.JSONObject;

/**
 * Servlet implementation class RemoveCafe
 */
@WebServlet("/RemoveCafe")
public class RemoveCafe extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public RemoveCafe() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		PrintWriter out = response.getWriter();
		String cafeUserId = request.getParameter("loginId");
		String cafeName = request.getParameter("cafeName");
		
		String sql = "select cafeid from magnifood.cafe where cafeteriauser=? and cafename=?";
		String sql2 = "delete from magnifood.cafe WHERE cafeId=?";
		String sql4 = "delete from magnifood.menu WHERE cafeId=?";
		String sql3 = "select itemid from magnifood.menu where cafeid = ?";
		String sql5 = "delete from magnifood.item where itemid = ?";
		
		try {
			Class.forName("com.mysql.jdbc.Driver");
			Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/magnifood", "root", "yash1234");
			
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setString(1, cafeUserId);
			ps.setString(2, cafeName);
			System.out.println(ps.toString());
			String dbCafeId = null;
			ResultSet rs = ps.executeQuery();
			while(rs.next()) {
				dbCafeId = rs.getString(1);
			}
			System.out.println(dbCafeId);
			PreparedStatement ps2 = conn.prepareStatement(sql2);
			ps2.setString(1, dbCafeId);
			ps2.executeUpdate();
			
			ArrayList<String> al = new ArrayList<String>();
			PreparedStatement ps3 = conn.prepareStatement(sql3);
			ps3.setString(1, dbCafeId);
			ResultSet rs3 = ps3.executeQuery();
			while(rs3.next()) {
				al.add(rs3.getString(1));
			}
			
			PreparedStatement ps4 = conn.prepareStatement(sql4);
			ps4.setString(1, dbCafeId);
			ps4.executeUpdate();
			
			PreparedStatement ps5 = conn.prepareStatement(sql5);
			for(String val : al) {
				ps5.setString(1, val);
				ps5.executeUpdate();
			}
			
			JSONObject jo = new JSONObject();
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
