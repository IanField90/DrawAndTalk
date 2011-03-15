package cs2ts6.server;

import java.io.IOException;
import java.util.ArrayList;

import cs2ts6.client.ChatPanel;
import cs2ts6.packets.Packet;

/**
 * For running the server as a thread within the Client - Teacher and Testing
 * Embedded - allows 'master' client to create server via #startserver
 * @author stephen
 *
 */
public class ServerThread extends Thread{

	
	private ArrayList<Packet> packets; //TODO STEPHEN: Maybe remove for removal of warnings?
	private ChatPanel log;
	
	public ServerThread(ChatPanel cp) {
		super("ServerThread");
		log = cp;
		packets = new ArrayList<Packet>(); //TODO STEPHEN used here too
	}
	
	public void run(){
		System.out.println("Launching Server");
		Server srv = new Server(log);
		new CollectorServer(srv).start();
		try {
			new ServerUDPThread(srv).start();
		} catch (IOException e) {
			System.err.println("Error in ServerUDP Broadcast");
		}
	}
}
