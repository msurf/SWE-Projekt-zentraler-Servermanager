package mainpackage;

import java.io.*;
import java.beans.*;

public class Packer {
	
	private String filename = "/home/user/command1.xml";
    
	Packer(String file)
	{
		this.filename = file;
	}
	public void  pack(Command c){
    XMLEncoder enc = null;
    try
    {
      enc = new XMLEncoder( new FileOutputStream(filename) );
      enc.writeObject(c);
    }
    catch ( IOException e ) {
      e.printStackTrace();
    }
    finally {
      if ( enc != null )
        enc.close();
    }
    }
}