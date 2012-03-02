package mainpackage;

public class Worker_updatehwinfo extends Worker{

	public Worker_updatehwinfo(Command command, Config conf, Communication com) {
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
		try{
			String[] tmp = base.getClientIP(this._command.getClientID()).split(":");
			ip = tmp[0];
			port = Integer.parseInt(tmp[1]);
		}catch(Exception e){System.out.println("Cannot access Database!");}
		
		Command c = this._command.clone();
		c.setName("hwinfo");
		c.setStatus(100);
		c.setInfo("default");
		this._com.send(c, ip, port);
		
	}
}
