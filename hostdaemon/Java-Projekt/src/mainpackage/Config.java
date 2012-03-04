package mainpackage;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Config {

	
	
	/*Config*/
	private String _config = System.getProperty("user.dir")+"/swe.conf";
	
	/* syntax of the conifg-file
	 ## Comment
	 own_ip= 127.0.0.1
	 own_port= 5550
	 send_ip=
	 send_port=
	 architectur=
	 cpu= 
	 ram=
	 logpath= 
	 */
	
		
	
	/*System*/
	private String _logpath = System.getProperty("user.dir");
	private String _CPU = "unknown"; // lshw -short | grep -e 'processor.*@' -> dann noch bei processor spliten
	private String _RAM = "unknown";
	private String _architectur = "unknown";
	
	private void setLogpath(String s){this._logpath = s;}
	private void setCPU(String s){this._CPU = s;}
	private void setRAM(String s){this._RAM = s;}
	private void setArchitecture(String s){this._architectur = s;}
	
	
	public synchronized String getLogpath(){return this._logpath;}
	public synchronized String getCpu(){return this._CPU;}
	public synchronized String getRam(){return this._RAM;}
	public synchronized String getArchitecture(){return this._architectur;}
	
	/*listening*/
	private String _IP_own = "localhost";
	private int _Port_own = 5550;
	
	private void setIP_own(String s){this._IP_own = s;}
	private void setPort_own(int i){this._Port_own = i;}
	
	public synchronized String getIP_own(){return this._IP_own;}
	public synchronized int getPort_own(){return this._Port_own;}
	
	
	/*sending*/
	private String _defSendIP = "localhost";
	private int _defSendPort = 5550;
	
	private void setDefaultSendingIP(String s){this._defSendIP = s;}
	private void setDefaultSendingPort(int i){this._defSendPort = i;}
	
	public synchronized String getIP_send(){return this._defSendIP;}
	public synchronized int getPort_send(){return this._defSendPort;}
	
	Config(){
		loadConfig();
	}//constructor
	
	public synchronized void loadConfig(){
		System.out.println("Loading Config");
		File test = new File(this._config);
		if(test.exists())
		{
			System.out.println("from File");
			try {
				Scanner scan = new Scanner(test);
				String line = "";
				while(scan.hasNextLine())
				{
					line = scan.nextLine();
					if(!line.contains("##"))
					{
						String[] tmp = line.split("=");
						if(tmp.length == 2)
						{
						if(tmp[0].trim().equals("own_port") )
							setPort_own(Integer.parseInt(tmp[1].trim()));
						
						if(tmp[0].trim().equals("own_ip"))
							setIP_own(tmp[1].trim());
						
						if(tmp[0].trim().equals("send_ip"))
							setDefaultSendingIP(tmp[1].trim());
						
						if(tmp[0].trim().equals("send_port"))
							setDefaultSendingPort(Integer.parseInt(tmp[1].trim()));
						
						if(tmp[0].trim().equals("cpu"))
							setCPU(tmp[1].trim());
						
						if(tmp[0].trim().equals("ram"))
							setRAM(tmp[1].trim());
						
						if(tmp[0].trim().equals("logpath"))
							setLogpath(tmp[1].trim());
						
						if(tmp[0].trim().equals("architecture"))
							setArchitecture(tmp[1].trim());
						}
						
					}
				}
			} catch (FileNotFoundException e) {System.out.println("Cannot find Config-File");}
		}
		if(!checkSys())
		{
			System.out.println("asking System");
			getSys();
		}
		System.out.println("Complete");
	}
	
	public synchronized void writeConfig(){
		System.out.println("Writing config...");
		ShellRunner shell = new ShellRunner();
		shell.execute("echo 'own_ip="+this._IP_own+"'>"+this._config);//old file will be overwritten
		shell.execute("echo 'own_port="+this._Port_own+"'>>"+this._config);
		shell.execute("echo 'send_ip="+this._defSendIP+"'>>"+this._config);
		shell.execute("echo 'send_port="+this._defSendPort+"'>>"+this._config);
		shell.execute("echo 'architecture="+this._architectur+"'>>"+this._config);
		shell.execute("echo 'cpu="+this._CPU+"'>>"+this._config);
		shell.execute("echo 'ram="+this._RAM+"'>>"+this._config);
		shell.execute("echo 'logpath="+this._logpath+"'>>"+this._config);
		System.out.println("Writing done!");
	}
	
	
	private boolean checkSys(){
		if(this._CPU.equals("unknown") || this._RAM.equals("unknown") || this._architectur.equals("unknown"))
			return false;
		return true;
	}
	
	protected void getSys(){
		System.out.println("Checking for Hardware");
		SystemProperties props = new SystemProperties();
		//while(!props.done())
		{
			
			try {
				props.collect();
			} catch (Malfunction e) {System.out.println("Cannot collect HardwareInfos");}
		}
		this._CPU = props.getCPU();
		this._RAM = props.getRam();
		this._architectur = props.getArchitecture();
		System.out.println("Hardware updated");
	}
	
	public synchronized String hwinfo(){
		String tmp = "architecture:"+ this._architectur+"#cpu:"+ this._CPU +"#ram:"+ this._RAM;
		return tmp;
	}
	
	

}//class
