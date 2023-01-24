package com.minorproject.modules;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Properties;
import java.util.Random;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet implementation class CancellationOTP
 */
@WebServlet("/CancellationOTP")
public class CancellationOTP extends HttpServlet {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		int value=Integer.parseInt(request.getParameter("otp"));
		HttpSession session = request.getSession();
		int otp=(int)session.getAttribute("otp");
		String ID = session.getAttribute("id").toString();
		
		
		RequestDispatcher dispatcher=null;
		
		
		if (value==otp) 
		{
			try {
				Class.forName("com.mysql.cj.jdbc.Driver");
				Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/newDB?allowPublicKeyRetrieval=true&useSSL = false", "root", "structuredquerylanguage");
				
				
				PreparedStatement pst = con.prepareStatement("UPDATE contact SET sstatus = 'cancelled' WHERE (c_id = ?);");
				pst.setString(1, ID);
				
				int rowCount = pst.executeUpdate();
				
				if(rowCount> 0) {
					request.setAttribute("status", "cancelled");
					dispatcher = request.getRequestDispatcher("index.jsp");
				}else {
					request.setAttribute("status", "error");
					dispatcher = request.getRequestDispatcher("index.jsp");
				}
				dispatcher.forward(request, response);
			}catch (Exception e) {
				e.printStackTrace();
			}	
		}
		else
		{
			request.setAttribute("message","wrong otp");
			
		    dispatcher=request.getRequestDispatcher("EnterOtp.jsp");
			dispatcher.forward(request, response);
		
		}
		
	}

}
