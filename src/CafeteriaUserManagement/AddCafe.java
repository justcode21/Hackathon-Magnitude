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
 * Servlet implementation class AddCafe
 */
@WebServlet("/AddCafe")
public class AddCafe extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AddCafe() {
        super();
    }

	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		PrintWriter out = response.getWriter();
		String cafeteriaUser = request.getParameter("loginId");
		String cafeName = request.getParameter("cafeName");
		String cafeUserName = request.getParameter("cafeUserName");
		
		String sql = "select loginId from magnifood.user where username = ?";
		String sql2 = "insert into magnifood.cafe(cafename,cafeuserid,cafeteriauser) values(?,?,?)";
		
		JSONObject jo = new JSONObject();
		try {
			Class.forName("com.mysql.jdbc.Driver");
			Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/magnifood", "root", "yash1234");
			PreparedStatement ps = conn.prepareStatement(sql);
			String dbLoginId = null;
			ps.setString(1, cafeUserName);
			ResultSet rs = ps.executeQuery();
			while(rs.next()) {
				dbLoginId = rs.getString(1);
			}
			
			if(dbLoginId != null) {
				PreparedStatement ps2 = conn.prepareStatement(sql2);
				ps2.setString(1, cafeName);
				ps2.setString(2, dbLoginId);
				ps2.setString(3, cafeteriaUser);
				ps2.executeUpdate();
				
				jo.put("value","done");
				out.print(jo);
				out.flush();
			}
			else {
				jo.put("value", "user doesnot exist in database");
				out.println(jo);
			}
		}catch(Exception e) {
			e.printStackTrace();
		}
	}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
