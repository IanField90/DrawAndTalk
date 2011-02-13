package cs2ts6.server;

import java.net.Socket;
/**
 * TCP Server Implementation
 * @author stephen
 *
 */
public class CollectorServerThread extends Thread{
	private Socket skt;
	CollectorServerThread(Socket connection) {
		super();
		skt = connection;
	}
	
	public void run() {
		
	}
}