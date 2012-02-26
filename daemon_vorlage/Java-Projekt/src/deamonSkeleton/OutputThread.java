package deamonSkeleton;

import java.beans.XMLDecoder;
import java.beans.XMLEncoder;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.IOException;
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
	
	private Config _conf;
	
	private Command _command;
	
	/**
	 * constructor
	 * @param server sets the serveradress
	 * @param port sets the serverport
	 * @param message sets the message
	 */
	OutputThread(String server, int port, Command com, Config conf){
		this._serveradress = server;
		this._port = port;
		this._command = com;
		this._conf = conf;
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
		XMLEncoder enc = null;
		XMLDecoder dec = null;
		Socket socket = null;
		Command send = this._command.clone();;
		Command response = null;
		try {
			
			send.setFrom(this._conf.getIP_own());
			socket = new Socket(this._serveradress, this._port);
			enc = new XMLEncoder(new BufferedOutputStream(socket.getOutputStream()));
			dec = new XMLDecoder(new BufferedInputStream(socket.getInputStream()));
			enc.writeObject(send);
			enc.flush();
			socket.shutdownOutput(); // socket half opened -> make the input read the Object
			//response
			response = (Command) dec.readObject();
			System.out.println(response.getStatus());
			System.out.println(response.getInfo());
			new ShellRunner().execute("echo 'Command_ID: " + response.getID() + " : " + response.getStatus()+"'>>"+this._conf.getLogpath()+"/swe.response");
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
		finally{
			if(socket != null)
			{	try {
					socket.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			if(dec != null)
				dec.close();
			if(enc != null)
				enc.close();
		}
	}//sendMessage
}
