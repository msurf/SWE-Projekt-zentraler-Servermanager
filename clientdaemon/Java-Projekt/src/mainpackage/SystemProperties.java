package mainpackage;

import java.util.ArrayList;

public class SystemProperties{

	//consider that lshw has to be installed
	
	
	private String _cpu = "unknown";
	private String _ram = "unknown";
	private String _architecture = "unknown";
	private boolean _done = false;
	
	public boolean done(){return this._done;}
	public String getCPU(){return this._cpu;}
	public String getRam(){return this._ram;}
	public String getArchitecture(){return this._architecture;}
	
	public int collect(){
		
		ShellRunner shell = new ShellRunner();
		shell.execute("lshw -short | grep -e 'processor.*@'"); 
		shell.execute("lshw -short | grep -e 'System memory'");
		shell.execute("lscpu | grep -e 'Architecture'");
		ArrayList<String> out = shell.getOutput();
		if(out.size() == 3)
		{
			for(String line : out)
			{
				if(line.contains("processor"))
				{
					try{
						this._cpu = line.split("processor")[1].trim();
						this._done = true;
					}//try
					catch(Exception e)
					{
						this._cpu = "unknown";
					}//catch
				}
				if(line.contains("System memory"))
				{
					try
					{
						this._ram = line.split("memory")[1].replaceAll("System", "").trim();
					}//try
					catch(Exception e)
					{
						this._ram = "unknown";
						this._done = false;
					}//catch
				}//if
				if(line.contains("Architecture"))
				{
					try
					{
						this._architecture = line.split("Architecture:")[1].trim();
					}//try
					catch(Exception e)
					{
						this._architecture = "unknown";
						this._done = false;
					}//catch
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
