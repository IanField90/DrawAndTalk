package cs2ts6.server;

import java.io.IOException;
import java.util.*;

import cs2ts6.packets.*;

/**
 * 
 * @author Stephen
 *
 */
public class Server {
	
	private ArrayList<Packet> packets; //FIFO queue of packets awaiting broadcast
	
	Server() {
		packets = new ArrayList<Packet>();
	}
	/**
	 * USed to get a packet top broadcast (Used by the ServerUDPThread)
	 * @return The packet to be transmitted
	 */
	public Packet getForBroadcast() {
		Packet bcast;
		//Keep taking first element while element exists
		if (packets.size() > 0) {
			bcast = packets.get(0);
			packets.remove(0);
			return bcast;
		}
		else {
			try {
				Thread.sleep(10); //pause for 10ms
			} catch (InterruptedException e) {}
			return new ChatPacket("KEEPALIVE",""); // Ensures clients can connect
		}
	}
	
	/**
	 * 
	 * @param pkt Packet to be added to the packet queue, not sent immediately.
	 */
	public void addToBroadcast(Packet pkt) {
		packets.add(pkt);
	}
	
	public static void main(String[] args) throws IOException{
		System.out.println("Launching Server");
		Server srv = new Server();
		new CollectorServer(srv).start(); //starts receives from client
		new ServerUDPThread(srv).start(); //starts broadcast thread
	}
}
