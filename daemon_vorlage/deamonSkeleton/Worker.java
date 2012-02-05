package deamonSkeleton;

import java.util.ArrayList;

/**
 * This Thread coordinates the work which is given by the task-list 
 * 
 * @author Philipp Tendyra
 */
public class Worker extends Thread {
	
	/** references the task-list which is created in the Administration-Thread */
	private ArrayList<String> _task_list;
	/** points to the current task in the task-list */
	private int _task_count;
	
	/**
	 * constructor
	 * @param list sets the reference to the task-list
	 * 
	 * thread is marked as daemon-thread
	 */
	Worker(ArrayList<String> list) {
		this._task_list = list;
		this._task_count = 0;
		setDaemon(true); // all daemon-threads are terminated, if there is no user-thread. the user-thread in this program is the Administration-thread!
	}//constructor
	/**
	 * constructor
	 * @param list sets the reference to the task-list
	 * @param task_count sets the task_count
	 * 
	 * thread is marked as daemon-thread
	 */
	Worker(ArrayList<String> list, int task_count) {
		this._task_list = list;
		this._task_count = task_count;
		setDaemon(true); // all daemon-threads are terminated, if there is no user-thread. the user-thread in this program is the Administration-thread!
	}//constructor

	/**
	 * endless loop with sleeptime 10 seconds.
	 * runs the printTaskList-method
	 */
	public void run() {
		while (true) {
			try {
				sleep(10000);
			}//try
			catch (Exception e) {
				// TODO: handle exception
			}//catch
			printTaskList();
			
		}//while
	}//run()
	
	/**
	 * prints all elements the task-list contains at this short moment
	 * 
	 * operation on the task-list is synchronized
	 */
	private void printTaskList(){
		ArrayList<String> tmp = null;
		synchronized (this._task_list) {
			tmp = this._task_list;
		}//synchronized
		
		int size = tmp.size();
		System.out.println("The Queue contains " + size + " elements");
		if (size > 0)
			for (String s : tmp)
				System.out.println(s);
	}//printTaskList()
}//class
