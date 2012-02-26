package mainpackage;

import java.util.InputMismatchException;
import java.util.Scanner;
/**
 * This class manages the whole program.
 * @author Philipp Tendyra
 *
 */
public class Administration extends Thread {

	/** The programs task-list , unique */
	private TaskList<Command> _task_list;
	/** The programs communication-thread which is used to communicate with equivalent programs */
	private Communication _com;
	/**The Programms Config - unique */
	private Config _config;
	
	private Dispatcher _dispatch;
	
	/**
	 * initializes the task-list
	 * initializes the communication-thread
	 * starts the communication-thread
	 * initializes the worker-thread
	 * starts the worker-thread
	 */
	protected void startProgram(){
	 System.out.println("Program start");
		this._config = new Config();
	 this._task_list = new TaskList<Command>();
	 this._com = new Communication(this._task_list, this._config);
	 this._dispatch = new Dispatcher(this._task_list, this._com, this._config);
	 this._com.start();
	 this._task_list.addListDataListener(this._dispatch);
	}//startProgram()
	
	
	/**
	 * starts scanner on the system-input
	 * prints options(0-4) for the input
	 * switches to chosen option, without option sends text as message
	 */
	private void runDialog(){
		
		int input = -1;
		Scanner sc = null;
		while (input != 0) {
			System.out
					.println(" 0 -> Stop \n 1 -> Build a Command an send it \n 3 -> reload SystemProperties \n 4 -> load the Configfile \n 5 -> write Configfile");
			sc = new Scanner(System.in);
			try{
			input = sc.nextInt();
			}//try
			catch(InputMismatchException ime){
				System.out.println("Please use the menue");
				input = -1;
			}//catch
			
			switch (input) {
			case 1:
				send();
				break; 
			case 2:
				break;
			case 3:
				this._config.getSys();
				break;
			case 4:
				this._config.loadConfig();
				break;
			case 5:
				this._config.writeConfig();
				break;
			case 0:
				stopProgram();
				break;
			}//switch
		}//while
		
	}//runDialog()
	
	private void stopProgram(){
		this._config.writeConfig();
	}
	private void send(){
		Command tmp = new Command();
		tmp.setName(getText("name="));
		tmp.setDirection(getText("direction="));
		tmp.setFTP_URL(getText("ftp_url="));
		tmp.setQuery(getText("query="));
		tmp.setURL(getText("url="));
		tmp.setParameter(getText("Parameters="));
		this._com.send(tmp, this._config.getIP_send(), this._config.getPort_send());
	}
	
	private String getText(String message){
		Scanner sc = new Scanner(System.in);
		try{
			System.out.println(message);
			String tmp = sc.nextLine();
			if(tmp.length() > 0)
				return tmp;
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return "default";
	}
	
	/**
	 * runs the startProgram-method
	 * runs the runDialog-method
	 */
	public void run(){
		startProgram();
		runDialog();
	}//run()
}//class
