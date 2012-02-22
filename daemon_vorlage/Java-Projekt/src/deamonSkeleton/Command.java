package deamonSkeleton;


public class Command {

	private String 	_name		= "default";
	private String 	_from		= "default";
	private String 	_ftp_url	= "default";
	private String 	_url 		= "default";
	private String 	_query		= "default";
	private String 	_user		= "default";
	private String 	_parameter	= "default"; //all paramters are separated by a semicolon(;)
	private String 	_direction	= "default"; // w2h , h2c , c2h , self
	private String 	_time		= "default";
	private int 	_id			= -1;
	private String _client		= "default";
	
	public void setName(String name){this._name = name;}
	public void setFrom(String from){this._from = from;}
	public void setFTP_URL(String ftp_url){this._ftp_url = ftp_url;}
	public void setURl(String url){this._url = url;}
	public void setQuery(String query){this._query = query;}
	public void setUser(String user){this._user = user;}
	public void setParameter(String params){this._parameter = params;}
	public void setDirection(String direction){this._direction = direction;}
	public void setTime(String time){this._time = time;}
	public void setID(int id){this._id = id;}
	public void setClient(String client){this._client = client;}
	
	public String 	getName(){return this._name;}
	public String 	getFrom(){return this._from;}
	public String 	getFTP_URL(){return this._ftp_url;}
	public String 	getURl(){return this._url;}
	public String 	getQuery(){return this._query;}
	public String 	getUser(){return this._user;}
	public String 	getParameter(){return this._parameter;}
	public String 	getDirection(){return this._direction;}
	public String 	getTime(){return this._time;}
	public int 		getID(){return this._id;}
	public String 	getClient(){return this._client;}
	
	
	public Command clone(){
		Command c = new Command();
		c.setName(getName());
		c.setFrom(getFrom());
		c.setFTP_URL(getFTP_URL());
		c.setURl(getURl());
		c.setQuery(getQuery());
		c.setUser(getUser());
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
		System.out.println(_ftp_url);
		System.out.println(_url);
		System.out.println(_query);
		System.out.println(_user);
		System.out.println(_parameter);
		System.out.println(_direction);
		System.out.println(_time);
		System.out.println(_id);
		System.out.println(_client);
	}
}

