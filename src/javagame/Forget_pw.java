package javagame;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JTextField;


public class Forget_pw extends JFrame implements ActionListener, Runnable{//Jan-Niklas

	JButton confirm;
	
	Thread t = new Thread();
	
	JTextField email;
	
	public Forget_pw(){
		super("TowerDefense Launcher");
		this.setLayout(null);
		this.setBounds(650, 250, 500, 500);
		this.setResizable(false);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		
		email = new JTextField("Email");
		email.setBounds(175, 100, 150, 50);
		email.addMouseListener(new MouseListener() {
			
			@Override
			public void mouseReleased(MouseEvent arg0) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mousePressed(MouseEvent arg0) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mouseExited(MouseEvent arg0) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mouseEntered(MouseEvent arg0) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mouseClicked(MouseEvent e) {
				// TODO Auto-generated method stub
				email.setText("");
			}
		});
		
		confirm = new JButton("confirm");
		confirm.setBounds(175, 200, 150, 50);
		confirm.addActionListener(this);
		
		this.add(confirm);
		this.add(email);
		this.setVisible(true);
			
	
		
		
	}

	public void actionPerformed(ActionEvent e) {
	 if(e.getSource()== confirm){	
		 run();
		 JDBC jdbc = new JDBC();
		 jdbc.verbindungsaufbau();
		jdbc.password_forgot(email.getText());
		this.setVisible(false);
		Login log = new Login();
//		log.setBounds(650, 250, 500, 500);
	}}
	@Override
	public void run() {
		// TODO Auto-generated method stub
	 try {
		t.sleep(5000);
	} catch (InterruptedException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}	
	}
}
