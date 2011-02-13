package cs2ts6.server;

import java.io.IOException;
import java.net.ServerSocket;

/**
 * TCP Connections to Server
 * Depositing Packets for transmission
 * @author stephen
 *
 */
public class CollectorServer extends Thread{
	
	private static final int port = 61000;
	/**
	 * Server Constructor
	 */
	CollectorServer() {
		super("CollectorServer");
	}
	
	/**
	 * Main method - ported from server
	 */
	public void run() {
		ServerSocket svrSkt = null;
		try {
			svrSkt = new ServerSocket(port);
		} catch (IOException e) {
			System.err.println("Could not listen on port: "+port);
			System.exit(-1);
		}
		
		while (true) {
			try {
				new CollectorServerThread(svrSkt.accept()).start();
			} catch (IOException e) {
				System.err.println("Error in client connection");
				e.printStackTrace();
			}
		}
	}
}
