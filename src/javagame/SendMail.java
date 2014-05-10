package javagame;

import java.util.Properties;
import javax.mail.Address;
import javax.mail.Message;
import javax.mail.NoSuchProviderException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class SendMail {//Jan-Niklas

	String host="mail.gmx.net";
	int port=25;
	String user="snowgamesproduction@gmx.de";
	String pass="loster719";
	
	public void send(String email,String name,String password) throws Exception{
	    Properties props=new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        
        Session session=Session.getInstance(props);
        Transport transport=session.getTransport("smtp");
        transport.connect(host, port, user, pass);
        
        Address[] addresses=InternetAddress.parse(email);
        
        Message message=new MimeMessage(session);
        message.setFrom(new InternetAddress(user));
        message.setRecipients(Message.RecipientType.TO, addresses);
        message.setSubject("Registration");
        
        message.setText("Yout have registrated successfully at SnowGamesProduction. Your Username is:"+ name+" and your password is:"+password);
        
        transport.sendMessage(message, addresses);
        System.out.println("SEND");
        
        transport.close();
	}
	
	public void send_pw(String email, String password) throws Exception{
		   Properties props=new Properties();
	        props.put("mail.smtp.auth", "true");
	        props.put("mail.smtp.starttls.enable", "true");
	        
	        Session session=Session.getInstance(props);
	        Transport transport=session.getTransport("smtp");
	        transport.connect(host, port, user, pass);
	        
	        Address[] addresses=InternetAddress.parse(email);
	        
	        Message message=new MimeMessage(session);
	        message.setFrom(new InternetAddress(user));
	        message.setRecipients(Message.RecipientType.TO, addresses);
	        message.setSubject("Password");
	        
	        message.setText("Your password is "+ password);
	        
	        transport.sendMessage(message, addresses);
	        System.out.println("SEND");
	        
	        transport.close();
	}
	}

