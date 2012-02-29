package mainpackage;

import java.sql.SQLException;

public class Worker_start extends Worker{

	public Worker_start(Command command, Config conf, Communication com) {
		super();
		this._command = command;
		this._conf = conf;
		this._com = com;
	}
	public void run(){
		sendToClient();
	}
	
	public void sendToClient(){
		Database data = new Database();
		String url="";
		try {
			url = data.getClientIP(this._command.getClientID());
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String ip = url.split(":")[0];
		
		int port = Integer.parseInt(url.split(":")[1]);
	
		this._com.send(this._command, ip, port);
	}
}
