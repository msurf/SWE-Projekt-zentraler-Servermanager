package mainpackage;

import java.util.Scanner;

public class INIT {
	
	public static void main(String[] args) {
		String ip ="localhost";
		int port = 5550;

		ip = getText("Sending to IP=");
		
		
		Scanner sc = new Scanner(System.in);
		int input = -1;
		while(input != 0){
			System.out.println("0 -> quit \n1 -> build Command");
			try{input=sc.nextInt();}catch(Exception e){}
			if(input == 1)
			{
				Command c = new Command().buildCommand();
				OutputThread out = new OutputThread(ip, port, c);
				out.start();
				input = -1;
				
				String conf = getText("Do you want to store Command?(y/n)");
				String conf2 = conf.toLowerCase();
				if(conf2.equals("y") || conf2.equals("yes"))
				{
					Packer p = new Packer(getText("Please insert Filename:"));
					p.pack(c);
				}
			}
		}
	}
	private static String getText(String message){
		Scanner sc = new Scanner(System.in);
		try{
			System.out.println(message);
			String tmp = sc.nextLine();
			if(tmp.length() > 0)
				return tmp;
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return "default";
	}	
}
