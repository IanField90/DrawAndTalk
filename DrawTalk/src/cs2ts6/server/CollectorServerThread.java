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
	
	CollectorServerThread(Socket connection, Server srv) {
		super();
		skt = connection;
		server = srv;
	}
	
	public void run() {
		try {
			ois = new ObjectInputStream(skt.getInputStream());
			do {
				pkt = (Packet)ois.readObject();
				//System.out.println((PointPacket)pkt);
				server.addToBroadcast(pkt);
			} while (pkt != null);
		} catch (IOException e) {
			System.err.println("Error In establishing Tunnel for TCP communication");
		} catch (ClassNotFoundException e) {}
	}
}