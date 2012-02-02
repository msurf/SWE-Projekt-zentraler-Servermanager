/*
 * This Thread coordinates the work wich is given by the task-list 
 */
package deamonSkeleton;

import java.util.ArrayList;

public class Worker extends Thread {
	// TODO refer to the UML-Diagrams
	private ArrayList<String> _queue;

	Worker(ArrayList<String> list) {
		this._queue = list;
		setDaemon(true);
	}

	public void run() {
		while (true) {
			try {
				sleep(10000);
			} catch (Exception e) {
				// TODO: handle exception
			}
			ArrayList<String> tmp = this._queue;
			int size = tmp.size();
			System.out.println("The Queue contains " + size + " elements");
			if(size > 0)
				for(String s : tmp)
					System.out.println(s);
		}
	}
}
