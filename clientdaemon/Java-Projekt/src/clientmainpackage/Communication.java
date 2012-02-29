package clientmainpackage;


/**
 * responsible for the communication between this and similar programs
 * opens up a Listener-Thread and an Output-Thread
 */
public class Communication extends Thread {
	/** stores the reference to the task-list*/
	private TaskList<Command> _task_list = new TaskList<Command>();
	/** stores the reference to the Config*/
	private Config _config;
	
	/**
	 * constructor 
	 * @param tasks sets the reference of the task-list
	 * 
	 * thread is marked as daemon-thread
	 */
	public Communication(TaskList<Command> tasks, Config conf) {
		this._task_list = tasks;
		this._config = conf;
		setDaemon(true);// all daemon-threads are terminated, if there is no user-thread. the user-thread in this program is the Administration-thread!
	}//constructor
	
	/** starts a InputThread-thread*/
	public void run() {
		InputThread listener = new InputThread(this._task_list, this._config);
		listener.start();
	}//run()

	
	/**
	 * sends a message to the serveradress
	 * @param s sets the message
	 */
	protected synchronized void send(Command com, String to_IP, int on_Port){
		OutputThread out = new OutputThread(to_IP, on_Port, com, this._config);
		out.start();
	}//send(String s)

	
}//class
