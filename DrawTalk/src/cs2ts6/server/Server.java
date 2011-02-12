package cs2ts6.server;

import java.io.*;
import java.net.*;
/**
 * TCP Server Implementation
 * @author stephen
 *
 */
public class Server {
	private static final int port = 61000;
	public static void main(String[] args) {
		ServerSocket svrSkt = null;
		
		try {
			svrSkt = new ServerSocket(port);
		} catch (IOException e) {
			System.err.println("Could not listen on port: "+port);
			System.exit(-1);
		}
		
		while (true) {
			try {
				new ServerThread(svrSkt.accept()).start();
			} catch (IOException e) {
				System.err.println("Error in client connection");
				e.printStackTrace();
			}
		}
	}
}
