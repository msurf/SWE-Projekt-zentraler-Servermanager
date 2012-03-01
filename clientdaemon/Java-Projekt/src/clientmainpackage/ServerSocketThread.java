package clientmainpackage;

import java.beans.XMLDecoder;
import java.beans.XMLEncoder;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.net.Socket;

/**
 * This Thread handles the incoming requests and adds the message to the task-list
 * 
 * @author Philipp Tendyra
 */
public class ServerSocketThread extends Thread {
	/** temporarily stores the socket, given by the InputThread-class */
	private Socket _socket = null;
	/** references the task-list which is created in the Administration-Thread */
	private TaskList<Command> _queue;
	
	private Command _command = new Command();
	
	private Config _config;

	/**
	 * This is the constructor
	 * 
	 * @param s sets the Input-socket
	 * @param list sets the reference to the task-list
	 * 
	 * thread is marked as daemon-thread
	 */
	public ServerSocketThread(Socket s, TaskList<Command> list, Config config) {
		this._socket = s;
		this._queue = list;
		System.out.println(config.hwinfo());
		this._config = config;
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
		XMLDecoder dec = null;
		XMLEncoder enc = null;
		try {
			enc = new XMLEncoder(new BufferedOutputStream(this._socket.getOutputStream()));
			dec = new XMLDecoder(new BufferedInputStream(this._socket.getInputStream()));
			int status = 0;
				this._command = (Command) dec.readObject();
			work();
				status = this._command.getStatus();
			if(status != 105 && status != 0)//105 is response, responding commands are allready done
			{
				
				if(status == 100)
				{
					this._command.setStatus(101);
					this._command.setInfo("recived");
				}
				enc.writeObject(this._command);
					this._queue.add(this._command.clone());
			}
				enc.writeObject(this._command);
		}// try
		catch (IOException e) {
			System.out.println("ServerSocketThread : "+this.getId()+" Cannot handle Command");
			new Logger(this._config.getLogpath()).write("ServerSocketThread : "+this.getId()+" Cannot handle Command");
		}// catch
		finally{
			if(enc != null)
				enc.close();
			if(dec != null)
				dec.close();
		}
	}//readIn
	private void work(){
		// direct response
			String name = "none";
			name = this._command.getName();
			boolean work_done = false;
		if(name.equals("busy")){
			String info = this._config.isBusy();
			this._command.setInfo(info);
			work_done = true;
		}
		if(name.equals("hwinfo")){
				String info = this._config.hwinfo();
				this._command.setInfo(info);
			work_done = true;
		}
		if(name.equals("swinfo")){
			String info = "default";
				info = this._config.swinfo();
				this._command.setInfo(info);
			work_done = true;
		}
		if(name.equals("busy")){
			String info = "on";
				info = this._config.isBusy();
				this._command.setInfo(info);
			work_done = true;
		}
		if(work_done)			
				this._command.setStatus(105);
	}
}// class
