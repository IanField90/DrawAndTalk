package cs2ts6.client;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;

import javax.swing.JOptionPane;

import cs2ts6.packets.Packet;
/**
 * 
 * @author stephen
 *
 */
public class ClientSendThread extends Thread{
	
	private static final int port = 61000;
	ObjectOutputStream oos;
	Packet pkt;
	InetAddress serverAddress;
	private Client client;
	private Client2Thread caller;
	int ctr = 0;
	
	/**
	 * 
	 * @param add Address of server
	 * @param cliThrd reference to the calling thread
	 * @param cli Container for packet list to be handled
	 */
	ClientSendThread(InetAddress add, Client2Thread cliThrd, Client cli) {
		super("ClientSendThread");
		serverAddress = add;
		System.out.println("Server address: "+serverAddress.toString());
		caller = cliThrd;
		client = cli;
	}
	
	public void run() {
		//Setup sockets
		Socket skt = null;
		try {
			skt = new Socket(serverAddress,port+2);
			oos = new ObjectOutputStream(skt.getOutputStream());
		} catch (IOException e) {
			System.err.println("Error setting up ClientSender\nCan only listen");
		}
		//Inform user everyting is ready to GO (Both send/recieve active)
		JOptionPane.showMessageDialog(null,"You can now send to the server\nServer detected at: "+serverAddress.toString());
		client.onServerSet(true);
		try {
			while(true) {
				pkt = client.getPacketToSend();
				oos.writeObject(pkt);
				oos.flush();
			}
		} catch (Exception e) {
			System.err.println("Cannot send Packet");
			JOptionPane.showMessageDialog(null,"No Longer connected to server");
			client.onServerSet(false);
			// Reset Calling Thread so that it can be re-instantiated on next successful communication
			caller.invertRunParam(); // If server disconnects - resets client to 'searching'
		}
	}
}
