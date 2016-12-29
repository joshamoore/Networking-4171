import java.io.*;
import java.nio.file.Files;
import java.util.*;

/*
 * Important: FDB file must start with 1 integer representing ports,
 * followed by 1 character, 1 space, 1 integer on the following lines
 * example:
 * 4
 * A 1
 * B 2
 * etc...
 * 
 * This program also requires the class file FDBNode.java
 * */

public class BridgeSimulator {
	private static int ports;
	private static ArrayList<FDBNode> FDB;
	
	public static void main(String[] args) throws IOException {
		FDB = new ArrayList<FDBNode>();
		
		// Read the following files
		String fdbpath = "/Users/josh/Documents/fdb.txt";
		String framepath = "/Users/josh/Documents/frames.txt";
		
		// This will read and generate the FDB to work from
		readFDB( fdbpath );
		
		// FDB is created, we can now run the simulation by processing the frames
		simulate(framepath);
		System.out.println("--------------------\nUpdated FDB:");
		// Updated FDB
		for ( int i = 0; i < FDB.size(); i++ ) {
			System.out.println(FDB.get(i).getAddress() + " " + FDB.get(i).getPort_out() );
		}
	}
	// Loads the FDB from a file
	// Note that address and port should be separated by 1 space
	static void readFDB( String filename ) {
		int count = 0;
		String curr;
		String addr;
		int port;
		try {
			BufferedReader reader = new BufferedReader(new FileReader(filename));
		    String line;
		    while ((line = reader.readLine()) != null)
		    {
		    	curr = line;
		    	if ( count!=0 ) {
		    		// Convert the line into a String and int
		    	    addr = curr.substring(0,1);
		    	    port = Integer.parseInt(curr.substring(2,3));
		    	    FDB.add( new FDBNode(addr, port) );
		    	}
		    	else {
		    		ports = Integer.parseInt(curr);
		    	}
		    	count++;
		    }
		    reader.close();
		  }
		  catch (Exception e)
		  {
			  System.err.format("Exception occurred trying to read '%s'.", filename);
			  e.printStackTrace();
		  }
	}
	// This is the main simulation function
	static void simulate( String filename ) {
		boolean found = false;
		boolean destfound = false;
		String src;
		String dest;
		int out;
		String res = "";
		// Process the second file containing the frames
		try {
			BufferedReader reader = new BufferedReader(new FileReader(filename));
		    String line;
		    while ((line = reader.readLine()) != null)
		    {
		    	src = line.substring(0,1);
		    	dest = line.substring(2,3);
		    	out = Integer.parseInt(line.substring(4,5));
		    	
		    	// There are 4 possible outcomes
		    	for ( int i=0; i<FDB.size(); i++ ) {
		    		//System.out.println(FDB.get(i).getPort_out()+" "+FDB.get(i).getAddress()+" portout="+out+" addr1="+src+" dest="+dest);
		    		// If they use the same port
		    		if ( FDB.get(i).getAddress().equals(dest) && FDB.get(i).getPort_out()==out ) {
		    			res = "Frame discarded";
		    		}
		    		else if ( FDB.get(i).getAddress().equals(dest) && FDB.get(i).getPort_out()!=out ) {
		    			res = "Frame sent on "+FDB.get(i).getPort_out();
		    		}
		    		if ( FDB.get(i).getAddress().equals(src) ) {
		    			found = true;
		    		}
		    		if (FDB.get(i).getAddress().equals(dest)) {
		    			destfound = true;
		    		}
		    		
		    	}
		    	if (!found) {
		    		if (out>ports) {
		    			System.out.println("Error, port not available.");
		    		}
		    		else {
		    			updateFDB(src, out);
		    			System.out.println("Updating FDB: "+src+" on port "+out+"");
		    		}
		    	}
		    	if (!destfound) {
		    		res = "Frame broadcast on all out ports";
		    	}
		    	System.out.println(src+" "+dest+" "+out+" "+res);
		    	res = "";
		    	found = false;
		    	destfound = false;
		    }
		    reader.close();
		  }
		  catch (Exception e)
		  {
			  System.err.format("Exception occurred trying to read '%s'.", filename);
			  e.printStackTrace();
		  }
	}
	// Updates a the FDB
	static void updateFDB( String address, int port ) {
		FDB.add( new FDBNode(address, port) );
	}
}
