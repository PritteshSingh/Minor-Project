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
 * Servlet implementation class Cancellation
 */
@WebServlet("/cancellation")
public class Cancellation extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String cID = request.getParameter("ID");

		HttpSession session = request.getSession();
		RequestDispatcher dispatcher = null;
		
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/newDB?allowPublicKeyRetrieval=true&useSSL = false", "root", "structuredquerylanguage");
			
			/*PreparedStatement ps = con.prepareStatement("Select * from contact WHERE (c_id = ?);");
			ps.setString(1, cID);
			ResultSet rs = ps.executeQuery();
			rs.next();
			
			if(rs.getString("sstatus") == "serviced") {
				request.setAttribute("status", "rejected");
				dispatcher = request.getRequestDispatcher("index.jsp");
			}*/
			
			/*
			 * PreparedStatement pst = con.
			 * prepareStatement("UPDATE contact SET sstatus = 'cancelled' WHERE (c_id = ?);"
			 * );
			 */
			PreparedStatement pst = con.prepareStatement("select c_email from contact WHERE (c_id = ?);");
			pst.setString(1, cID);
			
			ResultSet rs = pst.executeQuery();
			if(rs.next()) {
				String email = rs.getString("c_email");
				int otpvalue = 0;
				
				if(email!=null || !email.equals("")) {
					// sending otp
					Random rand = new Random();
					otpvalue = rand.nextInt(1255650);

					String to = email;// change accordingly
					// Get the session object
					Properties props = new Properties();
					props.put("mail.smtp.host", "smtp.gmail.com");
					props.put("mail.smtp.socketFactory.port", "465");
					props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
					props.put("mail.smtp.auth", "true");
					props.put("mail.smtp.port", "465");
					Session session0 = Session.getDefaultInstance(props, new javax.mail.Authenticator() {
						protected PasswordAuthentication getPasswordAuthentication() {
							return new PasswordAuthentication("projectminor011@gmail.com", "zksgtjzszrhtqswl");// Put your email
																											// id and
																											// password here
						}
					});
					// compose message
					try {
						MimeMessage message = new MimeMessage(session0);
						message.setFrom(new InternetAddress(email));// change accordingly
						message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));
						message.setSubject("Hello");
						message.setText("your cancellation OTP is: " + otpvalue);
						// send message
						Transport.send(message);
						System.out.println("message sent successfully");
					}

					catch (MessagingException e) {
						throw new RuntimeException(e);
					}
					dispatcher = request.getRequestDispatcher("CancelOTP.jsp");
					request.setAttribute("message","OTP is sent to your email id");
					//request.setAttribute("connection", con);
					session.setAttribute("otp",otpvalue); 
					session.setAttribute("id",cID); 
					//request.setAttribute("status", "success");
				}
			}else {
				request.setAttribute("status", "error");
				dispatcher = request.getRequestDispatcher("index.jsp");
			}
			dispatcher.forward(request, response);
		}catch (Exception e) {
			e.printStackTrace();
		}
	}

}
