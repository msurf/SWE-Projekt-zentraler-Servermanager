package mainpackage;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class Config {

	
	
	/*Config*/
	private String _config = System.getProperty("user.dir")+"/swe.conf";
	
	/* 
	 * syntax of the conifg-file
	 * ## Comment
	 * own_ip= 127.0.0.1
	 * own_port= 5550
	 * send_ip=
	 * send_port=
	 * architectur=
	 * cpu= 
	 * ram=
	 * logpath= 
	 * software=
	 * software=
	 * ...
	 */
	
	/*Software*/
	private ArrayList<String> _software = new ArrayList<String>();
	private ArrayList<String> _softwareStatus = new ArrayList<String>();
	private int _busy = 0;
	
	
	
	private void addSoftware(String software){this._software.add(software);}
	private void removeSoftware(String software){
		if(this._software.contains(software))
			this._software.remove(software);
	}
	public ArrayList<String> getSoftware(){return this._software;}
	
	public ArrayList<String> getSoftStatus(){return this._softwareStatus;}
	
	public synchronized void addBusy(){this._busy++;}
	public synchronized void remBusy(){this._busy--;}
	public String isBusy(){ return ((this._busy <= 0) ? "on":("busy:"+this._busy));}
	
	/*System*/
	private String _logpath = System.getProperty("user.dir");
	private String _CPU = "unknown"; // lshw -short | grep -e 'processor.*@' -> dann noch bei processor spliten
	private String _RAM = "unknown";
	private String _architectur = "unknown";
	
	private void setLogpath(String s){this._logpath = s;}
	private void setCPU(String s){this._CPU = s;}
	private void setRAM(String s){this._RAM = s;}
	private void setArchitecture(String s){this._architectur = s;}
	
	
	public String getLogpath(){return this._logpath;}
	public String getCpu(){return this._CPU;}
	public String getRam(){return this._RAM;}
	public String getArchitecture(){return this._architectur;}
	
	/*listening*/
	private String _IP_own = "localhost";
	private int _Port_own = 5550;
	
	private void setIP_own(String s){this._IP_own = s;}
	private void setPort_own(int i){this._Port_own = i;}
	
	public String getIP_own(){return this._IP_own;}
	public int getPort_own(){return this._Port_own;}
	
	
	/*sending*/
	private String _defSendIP = "localhost";
	private int _defSendPort = 5550;
	
	private void setDefaultSendingIP(String s){this._defSendIP = s;}
	private void setDefaultSendingPort(int i){this._defSendPort = i;}
	
	public String getIP_send(){return this._defSendIP;}
	public int getPort_send(){return this._defSendPort;}
	
	/*constructor*/
	Config(){
		this._busy = 0;
		loadConfig();
	}//constructor
	
	/*getting values*/
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
						if(tmp[0].trim().equals("software"))
							addSoftware(tmp[1].trim());
					}
				}
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		getSof();
		if(!checkSys())
		{
			System.out.println("asking System");
			getSys();
		}
		System.out.println("Complete");
	}
	
	private boolean checkSys(){
		if(this._CPU.equals("unknown") || this._RAM.equals("unknown") || this._architectur.equals("unknown"))
			return false;
		return true;
	}
	
	public synchronized void getSys(){
		System.out.println("Checking for Hardware");
		SystemProperties props = new SystemProperties();
		//while(!props.done())
		{
			props.collect();
		}
		this._CPU = props.getCPU();
		this._RAM = props.getRam();
		this._architectur = props.getArchitecture();
		System.out.println("Hardware updated");
	}
	public synchronized void getSof(){
		System.out.println("Checking for Software");
		SystemSoftware soft = new SystemSoftware();
		ArrayList<String> tmp = soft.getSoftware();
		for(String i : tmp)
			if(!this._software.contains(i))
				addSoftware(i);
		for(String i : this._software)
			if(!tmp.contains(i))
				removeSoftware(i);
		
		this._softwareStatus = soft.getSoftStatus();
		System.out.println("Software updated");
	}
	
	public synchronized void update(){
		
	}
	
	/*safe config*/
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
		for(String i : this._software)
			shell.execute("echo 'software="+i+"'>>"+this._config);
		System.out.println("Writing done!");
	}
	/*output*/
	public String hwinfo(){
		String tmp = "architecture:"+ this._architectur+"#cpu:"+ this._CPU +"#ram:"+ this._RAM;
		return tmp;
	}
	public String swinfo(){
		String tmp = "";
		for (String i : this._softwareStatus)
			tmp += "#" +i;
		if(tmp.length() > 0)
			tmp = tmp.substring(1);
		return tmp;
	}
	

}//class
