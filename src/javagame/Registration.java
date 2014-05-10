package javagame;

import javax.swing.*;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.sql.SQLException;
import java.util.Random;
import java.util.UUID;


public class Registration extends JFrame implements ActionListener{//Jan-Niklas
	JButton confirm;
	JButton back;
	
	JTextField name;
	JTextField email;
	
	JLabel info;
	JLabel info2;
	JLabel info3;
	
	String password ="";
	
	UUID uuid;
	
	Random rand = new Random(36);
	JDBC jdbc = new JDBC();

	char [] passwordgene = {'a','b','c','d','e','f','g','h','i','j','k','l','m','n','o','p','q','r','s','t','u','v','w','x','y','z','1','2','3','4','5','6','7','9','8'};
	
	public Registration(){
		
	super("TowerDefense Launcher");
	
	back = new JButton("back");
	back.setBounds(200, 300, 150, 50);
	back.addActionListener(this);

	confirm = new JButton("confirm");
	confirm.setBounds(40, 300, 150, 50);
	confirm.addActionListener(this);
	
	info = new JLabel("Choose a name and a valid E-Mail for your account.");
	info.setBounds(40, 30, 310, 20);
	
	info2 = new JLabel("You will get a random password to your E-Mail adress.");
	info2.setBounds(40, 50, 310, 20);
	
	info3 = new JLabel("Your password can easily changed later on");
	info3.setBounds(40, 70, 310, 20);

	name = new JTextField("name");
	name.setBounds(40, 180, 150, 20);
	name.addMouseListener(new MouseListener() {
		
		@Override
		public void mouseReleased(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}
		
		@Override
		public void mousePressed(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}
		
		@Override
		public void mouseExited(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}
		
		@Override
		public void mouseEntered(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}
		
		@Override
		public void mouseClicked(MouseEvent e) {
			// TODO Auto-generated method stub
			name.setText("");
		}
	{
		
	}});

	email = new JTextField("email");
	email.setBounds(40, 220, 150, 20);
	email.addMouseListener(new MouseListener() {
		
		@Override
		public void mouseReleased(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}
		
		@Override
		public void mousePressed(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}
		
		@Override
		public void mouseExited(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}
		
		@Override
		public void mouseEntered(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}
		
		@Override
		public void mouseClicked(MouseEvent e) {
			// TODO Auto-generated method stub
			email.setText("");
		}
	{
		
	}});
	
	this.add(info);
	this.add(info2);
	this.add(info3);
	this.add(back);
	this.add(confirm);
	this.add(email);
	this.add(name);
	this.setLayout(null);
	this.setBounds(650, 250, 400, 425);
	this.setResizable(false);
	this.setDefaultCloseOperation(EXIT_ON_CLOSE);
	this.setVisible(true);
		
	}
	
	public void get_password(){
		 uuid = UUID.randomUUID();
	     
		}


	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		
 if(e.getSource()==confirm){
	 try {
		 password ="";
		 jdbc.verbindungsaufbau();
		 get_password();
		 jdbc.insert_reg(name.getText(), email.getText(), uuid.toString());
		
	} catch (SQLException e1) {
		e1.printStackTrace();
	}
} if(e.getSource() == back){
	this.setVisible(false);
	Login log = new Login();
	//log.setBounds(650, 250, 500, 500);
}
	}
}