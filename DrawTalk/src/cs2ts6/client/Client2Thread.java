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
	
	
	/**
	 * Constructor linking client to windows
	 * @param canv DrawingCanvas reference
	 * @param cht ChatPanel reference
	 */
	public Client2Thread(DrawingCanvas canv, ChatPanel cht) {
		super("Client2");
		canvas = canv;
		chat = cht;
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
	
	
	// Client/Server Architecture methods
	/**
	 * For sending points to the server
	 * PUBLIC method!
	 */
	public void sendPoints(PointPacket pnt) {
		//TODO Implement Socket send to server - probably run a new thread
		canvas.drawPoints(pnt);
	}
	/**
	 * For sending chats to the server
	 * PUBLIC method!
	 */
	public void sendMessage(ChatPacket cht) {
		//TODO Implement Socket send to server - probably run a new thread
		chat.drawMessage(cht);
	}
	
	
	/**
	 * Running the server watch thread (UDP)
	 */
	public void run() {
		byte[] buffer = new byte[5000];
		DatagramPacket packet;
		try {
			MulticastSocket socket = new MulticastSocket(port+1);
			InetAddress address = InetAddress.getByName("224.0.0.1");
			socket.joinGroup(address);
			while (true) {
				packet = new DatagramPacket(buffer, buffer.length);
				socket.receive(packet); // Will wait until new packet received - Hence separate thread
				ByteArrayInputStream bais=new ByteArrayInputStream(buffer);		        
		        ObjectInputStream ois=new ObjectInputStream(new BufferedInputStream(bais));
		        Packet pkt = (Packet)ois.readObject();
		        System.out.println(pkt.toString());
		        System.out.println(packet.getAddress().toString());
		        if(pkt instanceof PointPacket) {
		        	//Send packet to Canvas
		        	//canvas.drawPoints((PointPacket)pkt);
		        }
		        if(pkt instanceof ChatPacket) {
		        	//chat.drawMessage((ChatPacket)pkt);
		        }
			}
		} catch (Exception e) {
			System.err.println("Exception");
		}
	}
	
}
