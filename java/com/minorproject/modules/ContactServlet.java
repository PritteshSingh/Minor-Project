package com.minorproject.modules;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;

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
 * Servlet implementation class ContactServlet
 */
@WebServlet("/contact")
public class ContactServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
   
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String cname = request.getParameter("name");
		String cemail = request.getParameter("email");
		String caddress= request.getParameter("address");
		String cmobile = request.getParameter("mobile");
		String service = request.getParameter("service");
		RequestDispatcher dispatcher = null;
		Connection con = null;
		
		HttpSession mySession = request.getSession();
		
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			con = DriverManager.getConnection("jdbc:mysql://localhost:3306/newDB?allowPublicKeyRetrieval=true&useSSL = false", "root", "structuredquerylanguage");
			PreparedStatement pst = con.prepareStatement("insert into contact(c_name,c_address,c_email,c_mobile,srviceName) values(?,?,?,?,?)");
			pst.setString(1, cname);
			pst.setString(2, caddress);
			pst.setString(3, cemail);
			pst.setString(4, cmobile);
			pst.setString(5, service);
			
			int rowCount = pst.executeUpdate();
			
			if(cemail!=null || !cemail.equals("")) {

				String to = cemail;// change accordingly
				// Get the session object
				Properties props = new Properties();
				props.put("mail.smtp.host", "smtp.gmail.com");
				props.put("mail.smtp.socketFactory.port", "465");
				props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
				props.put("mail.smtp.auth", "true");
				props.put("mail.smtp.port", "465");
				Session session = Session.getDefaultInstance(props, new javax.mail.Authenticator() {
					protected PasswordAuthentication getPasswordAuthentication() {
						return new PasswordAuthentication("projectminor011@gmail.com", "app-password");
					}
				});
				
				PreparedStatement ps = con.prepareStatement("SELECT * FROM contact ORDER BY c_id DESC LIMIT 1");
				ResultSet rs = ps.executeQuery();
				rs.next();
				// compose message
				try {
					MimeMessage message = new MimeMessage(session);
					message.setFrom(new InternetAddress(cemail));// change accordingly
					message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));
					message.setSubject("Request for garbage collection");
					message.setText("Hello "+ cname +",\nThank you for choosing us, we are happy to help, our scrap collectors will contact you within 2-3 business days.\n"
							+ "Your service ID: " + rs.getInt("c_id") + "\nService Type: " + rs.getString("srviceName")
							+ "\n\nRegards,"
							+ "\nTeam JRC");
					// send message
					Transport.send(message);
					System.out.println("message sent successfully");
				}

				catch (MessagingException e) {
					throw new RuntimeException(e);
				}
			}
			
			dispatcher = request.getRequestDispatcher("index.jsp");
			if(rowCount>0) {
				  request.setAttribute("status", "success");
			} else {
				request.setAttribute("status", "failed");
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
