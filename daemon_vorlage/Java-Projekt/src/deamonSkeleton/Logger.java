package deamonSkeleton;


public class Logger {

	private String _path = System.getProperty("user.dir");
	private String _file = "output";
	
	Logger(String path, String file){
		this._path = path;
		this._file = file;
	}
	
	Logger(String path){
		this._path = path;
	}
	

	public void write(String message){
		try{
		ShellRunner shell = new ShellRunner();
		shell.execute("echo '"+ message +"'>>"+this._path+"/"+this._file);
		}
		catch(Exception e)
		{
			System.out.println("Logwriter failed! Please Check!");
			e.printStackTrace();
		}
	}
	
}
