package clientmainpackage;

import java.util.ArrayList;

public class Worker_install extends Worker{

	Worker_install(Command command, Config conf, Communication com) {
		super();
		this._com = com;
		this._conf = conf;
		this._command = command;
	}
	
	public void run(){
		install();
		this._com.send(this._command, this._conf.getIP_send(), this._conf.getPort_send());
	}
	
	private String getDirName(){
		
		String dirname = "none";
		String in = "";
		in = this._command.getProgram();
		ArrayList<String> soft = new ArrayList<String>();
		
		this._conf.getSof();
		soft = this._conf.getSoftware();
		
		for(int i = 1; i <= 10; i++) // 10 times the same service on one machine
		{
			if(!soft.contains(in+i))
			{
				dirname = in+i;
				break;
			}
		}
		return dirname;
	}
	private void install(){
			this._conf.addBusy();
		String service = getDirName();
		if(!service.equals("none"))
		{
			ShellRunner shell = new ShellRunner();
			try{
			//getting package
			String ip = this._command.getFTP_IP();
			String file = this._command.getFTP_File();
			shell.execute("./ftpget "+ip+" "+"sweftp sweftp1234 "+file);
			//installing package
			String pack = this._command.getProgram()+"tar.gz";
			shell.execute("./installService "+service+" " + pack);
			
			this._command.setStatus(102);
			this._command.setUser(service);
			this._command.setInfo("installed");
			}catch(Malfunction m)
			{
				this._command.setStatus(200);
				this._command.setInfo("Cannot install Software! "+ this._command.getFTP_File());
				this._com.send(this._command, this._conf.getIP_send(), this._conf.getPort_send());
			}
			
		}else
		{
			this._command.setStatus(200);
			this._command.setInfo("Couldn't install Service.");
		}
			this._conf.remBusy();
	}
}
