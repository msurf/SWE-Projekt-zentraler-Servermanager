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
		boolean done = false;
		if(name.equals("reloadconfig"))
		{
			callWorker_reloadconfig(c);
			done = true;
		}
		if(name.equals("install"))
		{
			callWorker_install(c);
			done = true;
		}
		if(name.equals("start"))
		{
			callWorker_start(c);
			done = true;
		}
		if(name.equals("stop"))
		{
			callWorker_stop(c);
			done = true;
		}
		if(name.equals("restart"))
		{
			callWorker_restart(c);
			done = true;
		}
		/* Not in user because of direct response
		 * if(name.equals("hwinfo"))
			callWorker_HardwareInfo(c);
		if(name.equals("swinfo"))
			callWorker_SoftwareInfo(c);
		*/
		if(!done)
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
		
	private void callWorker_reloadconfig(Command command){
		
	}
	private void callWorker_install(Command command){
		
	}
	private void callWorker_start(Command command){
		
	}
	private void callWorker_stop(Command command){
		
	}
	private void callWorker_restart(Command command){
		
	}
	

	private void callWorker_Print(Command c){
		Worker_Print print = new Worker_Print(c, this._com, this._conf);
		print.start();
	}
	
}
