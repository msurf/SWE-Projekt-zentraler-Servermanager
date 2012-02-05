package deamonSkeleton;

import java.util.ArrayList;
import java.util.Scanner;

/**
 * responsible for the communication between this and similar programs
 * opens up a Listener-Thread and an Output-Thread
 */
public class Communication extends Thread {
	/** stores the serveradress outgoing messages are send to*/
	private String _server = "localhost";
	/** stores the port which belongs to the serveradress*/
	private int _port = 5550;
	/** stores the reference to the task-list*/
	private ArrayList<String> _task_list = new ArrayList<String>();
	
	/**
	 * constructor 
	 * @param tasks sets the reference of the task-list
	 * 
	 * thread is marked as daemon-thread
	 */
	public Communication(ArrayList<String> tasks) {
		this._task_list = tasks;
		setDaemon(true);// all daemon-threads are terminated, if there is no user-thread. the user-thread in this program is the Administration-thread!
	}//constructor

	/** starts a InputThread-thread*/
	public void run() {
		InputThread listener = new InputThread(this._task_list);
		listener.start();
	}//run()

	/** prints all elements of the task-list*/
	protected void printTaskList() {
		if (this._task_list.size() > 0) {
			for (String s : this._task_list)
				System.out.println(s);
		}//if
		else
			System.out.println("Task-List doesn't containe anything");
	}//printTaskList()
	/** changes the serveradress with a prompt
	 *	trivial 
	 */
	protected void changeServer() {
		this._server = textinput("Insert new serveradress:\n");
	}
	/** changes the serverport with a promt
	 * 	trivial
	 */
	protected void changePort() {
		System.out.println("Insert new port:\n");
		int input = new Scanner(System.in).nextInt();
		this._port = input;
	}//changePort
	
	/** sends a message to the serveradress you type in a prompt */
	protected void send() {
		String message = textinput("Insert new message:\n");
		OutputThread out = new OutputThread(this._server, this._port, message);
		out.start();
	}//send()
	
	/**
	 * sends a message to the serveradress
	 * @param s sets the message
	 */
	protected void send(String s){
		OutputThread out = new OutputThread(this._server, this._port, s);
		out.start();
	}//send(String s)

	/** This is a prompt for text input*/
	private String textinput(String prompt) {
		Scanner sc = new Scanner(System.in);
		sc.useDelimiter("\n");
		System.out.print(prompt);
		String text = sc.next();
		System.out.println("Your Input: " + text.substring(0, text.length()));
		return text.substring(0, text.length());
	}//textinput()
}//class
