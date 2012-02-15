import java.sql.*;

public class add {
	
	public static void main(String[] args) throws ClassNotFoundException, SQLException {
		Class.forName("org.sqlite.JDBC");
		Connection conn = DriverManager.getConnection("jdbc:sqlite:servermanager.db");
		Statement stat = conn.createStatement();
		stat.executeUpdate("create table benutzer( name text not null unique,"+
									  										 "pwd text ot null,"+
									  										 "rights text not null,"+
									  										 "primary key (name),"+
									  										 "check (rights='admin' or 'read'));");
		stat.executeUpdate("create table client( name text not null unique,"+
																		 "IP integer not null unique,"+
																		 "client_ID integer not null unique,"+
																		 "user text not null default 'root',"+
																		 "pw text not null default 'swe1234',"+
																		 "primary key (IP));");
		stat.executeUpdate("create table software( software_ID integer not null unique,"+
																			  "beschreibung text,"+
																			  "FTP_Pfad text not null,"+
																			  "primary key (software_ID));");
		stat.executeUpdate("create table installierte_software(ID integer not null unique,"+
										 														 "software_ID references software(software_ID) on delete restrict on update restrict,"+
										 														 "client_ID references client(client_ID) on delete restrict on update restrict,"+
										 														 "software_benutzer text,"+
										 														 "passwort text,"+
										 														 "primary key (ID));");
		ResultSet rs = stat.executeQuery("select name from sqlite_master where type = 'table';");
		while (rs.next()) {
			System.out.println(rs.getString(1));
		}
		rs.close();
		stat.close();
		conn.close();
	}

}
