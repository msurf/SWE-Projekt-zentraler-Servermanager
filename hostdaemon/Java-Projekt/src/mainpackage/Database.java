package mainpackage;

import java.sql.*;

public class Database {
	
	// table: 			users
	// 3 columns:	name, pw, rigths
	//
	// table:			client
	// 6 columns:	name, ip, clientid, user, pw, status
	//
	// table:			software
	// 5 columns:	softid, name, description, file, frpip
	//
	// table:			insoftware
	// 6 columns:	id, softid, clientid, user, pw, status
	//
	// table:			hardware
	// 5 columns:	id, clientid, cpu, ram, architecture
	//
	// table:			messages
	// 5 columns:	id, clientid, time, status, message
	public Database () {
	}
	
	// delete all tables
	public void deleteDB() throws ClassNotFoundException, SQLException{
		System.out.println("Start deleting Tables ->");
		Class.forName("org.sqlite.JDBC");
		Connection conn = DriverManager.getConnection("jdbc:sqlite:servermanager.db");
		Statement stat = conn.createStatement();
		stat.executeUpdate("drop table if exists users;");
		stat.executeUpdate("drop table if exists client;");
		stat.executeUpdate("drop table if exists software;");
		stat.executeUpdate("drop table if exists insoftware;");
		stat.executeUpdate("drop table if exists hardware;");
		stat.executeUpdate("drop table if exists messages;");
		System.out.println("<- All Tables deleted");
	}
	// create important tables
	protected void create_DB() throws ClassNotFoundException, SQLException, Exception {
			System.out.println("Start creating Tables ->");
			Class.forName("org.sqlite.JDBC");
			Connection conn = DriverManager.getConnection("jdbc:sqlite:servermanager.db");
			Statement stat = conn.createStatement();
			// table: 			users
			// 3 columns:	name, pw, rigths
			stat.executeUpdate("create table if not exists users( name text primary key not null unique,"+
										  										                 "pw text not null,"+
										  										                 "rights text not null,"+
										  										                 "check (rights = 'admin' or rights = 'read'));");
			// table:			client
			// 6 columns:	name, ip, clientid, user, pw, status
			stat.executeUpdate("create table if not exists client( name text not null unique,"+
																			                     "ip text not null unique,"+
																			                     "clientid integer primary key autoincrement not null unique,"+
																			                     "user text not null default 'root',"+
																			                     "pw text not null default 'swe1234',"+
																			                     "status text not null default 'off',"+
																			                     "check (status = 'busy' or status = 'off' or status = 'on'));");
			// table:			software
			// 5 columns:	softid, name, description, file, frpip
			stat.executeUpdate("create table if not exists software( softid integer primary key autoincrement not null unique,"+
																				                      "name  text not null unique,"+
																				                      "description text,"+
																				                      "file text not null,"+
																				                      "ftpip text not null);");
			// table:			insoftware
			// 6 columns:	id, softid, clientid, user, pw, status
			stat.executeUpdate("create table if not exists insoftware( id integer primary key autoincrement not null unique,"+
											 														     "softid references software(softid) on delete restrict on update restrict not null,"+
											 														     "clientid references client(clientid) on delete restrict on update restrict not null,"+
											 														     "user text,"+
											 														     "pw text,"+
											 														     "status text not null default 'install',"+
											 														     "check (status='install' or status = 'on' or status = 'off'));");
			// table:			hardware
			// 5 columns:	id, clientid, cpu, ram, architecture
			stat.executeUpdate("create table if not exists hardware( id integer primary key not null unique, " +
																										"clientid integer not null ,"+
																										"cpu text,"+
																										"ram text,"+
																								       	"architecture text);");
			// table:			messages
			// 5 columns:	id, clientid, time, status, message
			stat.executeUpdate("create table if not exists messages( id integer primary key autoincrement not null unique,"+
																										"clientid references client(clientid) on delete restrict on update restrict,"+
																										"time text,"+
																										"status text not null,"+
																										"message text);");
			stat.close();
			conn.close();
			System.out.println("<- All Tables created");
	}
	
