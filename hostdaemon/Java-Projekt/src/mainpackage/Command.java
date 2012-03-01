package mainpackage;


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
	
	public synchronized void setName(String name){this._name = name;}
	public synchronized void setFrom(String from){this._from = from;}
	public synchronized void setFTP_IP(String ftp_ip){this._ftp_ip = ftp_ip;}
	public synchronized void setFTP_File(String file){this._ftp_file = file;}
	public synchronized void setURL(String url){this._url = url;}
	public synchronized void setQuery(String query){this._query = query;}
	public synchronized void setUser(String user){this._user = user;}
	public synchronized void setPassword(String password){this._password = password;}
	public synchronized void setParameter(String params){this._parameter = params;}
	public synchronized void setDirection(String direction){this._direction = direction;}
	public synchronized void setTime(String time){this._time = time;}
	public synchronized void setID(int id){this._id = id;}
	public synchronized void setClient(String client){this._client = client;}
	public synchronized void setClientID(int id){this._client_id = id;}
	public synchronized void setInfo(String info){this._info = info;}
	public synchronized void setStatus(int status){this._status = status;}
	public synchronized void setProgram(String program){this._program = program;}
	
	public synchronized String 	getName(){return this._name;}
	public synchronized String 	getFrom(){return this._from;}
	public synchronized String 	getFTP_IP(){return this._ftp_ip;}
	public synchronized String	getFTP_File(){return this._ftp_file;}
	public synchronized String 	getURL(){return this._url;}
	public synchronized String 	getQuery(){return this._query;}
	public synchronized String 	getUser(){return this._user;}
	public synchronized String	getPassword(){return this._password;}
	public synchronized String 	getParameter(){return this._parameter;}
	public synchronized String 	getDirection(){return this._direction;}
	public synchronized String 	getTime(){return this._time;}
	public synchronized int 	getID(){return this._id;}
	public synchronized String 	getClient(){return this._client;}
	public synchronized int		getClientID(){return this._client_id;}
	public synchronized String	getInfo(){return this._info;}
	public synchronized int 	getStatus(){return this._status;}
	public synchronized String 	getProgram(){return this._program;}
	
	
	public synchronized Command clone(){
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
	
	public synchronized void print(){
		System.out.println(_name);
		System.out.println(_from);
		System.out.println(_ftp_ip);
		System.out.println(_ftp_file);
		System.out.println(_url);
		System.out.println(_query);
		System.out.println(_user);
		System.out.println(_password);
		System.out.println(_parameter);
		System.out.println(_direction);
		System.out.println(_time);
		System.out.println(_id);
		System.out.println(_client);
		System.out.println(_status);
		System.out.println(_info);
	}
	public synchronized String toString(){
		String s = "name:"+_name+";from:"+_from+";ftp_url:"+_ftp_ip+";ftp_file:"+_ftp_file+";url:"+_url+";query:"+_query+";user:"+_user+";parameter:"+_parameter+";direction:"+_direction+";id"+_id+";time:"+_time+";client"+_client+";status:"+_status+";info:"+_info;       
		return s;
	}
}

