package deamonSkeleton;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.ArrayList;

/**
 * This Thread handles the incoming requests and adds the message to the task-list
 * 
 * @author Philipp Tendyra
 */
public class ServerSocketThread extends Thread {
	/** temporarily stores the socket, given by the InputThread-class */
	private Socket _socket = null;
	/** references the task-list which is created in the Administration-Thread */
	private ArrayList<String> _queue;

	/**
	 * This is the constructor
	 * 
	 * @param s sets the Input-socket
	 * @param list sets the reference to the task-list
	 * 
	 * thread is marked as daemon-thread
	 */
	public ServerSocketThread(Socket s, ArrayList<String> list) {
		this._socket = s;
		this._queue = list;
		setDaemon(true); // all daemon-threads are terminated, if there is no user-thread. the user-thread in this program is the Administration-thread!
	}// constructor

	/** runs the readIn-method */
	public void run() {
		readIn();
	}// run()
	
	/**
	 * Reads the Input-Stream of the stored socket and tries to add the statement to the task-list.
	 * The operation on the task-list is synchronized.
	 */
	private void readIn(){
		BufferedReader in;
		try {
			in = new BufferedReader(new InputStreamReader(
					_socket.getInputStream()));
			String text = in.readLine();
			System.out.println("Message : " + text);

			synchronized (this._queue) {
				this._queue.add(text);
			}// synchronized

			in.close();
			// TODO send success to host-server
		}// try
		catch (IOException e) {
			// TODO store error on local device
			// send error-message to the host-server
			e.printStackTrace();
		}// catch
	}//readIn
}// class
