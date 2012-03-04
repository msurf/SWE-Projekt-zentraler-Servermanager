package mainpackage;


public class Worker_addclient extends Worker{
	
	public Worker_addclient(Command command, Config conf, Communication com) {
		super();
		this._command = command;
		this._conf = conf;
		this._com = com;
	}
	public void run(){
		addClient();
	}
	
	
	private void addClient(){
		Database base = new Database();
		String name = "default";
		String ip 	= "default:default";
		String user = "default";
		String pwd	= "default";
		name = this._command.getClient();
		ip = this._command.getURL();// xxx.xxx.xxx.xxx:port
		user = this._command.getUser();
		pwd = this._command.getPassword();
		this._command.setStatus(103);
		
		try{
		base.insertNewClient(name, ip, user, pwd);
		}catch(Exception e)
		{
			this._command.setStatus(200);
			System.out.println("Could not add Client. Problems with the Database");
		}
	}
	

}
