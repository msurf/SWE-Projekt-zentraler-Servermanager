package clientmainpackage;

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
			System.out.println(" 0 -> Stop \n 1 -> Get Hardwareinfo \n 2 -> Get Softwareinfo \n 3 -> reload Configfile \n 4 -> Safe the Configfile \n 5 -> Install Software \n 6 -> Send a InfoMessage to the Host");
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
				System.out.println(this._config.hwinfo());
				break; 
			case 2:
				System.out.println(this._config.swinfo());
				break;
			case 3:
				this._config.loadConfig();
				break;
			case 4:
				this._config.writeConfig();
				break;
			case 5:
				install();
				break;
			case 6:
				message2host();
				break;
			case 0:
				stopProgram();
				break;
			}//switch
		}//while
		
	}//runDialog()
	private void install(){
		String ftp_url = getText("Please insert ftp_Url: ");
		String ftp_file = getText("Please insert ftp_file: ");
		Command tmp = new Command();
		tmp.setStatus(101);
		tmp.setName("install");
		tmp.setInfo("recived");
		tmp.setFTP_IP(ftp_url);
		tmp.setFTP_File(ftp_file);
		
		this._task_list.add(tmp);
	}
	
	private void message2host(){
		Command tmp = new Command();
		tmp.setName("info");
		tmp.setStatus(103);
		tmp.setInfo(getText("Please insert your message: "));
	}
	
	private void stopProgram(){
		this._config.writeConfig();
	}
	/*private void send(){
		Command tmp = new Command();
		tmp.setName(getText("name="));
		tmp.setStatus(100);
		this._com.send(tmp, this._config.getIP_send(), this._config.getPort_send());
	}*/
	
	private String getText(String message){
		Scanner sc = new Scanner(System.in);
		try{
			System.out.print(message);
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
