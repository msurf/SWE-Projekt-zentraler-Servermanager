package deamonSkeleton;

import java.util.ArrayList;

import javax.swing.event.ListDataEvent;
import javax.swing.event.ListDataListener;

public class Dispatcher implements ListDataListener{

	private TaskList<Command> _task_list = null;
	private Communication _com;
	
	Dispatcher(TaskList<Command> t, Communication com){
		this._task_list = t;
		this._com = com;
	}
	
	@Override
	public void contentsChanged(ListDataEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public synchronized void intervalAdded(ListDataEvent arg0) {
		// TODO Auto-generated method stub
		// Hier werden die Workerthreads implementiert
		// arg0.getindex0 -> gibt die stelle des Befehls wieder
		int pos = arg0.getIndex0();
		Command c = (Command) this._task_list.getElementAt(pos);
		
		String message = "CommandName:"+c.getName()+ " CommandFrom:"+c.getFrom();
		ArrayList<String> tmp = c.getArguments();
		for(String s : tmp)
		{
			message += " Argument:"+s;
		}
		Worker work = new Worker(message, this._com);
		work.start();
		
	}

	@Override
	public void intervalRemoved(ListDataEvent arg0) {
		// TODO Auto-generated method stub
		
	}

}
