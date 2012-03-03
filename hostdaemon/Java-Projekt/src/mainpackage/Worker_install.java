package mainpackage;


public class Worker_install extends Worker{

	public Worker_install(Command command, Config conf, Communication com) {
		super();
		this._command = command;
		this._conf = conf;
		this._com = com;
	}
	public void run(){
		decide();
	}
	private void decide(){
		if(this._command.getStatus() == 102 || this._command.getStatus() == 103)
			workOnData();
		else
			buildCommand();
	}
	private void workOnData(){
		Database base = new Database();
		try{
			int softid = base.getSoftID(this._command.getProgram());
			int cid = this._command.getClientID();
			String user =  this._command.getUser();
			String status = "off";
			base.insertInstalledSofware(softid, cid, user, status);
			this._command.setStatus(102);
		}catch(Exception e){
			this._command.setStatus(200);
			System.out.println("Cannot write SoftwareInfo from: "+this._command.getClient());}
	}
	
	
	private void buildCommand(){
		Database base = new Database();
		String ip = "";
		int port = 5550;
		String file = "";
		String ftpip = "";
		Command c = this._command.clone();
		c.setName("install");
		c.setFTP_File(file);
		c.setFTP_IP(ftpip);
		c.setStatus(100);
		c.setInfo("default");
		try{
			String[] tmp = base.getClientIP(this._command.getClientID()).split(":");
			ip = tmp[0];
			port = Integer.parseInt(tmp[1]);
			base.update_ClientStatus(this._command.getClientID(), "busy");
			
			 tmp = base.getFTP_IP_FILE(this._command.getProgram());
			 ftpip = tmp[0];
			 file = tmp[1];
			 this._com.send(c, ip, port);
		}catch(Exception e){
			this._command.setStatus(200);
			System.out.println("Cannot access Database!");}
		
	}
}
