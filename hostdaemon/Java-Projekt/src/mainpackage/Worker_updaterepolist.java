package mainpackage;

import java.util.ArrayList;

public class Worker_updaterepolist extends Worker{

	public Worker_updaterepolist(Command command, Config conf, Communication com) {
		super();
		this._command = command;
		this._conf = conf;
		this._com = com;
	}
	public void run(){
		updateRepo();
	}
	
	private void updateRepo(){
		ShellRunner shell = new ShellRunner();
		shell.execute("ls /repository");
		ArrayList<String> tmp = shell.getOutput();
		ArrayList<String> hard = new ArrayList<String>();
		try{
		for(String i : tmp)
			hard.add(i.split("out:")[1].replace(".tar.gz", "").trim());
		}catch(Exception e){
			this._command.setStatus(200);
			System.out.println("Problems while splitting Repolist");}
		
		ArrayList<String> delete = new ArrayList<String>();
		Database base = new Database();
		try{
			String[] db = base.getInfo_getRepoList().split("#");
			for(String i : db)
				if(!hard.contains(i))
					delete.add(i);
					
			for(String i : delete)
				base.delSoftware(i);
					
			for(String i : hard)
				base.insertSoftware(i, i+".tar.gz", this._conf.getIP_own());
		}catch(Exception e){
			this._command.setStatus(200);
			System.out.println("Cannot access Database");}
	}
}
