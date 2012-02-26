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
		updateCommand();
		this._com.send(this._command, this._conf.getIP_send(), this._conf.getPort_send());
	}
	
	private void startService(){
		String service = this._command.getProgram();
		ShellRunner shell = new ShellRunner();
		shell.execute("./start "+service);
	}
	private void updateCommand(){
		this._command.setStatus(102);
		this._command.setInfo("started");
	}

}
