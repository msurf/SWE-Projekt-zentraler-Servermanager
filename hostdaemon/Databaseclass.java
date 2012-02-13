import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.JOptionPane;

//import javax.swing.JOptionPane;

public class Databaseclass extends Thread  {
	
	private String username;
	private String passwort;
	private String CPU;
	private String RAM;
	private int ID;
	private String software_name;
	
	public Databaseclass () {
		this.username="";
		this.passwort="";
		this.CPU="";
		this.RAM="";
		this.ID=0;
		this.software_name="";
	}

	public static void leseAusBenutzerDB() throws SQLException, ClassNotFoundException {
		Class.forName("org.sqlite.JDBC");
		Connection conn = DriverManager.getConnection("jdbc:sqlite:servermanager.db");
		Statement stat = conn.createStatement();
		ResultSet rs = stat.executeQuery("Select * FROM benutzer ");
		while(rs.next()) {
			System.out.println("Benutzername: "+rs.getString(1));
			System.out.println("Passwort: "+rs.getString(2));
		}
		rs.close();
		stat.close();
		conn.close();
	}
	
	public static void leseAusClientDB() throws SQLException, ClassNotFoundException {
		Class.forName("org.sqlite.JDBC");
		Connection conn = DriverManager.getConnection("jdbc:sqlite:servermanager.db");
		Statement stat = conn.createStatement();
		ResultSet rs = stat.executeQuery("Select * FROM client ");
		while(rs.next()) {
			System.out.println("CPU: "+rs.getString(1));
			System.out.println("RAM: "+rs.getString(2));
			System.out.println("ID: "+rs.getInt(3));
		}
		rs.close();
		stat.close();
		conn.close();
	}	
	
	public static void leseAusSoftwareDB() throws SQLException, ClassNotFoundException {
		Class.forName("org.sqlite.JDBC");
		Connection conn = DriverManager.getConnection("jdbc:sqlite:servermanager.db");
		Statement stat = conn.createStatement();
		ResultSet rs = stat.executeQuery("Select * FROM software ");
		while(rs.next()) {
			System.out.println("Name: "+rs.getString(1));
		}
		rs.close();
		stat.close();
		conn.close();
		}
	
	public static void trageEinInBenutzerDB() throws SQLException, ClassNotFoundException {
		Class.forName("org.sqlite.JDBC");
		Connection conn = DriverManager.getConnection("jdbc:sqlite:servermanager.db");
		Statement stat = conn.createStatement();
		PreparedStatement ps = conn.prepareStatement("insert into benutzer values(?,?);");
		
		ps.setString(1, JOptionPane.showInputDialog("Usernamen eingeben"));
		ps.setString(2, JOptionPane.showInputDialog("Passwort eingeben"));
		ps.addBatch();
		
		conn.setAutoCommit(false);
		ps.executeBatch();
		conn.setAutoCommit(true);
		
		ps.close();
		stat.close();
		conn.close();
	}
	
	public static void trageEinInClientDB() throws SQLException, ClassNotFoundException {
		Class.forName("org.sqlite.JDBC");
		Connection conn = DriverManager.getConnection("jdbc:sqlite:servermanager.db");
		Statement stat = conn.createStatement();
		PreparedStatement ps = conn.prepareStatement("insert into client values(?,?,?);");
		
		ps.setString(1, JOptionPane.showInputDialog("CPU eingeben"));
		ps.setString(2, JOptionPane.showInputDialog("RAM eingeben"));
		ps.setInt(3, Integer.parseInt(JOptionPane.showInputDialog("ID eingeben")));
		ps.addBatch();
		
		conn.setAutoCommit(false);
		ps.executeBatch();
		conn.setAutoCommit(true);
		
		ps.close();
		stat.close();
		conn.close();
	}
	
	public static void trageEinInSoftwareDB() throws SQLException, ClassNotFoundException {
		Class.forName("org.sqlite.JDBC");
		Connection conn = DriverManager.getConnection("jdbc:sqlite:servermanager.db");
		Statement stat = conn.createStatement();
		PreparedStatement ps = conn.prepareStatement("insert into software values(?);");
		
		ps.setString(1, JOptionPane.showInputDialog("Softwarenamen eingeben"));
		ps.addBatch();
		
		conn.setAutoCommit(false);
		ps.executeBatch();
		conn.setAutoCommit(true);
		
		ps.close();
		stat.close();
		conn.close();
	}

	public void run() {
		try {
			leseAusBenutzerDB();
			leseAusClientDB();
			leseAusSoftwareDB();
			trageEinInBenutzerDB();
			trageEinInClientDB();
			trageEinInSoftwareDB();
		}	catch (Exception e ) {
				System.out.println("Da haut was nicht hin!");
		}		
	}
	public static void main(String[] args) throws SQLException, ClassNotFoundException {
		
	}
}