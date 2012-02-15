package deamonSkeleton;

import java.util.InputMismatchException;
import java.util.Scanner;
/**
 * This class manages the whole program.
 * @author Philipp Tendyra
 *
 */
public class Administration extends Thread {

	/** The programs task-list */
	private TaskList<Command> _task_list;
	/** The programs communication-thread which is used to communicate with equivalent programs */
	private Communication _com;
	
	private Dispatcher _dispatch;
	
	/**
	 * initializes the task-list
	 * initializes the communication-thread
	 * starts the communication-thread
	 * initializes the worker-thread
	 * starts the worker-thread
	 */
	protected void startProgram(){
	 this._task_list = new TaskList<Command>();
	 this._dispatch = new Dispatcher(this._task_list, this._com);
	 this._task_list.addListDataListener(this._dispatch);
	 this._com = new Communication(this._task_list);
	 this._com.start();
	}//startProgram()
	
	
	/**
	 * starts scanner on the system-input
	 * prints options(0-4) for the input
	 * switches to chosen option, without option sends text as message
	 */
	private void runDialog(){
		
		int input = -1;
		Scanner sc = new Scanner(System.in);
		while (input != 0) {
			System.out
					.println(" 0 -> Stop \n 1 -> new SendServer \n 2 -> new SendPort \n 3 -> Send Message\n 4 -> Nothing");
			try{
			input = sc.nextInt();
			}//try
			catch(InputMismatchException ime){
				this._com.send(sc.nextLine());
			}//catch
			
			switch (input) {
			case 1:
				this._com.changeServer();
				break; 
			case 2:
				this._com.changePort();
				break;
			case 3:
				this._com.send();
				break;
			case 4:
				break; 
			}//switch
		}//while
		
	}//runDialog()
	
	/**
	 * runs the startProgram-method
	 * runs the runDialog-method
	 */
	public void run(){
		startProgram();
		runDialog();
	}//run()
}//class
