package mainpackage;

import java.util.ArrayList;


public class SystemProperties{

	
	
	private String _cpu = "unknown";
	private String _ram = "unknown";
	private String _architecture = "unknown";
	private boolean _done = false;
	
	public boolean done(){return this._done;}
	public String getCPU(){return this._cpu;}
	public String getRam(){return this._ram;}
	public String getArchitecture(){return this._architecture;}
	
	public int collect() throws Malfunction{
		
		ShellRunner shell = new ShellRunner();
		shell.execute("cat /proc/cpuinfo | grep -e 'model name'"); 
		shell.execute("cat /proc/meminfo | grep -e 'MemTotal'");
		shell.execute("dpkg --print-architecture");
		ArrayList<String> out = shell.getOutput();
		if(out.size() >= 3)
		{
			int cores = 0;
			for(String line : out)
			{
				if(line.contains("model name"))
				{
					
					try{
						cores++;
						this._cpu = line.split("model name")[1].replace(":", "").replaceAll("\\s+", " ").trim() + "Cores: "+cores;
						this._done = true;
					}//try
					catch(Exception e)
					{
						this._cpu = "unknown";
					}//catch
				}
				if(line.contains("MemTotal"))
				{
					try
					{
						this._ram = line.split("MemTotal:")[1].replace("  ", " ").trim();
					}//try
					catch(Exception e)
					{
						this._ram = "unknown";
						this._done = false;
					}//catch
				}//if
				if(line.contains("i386") || line.contains("amd64"))
				{
					try{
						this._architecture = line.split("out:")[1].replace("  ", " ").trim();
					}catch(Exception e)
					{
						this._architecture = "default";
						this._done = false;
					}
				}//if
			}//for
		}//if
		else
		{
			if(out.size() > 2)
				return -2; // there are too much lines, may be no correct info
			else
				return -3; // there are too less lines, one attribute will not be set
		}//else
		return 0;
	}//collect()
}//class