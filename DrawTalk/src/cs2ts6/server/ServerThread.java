package cs2ts6.server;

import java.net.Socket;
/**
 * TCP Server Implementation
 * @author stephen
 *
 */
public class ServerThread extends Thread{
	private Socket skt;
	ServerThread(Socket connection) {
		super();
		skt = connection;
	}
	
	public void run() {
		
	}
}
