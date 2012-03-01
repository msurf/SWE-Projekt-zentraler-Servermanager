package mainpackage;

import java.util.Scanner;


public class Command {

	private String 	_name		= "default";
	private String 	_from		= "default";
	private String 	_ftp_ip		= "default";
	private String	_ftp_file	= "default";
	private String 	_url 		= "default";
	private String 	_query		= "default";
	private String 	_user		= "default";
	private String	_password	= "default";
	private String 	_parameter	= "default"; //all paramters are separated by a semicolon(;)
	private String 	_direction	= "default"; // w2h , h2c , c2h , self
	private String 	_time		= "default";
	private int 	_id			= -1;
	private String 	_client		= "default";
	private int 	_client_id	= -1;
	private int		_status		= -1;
	private String 	_info		= "default";
	private String 	_program	= "default";
	
	public void setName(String name){this._name = name;}
	public void setFrom(String from){this._from = from;}
	public void setFTP_IP(String ftp_ip){this._ftp_ip = ftp_ip;}
	public void setFTP_File(String file){this._ftp_file = file;}
	public void setURL(String url){this._url = url;}
	public void setQuery(String query){this._query = query;}
	public void setUser(String user){this._user = user;}
	public void setPassword(String password){this._password = password;}
	public void setParameter(String params){this._parameter = params;}
	public void setDirection(String direction){this._direction = direction;}
	public void setTime(String time){this._time = time;}
	public void setID(int id){this._id = id;}
	public void setClient(String client){this._client = client;}
	public void setClientID(int id){this._client_id = id;}
	public void setInfo(String info){this._info = info;}
	public void setStatus(int status){this._status = status;}
	public void setProgram(String program){this._program = program;}
	
	public String 	getName(){return this._name;}
	public String 	getFrom(){return this._from;}
	public String 	getFTP_IP(){return this._ftp_ip;}
	public String	getFTP_File(){return this._ftp_file;}
	public String 	getURL(){return this._url;}
	public String 	getQuery(){return this._query;}
	public String 	getUser(){return this._user;}
	public String	getPassword(){return this._password;}
	public String 	getParameter(){return this._parameter;}
	public String 	getDirection(){return this._direction;}
	public String 	getTime(){return this._time;}
	public int 		getID(){return this._id;}
	public String 	getClient(){return this._client;}
	public int		getClientID(){return this._client_id;}
	public String	getInfo(){return this._info;}
	public int 		getStatus(){return this._status;}
	public String 	getProgram(){return this._program;}
	
	
	public Command clone(){
		Command c = new Command();
		c.setName(_name);
		c.setFrom(_from);
		c.setFTP_IP(_ftp_ip);
		c.setFTP_File(_ftp_file);
		c.setURL(_url);
		c.setQuery(_query);
		c.setUser(_user);
		c.setPassword(_password);
		c.setParameter(_parameter);
		c.setDirection(_direction);
		c.setTime(_time);
		c.setID(_id);
		c.setClient(_client);
		c.setClientID(_client_id);
		c.setInfo(_info);
		c.setStatus(_status);
		c.setProgram(_program);
		
		
		return c;
	}
	public Command buildCommand(){
		Command c = new Command();
		c.setName(getText("name="));
		c.setFTP_IP(getText("ftp_ip="));
		c.setFTP_File(getText("ftp_file"));
		c.setURL(getText("url="));
		c.setQuery(getText("query="));
		c.setUser(getText("user="));
		c.setPassword(getText("password="));
		c.setParameter(getText("parameter"));
		c.setDirection(getText("direction="));
		c.setTime(getText("time="));
		c.setClient(getText("client"));
		String tmp = getText("clientid");
		c.setClientID(tmp.equals("default")? 1:Integer.parseInt(tmp) );
		c.setInfo(getText("info"));
		c.setStatus(100);
		c.setProgram(getText("program="));
		return c;
	}
	private String getText(String message){
		Scanner sc = new Scanner(System.in);
		try{
			System.out.println(message);
			String tmp = sc.nextLine();
			if(tmp.length() > 0)
				return tmp;
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return "default";
	}
	
	public void print(){
		if(!_name.equals("default"))
		System.out.println(_name);
		if(!_from.equals("default"))
		System.out.println(_from);
		if(!_ftp_ip.equals("default"))
		System.out.println(_ftp_ip);
		if(!_ftp_file.equals("default"))
		System.out.println(_ftp_file);
		if(!_url.equals("default"))
		System.out.println(_url);
		if(!_query.equals("default"))
		System.out.println(_query);
		if(!_user.equals("default"))
		System.out.println(_user);
		if(!_password.equals("default"))
		System.out.println(_password);
		if(!_parameter.equals("default"))
		System.out.println(_parameter);
		if(!_direction.equals("default"))
		System.out.println(_direction);
		if(!_time.equals("default"))
		System.out.println(_time);
		if(_id != -1)
		System.out.println(_id);
		if(!_client.equals("default"))
		System.out.println(_client);
		if(_client_id != -1)
			System.out.println(_client_id);
		if(_status != -1)
		System.out.println(_status);
		if(!_name.equals("default"))
		System.out.println(_info);
	}
	public String toString(){
		String s = "name:"+_name+";from:"+_from+";ftp_url:"+_ftp_ip+";ftp_file:"+_ftp_file+";url:"+_url+";query:"+_query+";user:"+_user+";parameter:"+_parameter+";direction:"+_direction+";id"+_id+";time:"+_time+";client"+_client+";status:"+_status+";info:"+_info;       
		return s;
	}
}

