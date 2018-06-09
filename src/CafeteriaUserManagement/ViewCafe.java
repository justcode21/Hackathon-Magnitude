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

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

/**
 * Servlet implementation class ViewCafe
 */
@WebServlet("/ViewCafe")
public class ViewCafe extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ViewCafe() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		PrintWriter out = response.getWriter();
		JSONObject jo = new JSONObject();
		JSONArray ja = new JSONArray();
		String cafeteriaUser = request.getParameter("loginId");
		String sql = "SELECT cafeid FROM magnifood.cafe where cafeteriauser = ?";
		String sql2 = "select * from magnifood.cafe where cafeid = ?";
		
		try {
			Class.forName("com.mysql.jdbc.Driver");
			Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/magnifood", "root", "yash1234");
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setString(1, cafeteriaUser);
			ResultSet rs = ps.executeQuery();
			while(rs.next()) {
				String cafeId = rs.getString(1);
				PreparedStatement ps2 = conn.prepareStatement(sql2);
				ps2.setString(1, cafeId);
				ResultSet rs2 = ps2.executeQuery();
				while(rs2.next()) {
					JSONObject jnew = new JSONObject();
					jnew.put("cafeid", rs2.getInt(1));
					jnew.put("cafename", rs2.getString(2));
					ja.add(jnew);
				}
			}
			jo.put("value", ja);
			out.print(jo);
		}catch(Exception e) {
			
		}
	}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
