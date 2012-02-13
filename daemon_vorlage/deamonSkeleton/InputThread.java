package deamonSkeleton;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * This Thread opens up a Socket and permanently listens to it
 * if there is a request, this request is given to a temporarily ServerSocketThread
 * 
 * @author Philipp Tendyra
 */
public class InputThread extends Thread {

	/** references the task-list which is created in the Administration-Thread */
	private TaskList<Command> _task_list;
	/** stores the port on which the thread is listening, the default is 5550 */
	private int _port = 5550;
	/** indicates if the port was changed, but not recognized by the listener */
	private boolean _portchange = false;

	/**
	 * This is the constructor.
	 * 
	 * @param liste sets the reference to the task-list
	 * 
	 * thread is marked as daemon-thread
	 */
	InputThread(TaskList<Command> liste) {
		this._task_list = liste;
		setDaemon(true);// all daemon-threads are terminated, if there is no
						// user-thread. the user-thread in this program is the
						// Administration-thread!
	}//constructor
	/**
	 * This is the constructor.
	 * 
	 * @param liste sets the task-list
	 * @param port sets the port
	 * 
	 * thread is marked as daemon-thread
	 */
	InputThread(TaskList<Command> liste, int port) {
		this._task_list = liste;
		this._port = port;
		setDaemon(true); // all daemon-threads are terminated, if there is no
							// user-thread. the user-thread in this program is
							// the Administration-thread!
	}//constructor

	/** runs the listenUp-method */
	public void run() {
		listenUp();
	}//run()

	/**
	 * opens up a ServerSocket and waits until there is a request
	 * the request is given to a temporarily ServerSocketThread who handles the request
	 */
	public void listenUp() {
		while(true)
		{
		try {
				ServerSocket server = new ServerSocket(this._port);
				while (!this._portchange) {
					this._portchange = false;
					Socket socket = server.accept();
					ServerSocketThread sst = new ServerSocketThread(socket,
							this._task_list);
					sst.start();
				}// while
		}// try
		catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}// catch
		}//while
	}// listenUp()
	
	/**
	 * changes the port on which the thread is listening
	 * @param port sets the new port
	 * 
	 * sends a message to itself to initialize the use of the new port
	 */
	public void changePort(int port){
		int tmp = this._port;
		this._port = port;
		this._portchange = true;
		OutputThread change = new OutputThread("localhost", tmp, "port changed to " + this._port);
		change.start();
	}//changePort()
}//class