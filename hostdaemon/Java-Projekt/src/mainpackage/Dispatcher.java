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
		if(c.getStatus() != 100 && c.getStatus() != 103)
		{
			callWorker_Print(c);
			done = true;
		}
		//commands start
		if(name.equals("addclient"))
		{
			callWorker_addclient(c);
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
		if(name.equals("updatehwinfo"))
		{
			callWorker_updatehwinfo(c);
			done = true;
		}
		if(name.equals("updateswinfo"))
		{
			callWorker_updateswinfo(c);
			done = true;
		}
		if(name.equals("updateclientstatus"))
		{
			callWorker_updateclientstatus(c);
			done = true;
		}
		if(name.equals("updaterepolist"))
		{
			callWorker_updaterepolist(c);
			done = true;
		}
		//commands end
		
		if(!done)
			callWorker_Print(c);
		
	}

	@Override
	public void intervalRemoved(ListDataEvent arg0) {
		// TODO Auto-generated method stub
		
	}
	
	// The Worker class has to decide if the status = 103 the the worker hast recived an answer to an former command
	// in some case he has to update the db with that information e.g. install comes back, than he has to send an updateswinfo
	
	private void callWorker_addclient(Command c)
	{
		
	}
	private void callWorker_install(Command c)
	{
		
	}
	private void callWorker_start(Command c){
		
	}
	private void callWorker_stop(Command c){
		
	}
	private void callWorker_restart(Command c){
		
	}
	private void callWorker_updatehwinfo(Command c){
		
	}
	private void callWorker_updateswinfo(Command c){
		
	}
	private void callWorker_updateclientstatus(Command c){
		
	}
	private void callWorker_updaterepolist(Command c){
		
	}
	
	
	
	private void callWorker_Print(Command c){
		Worker_Print print = new Worker_Print(c, this._com, this._conf);
		print.start();
	}
	
}
