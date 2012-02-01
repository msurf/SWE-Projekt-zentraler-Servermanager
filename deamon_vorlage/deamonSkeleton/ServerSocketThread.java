/*
 * This Thread handles the incoming requests and adds the message to the task-list
 */
package deamonSkeleton;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.ArrayList;

public class ServerSocketThread extends Thread {
	private Socket _socket = null;
	private ArrayList<String> _queue;
	
	public ServerSocketThread(Socket s, ArrayList<String> list) {
		this._socket = s;
		this._queue = list;
	}

	public void run(){
		//System.out.println("ServerSocketThread läuft");
		BufferedReader in;
		try {
			in = new BufferedReader(new InputStreamReader(_socket.getInputStream()));
			//System.out.println("Input verarbeitet 1");
			String text = in.readLine();
			System.out.println("Message : " + text);
			this._queue.add(text);
			in.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
