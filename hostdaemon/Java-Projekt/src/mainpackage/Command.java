package mainpackage;


public class Command {

	private String 	_name		= "default";
	private String 	_from		= "default";
	private String 	_ftp_ip		= "default";
	private String	_ftp_file	= "default";
	private String 	_url 		= "default";
	private String 	_query		= "default";
	private String 	_user		= "default";
	private String _password = "default";
	private String 	_parameter	= "default"; //all paramters are separated by a semicolon(;)
	private String 	_direction	= "default"; // w2h , h2c , c2h , self
	private String 	_time		= "default";
	private int 	_id			= -1;
	private String 	_client		= "default";
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
	public String  	getPassword(){return this._password;}
	public String 	getParameter(){return this._parameter;}
	public String 	getDirection(){return this._direction;}
	public String 	getTime(){return this._time;}
	public int 		getID(){return this._id;}
	public String 	getClient(){return this._client;}
	public String	getInfo(){return this._info;}
	public int 		getStatus(){return this._status;}
	public String 	getProgram(){return this._program;}
	
	
	public Command clone(){
		Command c = new Command();
		c.setName(getName());
		c.setFrom(getFrom());
		c.setFTP_IP(getFTP_IP());
		c.setFTP_File(getFTP_File());
		c.setURL(getURL());
		c.setQuery(getQuery());
		c.setUser(getUser());
		c.setPassword(getPassword());
		c.setParameter(getParameter());
		c.setDirection(getDirection());
		c.setTime(getTime());
		c.setID(getID());
		c.setClient(getClient());
		return c;
	}
	
	public void print(){
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
	public String toString(){
		String s = "name:"+_name+";from:"+_from+";ftp_url:"+_ftp_ip+";ftp_file:"+_ftp_file+";url:"+_url+";query:"+_query+";user:"+_user+";parameter:"+_parameter+";direction:"+_direction+";id"+_id+";time:"+_time+";client"+_client+";status:"+_status+";info:"+_info;       
		return s;
	}
}

