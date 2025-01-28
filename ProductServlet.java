package com.devanshu;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 * Servlet implementation class ProductServlet
 */
@WebServlet("/ProductServlet")
public class ProductServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ProductServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#service(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		String Name=request.getParameter("name");
		String Price=request.getParameter("price");
		String Category=request.getParameter("category");
		String choice=request.getParameter("action");
		PrintWriter out=response.getWriter();
		response.setContentType("text/html");
		
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			try(Connection con=DriverManager.getConnection("jdbc:mysql://localhost:3306/product","root","admin")){
				PreparedStatement ps;
				switch(choice) {
				
				case "Add":
					
					if(!Name.isEmpty() && Name!=null && !Price.isEmpty() && Price!=null && Category!=null && !Category.isEmpty()) {
						ps=con.prepareStatement("insert into prodinfo(name,price,category) values (?,?,?)");
						ps.setString(1, Name);
						ps.setInt(2, Integer.parseInt(Price));
						ps.setString(3, Category);
						ps.execute();
						out.print("Successfully Added");
					}
					else {
						out.print("Please add all fields");
					}
					
				break;
				
				case "View":
					ps=con.prepareStatement("select * from prodinfo");
					ResultSet rs=ps.executeQuery();
					out.println("<table border=1><tr><th>Id</th><th>Name</th><th>Price</th><th>Category</th></tr>");
					while(rs.next()) {
						out.println("<tr>");
						out.println("<td>" + rs.getInt("id") + "</td>");
                        out.println("<td>" + rs.getString("name") + "</td>");
                        out.println("<td>" + rs.getInt("price") + "</td>");
                        out.println("<td>" + rs.getString("category") + "</td>");
                        out.println("</tr>");
					}
					out.println("</table>");
                    rs.close();
                 break;
                 
                 
				case "Delete":
					if(Name!=null && !Name.isEmpty()) {
						ps=con.prepareStatement("Delete from prodinfo where name=?");
						ps.setString(1, Name);
						ps.execute();
						out.print("Deleted Successfully");
					}
					else {
						out.print("Name is Required");
					}
				break;
				
				case "Update":
					if(!Name.isEmpty() && Name!=null && !Price.isEmpty() && Price!=null && Category!=null && !Category.isEmpty()) {
						ps=con.prepareStatement("update prodinfo set price=?,category=? where name=?");
						ps.setInt(1, Integer.parseInt(Price));
						ps.setString(2, Category);
						ps.setString(3, Name);
						ps.executeUpdate();
						out.print("Updated Successfully");
					}
					else {
						out.print("All fields are necessary");
					}
				break;
				default:
					break;
				}
				
			}
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
