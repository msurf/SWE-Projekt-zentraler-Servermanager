package mainpackage;

import java.sql.*;

public class Database {

	@SuppressWarnings("unused")
	private String info_authenticate;
	@SuppressWarnings("unused")
	private String info_getclients;
	@SuppressWarnings("unused")
	private String info_getClientsStatus;
	@SuppressWarnings("unused")
	private String info_getRepoListe;
	@SuppressWarnings("unused")
	private String info_hwInfo;
	@SuppressWarnings("unused")
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
										  										  "Pwd text not null,"+
										  										  "Rights text not null,"+
										  										  "check (Rights = 'admin' or Rights = 'read'));");
			stat.executeUpdate("create table Client( Name text not null unique,"+
																			 "IP text not null unique,"+
																			 "Client_ID integer primary key autoincrement not null unique,"+
																			 "user text not null default 'root',"+
																			 "pw text not null default 'swe1234',"+
																			 "status text not null default 'off',"+
																			 "check (status = 'busy' or status = 'off' or status = 'on'));");
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
											 														 "check (status='install' or status = 'on' or status = 'off'));");
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
		String ergebnis = "none";
		Class.forName("org.sqlite.JDBC");
		Connection conn = DriverManager.getConnection("jdbc:sqlite:servermanager.db");
		Statement stat = conn.createStatement();
		ResultSet rs = stat.executeQuery("select distinct Name from Software;");
		while(rs.next()) {
			ergebnis += "#"+rs.getString(1);
		}
		ergebnis += ergebnis.replace("none#", "");
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
		String ergebnis = "none";
		Class.forName("org.sqlite.JDBC");
		Connection conn = DriverManager.getConnection("jdbc:sqlite:servermanager.db");
		Statement stat = conn.createStatement();
		ResultSet rs = stat.executeQuery("select Status, Software_Benutzer from Installierte_Software where Client_ID = "+clientID+";");
		while (rs.next()) {
			ergebnis += "#" + rs.getString(2)+":"+rs.getString(1);
		}
		ergebnis = ergebnis.replace("none#", "");
		rs.close();
		stat.close();
		conn.close();
		return ergebnis;
	}
	
	protected void insertNewClient(String clientName, String clientIP, String user, String password) throws ClassNotFoundException, SQLException{
		Class.forName("org.sqlite.JDBC");
		Connection conn = DriverManager.getConnection("jdbc:sqlite:servermanager.db");
		Statement stat = conn.createStatement();
		stat.executeUpdate("INSERT INTO Client"+
						   "VALUES ("+clientName+", "+
									  clientIP+", "+
									  user+", "+
									  password+")");
				
	}
	
	protected String getClientIP(int clientID) throws SQLException, ClassNotFoundException, Exception {
		Class.forName("org.sqlite.JDBC");
		Connection conn = DriverManager.getConnection("jdbc:sqlite:servermanager.db");
		Statement stat = conn.createStatement();
		ResultSet rs = stat.executeQuery("SELECT ip" +
										 "FROM client" +
										 "WHERE client_id="+clientID+";");
		String ergebnis = "";
		while (rs.next()) {
			ergebnis = rs.getString(0);
		}
		rs.close();
		stat.close();
		conn.close();
		return ergebnis;
	}
	
	protected String[] getFTP_IP_FILE(int parameter) throws SQLException, ClassNotFoundException, Exception {
		Class.forName("org.sqlite.JDBC");
		Connection conn = DriverManager.getConnection("jdbc:sqlite:servermanager.db");
		Statement stat = conn.createStatement();
		ResultSet rs = stat.executeQuery("SELECT ftp_id, ftp_file" +
										 "FROM software" +
										 "WHERE software_id="+parameter+";");
		String []ergebnis = new String[2];
	
		//ergebnis[0] beinhaltet die ip und ergebnis[1] den filenamen
		while (rs.next()) {
			ergebnis[0] = rs.getString(0);
			ergebnis[1] = rs.getString(1);
		}
		rs.close();
		stat.close();
		conn.close();
		return ergebnis;
	}
}
