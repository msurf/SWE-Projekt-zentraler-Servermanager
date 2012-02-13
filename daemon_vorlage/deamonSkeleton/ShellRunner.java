package deamonSkeleton;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class ShellRunner {
	
	private ArrayList<String> _input;
	private ArrayList<String> _output;
	
	ShellRunner(){
		this._input = new ArrayList<String>();
		this._output = new ArrayList<String>();
	}//constructor
	
	public int execute(String cmd){
		int result = 1;
				this._input.add("Time: " + System.currentTimeMillis() + " cmd: " + cmd);
		try {
			ProcessBuilder pb = new ProcessBuilder("bash", "-c", cmd);
			Process p = pb.start();
			result = p.waitFor();
			BufferedReader in = new BufferedReader(new InputStreamReader(p.getInputStream()));
			String wait = in.readLine();
			while(wait != null && !wait.equals("null"))
			{
				this._output.add("Time: " + System.currentTimeMillis() + " out: " + wait);
				wait = in.readLine();
			}//While
		}//try
		catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}//catch
		
		return result;
	}
	
	public int execute(String cmd, String[] parameters){
		
		String params = " ";
		for(String i : parameters)
			params += " "+ i; 
				
			int result = execute(cmd + params);
		return result;
	}//excecute()
	
	public ArrayList<String> getOutput(){
		return this._output;
	}//getOutput()
	
	public ArrayList<String> getInput(){
		return this._input;
	}//getInput()
	

}
