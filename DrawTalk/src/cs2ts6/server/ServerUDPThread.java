package cs2ts6.server;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.UnknownHostException;

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
	
	/**
	 * Inet Address group
	 */
	private InetAddress group;
	/**
	 * Constructs DataGram Class
	 * @throws IOException to allow for exit - premature disconnect
	 */
	public ServerUDPThread() throws IOException {
        super("UDPServerThread"); //Thread super constructor
        socket = new DatagramSocket(port); //Datagram port setup
    }
	
	/**
	 * Main execution cycle
	 */
	public void run() {
		byte[] buffer;
		while(true){ //Continuous Execution in Thread
			while(/*Values in Buffer List*/) { // Only output packet when values are available - avoid NULL packet transmission
				//buffer =             // The packet to transmit - grab from sync method in mainserver class arraylist
				try {
					group = InetAddress.getByName("130.0.0.1"); //Broadcast group on Inet 130.0.0.1 broadcast - client match
				} catch (UnknownHostException e) {
					System.err.println("Unknown Host - Reduce error");
					e.printStackTrace();
				}
				packet = new DatagramPacket(buffer, buffer.length, group, port+1); // Construct the packet - DEST PORT = serverport+1
				socket.send(packet); // Send the packet
			}
		}
	}
	
	
}
