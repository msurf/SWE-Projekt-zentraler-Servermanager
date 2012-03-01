package mainpackage;

import java.sql.*;

public class Database {
	
	public Database () {
	}
	
	public void deleteDB() throws ClassNotFoundException, SQLException{
		System.out.println("Start deleting Tables ->");
		Class.forName("org.sqlite.JDBC");
		Connection conn = DriverManager.getConnection("jdbc:sqlite:servermanager.db");
		Statement stat = conn.createStatement();
		stat.executeUpdate("drop table if exists benutzer");
		stat.executeUpdate("drop table if exists client");
		stat.executeUpdate("drop table if exists software");
		stat.executeUpdate("drop table if exists installierte_software");
		stat.executeUpdate("drop table if exists hardware");
		System.out.println("<- All Tables deleted");
	}
	
	protected void create_DB() throws ClassNotFoundException, SQLException, Exception {
			System.out.println("Start creating Tables ->");
			Class.forName("org.sqlite.JDBC");
			Connection conn = DriverManager.getConnection("jdbc:sqlite:servermanager.db");
			Statement stat = conn.createStatement();
			stat.executeUpdate("create table if not exists Benutzer( Name text primary key not null unique,"+
										  										  "Pwd text not null,"+
										  										  "Rights text not null,"+
										  										  "check (Rights = 'admin' or Rights = 'read'));");
			stat.executeUpdate("create table if not exists Client( Name text not null unique,"+
																			 "IP text not null unique,"+
																			 "Client_ID integer primary key autoincrement not null unique,"+
																			 "user text not null default 'root',"+
																			 "pw text not null default 'swe1234',"+
																			 "status text not null default 'off',"+
																			 "check (status = 'busy' or status = 'off' or status = 'on'));");
			stat.executeUpdate("create table if not exists Software( Software_ID integer primary key autoincrement not null unique,"+
																				  "Name  text not null unique,"+
																				  "Beschreibung text,"+
																				  "FTP_Pfad text not null,"+
																				  "FTP_IP text not null);");
			stat.executeUpdate("create table if not exists Installierte_Software(ID integer primary key autoincrement not null unique,"+
											 														 "Software_ID references Software(Software_ID) on delete restrict on update restrict not null,"+
											 														 "Client_ID references Client(Client_ID) on delete restrict on update restrict not null,"+
											 														 "Software_Benutzer text,"+
											 														 "Passwort text,"+
											 														 "Status text not null default 'install',"+
											 														 "check (status='install' or status = 'on' or status = 'off'));");
			stat.executeUpdate("create table if not exists Hardware( Client_ID references Software(Software_ID) on delete restrict on update restrict primary key,"+
																					"CPU text,"+
																					"RAM text,"+
																					"Architecture text);");
			stat.close();
			conn.close();
			System.out.println("<- All Tables created");
	}
	
	public void fillDB() throws ClassNotFoundException, SQLException{
		System.out.println("Start creating Entries ->");
		Class.forName("org.sqlite.JDBC");
		Connection conn = DriverManager.getConnection("jdbc:sqlite:servermanager.db");
		Statement stat = conn.createStatement();
		String clientid = "";
		String softwareid = "";
		ResultSet rs;
		/* Benutzer */
		stat.executeUpdate("insert into benutzer(name,pwd,rights) values ('ptendyra','tendyrapw','admin')");
		stat.executeUpdate("insert into benutzer(name,pwd,rights) values ('fmooz','moozpw','admin')");
		stat.executeUpdate("insert into benutzer(name,pwd,rights) values ('tgies','giespw','admin')");
		stat.executeUpdate("insert into benutzer(name,pwd,rights) values ('tthieron','thieronpw','admin')");
		stat.executeUpdate("insert into benutzer(name,pwd,rights) values ('fwolff','wolffpw','admin')");
		
		/* Clients */
		stat.executeUpdate("insert into client(name,ip,user,pw) values ('debian2','192.168.21.22:5550','debian2','debian2')");
		stat.executeUpdate("insert into client(name,ip,user,pw) values ('debian3','192.168.21.23:5550','debian3','debian3')");
		/* Software */
		stat.executeUpdate("insert into software(name,beschreibung,ftp_pfad,ftp_ip) values ('css','Installiert CounterStrike:Source','css.tar.gz','192.168.1.21')");
		
		/* installierte Software*/
		 	
			rs = stat.executeQuery("select software_id from software where name='css'");
			while(rs.next())
			softwareid = rs.getString("software_id");
			rs = stat.executeQuery("select client_id from client where name='debian2'");
			while(rs.next())
				clientid = rs.getString("client_id");
		
			stat.executeUpdate("insert into installierte_software(software_id,client_id,software_benutzer,passwort) values ('"+softwareid+"','"+clientid+"','css1','none')");
			stat.executeUpdate("insert into installierte_software(software_id,client_id,software_benutzer,passwort) values ('"+softwareid+"','"+clientid+"','css2','none')");
			stat.executeUpdate("insert into installierte_software(software_id,client_id,software_benutzer,passwort) values ('"+softwareid+"','"+clientid+"','css3','none')");
		
			rs = stat.executeQuery("select client_id from client where name='debian3'");
			while(rs.next())
				clientid = rs.getString("client_id");	
			stat.executeUpdate("insert into installierte_software(software_id,client_id,software_benutzer,passwort) values ('"+softwareid+"','"+clientid+"','css1','none')");
			stat.executeUpdate("insert into installierte_software(software_id,client_id,software_benutzer,passwort) values ('"+softwareid+"','"+clientid+"','css2','none')");

		/* Hardware */
		rs = stat.executeQuery("select client_id from client where name='debian2'");
		while(rs.next())
			clientid = rs.getString("client_id");
		
		stat.executeUpdate("insert into hardware(client_id,cpu,ram,architecture) values ('"+clientid+"','C2D@2.667Ghz','2024Mb','i386')");
		rs = stat.executeQuery("select client_id from client where name='debian3'");
		while(rs.next())
			clientid = rs.getString("client_id");
		stat.executeUpdate("insert into hardware(client_id,cpu,ram,architecture) values ('"+clientid+"','AMD@1.8Ghz','4096Mb','amd64')");
		
		
		System.out.println("<- All Tables updated");
	}
	
	protected String getInfo_Authenticate(String user, String password) throws SQLException, ClassNotFoundException, Exception {
		String ergebnis = "";
		Class.forName("org.sqlite.JDBC");
		Connection conn = DriverManager.getConnection("jdbc:sqlite:servermanager.db");
		Statement stat = conn.createStatement();
		ResultSet rs = stat.executeQuery("select Rights from Benutzer where Name = '"+user+"' and Pwd ='"+password+"';");
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
		ResultSet rs = stat.executeQuery("select distinct name from Software;");
		while(rs.next()) {
			ergebnis += "#"+rs.getString("name");
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
		ResultSet rs = stat.executeQuery("select cpu,ram,architecture from Hardware where Client_ID = '"+clientID+"';");
		while (rs.next()) {
			ergebnis = "cpu:"+rs.getString("cpu")+"#ram:"+rs.getString("ram")+"#architecture:"+rs.getString("architecture");
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
		ResultSet rs = stat.executeQuery("select status, software_benutzer from Installierte_Software where Client_ID = '"+clientID+"';");
		while (rs.next()) {
			ergebnis += "#" + rs.getString("software_benutzer")+":"+rs.getString("status");
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
						   "VALUES ('"+clientName+"', '"+
									  clientIP+"', '"+
									  user+"', '"+
									  password+"')");
				
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
