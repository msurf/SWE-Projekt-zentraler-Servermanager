package mainpackage;

import java.sql.SQLException;

public class Worker_install extends Worker{

	public Worker_install(Command command, Config conf, Communication com) {
		super();
		this._command = command;
		this._conf = conf;
		this._com = com;
	}
	public void run(){
		checkStatus();
	}
	
	private void checkStatus() {
		int status = this._command.getStatus();
		switch (status) {
		case 100:
			sendToClient();
			break;
		case 102:
			insertIntoDB();
			break;
		case 103:
			insertIntoDB();
			break;
		}

	}
	private void insertIntoDB() {
		Database db = new Database();
		db.insertInstalledSofware(this._command.getFTP_File(), this._command.getFTP_IP());
		
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
		try {
			this._command.setFTP_IP(data.getFTP_IP_FILE(Integer.parseInt(this._command.getParameter()))[0]);
			this._command.setFTP_File(data.getFTP_IP_FILE(Integer.parseInt(this._command.getParameter()))[1]);
		} catch (NumberFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
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
		int port = Integer.parseInt(url.split(":")[1]);
		this._com.send(this._command, ip, port);
	}
}
