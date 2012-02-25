package mainpackage;


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
		
		if(name.equals("hwinfo"))
			callWorker_HardwareInfo(c);
		if(name.equals("swinfo"))
			callWorker_SoftwareInfo(c);
		else
			callWorker_Print(c);
	}

	@Override
	public void intervalRemoved(ListDataEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	private void callWorker_HardwareInfo(Command command){
		System.out.println("Worker Hardwareinfo");
		Worker_HardwareInfo work = new Worker_HardwareInfo(this._com, this._conf, command);
		work.start();
	}
	private void callWorker_SoftwareInfo(Command command){
		
	}
	
	private void callWorker_Print(Command c){
		Worker_Print print = new Worker_Print(c, this._com, this._conf);
		print.start();
	}
	
}
