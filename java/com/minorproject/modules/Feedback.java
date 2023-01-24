package com.minorproject.modules;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
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

/**
 * Servlet implementation class Feedback
 */
@WebServlet("/feedback")
public class Feedback extends HttpServlet {
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String q1 = request.getParameter("q1");
		String q2 = request.getParameter("q2");
		String q3 = request.getParameter("q3");
		String suggestion = request.getParameter("suggestions");
		RequestDispatcher dispatcher = null;
		Connection con = null;
		
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			con = DriverManager.getConnection("jdbc:mysql://localhost:3306/newDB?allowPublicKeyRetrieval=true&useSSL = false", "root", "structuredquerylanguage");
			PreparedStatement pst = con.prepareStatement("insert into feedback(q1,q2,q3) values(?,?,?)");
			pst.setString(1, q1);
			pst.setString(2, q2);
			pst.setString(3, q3);
			int rowCount = pst.executeUpdate();
			
			if(suggestion != null) {
				PreparedStatement ps = con.prepareStatement("SELECT f_id FROM feedback ORDER BY f_id DESC LIMIT 1");
				ResultSet rs = ps.executeQuery();
				rs.next();
				
				File obj = new File("C:\\Users\\Prittesh Singh\\MinorProject\\Feedback\\"+rs.getString("f_id")+".txt");
				try {
		            FileWriter Writer
		                = new FileWriter("C:\\Users\\Prittesh Singh\\MinorProject\\Feedback\\"+rs.getString("f_id")+".txt");
		            Writer.write(suggestion);
		            Writer.close();
		            System.out.println("Successfully written.");
		        }
		        catch (IOException e) {
		            System.out.println("An error has occurred.");
		            e.printStackTrace();
		        }
			}
			
			dispatcher = request.getRequestDispatcher("index.jsp");
			if(rowCount>0) {
				  request.setAttribute("status", "feedback");
			} else {
				request.setAttribute("status", "fail");
			}
			dispatcher.forward(request, response);
		}catch (Exception e){
			e.printStackTrace();
		}finally {
			try {
				con.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}	
	}
}