	//fill the tables with some information
	public void fillDB() throws ClassNotFoundException, SQLException{
		System.out.println("Start creating Entries ->");
		Class.forName("org.sqlite.JDBC");
		Connection conn = DriverManager.getConnection("jdbc:sqlite:servermanager.db");
		Statement stat = conn.createStatement();
		String clientid = "";
		String softid = "";
		ResultSet rs;
		
		/* Clients */
		stat.executeUpdate("insert into client(name,ip,user,pw) values ('debian2','192.168.21.22:5550','debian2','debian2')");
		stat.executeUpdate("insert into client(name,ip,user,pw) values ('debian3','192.168.21.23:5550','debian3','debian3')");
		/* Software */
		stat.executeUpdate("insert into software(name,description,file,ftpip) values ('css','Installiert CounterStrike:Source','css.tar.gz','192.168.1.21')");
		
		/* installierte Software*/
			rs = stat.executeQuery("select softid from software where name='css';");
			while(rs.next())
			softid = rs.getString("softid");
			rs = stat.executeQuery("select clientid from client where name='debian2';");
			while(rs.next())
				clientid = rs.getString("clientid");
		
			stat.executeUpdate("insert into insoftware(softid,clientid,user,pw) values ('"+softid+"','"+clientid+"','css1','none')");
			stat.executeUpdate("insert into insoftware(softid,clientid,user,pw) values ('"+softid+"','"+clientid+"','css2','none')");
			stat.executeUpdate("insert into insoftware(softid,clientid,user,pw) values ('"+softid+"','"+clientid+"','css3','none')");
		
			rs = stat.executeQuery("select clientid from client where name='debian3'");
			while(rs.next())
				clientid = rs.getString("clientid");	
			stat.executeUpdate("insert into insoftware(softid,clientid,user,pw) values ('"+softid+"','"+clientid+"','css1','none')");
			stat.executeUpdate("insert into insoftware(softid,clientid,user,pw) values ('"+softid+"','"+clientid+"','css2','none')");

		/* Hardware */
		rs = stat.executeQuery("select clientid from client where name='debian2'");
		while(rs.next())
			clientid = rs.getString("clientid");
		
		stat.executeUpdate("insert into hardware(clientid,cpu,ram,architecture) values ('"+clientid+"','C2D@2.667Ghz','2024Mb','i386');");
		
		rs = stat.executeQuery("select clientid from client where name='debian3'");
		while(rs.next())
			clientid = rs.getString("clientid");
		
		stat.executeUpdate("insert into hardware(clientid,cpu,ram,architecture) values ('"+clientid+"','AMD@1.8Ghz','4096Mb','amd64');");
		
		System.out.println("<- All Tables updated");
	}
	
	// identify the user 
	protected String getInfo_Authenticate(String user, String password) throws SQLException, ClassNotFoundException, Exception {
		String result = "";
		Class.forName("org.sqlite.JDBC");
		Connection conn = DriverManager.getConnection("jdbc:sqlite:servermanager.db");
		Statement stat = conn.createStatement();
		ResultSet rs = stat.executeQuery("select rights from user where name = '"+user+"' and pw ='"+password+"';");
		while(rs.next()) {
			result = rs.getString("rights");
		}
		rs.close();
		stat.close();
		conn.close();
		if(result.equals("")) {
				return "incorrect:none";
			}
			else {
				return "correct:"+result;
			}
		}
	
	// get the names and clientid of all available clients
	protected String getInfo_getClients() throws SQLException, ClassNotFoundException, Exception {
		String result = "none";
		Class.forName("org.sqlite.JDBC");
		Connection conn = DriverManager.getConnection("jdbc:sqlite:servermanager.db");
		Statement stat = conn.createStatement();
		ResultSet rs = stat.executeQuery("select distinct name, clientid from client;");
		while (rs.next()) {
			result += "#"+rs.getString("name")+":"+rs.getInt("clientid");																				
		}
		result = result.replace("none#", "");
		rs.close();
		stat.close();
		conn.close();
		return result;
	}
	
	// get the status of a choosen client
	protected String getInfo_getClientStatus(int clientID) throws SQLException, ClassNotFoundException, Exception {
		String result = "";
		Class.forName("org.sqlite.JDBC");
		Connection conn = DriverManager.getConnection("jdbc:sqlite:servermanager.db");
		Statement stat = conn.createStatement();
		ResultSet rs = stat.executeQuery("select status from client where clientid = "+clientID+";");
		while(rs.next()) {
			result = rs.getString("status");
		}
		rs.close();
		stat.close();
		conn.close();
		return result;
	}
	
	// get the repolist of all available software
	protected String getInfo_getRepoList() throws SQLException, ClassNotFoundException, Exception {
		String result = "none";
		Class.forName("org.sqlite.JDBC");
		Connection conn = DriverManager.getConnection("jdbc:sqlite:servermanager.db");
		Statement stat = conn.createStatement();
		ResultSet rs = stat.executeQuery("select distinct name from software;");
		while(rs.next()) {
			result += "#"+rs.getString("name");
		}
		result = result.replace("none#", "");
		rs.close();
		stat.close();
		conn.close();
		return result;
	}
	
