/*
 * This Thread is respsonsable for the outgoing messages
 */

package deamonSkeleton;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.net.UnknownHostException;

public class OutputThread extends Thread {
	private String _server;
	private int _port;
	private String _message;
	
	
	OutputThread(String server, int port, String message){
		this._server = server;
		this._port = port;
		this._message = message;
	}
	
	public void run(){
		//System.out.println("OutputThread läuft!");
		BufferedWriter out;
		try {
			Socket socket = new Socket(this._server, this._port);
			out = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
			out.write(this._message);
			out.newLine();
			out.flush();
			out.close();
			//System.out.println("Nachricht gesendet");
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			System.out.println("Can't resolve Host!");
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
