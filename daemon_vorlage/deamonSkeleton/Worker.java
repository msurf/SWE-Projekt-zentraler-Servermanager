package deamonSkeleton;

import java.util.ArrayList;

/**
 * This Thread coordinates the work which is given by the task-list 
 * 
 * @author Philipp Tendyra
 */
public class Worker extends Thread {
	
	private Communication _com;
	private boolean daemon = true;
	private String _message = "default";
	/**
	 * constructor
	 * @param list sets the reference to the task-list
	 * 
	 * thread is marked as daemon-thread
	 */
	Worker() {
		setDaemon(daemon); // all daemon-threads are terminated, if there is no user-thread. the user-thread in this program is the Administration-thread!
	}//constructor
	
	Worker(String s){
		this._message = s;
		setDaemon(daemon);
	}
	
	Worker(String s, Communication com){
		this._com = com;
		this._message = s;
		setDaemon(daemon);
	}

	/**
	 * 
	 */
	public void run() {
		print(this._message);
	}//run()
	
	private void print(String s){
		System.out.println("Worker ID: " +this.getId()+ " message: " + s);
	}
	
}//class
