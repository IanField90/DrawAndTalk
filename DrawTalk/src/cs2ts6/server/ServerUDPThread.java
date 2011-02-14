package cs2ts6.server;

import java.awt.Color;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.UnknownHostException;

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
	 * Main execution cycle - sends broadcast messages to ALL connected clients
	 */
	public void run() {
		byte[] buffer = new byte[256];
		try {
			group = InetAddress.getByName("224.0.0.1"); //Broadcast group on Inet 130.0.0.1 broadcast - client match
		} catch (UnknownHostException e) {
			System.err.println("Unknown Host - Reduce error");
			e.printStackTrace();
		}
		while(true){ //Continuous Execution in Thread
			while(true) { // Only output packet when values are available - avoid NULL packet transmission/*Values in Buffer List*/
				PointPacket pnt = new PointPacket(1,2,3,4,Color.RED,6,cs2ts6.client.DrawingPanel.DrawType.PEN);
				try {
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
					socket.send(packet); // Send the packet
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}
	
	
}
