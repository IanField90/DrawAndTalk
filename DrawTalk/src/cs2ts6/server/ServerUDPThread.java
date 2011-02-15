package cs2ts6.server;

import java.awt.Color;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.UnknownHostException;

import cs2ts6.packets.Packet;
import cs2ts6.packets.PointPacket;

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
        super("UDPServerThread"); //Thread super constructor
        socket = new DatagramSocket(port); //Datagram port setup
        server = srv;
    }
	
	/**
	 * Main execution cycle - sends broadcast messages to ALL connected clients
	 */
	public void run() {
		byte[] buffer = new byte[256];
		int ctr = 0; //for testing
		try {
			group = InetAddress.getByName("224.0.0.31"); //Broadcast group on Inet 130.0.0.1 broadcast - client match
		} catch (UnknownHostException e) {
			System.err.println("Unknown Host - Reduce error");
			e.printStackTrace();
		}
		while(true){ //Continuous Execution in Thread
			while(true) { // Only output packet when values are available - avoid NULL packet transmission/*Values in Buffer List*/
				//PointPacket pnt = new PointPacket(ctr,2,3,4,Color.RED,6,cs2ts6.client.DrawingPanel.DrawType.PEN); // WAS FOR TESTING
				try {
					Packet pnt = server.getForBroadcast();
					ByteArrayOutputStream baos = new ByteArrayOutputStream();
					ObjectOutputStream oos = new ObjectOutputStream(baos);
					oos.writeObject(pnt);
						oos.flush();
						buffer = baos.toByteArray();          // The packet to transmit - grab from sync method in mainserver class arraylist
				} catch (IOException e) {
					System.err.println("Cannot serialize");
				}
				packet = new DatagramPacket(buffer, buffer.length, group, port+1); // Construct the packet - DEST PORT = serverport+1
				try {
					//System.out.println(packet);
					socket.send(packet); // Send the packet
					//ctr++;
				} catch (Exception e) {
					System.err.println("Error in sending a UDP Broadcast");
				}
			}
		}
	}
	
	
}
