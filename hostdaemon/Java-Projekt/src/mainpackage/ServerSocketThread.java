package mainpackage;

import java.beans.XMLDecoder;
import java.beans.XMLEncoder;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.sql.*;

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
	
	private Config _config = null;

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
		this._config = config;
		setDaemon(true); // all daemon-threads are terminated, if there is no user-thread. the user-thread in this program is the Administration-thread!
	}// constructor

	/** runs the readIn-method */
	public void run(){
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
			this._command = (Command) dec.readObject();
			this._command.setStatus(101);
			work();
			if(this._command.getStatus() != 105)//105 is response, responding commands are allready done
			{
				synchronized (this._queue) {
					this._queue.add(this._command);
				}// synchronized
			}
			enc.writeObject(this._command);
		}// try
		catch (IOException e) {
			// TODO store error on local device
			// send error-message to the host-server
			e.printStackTrace();
		}// catch
		finally{
			if(enc != null)
				enc.close();
			if(dec != null)
				dec.close();
		}
	}//readIn
	private void work() {
		String name = this._command.getName();
		boolean work_done = false;
		database base = new database();
		if(name.equals("authenticate"))
		{
<<<<<<< HEAD
			try {
				String erg = base.getInfo_Authenticate(this._command.getUser(),this._command.getPassword());
				this._command.setInfo(erg);
				work_done = true;
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}		
=======
			//TODO
			// Database request for the user and the password to proof
			// setInfo(); <- here are the infos about the user(syntax: correct:admin or incorrect:none -> loginstatus:right)
			String dbbefehl = this._command.getQuery(); // das ist der befehl der auf die db angewand wird
			String dbresponse = "Das was die Datenbank ausgibt, in einer Sinnvollen Reihenfolge"; //hier wäre das nur correct:admin or incorrect:none -> loginstatus:right
			this._command.setInfo(dbresponse); // hier wirds in das Objekt gespeichert
			work_done = true;
>>>>>>> 50c1c235319e48d9f2de08d7fa09f38b668bc707
		}
		if(name.equals("getclients"))
		{
			//TODO
			//holt die liste der verfügbaren clients aus der Datenbank
		}
		if(name.equals("getclientstatus"))
		{
			//TODO
			//holt den status vom Client
			
		}
		if(name.equals("getrepoliste"))
		{
			//TODO
			//holt die liste der verfügbaren Software(name) aus der DB
		}
		if(name.equals("hwinfo"))
		{
			//TODO
			//this._command.setInfo(Database request for the this._config.getClient());
			work_done = true;
		}
		if(name.equals("swinfo"))
		{
			//TODO
			//this._command.setInfo(Database request for the this._config.getClient());
			work_done = true;
		}
		
		if(work_done)
			this._command.setStatus(105);
	}
}// class
