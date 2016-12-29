import java.io.BufferedReader;
import java.io.FileReader;
import java.util.*;

public class PacketRouting {
	private static ArrayList<RTNode> RT;
	public static void main(String[] args) {
		RT = new ArrayList<RTNode>();
		String rtpath = "/Users/josh/Documents/rt.txt";
		String packetspath = "/Users/josh/Documents/packets.txt";
		
		// Load the routing table into memory
		makeRT(rtpath);
		// Display the values in the routing table
		System.out.println("Displaying routing table:");
		
		for ( int i = 0; i < RT.size(); i++ ) {
			System.out.println(RT.get(i).getMask() + " " + RT.get(i).getDest() + " " + RT.get(i).getNextHop() + " " + RT.get(i).getFlag() + " " + RT.get(i).getRtInterface() );
		}
		System.out.println("-------------------------------------------------------------------");
		// Process the outgoing packets
		simulate(packetspath);
	}
	
	static void makeRT( String filename ) {
		String mask;
		String dest;
		String next;
		String flag;
		String rtInterface;
		String[] tokens; // Array of the tokens from the line
		try {
			BufferedReader reader = new BufferedReader(new FileReader(filename));
		    String line;
		    while ((line = reader.readLine()) != null) {
		    	// Splitting each line into separate strings using whitespace as the delimeter
		    	tokens = line.split("\\s+");
		    	mask = tokens[0];
		    	dest = tokens[1];
		    	next = tokens[2];
		    	flag = tokens[3];
		    	rtInterface = tokens[4];
		    	// Insert values into routing table
		    	RT.add( new RTNode(mask, dest, next, flag, rtInterface));
		    }
		    reader.close();
		}
		catch (Exception e) {
			  System.err.format("Exception occurred trying to read '%s'.", filename);
			  e.printStackTrace();
		}
	}
	static void simulate( String filename ) {
		try {
			BufferedReader reader = new BufferedReader(new FileReader(filename));
		    String line;
		    while ((line = reader.readLine()) != null) {
		    	// Process our destination address
		    	findRoute(line);
		    }
		    reader.close();
		}
		catch (Exception e) {
			  System.err.format("Exception occurred trying to read '%s'.", filename);
			  e.printStackTrace();
		}
	}
	static void findRoute( String dest ) {
		//System.out.println("Calculating route for "+dest);
		String outHostID = "";
		
		String[] ids = dest.split("\\.");
		String[] destids;

		// Look through our Routing Table
		for (int i=0; i < RT.size(); i++) {
			// Check if it is a Direct route
			if ( RT.get(i).getFlag().equals("Direct") ) {
				// Check if we have the destination available
				outHostID = RT.get(i).getDest(); // We only need the following 192.168.X
				destids = outHostID.split("\\.");

				if ( ids[0].equals(destids[0]) && ids[1].equals(destids[1]) && ids[2].equals(destids[2]) ) {
					// We know the destination interface
					System.out.println("Packet with destination address "+dest+" will be forwarded to "+outHostID+" out on interface "+RT.get(i).getRtInterface()+"");
				}
				outHostID = "";
			}
			// Otherwise if it is Network-Specific
			else {
				outHostID = RT.get(i).getDest(); // We only need the following 192.168.X
				destids = outHostID.split("\\.");
				
				if ( ids[0].equals(destids[0]) && ids[1].equals(destids[1]) && ids[2].equals(destids[2]) ) {
					// We know the destination interface
					System.out.println("Packet with destination address "+dest+" will be forwarded to "+outHostID+" and sent out on "+RT.get(i).getNextHop()+" on interface "+RT.get(i).getRtInterface());
					//Set the new ip to the next hop
					dest = RT.get(i).getNextHop();
					// Find the new route for the new destination
					//findRoute(dest);
				}
				outHostID = "";
			}
		}
	}
}
