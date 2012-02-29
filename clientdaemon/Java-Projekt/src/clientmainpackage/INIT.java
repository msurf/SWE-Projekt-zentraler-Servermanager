/**
 * 	This is a server-program, which runs in the background.
 * 	The program interacts with equivalent server-programs, receiving/sending messages and tasks and completing incoming tasks.
 *  
 *  Communication -> communicates with other programs and builds up the task-list
 *  Worker -> does the work given by the task-list
 *  @author Philipp Tendyra
 */

package clientmainpackage;

/** Main-Class */
public class INIT {

	/**
	 * This is the main-methode.
	 * Only the Administration-class is initialized.	
	 * @param args
	 */
	public static void main(String[] args) {
		Administration admin = new Administration();
		admin.start();
	}//main(String[] args)
}//class
