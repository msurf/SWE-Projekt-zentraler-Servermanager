package mainpackage;

public class Worker_HardwareInfo extends Worker{
	
	public Worker_HardwareInfo(Communication com, Config conf, Command command) {
		super();
		this._com = com;
		this._conf = conf;
		this._command = command;
	}
	
	
	public void run(){
		sendHardwareInfo();
		//printHardwareInfo();
		//log("request for Hardwareinfo");
	}

	private void sendHardwareInfo(){
		Command cnew = new Command();
		cnew.setDirection("c2h");
		cnew.setName("hwinfo-back");
		cnew.setStatus(105);
		cnew.setFrom(this._conf.getIP_own());
		cnew.setInfo(this._conf.hwinfo());
		this._com.send(cnew, this._conf.getIP_send(), this._conf.getPort_send());
	}
	
	private void printHardwareInfo(){
		System.out.println("Architecture: "+this._conf.getArchitecture());
		System.out.println("Cpu: "+ this._conf.getCpu());
		System.out.println("Ram: "+ this._conf.getRam());
	}
}
