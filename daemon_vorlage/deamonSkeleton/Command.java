package deamonSkeleton;

import java.util.ArrayList;
import java.util.regex.Pattern;

public class Command {

	private String _name;
	private boolean _name_set = false;
	private String _from;
	private boolean _from_set = false;
	private ArrayList<String> _arguments;
	
	public boolean setName(String name){
		if(this._name_set)
			return false; //name was allready set
		
		this._name = name;
		this._name_set = true;
		return true;
	}
	
	public boolean setFrom(String from){
		if(this._from_set)
			return false;//from was allready set
		
		this._from = from;
		this._from_set = true;
		return true;
	}
	
	public boolean addArgument(String arg){
		
		boolean tmp = this._arguments.add(arg);
		return tmp;
	}
	
	public String getName(){
		return this._name;
	}
	public String getFrom(){
		return this._from;
	}
	public ArrayList<String> getArguments(){
		return this._arguments;
	}
	
	Command(){
		this._arguments = new ArrayList<String>();
		this._name = "none";
		this._from = "none";
	}
	
	public void splitXML(String s){
		String[] tmp = s.split(";");
		searchArgumentsAdd(tmp);
		searchFromSet(tmp);
		searchNameSet(tmp);
	}
	
	private boolean searchNameSet(String[] s){
		for(String i : s)
		{
			 Pattern p = Pattern.compile("name=.*");
			 boolean test = p.matcher(i.toLowerCase().trim()).matches();
			if(test)
			{
				String[] tmp = i.split("=");
				if(tmp.length <= 1 )
					return false;
				
				setName(tmp[1].toLowerCase().trim());
				return true;
			}
		}
		return false;
	}
	private boolean searchFromSet(String[] s){
		for(String i : s)
		{
			 Pattern p = Pattern.compile("from=.*");
			 boolean test = p.matcher(i.toLowerCase().trim()).matches();
			if(test)
			{
				String[] tmp = i.split("=");
				if(tmp.length <= 1 )
					return false;
				
				setFrom(tmp[1].toLowerCase().trim());
				return true;
			}
		}
		return false;
	}
	private boolean searchArgumentsAdd(String[] s){
		boolean done = false;
		for(String i : s)
		{
			 Pattern p = Pattern.compile("argument=.*");
			 boolean test = p.matcher(i).matches();
			if(test)
			{
				String[] tmp = i.split("=");
				if(tmp.length <= 1 )
					return false;
				
				for(int c = 1; c < tmp.length; c++)
				{	
					String[] tmp2 = tmp[c].trim().split(" ");
					for(String s2 : tmp2)
					addArgument(s2.trim().toLowerCase());
				}
				done = true;
			}
		}
		return done;
	}
	
}
