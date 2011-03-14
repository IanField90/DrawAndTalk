package cs2ts6.server;

import java.awt.Color;
import java.io.IOException;
import java.util.*;

import cs2ts6.client.ChatPanel;
import cs2ts6.client.DrawingPanel.DrawType;
import cs2ts6.packets.*;

/**
 * 
 * @author Stephen
 *
 */
public class Server {
	
	private ArrayList<Packet> packets; //FIFO queue of packets awaiting broadcast
	private ChatPanel logWindow; // Used if in Thread mode, to write to the chat window
	
	Server(ChatPanel cp) {
		packets = new ArrayList<Packet>();
		packets.add(new PointPacket(1, 1, 1, 1, Color.WHITE, 1, DrawType.FULL_CLEAR));
		logWindow = cp;
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
	
	public void writeServerMessage(String message) {
		if(logWindow == null) { //Am in CLI mode - direct and not thread from GUI
			System.out.println(message);
		} else {
			logWindow.drawMessage(new ChatPacket("SERVER",message));
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
		Server srv = new Server(null); // Pass null as log window, so will use System.out
		new CollectorServer(srv).start(); //starts receives from client
		new ServerUDPThread(srv).start(); //starts broadcast thread
	}
}
