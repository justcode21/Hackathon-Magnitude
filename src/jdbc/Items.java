package jdbc;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;


/**
 * Servlet implementation class Items
 */
@WebServlet("/Items")
public class Items extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Items() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String loginId = request.getParameter("loginId");
		String sqlValid = "select * from magnifood.user where loginId = ?";
		
		String sql = "select * from magnifood.item";
		PrintWriter pw = response.getWriter();
		try {
			Class.forName("com.mysql.jdbc.Driver");
			Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/magnifood", "root", "yash1234");
			
			PreparedStatement pst = conn.prepareStatement(sqlValid);
			pst.setString(1, loginId);
			JSONObject jo = new JSONObject();
			ResultSet rst = pst.executeQuery();
			String password = null;
			while(rst.next()) {
				password = rst.getString(5);
			}
			
			if(password == null) {
				jo.put("values", new JSONArray());
				pw.print(jo);
				pw.flush();
			}
			else {
				PreparedStatement ps = conn.prepareStatement(sql);
				ResultSet rs = ps.executeQuery();
				JSONArray ja = new JSONArray();
				while(rs.next()) {
					//pw.println(rs.getString(1) + " " + rs.getString(2) + " " + rs.getString(3));
					//pw.println("<br>");
					Map<String, String> m = new LinkedHashMap<>();
					m.put("ItemId", rs.getString(1));
					m.put("ItemName", rs.getString(2));
					m.put("ItemCost", rs.getString(3));
					ja.add(m);
				}
				jo.put("values", ja);
				pw.print(jo);
				pw.flush();
			}
			
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
