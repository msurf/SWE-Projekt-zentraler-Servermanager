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
	
	protected String getInfo_Authenticate(String user, String password) throws SQLException, ClassNotFoundException, Exception {
		String ergebnis = "";
		Class.forName("org.sqlite.JDBC");
		Connection conn = DriverManager.getConnection("jdbc:sqlite:servermanager.db");
		Statement stat = conn.createStatement();
		ResultSet rs = stat.executeQuery("select * from benutzer where name = "+user+" and pwd ="+password+";");
		while(rs.next()) {
			ergebnis = ergebnis+rs.getString(1)+rs.getString(2);
		}
		if(ergebnis.equals("")) {
				return "incorrect: none";
			}
			else {
				return "correct: admin";
			}
		}
	
	protected String getInfo_getClients() throws SQLException, ClassNotFoundException, Exception {
		String ergebnis = "";
		Class.forName("org.sqlite.JDBC");
		Connection conn = DriverManager.getConnection("jdbc:sqlite:servermanager.db");
		Statement stat = conn.createStatement();
		ResultSet rs = stat.executeQuery("select name, client_id from client;");
		while (rs.next()) {
			ergebnis = ""+rs.getString(1)+":"+rs.getInt(2)+"#"; 
		}
		return ergebnis;
	}
	
	protected String getInfo_getClientStatus(String clientName, int clientID) throws SQLException, ClassNotFoundException, Exception {
		String ergebnis = "";
		Class.forName("org.sqlite.JDBC");
		Connection conn = DriverManager.getConnection("jdbc:sqlite:servermanager.db");
		Statement stat = conn.createStatement();
		ResultSet rs = stat.executeQuery("select status from client where name = "+clientName+" and client_ID = "+clientID+";");
		while(rs.next()) {
			ergebnis = ""+rs.getString(1);
		}
		return ergebnis;
	}
}
