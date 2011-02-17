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
	String username = "dflt", ip = "dflt";
	
	/**
	 * 
	 * @param connection Socket between client and server
	 * @param srv Class packets are pushed to
	 */
	CollectorServerThread(Socket connection, Server srv) {
		super();
		skt = connection;
		server = srv;
		ip = connection.getInetAddress().toString();
	}
	
	public void run() {
		try {
			ois = new ObjectInputStream(skt.getInputStream());
			pkt = (Packet)ois.readObject(); //Casts packet
			if(pkt instanceof ChatPacket) { // A 'no-data packet sent from the client
				username = ((ChatPacket)pkt).get_message();
			}
			server.writeServerMessage("Client Connected\n   "+username+"@"+ip);
			do {
				pkt = (Packet)ois.readObject(); //Casts packet
				server.addToBroadcast(pkt); // Queues packet for broadcast
			} while (pkt != null);
		} catch (IOException e) {
			server.writeServerMessage("Client Diconnected\n   "+username+"@"+ip);
		} catch (ClassNotFoundException e) {}
	}
}