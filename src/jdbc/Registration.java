package jdbc;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.simple.JSONObject;



/**
 * Servlet implementation class Registration
 */
@WebServlet("/Registration")
public class Registration extends HttpServlet {
	private static final long serialVersionUID = 1L;
    
    public Registration() {
        super();
        // TODO Auto-generated constructor stub
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request,response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println(request.getMethod());
		PrintWriter out = response.getWriter();
		JSONObject jo = new JSONObject();
		try {
			String userName = request.getParameter("userName");
			String loginId = request.getParameter("loginId");
			String password = request.getParameter("password");
			String sql = "insert into magnifood.user(userName,loginId,password) values(?,?,?);";
			String sqlValid = "select userId from user where loginId = ?";
			Class.forName("com.mysql.jdbc.Driver");
			Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/magnifood", "root", "yash1234");
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setString(1, userName);
			ps.setString(2, loginId);
			ps.setString(3, password);
			System.out.println(userName);
			System.out.println(loginId);
			System.out.println(password);
			ps.executeUpdate();
			
			
			//validation
			PreparedStatement ps2 = conn.prepareStatement(sqlValid);
			ps2.setString(1, loginId);
			ResultSet rst = ps2.executeQuery();
			int userId = -1;
			while(rst.next()) {
				userId = rst.getInt(1);
			}
			jo.put("registeredStatus", userId);
			out.println(jo);
		} catch (ClassNotFoundException | SQLException e) {
			jo.put("registeredStatus", -1);
			out.println(jo);
		}
	}

}