	// get information about CPU, RAM and architecture of a choosen client
	protected String getInfo_hwInfo(int clientID) throws SQLException, ClassNotFoundException, Exception {
		String result = "";
		Class.forName("org.sqlite.JDBC");
		Connection conn = DriverManager.getConnection("jdbc:sqlite:servermanager.db");
		Statement stat = conn.createStatement();
		ResultSet rs = stat.executeQuery("select cpu, ram, architecture from hardware where clientid = '"+clientID+"';");
		while (rs.next()) {
			result = "cpu:"+rs.getString("cpu")+"#ram:"+rs.getString("ram")+"#architecture:"+rs.getString("architecture");
		}
		rs.close();
		stat.close();
		conn.close();
		return result;
	}
	
	// get information about status and user of a choosen software
	protected String getInfo_swInfo(int clientID) throws SQLException, ClassNotFoundException, Exception {
		String result = "none";
		Class.forName("org.sqlite.JDBC");
		Connection conn = DriverManager.getConnection("jdbc:sqlite:servermanager.db");
		Statement stat = conn.createStatement();
		ResultSet rs = stat.executeQuery("select status, user from insoftware where clientid = '"+clientID+"';");
		while (rs.next()) {
			result += "#" + rs.getString("user")+":"+rs.getString("status");
		}
		result = result.replace("none#", "");
		rs.close();
		stat.close();
		conn.close();
		return result;
	}
	
	// insert a new client
	protected void insertNewClient(String clientName, String clientIP, String user, String password) throws ClassNotFoundException, SQLException{
		Class.forName("org.sqlite.JDBC");
		Connection conn = DriverManager.getConnection("jdbc:sqlite:servermanager.db");
		Statement stat = conn.createStatement();
		stat.executeUpdate("insert into client(name, ip, user, pw)"+
						                 "values ('"+clientName+"', '"+
									                   clientIP+"', '"+
									                   user+"', '"+
									                   password+"');");
	}
	
	// get the IP of a choosen client
	protected String getClientIP(int clientID) throws SQLException, ClassNotFoundException, Exception {
		Class.forName("org.sqlite.JDBC");
		Connection conn = DriverManager.getConnection("jdbc:sqlite:servermanager.db");
		Statement stat = conn.createStatement();
		ResultSet rs = stat.executeQuery("select ip" +
										                      "from client" +
										                      "where clientid="+clientID+";");
		String result = "";
		while (rs.next()) {
			result = rs.getString("ip");
		}
		rs.close();
		stat.close();
		conn.close();
		return result;
	}
	
	// get the FTP_IP and file of a choosen software
	protected String[] getFTP_IP_FILE(int parameter) throws SQLException, ClassNotFoundException, Exception {
		Class.forName("org.sqlite.JDBC");
		Connection conn = DriverManager.getConnection("jdbc:sqlite:servermanager.db");
		Statement stat = conn.createStatement();
		ResultSet rs = stat.executeQuery("select ftpip, file" +
										    				  "from software" +
										    				  "where softid='"+parameter+"';");
		String []result = new String[2];
	
		//result[0] beinhaltet die ip und result[1] den filenamen
		while (rs.next()) {
			result[0] = rs.getString("ftpip");
			result[1] = rs.getString("file");
		}
		rs.close();
		stat.close();
		conn.close();
		return result;
	}
	
	protected String[] getFTP_IP_FILE(String parameter) throws SQLException, ClassNotFoundException, Exception {
		Class.forName("org.sqlite.JDBC");
		Connection conn = DriverManager.getConnection("jdbc:sqlite:servermanager.db");
		Statement stat = conn.createStatement();
		 ResultSet rs = stat.executeQuery("select ftpip, file" +
				  "from software" +
				  "where name='"+parameter+"';");
		String []result = new String[2];
	
		//result[0] beinhaltet die ip und result[1] den filenamen
		while (rs.next()) {
			result[0] = rs.getString("ftpip");
			result[1] = rs.getString("file");
		}
		rs.close();
		stat.close();
		conn.close();
		return result;
	}
	
	public void insertInstalledSofware(String softid, String clientid, String ftp_File, String ftp_IP) {
		try {
			Class.forName("org.sqlite.JDBC");
		} catch (ClassNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		Connection conn = null;
		Statement stat=null;
		try{
			conn=DriverManager.getConnection("jdbc:sqlite:servermanager.db");
			stat = conn.createStatement();
			stat.executeUpdate("insert into insoftware(softid,clientid,ftp_ip, ftp_file)"+
							   "values ('" +softid+"',"+
							   			"'" +clientid+"',"+
							   			"'"+ftp_IP+"', '"+
								           ftp_File+"');");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
