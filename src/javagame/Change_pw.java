package javagame;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

public class Change_pw extends JFrame implements ActionListener, Runnable {//Jan-Niklas

	JPasswordField old_password;
	JPasswordField new_password;
	JPasswordField new_password1;
	JTextField mail;

	JLabel lab1;
	JLabel lab2;
	JLabel lab3;

	JButton back;
	JButton confirm;
	
	Thread t1;

	public Change_pw() {
		super("TowerDefense Launcher");
		this.setLayout(null);
		this.setBounds(650, 250, 500, 500);
		this.setResizable(false);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);

		old_password = new JPasswordField();
		old_password.setBounds(175, 100, 150, 50);

		lab1 = new JLabel("Your old password");
		lab1.setBounds(175, 150, 150, 25);

		new_password = new JPasswordField();
		new_password.setBounds(175, 175, 150, 50);
		
		mail = new JTextField("Email");
		mail.setBounds(175,25,150,50);

		lab2 = new JLabel("Your new password");
		lab2.setBounds(175, 225, 150, 25);

		new_password1 = new JPasswordField();
		new_password1.setBounds(175, 250, 150, 50);

		lab3 = new JLabel("confirm your input");
		lab3.setBounds(175, 300, 150, 25);

		back = new JButton("back");
		back.setBounds(250, 350, 150, 50);
		back.addActionListener(this);

		confirm = new JButton("confirm");
		confirm.setBounds(100, 350, 150, 50);
		confirm.addActionListener(this);

		this.add(new_password);
		this.add(new_password1);
		this.add(old_password);
		this.add(lab1);
		this.add(lab2);
		this.add(lab3);
		this.add(confirm);
		this.add(back);
		this.add(mail);
		this.setVisible(true);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub

		if (e.getSource() == confirm) {
			if (new_password.getText().equals(new_password1.getText())) {
				run();
				this.setVisible(false);
				Login log = new Login();
//				log.setBounds(650, 250, 500, 500);
				JDBC jdbc = new JDBC();
				jdbc.verbindungsaufbau();
				jdbc.change_pw(old_password.getText(), new_password.getText(), mail.getText());
		
			}
		}
		if (e.getSource() == back) {
			this.setVisible(false);
			Login log = new Login();
//			log.setBounds(650, 250, 500, 500);
		}

	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		try {
			t1.sleep(5000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
