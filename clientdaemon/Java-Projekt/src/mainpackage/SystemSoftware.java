package mainpackage;

import java.util.ArrayList;

public class SystemSoftware {
	
	private ArrayList<String> _software;
	private ArrayList<String> _softstatus;
	
	
	SystemSoftware(){
		this._software = new ArrayList<String>();
		searchSoftware();
	}
	public void searchSoftware(){
		ShellRunner shell = new ShellRunner();
		shell.executeScript("software");
		ArrayList<String> out = shell.getOutput();
		for(String i : out)
			try{
				this._software.add(i.split("out:")[1].trim());
			}catch(Exception e)
			{
				System.out.println("Fehler");
			}
		for(String i : _software)
		{
			shell.execute("./status "+i);
			ArrayList<String> tmp = shell.getOutput();
			String stat = "off";
			for(String j : tmp)
				stat = j.split("out:")[1].trim();
				
			this._softstatus.add(i+":"+stat);
		
		}
	}
	public ArrayList<String> getSoftware(){return this._software;}
	public ArrayList<String> getSoftStatus(){return this._softstatus;}
}
