package mainpackage;

public class Worker_stop extends Worker{
	
	Worker_stop(Command command, Config conf, Communication com) {
		super();
		this._com = com;
		this._conf = conf;
		this._command = command;
	}
	
	public void run(){
		stopService();
		updateCommand();
		this._com.send(this._command, this._conf.getIP_send(), this._conf.getPort_send());
	}
	
	private void stopService(){
		String service = this._command.getProgram();
		ShellRunner shell = new ShellRunner();
		shell.execute("./stop "+service);
	}
	private void updateCommand(){
		this._command.setStatus(102);
		this._command.setInfo("stopped");
	}

}
