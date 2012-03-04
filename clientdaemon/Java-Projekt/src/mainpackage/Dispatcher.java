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
		if(c.getStatus() != 100)
		{
			callWorker_Print(c);
			done = true;
		}
		
		if(!done && name.equals("reloadconfig"))
		{
			callWorker_reloadconfig(c);
			done = true;
		}
		if(!done && name.equals("install"))
		{
			callWorker_install(c);
			done = true;
		}
		if(!done && name.equals("start"))
		{
			callWorker_start(c);
			done = true;
		}
		if(!done && name.equals("stop"))
		{
			callWorker_stop(c);
			done = true;
		}
		if(!done && name.equals("restart"))
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

	/*private void callWorker_HardwareInfo(Command command){
		System.out.println("Worker Hardwareinfo");
		Worker_HardwareInfo work = new Worker_HardwareInfo(this._com, this._conf, command);
		work.start();
	}*/
		
	private void callWorker_reloadconfig(Command command){
		Worker_reloadconfig reload = new Worker_reloadconfig(command, this._conf, this._com);
		reload.start();
	}
	private void callWorker_install(Command command){
		Worker_install install = new Worker_install(command, this._conf, this._com);
		install.start();
	}
	private void callWorker_start(Command command){
		Worker_start start = new Worker_start(command, this._conf, this._com);
		start.start();
	}
	private void callWorker_stop(Command command){
		Worker_stop stop = new Worker_stop(command, this._conf, this._com);
		stop.start();
	}
	private void callWorker_restart(Command command){
		Worker_restart restart = new Worker_restart(command, this._conf, this._com);
		restart.start();
	}
	

	private void callWorker_Print(Command c){
		Worker_Print print = new Worker_Print(c, this._com, this._conf);
		print.start();
	}
	
}
