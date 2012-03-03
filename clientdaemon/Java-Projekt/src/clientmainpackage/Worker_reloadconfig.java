package clientmainpackage;

public class Worker_reloadconfig extends Worker{
	
	public Worker_reloadconfig(Command command ,  Config conf, Communication com) {
		super();
		this._conf = conf;
		this._command = command;
		this._com = com;
	}
	
	private void updateConfig()
	{
		this._conf.loadConfig();
		this._conf.getSys();
		this._conf.getSof();
	}
	private void updateCommand(){
		this._command.setStatus(102);
		this._command.setInfo("Config reloaded");
	}
	
	public void run()
	{
		updateConfig();
		updateCommand();
		this._com.send(this._command, this._conf.getIP_send(), this._conf.getPort_send());
	}
	//TODO exception
}
