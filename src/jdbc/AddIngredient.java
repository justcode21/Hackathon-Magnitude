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
 * Servlet implementation class AddIngredient
 */
@WebServlet("/AddIngredient")
public class AddIngredient extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AddIngredient() {
        super();
        // TODO Auto-generated constructor stub
    }

	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//System.out.println("here1");
		PrintWriter out = response.getWriter();
		String ingredientName = request.getParameter("ingredientName");
		String ingredientQuantity = request.getParameter("ingredientQuantity");
		
		
		String sql = "UPDATE magnifood.ingredients SET IngredientQuantity=? WHERE Id=?";
		String sql2 = "select id from magnifood.ingredients where ingredientname = ?";
		String sql3 = "select itemid from magnifood.ingredients_items where ingredientid = ?";
		String sql4 = "select ingredientid,quantity from magnifood.ingredients_items where itemid = ?";
		String sql5 = "select ingredientquantity from magnifood.ingredients where id = ?";
		String sql6 = "update  magnifood.item set OutOfStock = ? where itemid = ?";
		try {
			Class.forName("com.mysql.jdbc.Driver");
			Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/magnifood", "root", "yash1234");
			PreparedStatement ps2 = conn.prepareStatement(sql2);
			ps2.setString(1, ingredientName);
			String dbIngredientId = null;
			ResultSet rs2 = ps2.executeQuery();
			while(rs2.next()) {
				dbIngredientId = rs2.getString(1);
			}
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setString(1, ingredientQuantity);
			ps.setString(2, dbIngredientId);
			ps.executeUpdate();
			
			PreparedStatement ps3 = conn.prepareStatement(sql3);
			ps3.setString(1, dbIngredientId);
			ResultSet rs3 = ps3.executeQuery();
			//ArrayList<String> itemIds = new ArrayList<String>();
			while(rs3.next()) {
				PreparedStatement ps4 = conn.prepareStatement(sql4);
				//get item id
				String itemId = rs3.getString(1);
				ps4.setString(1, itemId);
				ResultSet rs4 = ps4.executeQuery();
				boolean flag = false;
				while(rs4.next()) {
					String key = rs4.getString(1);
					String needed = rs4.getString(2);
					PreparedStatement ps5 = conn.prepareStatement(sql5);
					ps5.setString(1, key);
					String required = null;
					ResultSet rs5 = ps5.executeQuery();
					while(rs5.next()) {
						required = rs5.getString(1);
					}
					
					if(Integer.parseInt(needed) > Integer.parseInt(required)) {
						flag = true;
					}
					
				}
				if(!flag) {
					PreparedStatement ps6 = conn.prepareStatement(sql6);
					ps6.setString(1, "available");
					ps6.setString(2, itemId);
					ps6.executeUpdate();
				}
				
				
			}
			
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
