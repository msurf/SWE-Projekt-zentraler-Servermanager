package clientmainpackage;

public class Worker_stop extends Worker{
	
	Worker_stop(Command command, Config conf, Communication com) {
		super();
		this._com = com;
		this._conf = conf;
		this._command = command;
	}
	
	public void run(){
		stopService();
	}
	
	private void stopService(){
		String service = this._command.getProgram();
		ShellRunner shell = new ShellRunner();
		try{
		shell.execute("./stop "+service);
		updateCommand();
		this._com.send(this._command, this._conf.getIP_send(), this._conf.getPort_send());
		}catch(Malfunction m)
		{
			this._command.setStatus(200);
			this._command.setInfo("Could not stop Service: "+ this._command.getUser());
			this._com.send(this._command, this._conf.getIP_send(), this._conf.getPort_send());
		}
	}
	private void updateCommand(){
		this._command.setStatus(102);
		this._command.setInfo("stopped");
	}

}
