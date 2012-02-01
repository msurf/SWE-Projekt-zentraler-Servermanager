/*
 * This is the Main-Thread of the Communication.
 * opens up a Listener-Thread and an Output-Thread
 */

package deamonSkeleton;

import java.util.ArrayList;
import java.util.Scanner;

public class Communication extends Thread{
	private String _server = "localhost";
	private int _port = 5550;
	private ArrayList<String> _queue = new ArrayList<String>();
	
	public Communication(ArrayList<String> tasks) {
		this._queue = tasks;
	}
	public void run(){
		InputThread listener = new InputThread(this._queue);
		listener.start();
		
		int input = -1;
		while(input != 0)
		{
			System.out.println(" 0 -> Stopp \n 1 -> new Server \n 2 -> new Port \n 3 -> Send Message\n 4 -> Flush Queue");
			input=new Scanner(System.in).nextInt();
			switch(input){
			case 1: changeServer();
				break;
			case 2: changePort();
				break;
			case 3:
				send();
				break;
			case 4: flushQueue();
				break;
			}
		}
		listener.finish();
		}
		private void flushQueue(){
			if(this._queue.size() > 0){
				for(String s : this._queue)
					System.out.println(s);
			}
		}
	
		private void changeServer(){
			this._server = textinput("Insert new serveradress:\n");
		}
		
		private void changePort(){
			System.out.println("Insert new port:\n");
			int input=new Scanner(System.in).nextInt();
			this._port = input;
		}
		private void send()
		{
			String message = textinput("Insert new message:\n");
			OutputThread out = new OutputThread(this._server, this._port, message);
			out.start();
		}
		
		private String textinput(String prompt){
			Scanner sc= new Scanner(System.in);
			sc.useDelimiter("\n");
			System.out.print(prompt);
			String text=sc.next();
			System.out.println("Your Input: " + text.substring(0,text.length()));
			return text.substring(0,text.length());
		}
}
