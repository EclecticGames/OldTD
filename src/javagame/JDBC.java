package javagame;

import java.sql.*;

import org.newdawn.slick.util.Log;

public class JDBC { //Stefan und JAn-Niklas

	Connection con;
	Statement s = null;
	ResultSet rs = null;

	Boolean login = false;

	int[] points = new int[9];

	public void verbindungsaufbau() {
		try {
			Class.forName("com.mysql.jdbc.Driver");// Treiber Laden
		} catch (Exception e) {
			System.out.println("MySQL Treiber konnte nicht geladen werden.");
		}

		con = null;
		try {
			con = DriverManager.getConnection(
					"jdbc:mysql://drsky.de/JanNiklas", "JanNiklas", "info");// Verbindungsaufbau
																			// mit
																			// der
																			// Datenbank
		} catch (SQLException e) {
			System.out.println("Fehler beim Verbindungsaufbau: "
					+ e.getMessage());
		}

	}

	public void auslesen(String name, int column_number) {
		try {
			Statement s = con.createStatement();// erzeugt neues Statement s
			rs = s.executeQuery("SELECT * from " + name + ";");// Ã¼berschreibt
																// das resultset
																// rs mit den
																// Ergebnis der
																// Query
			while (rs.next()) {
				for (int i = 1; i <= column_number; i++) {
						System.out.println("" + rs.getString(i));
					}
				}

		} catch (Exception e) {
			System.out.println("Fehler:" + e.toString());

		}
	}
	
	public void login(String name, String passwort) throws SQLException {
		Statement s = con.createStatement();
		ResultSet rst = s.executeQuery("Select * from Spieler where	ID ='"
				+ name + "';");
		while (rst.next()) {
			if (rst.getString(1).equals(name)
					&& rst.getString(3).equals(passwort)) {
				login = true;
				System.out.print("your login is correct");
			} else {
				login = false;
				System.out.println("Login not correct");
			}
		}
	}

	public boolean get_login() {
		return login;
	}

	public void insert_highscore(String Spieler_ID, int Punktzahl) {

		try {
			s = con.createStatement();
			s.executeUpdate("insert into hat_Punkte values('" + Spieler_ID + "',"
					+ Punktzahl + ")");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void readcolumn(String table, int column) {
		try {
			rs = s.executeQuery("Select * from " + table + ";");
		} catch (Exception e) {
			System.out.println(e.toString());
		}
	}

//	public void highscore_select() {
//
//		try {
//			ResultSet rst = null;
//			Statement s = con.createStatement();
//			 Statement st = con.createStatement();
//			 Statement stm = con.createStatement();
//			  String sql = "DELETE FROM Punkte";
//			  int delete = st.executeUpdate(sql);
//			  if(delete == 0){
//				  System.out.println("All rows are completelly deleted!");
//				  }
//			rst = s.executeQuery("Select * from hat_Punkte order by Punktzahl limit 10");
//			while (rst.next()) {
//				for(int i =0;i<10;i++){
//					 player[i] = rs.getString(1) ; 
//					 points[i] = rs.getInt(2);
//			
//			
//				String befehl = "Insert INTO Punkte Values ('"
//						+ player[i]+ "'," + points[i] + ")";
//			stm.executeUpdate(befehl);
//
//			}
//			
//		}} catch (SQLException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//	}

	public void insert_reg(String name, String mail, String password) throws SQLException {
		int rowcount = 0 ;
	
		try {
			
			boolean reg = false;
		Statement	statement = con.createStatement();
			String select;
			select = "select * from Spieler";
			rs = statement.executeQuery(select);
			rowcount = rs.getRow();
			if (rowcount != 0) {
				while (rs.next() && reg == true) {
					if (name == rs.getString(1) || mail == rs.getString(2)) {
						System.out.println("User or email already exists");
					} else {

						break;
					}
				}
			
			}
			if(rowcount == 0) {
					System.out.println("top");
						statement.executeUpdate("INSERT INTO Spieler VALUES('" + name + "','"
								+ mail + "','" + password + "');");
			}}catch(Exception e){
				System.out.println(e);
	SendMail email = new SendMail();	
	try {
		email.send(mail, name, password);
	} catch (Exception er) {
		// TODO Auto-generated catch block
		er.printStackTrace();
	}
	}
	}
	
	public void password_forgot(String email){
		try {
			s = con.createStatement();
			ResultSet	rs = s.executeQuery("Select * from Spieler where mail ='"+email+"'");
			if(rs.next()){
				String pw = rs.getString(3);
				SendMail mail = new SendMail();
						try { 	
							mail.send_pw(email, pw);
						} catch (Exception e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
			}else{
				System.out.println("email isn't registrated");
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void change_pw(String old_password, String new_password, String email){
		try {
			s = con.createStatement();
			rs = s.executeQuery("Select * from Spieler where password = '"+old_password+"' and mail ='"+email+"';");
			if(rs.next()){
				Statement st= con.createStatement();
				st.executeUpdate("Update Spieler set password = '"+ new_password+"' where password ='"+ old_password+"';");
				SendMail mail = new SendMail();
				try {
					mail.send_pw(rs.getString(2), new_password);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

			
//		try {
//			bsp.insert_reg("jn", "loster@gmx.net", "123");
//		} catch (SQLException sqle) {  
//			   String message = sqle.getMessage();  
//			   if (message.indexOf("ORA-00001") != -1) {  
//			      System.out.println("Duplicate Data entry.");  
//			   } else {  
//			       System.out.println("some other ORA error");  
//			   }  
//			}  

}