package mainpackage;

public class Worker_start extends Worker{
	
	Worker_start(Command command, Config conf, Communication com) {
		super();
		this._com = com;
		this._conf = conf;
		this._command = command;
	}
	
	public void run(){
		startService();
	}
	
	private void startService(){
		String service = this._command.getProgram();
		ShellRunner shell = new ShellRunner();
		try{
		shell.execute("./start "+service);
		updateCommand();
		this._com.send(this._command, this._conf.getIP_send(), this._conf.getPort_send());
		}catch(Malfunction m)
		{
			this._command.setStatus(200);
			this._command.setInfo("Could not start Service: " + this._command.getUser());
			this._com.send(this._command, this._conf.getIP_send(), this._conf.getPort_send());
		}
	}
	private void updateCommand(){
		this._command.setStatus(102);
		this._command.setInfo("started");
	}

}
