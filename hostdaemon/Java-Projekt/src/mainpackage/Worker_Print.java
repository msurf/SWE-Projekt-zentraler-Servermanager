package mainpackage;

public class Worker_Print extends Worker{
	
	Worker_Print(Command command, Communication com, Config conf) {
		super(command, com, conf);
	}
	
	public void run() {
		printdef();
	}//run()
	
	private void printdef(){System.out.println("working on Command: "+this._command.getName());}
	
	
}
