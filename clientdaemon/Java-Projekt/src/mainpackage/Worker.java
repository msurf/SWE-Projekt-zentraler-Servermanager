package mainpackage;


/**
 * This Thread coordinates the work which is given by the task-list 
 * 
 * @author Philipp Tendyra
 */
public class Worker extends Thread {
	
	protected Communication _com;
	protected Config _conf;
	protected boolean daemon = true;
	protected Command _command = null;
	protected String _path = System.getProperty("user.dir");
	
	/**
	 * constructor
	 * @param list sets the reference to the task-list
	 * 
	 * thread is marked as daemon-thread
	 */
	Worker() {
		setDaemon(daemon); // all daemon-threads are terminated, if there is no user-thread. the user-thread in this program is the Administration-thread!
	}//constructor
	
	Worker(Command command){
		this._command = command;
		setDaemon(daemon);
	}
	
	Worker(Command command, Communication com){
		this._com = com;
		this._command = command;
		setDaemon(daemon);
	}
	
	Worker(Command command, Communication com, Config conf)
	{
		this._com = com;
		this._command = command;
		this._conf = conf;
		setDaemon(daemon);
	}
	
	public void run(){;}
	
	public void log(Command c){
		Logger logger = new Logger(this._conf.getLogpath(), "Worker");
		logger.write("");
		
	}
}//class
