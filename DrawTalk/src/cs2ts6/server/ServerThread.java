package cs2ts6.server;

import java.io.IOException;

import cs2ts6.client.ChatPanel;

/**
 * For running the server as a thread within the Client - Teacher and Testing
 * Embedded - allows 'master' client to create server via #startserver
 * @author stephen
 *
 */
public class ServerThread extends Thread{

	
	private ChatPanel log;
	
	public ServerThread(ChatPanel cp) {
		super("ServerThread");
		log = cp;
	}
	/**
	 * Launches the server clas within a thread, so it can be ran as part of a client setup
	 */
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
