package cs2ts6.server;

import java.io.*;
import java.net.*;

import cs2ts6.packets.*;
/**
 * TCP Server Implementation
 * This thread will stay alive the WHOLE time the client is connected and upon disconnect, it will close.
 * @author stephen
 *
 */
public class CollectorServerThread extends Thread{
	private Socket skt;
	ObjectInputStream ois;
	Packet pkt;
	Server server;
	
	/**
	 * 
	 * @param connection Socket between client and server
	 * @param srv Class packets are pushed to
	 */
	CollectorServerThread(Socket connection, Server srv) {
		super();
		skt = connection;
		server = srv;
	}
	
	public void run() {
		try {
			ois = new ObjectInputStream(skt.getInputStream());
			do {
				pkt = (Packet)ois.readObject(); //Casts packet
				server.addToBroadcast(pkt); // Queues packet for broadcast
			} while (pkt != null);
		} catch (IOException e) {
			System.err.println("Error In establishing Tunnel for TCP communication");
		} catch (ClassNotFoundException e) {}
	}
}