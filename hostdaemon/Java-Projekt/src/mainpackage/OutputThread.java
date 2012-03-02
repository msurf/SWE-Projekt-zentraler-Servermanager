package mainpackage;

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
			if(response.getStatus() == 105)
				work(response);
			
			new ShellRunner().execute("echo 'Command_ID: " + response.getID() + " : " + response.getStatus()+"'>>"+this._conf.getLogpath()+"/swe.response");
		}//try 
		catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			System.out.println("Can't resolve Host!");
			Database base = new Database();
			try{
				base.update_ClientStatus(this._serveradress, "off");
			}catch(Exception db){System.out.println("Cannot update ClientStatus!");}
			
		}//catch
		catch (IOException e) {
			// TODO Auto-generated catch block
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
	private void work(Command c){
		String name =c.getName();
		Database base = new Database();
		
		
		if(name.equals("hwinfo"))
		{
			int clientid = c.getClientID();
			String cpu = "default";
			String ram = "default";
			String architecture = "default";
			try{
				String[] tmp1 = c.getInfo().split("#");
				for(String i : tmp1)
				{
					if(i.contains("cpu"))
						cpu=i.split(":")[1];
					if(i.contains("ram"))
						ram=i.split(":")[1];
					if(i.contains("architecture"))
						architecture=i.split(":")[1];
				}
			}catch(Exception e){System.out.println("Cannot handle Infos from: "+c.getClient());}
			
			try{
			base.update_hwinfo(clientid, cpu, ram, architecture);
			}catch(Exception e){System.out.println("Cannot update HardwareInfo from: "+ c.getClient());}
		}
		if(name.equals("swinfo"))
		{
			String[][] soft = new String[0][];
			try{
				String in = c.getInfo();
				if(in.length() != 0){
					String[] tmp = in.split("#");
					soft = new String[tmp.length][];
					for(int i = 0; i < tmp.length; i++)
						soft[i]=tmp[i].split(":");
				}
			}catch(Exception e){System.out.println("Cannot handle Infos from: "+c.getClient());}
			
			try{
				for(String[] i: soft)
					{
					base.update_swinfo(c.getClientID(), i[0], i[1]);
					}
			}catch(Exception e){System.out.println("Cannot update SoftwareInfo from: "+c.getClient());}
			
			
		}
		if(name.equals("busy"))
		{
			try{
				base.update_ClientStatus(c.getClientID(), c.getInfo());
			}catch(Exception e){System.out.println("Cannot update ClientStatus from: "+c.getClient());}
		}
		
	}
	
}







