package javagame;


import java.awt.Desktop;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.sql.SQLException;

import javax.swing.*;

public class Login extends JFrame implements ActionListener{//Jan-Niklas

	public JButton quit;
	public JButton start;
	public JButton reg;
	public JButton forget;
	public JButton change;	
	public JButton qr_code;

	ImageIcon im;

	String check_name;
	String check_password;

	public JTextField name;
	private JPasswordField password;

	public JLabel error;
	public JLabel info;
	public JLabel info2;
	public JLabel snowGames;
	
	public Login() {
		super("TowerDefense Launcher");
		this.setLayout(null);
		this.setBounds(650, 250, 500, 550);
		this.setResizable(false);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);

		quit = new JButton("quit");
		quit.setBounds(270, 300, 200, 50);
		quit.addActionListener(this);

		start = new JButton("Login");
		start.setBounds(70, 450, 400, 50);
		start.addActionListener(this);

		error = new JLabel("");
		error.setBounds(90, 100, 310, 50);
		error.setVisible(false);
		
		info = new JLabel("Login with your AccountName and your password.");
		info.setBounds(100, 30, 310, 20);
		info.setVisible(true);
		
		info2 = new JLabel("If you dont have an account, follow the instructions under 'registration'.");
		info2.setBounds(60, 50, 400, 20);
		info2.setVisible(true);
		
		snowGames = new JLabel("Snow-Games-Production");
		snowGames.setBounds(263, 80, 150, 125);
		snowGames.setVisible(true);
		
		change = new JButton("change Password");
		change.setBounds(70, 300, 200, 50);
		change.addActionListener(this);
		
		name = new JTextField("Name");
		name.setBounds(90, 180, 150, 20);
		name.addMouseListener(new MouseListener() {
			
			public void mouseReleased(MouseEvent e) {
				
			}
			
			public void mousePressed(MouseEvent e) {
				
			}
			
			public void mouseExited(MouseEvent e) {
				
			}
			
			public void mouseEntered(MouseEvent e) {
				
			}
			
			public void mouseClicked(MouseEvent e) {
				name.setText("");
			}
		{
			
		}});

		password = new JPasswordField("password");
		password.setBounds(90, 220, 150, 20);
		password.addMouseListener(new MouseListener() {
			
			@Override
			public void mouseReleased(MouseEvent e) {
				
			}
			
			@Override
			public void mousePressed(MouseEvent e) {
				
			}
			
			@Override
			public void mouseExited(MouseEvent e) {
				
			}
			
			@Override
			public void mouseEntered(MouseEvent e) {
				
			}
			
			@Override
			public void mouseClicked(MouseEvent e) {
				password.setText("");
			}
		{
			
		}});
		
		reg = new JButton("Registration");
		reg.setBounds(70,375,200,50);
		reg.addActionListener(this);
		
		forget = new JButton("Forgot your password");
		forget.setBounds(270, 375, 200, 50);
		forget.addActionListener(this);
		
		im = new ImageIcon("res/QR_neu.png");

		qr_code = new JButton();
		qr_code.setBounds(270, 150, 125, 125);
		qr_code.setIcon(im);
		qr_code.addActionListener(this);

		this.add(info);
		this.add(info2);
		this.add(snowGames);
		this.add(name);
		this.add(start);
		this.add(quit);
		this.add(password);
		this.add(qr_code);
		this.add(reg);
		this.add(forget);
		this.add(change);
		this.add(error);
		this.setVisible(true);
	}

	public static void main(String[] args) {
		Login log = new Login();

	}

	
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == start) {
//			JDBC jdbc = new JDBC();
//			try {
//				jdbc.verbindungsaufbau();
//				jdbc.login(name.getText(), password.getText());
//				if (jdbc.get_login() == true) {
//					this.setVisible(false);
//					 Game gam = new Game(Game.gameName);
//					 gam.init();
//				}else {
//					error.setText("Your login isn't correct! Please try again");
//					error.setVisible(true);
//				}
//			} catch (SQLException e1) {
//				e1.printStackTrace();
//			}
			Game gam = new Game(Game.gameName);
			gam.init();
		}

		if (e.getSource() == quit) {
			System.exit(1);
		}
		if (e.getSource() == name) {
			name.setText("");
		}
		if (e.getSource() == password) {
			password.setText("");
		}
		if (e.getSource() == qr_code) {
			String url = "http://snowgamesproduction.lima-city.de";//ab hier kopiert von http://binfalse.de/2011/01/adding-a-hyperlink-to-java-swing-gui/
			String osName = System.getProperty("os.name");
			try
			{
			    if (osName.startsWith ("Windows"))
			    {
			        Runtime.getRuntime ().exec ("rundll32 url.dll,FileProtocolHandler " + url);
			    }
			    else if (osName.startsWith ("Mac OS"))
			    {
			        Class fileMgr = Class.forName ("com.apple.eio.FileManager");
			        java.lang.reflect.Method openURL = fileMgr.getDeclaredMethod ("openURL", new Class[] {String.class});
			        openURL.invoke (null, new Object[] {url});
			    }
			    else
			    {
			        //check for $BROWSER
			        java.util.Map<String, String> env = System.getenv ();
			        if (env.get ("BROWSER") != null)
			        {
			            Runtime.getRuntime ().exec (env.get ("BROWSER") + " " + url);
			            return;
			        }
			       
			        //check for common browsers
			        String[] browsers = { "firefox", "iceweasel", "chrome", "opera", "konqueror", "epiphany", "mozilla", "netscape" };
			        String browser = null;
			        for (int count = 0; count < browsers.length && browser == null; count++)
			            if (Runtime.getRuntime ().exec (new String[] {"which", browsers[count]}).waitFor () == 0)
			            {
			                browser = browsers[count];
			                break;
			            }
			        if (browser == null)
			            throw new RuntimeException ("couldn't find any browser...");
			        else
			            Runtime.getRuntime ().exec (new String[] {browser, url});
			    }
			}
			catch (Exception a)
			{
			    javax.swing.JOptionPane.showMessageDialog (null, "couldn't find a webbrowser to use...\nPlease browser for yourself:\n" + url);
			}//bis hier
	}
		if(e.getSource() == reg){
			this.setVisible(false);
			Registration reg = new Registration();
		
		}
		if(e.getSource()==change){
			this.setVisible(false);
			Change_pw ch_pw = new Change_pw();
		}
		if(e.getSource()== forget){
			this.setVisible(false);
			Forget_pw fog = new Forget_pw();
		}
	}

}
