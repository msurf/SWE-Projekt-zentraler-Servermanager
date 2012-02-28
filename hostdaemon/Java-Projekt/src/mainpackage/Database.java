package mainpackage;

import java.sql.*;

public class Database {

	private String info_authenticate;
	private String info_getclients;
	private String info_getClientsStatus;
	private String info_getRepoListe;
	private String info_hwInfo;
	private String info_swInfo;
	
	public Database () {
		this.info_authenticate = "";
		this.info_getclients = "";
		this.info_getClientsStatus = "";
		this.info_getRepoListe = "";
		this.info_hwInfo = "";
		this.info_swInfo = "";
	}
	
	protected void create_DB() throws ClassNotFoundException, SQLException, Exception {
			Class.forName("org.sqlite.JDBC");
			Connection conn = DriverManager.getConnection("jdbc:sqlite:servermanager.db");
			Statement stat = conn.createStatement();
			stat.executeUpdate("create table Benutzer( Name text primary key not null unique,"+
										  										  "Pwd text primary key not null,"+
										  										  "Rights text not null,"+
										  										  "check (Rights='admin' or 'read'));");
			stat.executeUpdate("create table Client( Name text not null unique,"+
																			 "IP text not null unique,"+
																			 "Client_ID integer primary key autoincrement not null unique,"+
																			 "user text not null default 'root',"+
																			 "pw text not null default 'swe1234',"+
																			 "status text not null default 'off',"+
																			 "check (status='busy' or 'off' or 'on'));");
			stat.executeUpdate("create table Software( Software_ID integer primary key autoincrement not null unique,"+
																				  "Name  text not null unique,"+
																				  "Beschreibung text,"+
																				  "FTP_Pfad text not null,"+
																				  "FTP_IP text not null;");
			stat.executeUpdate("create table Installierte_Software(ID integer primary key autoincrement not null unique,"+
											 														 "Software_ID references Software(Software_ID) on delete restrict on update restrict not null,"+
											 														 "Client_ID references Client(Client_ID) on delete restrict on update restrict not null,"+
											 														 "Software_Benutzer text,"+
											 														 "Passwort text,"+
											 														 "Status text not null default 'install',"+
											 														 "check (status='install' or 'on' or 'off'));");
			stat.executeUpdate("create table Hardware( Client_ID references Software(Software_ID) on delete restrict on update restrict primary key,"+
																					"CPU text,"+
																					"RAM text,"+
																					"Architecture text);");
			stat.close();
			conn.close();
	}
	
	protected String getInfo_Authenticate(String user, String password) throws SQLException, ClassNotFoundException, Exception {
		String ergebnis = "";
		Class.forName("org.sqlite.JDBC");
		Connection conn = DriverManager.getConnection("jdbc:sqlite:servermanager.db");
		Statement stat = conn.createStatement();
		ResultSet rs = stat.executeQuery("select Rights from Benutzer where Name = "+user+" and Pwd ="+password+";");
		while(rs.next()) {
			ergebnis = rs.getString(1);
		}
		rs.close();
		stat.close();
		conn.close();
		if(ergebnis.equals("")) {
				return "incorrect:none";
			}
			else {
				return "correct:"+ergebnis;
			}
		}
	
	protected String getInfo_getClients() throws SQLException, ClassNotFoundException, Exception {
		String ergebnis = "";
		Class.forName("org.sqlite.JDBC");
		Connection conn = DriverManager.getConnection("jdbc:sqlite:servermanager.db");
		Statement stat = conn.createStatement();
		ResultSet rs = stat.executeQuery("select distinct Name, Client_ID from Client;");
		while (rs.next()) {
			ergebnis += rs.getString(1)+":"+rs.getInt(2)+"#"; 
		}
		rs.close();
		stat.close();
		conn.close();
		return ergebnis;
	}
	
	protected String getInfo_getClientStatus(int clientID) throws SQLException, ClassNotFoundException, Exception {
		String ergebnis = "";
		Class.forName("org.sqlite.JDBC");
		Connection conn = DriverManager.getConnection("jdbc:sqlite:servermanager.db");
		Statement stat = conn.createStatement();
		ResultSet rs = stat.executeQuery("select status from Client where Client_ID = "+clientID+";");
		while(rs.next()) {
			ergebnis = rs.getString(1);
		}
		rs.close();
		stat.close();
		conn.close();
		return ergebnis;
	}
	
	protected String getInfo_getRepoList() throws SQLException, ClassNotFoundException, Exception {
		String ergebnis = "";
		Class.forName("org.sqlite.JDBC");
		Connection conn = DriverManager.getConnection("jdbc:sqlite:servermanager.db");
		Statement stat = conn.createStatement();
		ResultSet rs = stat.executeQuery("select distinct Name from Software;");
		while(rs.next()) {
			ergebnis += rs.getString(1)+"#";
		}
		rs.close();
		stat.close();
		conn.close();
		return ergebnis;
	}
	
	protected String getInfo_hwInfo(int clientID) throws SQLException, ClassNotFoundException, Exception {
		String ergebnis = "";
		Class.forName("org.sqlite.JDBC");
		Connection conn = DriverManager.getConnection("jdbc:sqlite:servermanager.db");
		Statement stat = conn.createStatement();
		ResultSet rs = stat.executeQuery("select CPU, RAM, Architecture from Hardware where Client_ID = "+clientID+";");
		while (rs.next()) {
			ergebnis = "cpu:"+rs.getString(1)+"#ram:"+rs.getString(2)+"#architecture:"+rs.getString(3);
		}
		rs.close();
		stat.close();
		conn.close();
		return ergebnis;
	}
	
	protected String getInfo_swInfo(int clientID) throws SQLException, ClassNotFoundException, Exception {
		String ergebnis = "";
		Class.forName("org.sqlite.JDBC");
		Connection conn = DriverManager.getConnection("jdbc:sqlite:servermanager.db");
		Statement stat = conn.createStatement();
		ResultSet rs = stat.executeQuery("select Status, Software_Benutzer from Installierte_Software where Client_ID = "+clientID+";");
		while (rs.next()) {
			ergebnis = rs.getString(2)+":"+rs.getString(1);
		}
		rs.close();
		stat.close();
		conn.close();
		return ergebnis;
	}
	
	protected void insertNewClient(String clientName, String clientIP, String user, String password) throws ClassNotFoundException, SQLException{
		Class.forName("org.sqlite.JDBC");
		Connection conn = DriverManager.getConnection("jdbc:sqlite:servermanager.db");
		Statement stat = conn.createStatement();
		stat.executeUpdate("INSERT INTO client"+
						   "VALUES ("+clientName+", "+
									  clientIP+", "+
									  user+", "+
									  password+")");
				
	}
}
