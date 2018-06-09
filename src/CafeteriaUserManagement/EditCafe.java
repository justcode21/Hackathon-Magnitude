package CafeteriaUserManagement;

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
 * Servlet implementation class EditCafe
 */
@WebServlet("/EditCafe")
public class EditCafe extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public EditCafe() {
        super();
        // TODO Auto-generated constructor stub
    }

	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String cafeteriaUserId = request.getParameter("loginId");
		String oldCafeName = request.getParameter("oldCafeName");
		String newCafeName = request.getParameter("newCafeName");
		PrintWriter out = response.getWriter();
		JSONObject jo = new JSONObject();
		String sql = "UPDATE magnifood.cafe SET CafeName=? WHERE cafeid=?";
		String sql2 = "select cafeid from cafe where cafename = ? and cafeteriauser = ?";
		try {
			Class.forName("com.mysql.jdbc.Driver");
			Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/magnifood", "root", "yash1234");
			PreparedStatement ps2 = conn.prepareStatement(sql2);
			ps2.setString(1, oldCafeName);
			ps2.setString(2, cafeteriaUserId);
			ResultSet rs = ps2.executeQuery();
			String dbCafeId = null;
			while(rs.next()) {
				dbCafeId = rs.getString(1);
			}
			
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setString(1, newCafeName);
			ps.setString(2, dbCafeId);
			
			System.out.println(ps.toString());
			ps.executeUpdate();
			
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
