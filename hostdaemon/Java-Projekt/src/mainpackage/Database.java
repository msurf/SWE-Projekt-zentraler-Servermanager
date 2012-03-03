package mainpackage;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

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
	public Database(){}
	
	public void testClass(){
		System.out.println("########################## Start Testing ###########################");
		try {
			System.out.println("## ClientID");
			int id = getClientID("debian2");
			int id2 = getClientID("wronginput");
			System.out.println("debian2-id: "+id);
			System.out.println("wronginput-id: "+id2);
			System.out.println("");
			
			System.out.println("## Client-IP");
			String ip = getClientIP(id);
			String ip2 = getClientIP(34);
			System.out.println("debian2-IP: "+ip);
			System.out.println("wronginput-IP: "+ip2);
			System.out.println("");
			
			System.out.println("## Software-ID");
			int sid1 = getSoftID("css");
			int sid2 = getSoftID("wrongsoft");
			System.out.println("css-id: "+ sid1);
			System.out.println("wrongsoft-id: "+sid2);
			System.out.println("");
			
			System.out.println("## FTP");
			String[] ftp1 = getFTP_IP_FILE("css");
			String[] ftp2 = getFTP_IP_FILE("wrongfile");
			if(ftp1.length != 0)
				System.out.println("css-ftp: "+ftp1[0]+":"+ftp1[1]);
			if(ftp2.length != 0)
				System.out.println("wrong-ftp: "+ftp2[0]+":"+ftp2[1]);
			System.out.println("");
			
			String[] ftp3 = getFTP_IP_FILE(sid1);
			String[] ftp4 = getFTP_IP_FILE(101);
			if(ftp3.length != 0)
				System.out.println("css-ftp: "+ftp3[0]+":"+ftp3[1]);
			if(ftp4.length != 0)
				System.out.println("wrong-ftp: "+ftp4[0]+":"+ftp4[1]);
			System.out.println("");
			
			System.out.println("## Auth");
			String right1 = getInfo_Authenticate("ptendyra", "tendyrapw");
			String right2 = getInfo_Authenticate("wrong", "incor");
			System.out.println("Philipp-Auth: "+right1);
			System.out.println("Wrong-Auth: "+right2);
			System.out.println("");
			
			System.out.println("## Get-Clients");
			String clients1 = getInfo_getClients();
			System.out.println("Clients: "+ clients1);
			System.out.println("");
			
			System.out.println("## Get-Clientstatus");
			String status1 = getInfo_getClientStatus(id);
			String status2 = getInfo_getClientStatus(111);
			System.out.println("debain2-Status: "+status1);
			System.out.println("wrong-status: "+status2);
			System.out.println("");
			
			System.out.println("## Get-Repo");
			String repo = getInfo_getRepoList();
			System.out.println("Repository: "+repo);
			System.out.println("");
			
			System.out.println("## Get-Hardwareinfo");
			String hw1 = getInfo_hwInfo(id);
			String hw2 = getInfo_hwInfo(123);
			System.out.println("debian2-hw: "+hw1);
			System.out.println("wrong-hw: "+hw2);
			System.out.println("");
			
			System.out.println("## Get-SoftwareInfo");
			String sw1 = getInfo_swInfo(id);
			String sw2 = getInfo_swInfo(123);
			System.out.println("debian2-sw: "+sw1);
			System.out.println("wrong-sw: "+sw2);
			System.out.println("");
			
			System.out.println("## Insert new Client");
			System.out.println("insert new Client: debian4");
			insertNewClient("debian4", "192.168.1.24:5550", "root", "swe1234");
			System.out.println("insert new Client: name allready in use");
			//insertNewClient("debian4", "192.168.1.25:5550", "root", "swe1234");//Exception correct
			System.out.println("insert new Client: ip allready in use");
			//insertNewClient("debian5", "192.168.1.24:5550", "root", "swe1234");//Exception correct
			System.out.println(getInfo_getClients());
			int iddeb4 = getClientID("debian4");
			System.out.println("");
			
			System.out.println("## Insert Installed Software");
			System.out.println("insert installed Software: debian2");
			insertInstalledSofware(sid1, id, "css4", "on");
			System.out.println("insert installed Software: wrong client");
			insertInstalledSofware(sid1, 101, "css4", "off");
			System.out.println("insert installed Software: wrong software");
			insertInstalledSofware(101, id, "css6", "off");
			System.out.println("insert installed Software: user allready in use");
			insertInstalledSofware(sid1, id, "css1", "off");
			System.out.println("insert installed Software: wrong status");
			//insertInstalledSofware(sid1, iddeb4, "css4", "none");//Exception correct
			System.out.println("");
			
			System.out.println("## Delete Client");
			System.out.println("Delete Client: debian4");
			delClient(iddeb4);
			System.out.println(getInfo_getClients());
			System.out.println("Delete Client: debian4, no entry for client");
			delClient(iddeb4);
			System.out.println(getInfo_getClients());
			System.out.println("");
			
			System.out.println("## Insert new Software");
			System.out.println("Insert new Software: fcss");
			insertSoftware("fcss", "fcss.tar.gz", "192.168.1.21");
			System.out.println(getInfo_getRepoList());
			System.out.println("Insert new Software: name allready exists");
			//insertSoftware("fcss", "fcss2.tar.gz", "192.168.1.21");//Exception correct
			System.out.println(getInfo_getRepoList());
			System.out.println("Insert new Software: file allready exists");
			//insertSoftware("fcss2", "fcss.tar.gz", "192.168.1.21");//Exception correct
			System.out.println(getInfo_getRepoList());
			System.out.println("");
			
			System.out.println("## Delete Software");
			System.out.println("Delete software: fcss");
			delSoftware("fcss");
			System.out.println(getInfo_getRepoList());
			System.out.println("Delete software: fcss, software not installed");
			delSoftware("fcss");
			System.out.println(getInfo_getRepoList());
			System.out.println("");
			
			System.out.println("## Update Clientstatus");
			System.out.println("Update Clientstatus: debian2");
			update_ClientStatus(id, "on");
			System.out.println(getInfo_getClientStatus(id));
			System.out.println("Update Clientstatus: wrong id");
			update_ClientStatus(123, "on");
			System.out.println(getInfo_getClientStatus(123));
			System.out.println("Update Clientstatus: wrong status");
			//update_ClientStatus(id, "some");// Exception correct
			System.out.println(getInfo_getClientStatus(id));
			System.out.println("");
			
			System.out.println("Update Clientstatus: debian2");
			update_ClientStatus(ip, "off");
			System.out.println(getInfo_getClientStatus(id));
			System.out.println("Update Clientstatus: wrong id");
			update_ClientStatus("tester", "on");
			System.out.println("Update Clientstatus: wrong status");
			//update_ClientStatus("debian2", "some");// Exception correct
			System.out.println(getInfo_getClientStatus(id));
			System.out.println("");
			
			System.out.println("## Update Hardwareinfo");
			System.out.println("Update HardwareInfo: debian2");
			update_hwinfo(id, "cputest", "ramtest", "archtest");
			System.out.println(getInfo_hwInfo(id));
			System.out.println("Update HardwareInfo: wrong client");
			update_hwinfo(123, "cputest", "ramtest", "archtest");
			System.out.println("");
			
			System.out.println("## Update Softwareinfo");
			System.out.println("Update SoftwareInfo short: debian2, css1");
			update_swinfo(sid1, id, "css1", "on");
			System.out.println(getInfo_swInfo(id));
			System.out.println("Update SoftwareInfo short: debian2, css1, wrong status");
			//update_swinfo(sid1, id, "css1", "some"); // Exception correct
			System.out.println("Update SoftwareInfo short: wrongclient, css1");
			update_swinfo(sid1, 123, "css1", "on");
			System.out.println("Update SoftwareInfo short: wrongsoft, css1");
			update_swinfo(133, id, "css1", "on");
			System.out.println("Update SoftwareInfo short: debian2, css6");
			update_swinfo(sid1, id, "css6", "on");
			System.out.println("");
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	// delete all tables
	public void deleteDB() throws Exception{
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
		stat.close();
		conn.close();
	}
	// create important tables
	protected void create_DB() throws Exception {
			System.out.println("Start creating Tables ->");
			Class.forName("org.sqlite.JDBC");
			Connection conn = DriverManager.getConnection("jdbc:sqlite:servermanager.db");
			Statement stat = conn.createStatement();
			/* users */
			stat.executeUpdate("create table if not exists users( " +
					"name text primary key not null unique,"+
					"pw text not null,"+
					"rights text not null,"+
					"check (rights = 'admin' or rights = 'read'));");
			/* client */
			stat.executeUpdate("create table if not exists client( " +
					"name text not null unique,"+
					"ip text not null unique,"+
					"clientid integer primary key autoincrement not null unique,"+
					"user text not null default 'root',"+
					"pw text not null default 'swe1234',"+
					"status text not null default 'off',"+
					"check (status = 'busy' or status = 'off' or status = 'on'));");
			/* software */
			stat.executeUpdate("create table if not exists software( " +
					"softid integer primary key autoincrement not null unique,"+
					"name  text not null unique,"+
					"description text,"+
					"file text not null unique,"+
					"ftpip text not null);");
			/* insoftware */
			stat.executeUpdate("create table if not exists insoftware( " +
					"id integer primary key autoincrement not null unique,"+
					"softid integer not null,"+
					"clientid references client(clientid) on delete restrict on update restrict not null,"+
					"user text,"+
					"pw text,"+
					"status text not null default 'install',"+
					"check (status='install' or status = 'on' or status = 'off'));");
			/* hardware */
			stat.executeUpdate("create table if not exists hardware( " +
					"id integer primary key not null unique, " +
					"clientid integer not null ,"+
					"cpu text,"+
					"ram text,"+
					"architecture text);");
			/* messages */
			stat.executeUpdate("create table if not exists messages( " +
					"id integer primary key autoincrement not null unique,"+
					"clientid references client(clientid) on delete restrict on update restrict,"+
					"time text,"+
					"status text not null,"+
					"message text);");
			stat.close();
			conn.close();
			System.out.println("<- All Tables created");
	}

	
	
	//fill the tables with some information
	public void fillDB() throws Exception{
		System.out.println("Start creating Entries ->");
		Class.forName("org.sqlite.JDBC");
		Connection conn = DriverManager.getConnection("jdbc:sqlite:servermanager.db");
		Statement stat = conn.createStatement();
		String clientid = "";
		int softid = getSoftID("css");
		ResultSet rs;
		/* Users */
		stat.executeUpdate("insert into users(name,pw,rights) values ('ptendyra','tendyrapw','admin')");
		
		/* Clients */
		stat.executeUpdate("insert into client(name,ip,user,pw) values ('debian2','192.168.21.22:5550','debian2','debian2')");
		stat.executeUpdate("insert into client(name,ip,user,pw) values ('debian3','192.168.21.23:5550','debian3','debian3')");
		/* Software */
		stat.executeUpdate("insert into software(name,description,file,ftpip) values ('css','Installiert CounterStrike:Source','css.tar.gz','192.168.1.21')");
		
		/* installierte Software*/
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
	
	
	/* ***********************************
	 * users */
	protected String getInfo_Authenticate(String user, String password) throws SQLException, ClassNotFoundException, Exception {
		String result = "";
		Class.forName("org.sqlite.JDBC");
		Connection conn = DriverManager.getConnection("jdbc:sqlite:servermanager.db");
		Statement stat = conn.createStatement();
		ResultSet rs = stat.executeQuery("select rights from users where name = '"+user+"' and pw ='"+password+"';");
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
	
	
	
	/* ***********************************
	 * client */
	protected int getClientID(String name)throws Exception
	{
		int clientid = -1;
		Class.forName("org.sqlite.JDBC");
		Connection conn = DriverManager.getConnection("jdbc:sqlite:servermanager.db");
		Statement stat = conn.createStatement();
		ResultSet rs = stat.executeQuery("select clientid from client where name='"+name+"';");
		while(rs.next())
			clientid = Integer.parseInt(rs.getString("clientid"));
		
		stat.close();
		conn.close();
		return clientid;
	}
	
	protected void delClient(int clientid)throws Exception
	 {
		 Class.forName("org.sqlite.JDBC");
			Connection conn = DriverManager.getConnection("jdbc:sqlite:servermanager.db");
			Statement stat = conn.createStatement();
			stat.executeUpdate("delete from insoftware where clientid='"+clientid+"'");
			stat.executeUpdate("delete from hardware where clientid='"+clientid+"'");
			stat.executeUpdate("delete from client where clientid='"+clientid+"'");
			stat.close();
			conn.close();
	 }

	protected void insertNewClient(String clientName, String clientIP, String user, String password) throws ClassNotFoundException, SQLException{
		Class.forName("org.sqlite.JDBC");
		Connection conn = DriverManager.getConnection("jdbc:sqlite:servermanager.db");
		Statement stat = conn.createStatement();
		int success = -1;
		success = stat.executeUpdate("insert into client(name, ip, user, pw)"+
						                 "values ('"+clientName+"', '"+
									                   clientIP+"', '"+
									                   user+"', '"+
									                   password+"');");
		if(success != 0)
			System.out.println("New Client successfully added");
		else
			System.out.println("Could not add Client");
		
		stat.close();
		conn.close();
	}

	protected String getInfo_getClients() throws Exception {
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
	
	protected void update_swinfo(int clientid, String user, String status)throws Exception
	{
		System.out.println("Updateing SoftwareInfo");
		Class.forName("org.sqlite.JDBC");
		Connection conn = DriverManager.getConnection("jdbc:sqlite:servermanager.db");
		Statement stat = conn.createStatement();
		int success = -1;
		
		ResultSet rs  = stat.executeQuery("select clientid from insoftware where clientid='"+clientid+"' and user='"+user+"';");
		String tmp = "";
		while(rs.next()){tmp += rs.getString("clientid");}
		
		
		if(tmp.length() != 0)
			success = stat.executeUpdate(	"update insoftware set status='"+status+"'where clientid='"+clientid+"' and user='"+user+"' ;");
		if(success == 1)
			System.out.println("Successfully updated SoftwareInfo");
		else
			System.out.println("Problems while updating SoftwareInfo");
			
	}

	protected void update_swinfo(int softid, int clientid, String user, String status)throws Exception
	{
		System.out.println("Updateing SoftwareInfo");
		Class.forName("org.sqlite.JDBC");
		Connection conn = DriverManager.getConnection("jdbc:sqlite:servermanager.db");
		Statement stat = conn.createStatement();
		int success = -1;
		
		ResultSet rs  = stat.executeQuery("select clientid from insoftware where clientid='"+clientid+"' and user='"+user+"';");
		String tmp = "";
		while(rs.next()){tmp += rs.getString("clientid");}
		
		rs = stat.executeQuery("select name from software where softid='"+softid+"';");
		String tmp2 = "";
		while(rs.next()){tmp2 += rs.getString("name");}
		
		rs = stat.executeQuery("select name from client where clientid='"+clientid+"';");
		String tmp3 = "";
		while(rs.next()){tmp3 += rs.getString("name");}
		
		if(tmp.length() != 0 && tmp2.length() != 0)
			success = stat.executeUpdate(	"update insoftware set status='"+status+"'where clientid='"+clientid+"' and user='"+user+"' ;");
		else
			if(tmp2.length() != 0 && tmp3.length() != 0)
				success = stat.executeUpdate(	"insert into insoftware(softid,clientid,user,status) values ('"+softid+"','"+clientid+"','"+user+"','"+status+"');");
		if(success == 1)
			System.out.println("Successfully updated SoftwareInfo");
		else
			System.out.println("Problems while updating SoftwareInfo");
		stat.close();
		conn.close();
	}
	
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
	
	protected void update_ClientStatus(String ip, String status)throws SQLException, ClassNotFoundException, Exception
	{
		System.out.println("Update Clientstatus");
		Class.forName("org.sqlite.JDBC");
		Connection conn = DriverManager.getConnection("jdbc:sqlite:servermanager.db");
		Statement stat = conn.createStatement();
		int clientid = -1;
		ResultSet rs = stat.executeQuery("select clientid from client where ip='"+ip+"';");
		while(rs.next())
			clientid = Integer.parseInt(rs.getString("clientid"));
		if(clientid != -1)
			update_ClientStatus(clientid, status);
		stat.close();
		conn.close();
	}
	
	protected void update_ClientStatus(int clientid, String status)throws SQLException, ClassNotFoundException, Exception
	{
		System.out.println("Update Clientstatus");
		Class.forName("org.sqlite.JDBC");
		Connection conn = DriverManager.getConnection("jdbc:sqlite:servermanager.db");
		Statement stat = conn.createStatement();
	int success = stat.executeUpdate(	"update client set status='"+status+"' where clientid='"+clientid+"';");
	if(success == 1)
		System.out.println("Successfully updated Clientstatus");
	else
		System.out.println("Problems while updating Clientstatus");
	stat.close();
	conn.close();
	}
	
	protected String getInfo_getClientStatus(int clientID) throws SQLException, ClassNotFoundException, Exception {
		String result = "null";
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
	
	protected String getClientIP(int clientID) throws SQLException, ClassNotFoundException, Exception {
		Class.forName("org.sqlite.JDBC");
		Connection conn = DriverManager.getConnection("jdbc:sqlite:servermanager.db");
		Statement stat = conn.createStatement();
		ResultSet rs = stat.executeQuery("select ip from client where clientid='"+clientID+"';");
		String result = "";
		while (rs.next()) {
			result = rs.getString("ip");
		}
		rs.close();
		stat.close();
		conn.close();
		if(result.length() == 0)
			result += "null";
		return result;
	}
	/* ***********************************
	 * software */
	protected int getSoftID(String soft)throws Exception{
		int softid = -1;
		Class.forName("org.sqlite.JDBC");
		Connection conn = DriverManager.getConnection("jdbc:sqlite:servermanager.db");
		Statement stat = conn.createStatement();
		ResultSet rs = stat.executeQuery("select softid from software where name='"+soft+"';");
		while(rs.next())
			softid = Integer.parseInt(rs.getString("softid"));
		stat.close();
		conn.close();
		return softid;
	}
	
	protected void delSoftware(String name)throws Exception
	{
		Class.forName("org.sqlite.JDBC");
		Connection conn = DriverManager.getConnection("jdbc:sqlite:servermanager.db");
		Statement stat = conn.createStatement();
		
		int success = stat.executeUpdate("delete from software where name='"+name+"'");
		if(success == 1)
			System.out.println("Software successfully deleted");
		else
			System.out.println("Could not delet Software");
		stat.close();
		conn.close();
	}
	
	protected void insertSoftware(String name, String file, String ftpip)throws Exception{
		Class.forName("org.sqlite.JDBC");
		Connection conn = DriverManager.getConnection("jdbc:sqlite:servermanager.db");
		Statement stat = conn.createStatement();
		
		int success = -1;
		
			success = stat.executeUpdate("insert into software(name,ftpip,file) values ('"+name+"','"+ftpip+"','"+file+"');");
		if(success == 1)
		System.out.println("Successfully updated Software");
		else
		System.out.println("Problems while updating Software");
		stat.close();
		conn.close();
	}
	
	protected String[] getFTP_IP_FILE(String parameter) throws Exception {
		Class.forName("org.sqlite.JDBC");
		Connection conn = DriverManager.getConnection("jdbc:sqlite:servermanager.db");
		Statement stat = conn.createStatement();
		 ResultSet rs = stat.executeQuery("select ftpip,file from software where name='"+parameter+"';");
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

	protected String[] getFTP_IP_FILE(int parameter) throws SQLException, ClassNotFoundException, Exception {
		Class.forName("org.sqlite.JDBC");
		Connection conn = DriverManager.getConnection("jdbc:sqlite:servermanager.db");
		Statement stat = conn.createStatement();
		ResultSet rs = stat.executeQuery("select ftpip, file from software where softid='"+parameter+"';");
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
	/* ***********************************
	 * insoftware */
	protected void insertInstalledSofware(int softid, int clientid, String user, String status)throws Exception {
		Class.forName("org.sqlite.JDBC");
		Connection conn = DriverManager.getConnection("jdbc:sqlite:servermanager.db");
		Statement stat = conn.createStatement();
		int success = -1;
		ResultSet rs  = stat.executeQuery("select clientid from insoftware where clientid='"+clientid+"' and user='"+user+"';");
		String tmp = "";
		while(rs.next()){tmp += rs.getString("clientid");}
		
		rs = stat.executeQuery("select name from software where softid='"+softid+"'");
		String tmp2 = "";
		while(rs.next())
			tmp2 += rs.getString("name");
		
		rs = stat.executeQuery("select name from client where clientid='"+clientid+"';");
		String tmp3 = "";
		while(rs.next())
			tmp3 += rs.getString("name");
		
		if(tmp.length() == 0 && tmp2.length() != 0 && tmp3.length() != 0)
		{	success = stat.executeUpdate("insert into insoftware(softid,clientid,user,status) values ('"+softid+"','"+clientid+"','"+user+"','"+status+"');");
		}
		if(success == 1)
		System.out.println("Successfully updated SoftwareInfo");
		else
		System.out.println("Problems while updating SoftwareInfo");
		stat.close();
		conn.close();
	}
	
	
	
	
	/* ***********************************
	 * hardware */
	protected void update_hwinfo(int clientid, String cpu, String ram, String architecture)throws SQLException, ClassNotFoundException, Exception
	{
		System.out.println("Updateing HardwareInfo");
		Class.forName("org.sqlite.JDBC");
		Connection conn = DriverManager.getConnection("jdbc:sqlite:servermanager.db");
		Statement stat = conn.createStatement();
		int success = -1;
		
		ResultSet rs  = stat.executeQuery("select clientid from hardware where clientid='"+clientid+"';");
		String tmp = "";
		while(rs.next()){tmp += rs.getString("clientid");};
		
		rs = stat.executeQuery("select name from client where clientid='"+clientid+"'");
		String tmp2 = "";
		while(rs.next()){tmp += rs.getString("name");}
		
		if(tmp.length() != 0)
		success = stat.executeUpdate(	"update hardware set cpu='"+cpu+"',ram='"+ram+"',architecture='"+architecture+"' where clientid='"+clientid+"';");
		else
			if(tmp2.length() != 0)
			success = stat.executeUpdate(	"insert into hardware(cpu,ram,architecture,clientid) values ('"+cpu+"','"+ram+"','"+architecture+"','"+clientid+"');");
		
		if(success == 1)
		System.out.println("Successfully updated HardwareInfo");
		else
		System.out.println("Problems while updating HardwareInfo");
		stat.close();
		conn.close();
	}
	
	protected String getInfo_hwInfo(int clientID) throws SQLException, ClassNotFoundException, Exception {
		String result = "none";
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
	
	
	/* ***********************************
	 * messages */
	
	
	
}
