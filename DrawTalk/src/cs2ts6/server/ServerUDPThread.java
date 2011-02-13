package cs2ts6.server;

import java.io.IOException;
import java.net.DatagramSocket;

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
	 * Constructs DataGram Class
	 * @throws IOException
	 */
	public ServerUDPThread() throws IOException {
        super("UDPServerThread"); //Thread super constructor
        socket = new DatagramSocket(port); //Datagram port setup
    }
	
	
}
