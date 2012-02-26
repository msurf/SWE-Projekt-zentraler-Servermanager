package deamonSkeleton;

import java.util.ArrayList;

public class SystemSoftware {
	
	private ArrayList<String> _software;
	
	
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
	}
	public ArrayList<String> getSoftware(){return this._software;}
	
}
