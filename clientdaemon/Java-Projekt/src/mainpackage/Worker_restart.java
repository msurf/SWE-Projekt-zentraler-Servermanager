package mainpackage;

public class Worker_restart extends Worker{
	
	Worker_restart(Command command, Config conf, Communication com) {
		super();
		this._com = com;
		this._conf = conf;
		this._command = command;
	}
	
	public void run(){
		restartService();
		updateCommand();
		this._com.send(this._command, this._conf.getIP_send(), this._conf.getPort_send());
	}
	
	private void restartService(){
		String service = this._command.getProgram();
		ShellRunner shell = new ShellRunner();
		shell.execute("./restart "+service);
	}
	private void updateCommand(){
		this._command.setStatus(102);
		this._command.setInfo("restarted");
	}

}
