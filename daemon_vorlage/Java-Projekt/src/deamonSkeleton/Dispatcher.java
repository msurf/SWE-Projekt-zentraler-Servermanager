package deamonSkeleton;


import javax.swing.event.ListDataEvent;
import javax.swing.event.ListDataListener;

public class Dispatcher implements ListDataListener{

	private TaskList<Command> _task_list = null;
	private Communication _com;
	private Config _conf;
	
	Dispatcher(TaskList<Command> t, Communication com, Config conf){
		this._task_list = t;
		this._com = com;
		this._conf = conf;
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
		String name= c.getName();
		
		if(name.equals("hardwareinfo"))
			callWorker_HardwareInfo();
		else
			callWorker_Print(c);
	}

	@Override
	public void intervalRemoved(ListDataEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	private void callWorker_HardwareInfo(){
		Worker_HardwareInfo work = new Worker_HardwareInfo(this._com, this._conf);
		work.start();
	}
	
	private void callWorker_Print(Command c){
	}
	
}
