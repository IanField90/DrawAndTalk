package cs2ts6.server;

import java.io.IOException;
import java.util.*;

import cs2ts6.packets.*;

public class Server {
	
	private ArrayList<Packet> packets;
	
	Server() {
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
	
	public static void main(String[] args) throws IOException{
		System.out.println("Launching Server");
		Server srv = new Server();
		new CollectorServer(srv).start();
		new ServerUDPThread(srv).start();
	}
}
