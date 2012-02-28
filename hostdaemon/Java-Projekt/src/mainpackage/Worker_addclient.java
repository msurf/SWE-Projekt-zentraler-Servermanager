package mainpackage;

import java.sql.SQLException;

public class Worker_addclient extends Worker{
	
	public Worker_addclient(Command command, Config conf, Communication com) {
		super();
		this._command = command;
		this._conf = conf;
		this._com = com;
	}
	public void run(){
		addClient();
		sendResponse();
	}
	
	
	public void addClient(){
		Database data = new Database();
		try {
			data.insertNewClient(this._command.getClient(), this._command.getURL(), this._command.getUser(), this._command.getPassword());
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}
	
	private void sendResponse() {
		Command cnew = new Command();
		cnew.setDirection("h2w");
		cnew.setName("addClient-back");
		cnew.setStatus(101);
		cnew.setFrom(this._command.getURL());
		cnew.setInfo("received");
		this._com.send(cnew, this._conf.getIP_send(), this._conf.getPort_send());
		
	}

}
