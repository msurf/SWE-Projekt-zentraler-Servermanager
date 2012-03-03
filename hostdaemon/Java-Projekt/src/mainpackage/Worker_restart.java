package mainpackage;


public class Worker_restart extends Worker{
	
	public Worker_restart(Command command, Config conf, Communication com) {
		super();
		this._command = command;
		this._conf = conf;
		this._com = com;
	}
	public void run(){
		buildCommand();
	}
	private void buildCommand(){
		Database base = new Database();
		String ip = "";
		int port = 5550;
		// the user specifies the program to stop/start/restart and is given by the page
		Command c = this._command.clone();
		c.setName("hwinfo");
		c.setStatus(100);
		c.setInfo("default");
		try{
			String[] tmp = base.getClientIP(this._command.getClientID()).split(":");
			ip = tmp[0];
			port = Integer.parseInt(tmp[1]);
			this._com.send(c, ip, port);
		}catch(Exception e){
			this._command.setStatus(200);
			System.out.println("Cannot access Database!");}
	}
}
