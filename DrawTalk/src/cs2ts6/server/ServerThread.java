package cs2ts6.server;

import java.io.IOException;
import java.util.ArrayList;

import cs2ts6.packets.ChatPacket;
import cs2ts6.packets.Packet;

/**
 * For running the server as a thread within the Client - Teacher and Testing
 * Embedded - allows 'master' client to create server via #startserver
 * @author stephen
 *
 */
public class ServerThread extends Thread{

	
	private ArrayList<Packet> packets;
	
	public ServerThread() {
		super("ServerThread");
		packets = new ArrayList<Packet>();
	}
	
	/**
	 * USed to get a packet top broadcast (Used by the ServerUDPThread)
	 * @return The packet to be transmitted
	 */
	public Packet getForBroadcast() {
		Packet bcast;
		if (packets.size() > 0) {
			bcast = packets.get(0);
			packets.remove(0);
			return bcast;
		}
		else {
			try {
				Thread.sleep(10);
			} catch (InterruptedException e) {}
			return new ChatPacket("KEEPALIVE","");
		}
	}
	
	public void addToBroadcast(Packet pkt) {
		packets.add(pkt);
	}
	
	public void run(){
		System.out.println("Launching Server");
		Server srv = new Server();
		new CollectorServer(srv).start();
		try {
			new ServerUDPThread(srv).start();
		} catch (IOException e) {
			System.err.println("Error in ServerUDP Broadcast");
		}
	}
}
