/*
 * This is the Main-Class
 * All relevant Threads are initialized and started here
 *  
 *  Communcication -> communicates with other programs and builds up the task-list
 *  Worker -> does the work given by the task-list
 *  
 */

package deamonSkeleton;

import java.util.ArrayList;


public class SkelMain {

		
	public static void main(String[] args) {
		ArrayList<String> task_list = new ArrayList<String>();
		Communication com = new Communication(task_list);
		com.start();
		Worker work = new Worker(task_list);
		work.start();
	}
	
}
