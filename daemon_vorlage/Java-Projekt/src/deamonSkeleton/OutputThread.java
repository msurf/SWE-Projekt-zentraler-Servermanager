package deamonSkeleton;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.net.UnknownHostException;

/**
 * This Thread is responsible for the outgoing messages
 * 
 * @author Philipp Tendyra
 */
public class OutputThread extends Thread {
	/** stores the serveradress the messages are send to*/
	private String _serveradress;
	/** stores the port which belongs to the serveradress*/
	private int _port;
	/** stores the message*/
	private String _message;
	
	/**
	 * constructor
	 * @param server sets the serveradress
	 * @param port sets the serverport
	 * @param message sets the message
	 */
	OutputThread(String server, int port, String message){
		this._serveradress = server;
		this._port = port;
		this._message = message;
		setDaemon(true); // all daemon-threads are terminated, if there is no user-thread. the user-thread in this program is the Administration-thread!
	}//constructor
	
	/** runs the sendMessage-method*/
	public void run(){
		sendMessage();
	}//run()
	
	/**
	 * opens up a socket for the outgoing message
	 * sends the message to the servername with the stored port
	 * closes the socket
	 */
	public void sendMessage(){
		BufferedWriter out;
		try {
			Socket socket = new Socket(this._serveradress, this._port);
			out = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
			out.write(this._message);
			out.newLine();
			out.flush();
			out.close();
		}//try 
		catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			System.out.println("Can't resolve Host!");
			e.printStackTrace();
		}//catch
		catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}//catch
	}//sendMessage
}
