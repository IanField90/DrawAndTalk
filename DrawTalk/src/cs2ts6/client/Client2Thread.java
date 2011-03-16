package cs2ts6.client;

import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.ObjectInputStream;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;

import cs2ts6.packets.ChatPacket;
import cs2ts6.packets.Packet;
import cs2ts6.packets.PointPacket;

/**
 * 
 * @author stephen
 *
 */
public class Client2Thread extends Thread{
	/**
	 * Internal reference to Canvas for draw packets
	 */
	private DrawingCanvas canvas;
	/**
	 * Internal reference to ChatPanel for chat packets
	 */
	private ChatPanel chat;
	private static final int port = 61021;
	boolean firstRun = true;
	private Client client;
	/**
	 * Constructor linking client to windows
	 * @param canv DrawingCanvas reference
	 * @param cht ChatPanel reference
	 */
	public Client2Thread(DrawingCanvas canv, ChatPanel cht, Client cli) {
		super("Client2");
		canvas = canv;
		chat = cht;
		client = cli;
	}
	public void setCanvas(DrawingCanvas canvas) {
		this.canvas = canvas;
	}
	public DrawingCanvas getCanvas() {
		return canvas;
	}
	public void setChat(ChatPanel chat) {
		this.chat = chat;
	}
	public ChatPanel getChat() {
		return chat;
	}
	public void invertRunParam() {
		if (firstRun) {
			firstRun = false;
		}
		else {
			firstRun = true;
		}
	}
	
	// Client/Server Architecture methods
	/**
	 * For recv points from the server
	 * PUBLIC method!
	 */
	public void sendPoints(PointPacket pnt) {
			canvas.drawPoints(pnt);
	}
	/**
	 * For recv chats from the server
	 * PUBLIC method!
	 */
	public void sendMessage(ChatPacket cht) {
			chat.drawMessage(cht);
	}
	
	
	/**
	 * Running the server watch thread (UDP)
	 */
	public void run() {
		byte[] buffer = new byte[5000];
		DatagramPacket packet;
		try {
			//Create a socket
			MulticastSocket socket = new MulticastSocket(port+1);
			InetAddress address = InetAddress.getByName("224.0.0.31");
			socket.joinGroup(address);
			while (true) {
				packet = new DatagramPacket(buffer, buffer.length);
				socket.receive(packet); // Will wait until new packet received - Hence separate thread
				
				if(firstRun) {
					firstRun = false;
					//Create thread to handle socket send
					new ClientSendThread(packet.getAddress(), this, client).start();
				}
				
				ByteArrayInputStream bais=new ByteArrayInputStream(buffer);		        
		        ObjectInputStream ois=new ObjectInputStream(new BufferedInputStream(bais));
		        Packet pkt = (Packet)ois.readObject();

		        //Check the packet type - perform action based on this
		        if(pkt instanceof PointPacket) {
		        	//Send packet to Canvas
		        	canvas.drawPoints((PointPacket)pkt);
		        }
		        if(pkt instanceof ChatPacket) {
		        	if(!((ChatPacket)pkt).get_sender().equals("KEEPALIVE")) {
		        		chat.drawMessage((ChatPacket)pkt);
		        	}
		        }
			}
		} catch (Exception e) {
			System.err.println("Exception");
		}
	}
	
}
