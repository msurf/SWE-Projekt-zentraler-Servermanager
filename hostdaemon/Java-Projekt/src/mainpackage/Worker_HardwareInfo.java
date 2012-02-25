package mainpackage;

public class Worker_HardwareInfo extends Worker{
	
	public Worker_HardwareInfo(Communication com, Config conf) {
		super();
		this._com = com;
		this._conf = conf;
	}
	
	
	public void run(){
		printHardwareInfo();
		//log("request for Hardwareinfo");
	}

	private void printHardwareInfo(){
		System.out.println("Architecture: "+this._conf.getArchitecture());
		System.out.println("Cpu: "+ this._conf.getCpu());
		System.out.println("Ram: "+ this._conf.getRam());
	}
}
