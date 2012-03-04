package mainpackage;

import java.util.Scanner;

public class INIT {
	
	public static void main(String[] args) {
		String ip ="localhost";
		int port = 5550;
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
			}
			
		}
	}

}
