package cs2ts6.server;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.UnknownHostException;

import cs2ts6.packets.Packet;

/**
 * Multi-Thread Server UDP (Transmit pkts)
 * @author stephen
 *
 */
public class ServerUDPThread extends Thread{
	
	/**
	 * Datagram Socket for MultiCast UDP
	 */
	private DatagramSocket socket = null;
	/**
	 * Constant port
	 */
	private static final int port = 61021;
	
	/**
	 * Packet for transmission
	 */
	private DatagramPacket packet;
	
	private Server server;
	
	/**
	 * Inet Address group
	 */
	private InetAddress group;
	/**
	 * Constructs DataGram Class
	 * @throws IOException to allow for exit - premature disconnect
	 */
	public ServerUDPThread(Server srv) throws IOException {
        super("UDPServerThread"); //Thread super constructor - with thread name
        socket = new DatagramSocket(port); //Datagram port setup
        server = srv;
    }
	
	/**
	 * Main execution cycle - sends broadcast messages to ALL connected clients
	 */
	public void run() {
		byte[] buffer = new byte[1024];
		try {
			group = InetAddress.getByName("224.0.0.31"); //Broadcast group on Inet 130.0.0.1 broadcast - client match
		} catch (UnknownHostException e) {
			System.err.println("Unknown Host - Reduce error");
			e.printStackTrace();
		}
		while(true){ //Continuous Execution in Thread
			while(true) { // Only output packet when values are available - avoid NULL packet transmission/*Values in Buffer List*/
				//Prepare data for transmission
				try {
					Packet pnt = server.getForBroadcast(); //Takes packet list item from queue
					ByteArrayOutputStream baos = new ByteArrayOutputStream();
					ObjectOutputStream oos = new ObjectOutputStream(baos);
					oos.writeObject(pnt); // prepares the data 'packet' for drawing or chat
						oos.flush();
						buffer = baos.toByteArray();          // The packet to transmit - grab from sync method in mainserver class arraylist
				} catch (IOException e) {
					System.err.println("Cannot serialize");
				}
				//Attempt to transmit data
				packet = new DatagramPacket(buffer, buffer.length, group, port+1); // Construct the packet - DEST PORT = serverport+1
				try {
					socket.send(packet); //Finally send the packet to clients
				} catch (Exception e) {
					System.err.println("Error in sending a UDP Broadcast");
				}
			}
		}
	}
	
	
}
