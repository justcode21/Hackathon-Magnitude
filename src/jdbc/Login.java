package jdbc;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.websocket.Session;

import org.json.simple.JSONObject;

import jdk.nashorn.api.scripting.JSObject;

/**
 * Servlet implementation class Login
 */
@WebServlet("/Login")
public class Login extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Login() {
        super();
        // TODO Auto-generated constructor stub
    }
    
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			JSONObject jo = new JSONObject();
			String loginId = request.getParameter("loginId");
			String password = request.getParameter("password");
			String sql = "select * from magnifood.user where loginId = ? and password = ?";
			Class.forName("com.mysql.jdbc.Driver");
			Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/magnifood", "root", "yash1234");
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setString(1, loginId);
			ps.setString(2, password);
			ResultSet rs = ps.executeQuery();
			String dbUserName = null;
			String dbLoginId = null;
			String dbPassword = null;
			while(rs.next()) {
				 dbUserName = rs.getString("userName");
				 dbLoginId = rs.getString("loginId");
				 dbPassword = rs.getString("password");
			}
			if(loginId.equals(dbLoginId) && password.equals(dbPassword)) {
				jo.put("loggedInStatus", "true");
			}else {
				jo.put("loggedInStatus", "false");
			}
			PrintWriter out = response.getWriter();
			out.print(jo);
			out.flush();
		} catch (ClassNotFoundException | SQLException e) {
			
			e.printStackTrace();
		}
	}

}
