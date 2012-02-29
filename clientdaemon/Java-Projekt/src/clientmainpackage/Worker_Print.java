package clientmainpackage;

public class Worker_Print extends Worker{
	
	Worker_Print(Command command, Communication com, Config conf) {
		super(command, com, conf);
	}
	
	public void run() {
		printdef();
		//print(this._command.clone());
		//log(this._message);
	}//run()
	
	private void printdef(){System.out.println("working on Command: "+this._command.getName());}
	
	/*private void print(Command c){
		System.out.println("+-------------------------");
		System.out.println("| Name -> " + c.getName());
		System.out.println("| From -> " + c.getFrom());
		System.out.println("| Status -> "+ c.getStatus());
		System.out.println("| Info -> "+ c.getInfo());
		System.out.println("+-------------------------");
	}//print()*/
}
