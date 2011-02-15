package cs2ts6.client;

import java.awt.Color;
import java.io.*;
import java.net.*;

import javax.swing.JOptionPane;

import cs2ts6.packets.Packet;
import cs2ts6.packets.PointPacket;
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
		try {
			while(true) {
				//PointPacket pnt = new PointPacket(ctr,2,3,4,Color.RED,6,cs2ts6.client.DrawingPanel.DrawType.PEN);
				pkt = client.getPacketToSend();
				//pkt = pnt;
				//System.out.println(pkt);
				oos.writeObject(pkt);
				oos.flush();
				//ctr++;
			}
		} catch (Exception e) {
			System.err.println("Cannot send Packet");
			// Reset Calling Thread so that it can be re-instantiated on next successful communication
			caller.invertRunParam();
			e.printStackTrace();
		}
	}
}
