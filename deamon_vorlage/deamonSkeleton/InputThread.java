/*
 * This Thread opens up a Socket an permanently listens to it
 * if there is a request, this request is given to the ServerSocketThread
 */

package deamonSkeleton;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class InputThread extends Thread {
	private ArrayList<String> _queue;
	private int _port = 5550;
	private boolean _runs = true;

	InputThread(ArrayList<String> liste){
		this._queue = liste;
	}
	
	InputThread(ArrayList<String> liste, int port) {
		this._queue = liste;
		this._port = port;
	};

	public void run() {
		//System.out.println("InputThread runs!");
		try {
			ServerSocket server = new ServerSocket(this._port);
			while (this._runs) {
				Socket socket = server.accept();
				//System.out.println("InputThread accept!");
				ServerSocketThread sst = new ServerSocketThread(socket, this._queue);
				sst.start();
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void finish(){
		this._runs = false;
		OutputThread kill = new OutputThread("localhost", this._port, "InputThread stopped.");
		kill.start();
	}
}