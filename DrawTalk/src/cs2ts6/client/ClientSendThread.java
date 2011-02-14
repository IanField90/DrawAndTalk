package cs2ts6.client;

import java.awt.Color;
import java.io.*;
import java.net.*;

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
	int ctr = 0;
	
	ClientSendThread() {
		super("ClientSendThread");
	}
	
	public void run() {
		//Setup sockets
		Socket skt = null;
		try {
			skt = new Socket("127.0.0.1",port+2);
			oos = new ObjectOutputStream(skt.getOutputStream());
		} catch (IOException e) {
			System.err.println("Error setting up ClientSender\nCan only listen");
		}
		
		try {
			while(true) {
				PointPacket pnt = new PointPacket(ctr,2,3,4,Color.RED,6,cs2ts6.client.DrawingPanel.DrawType.PEN);
				pkt = pnt;
				
				oos.writeObject(pkt);
				oos.flush();
				Thread.sleep(1000);
				ctr++;
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			System.err.println("Cannot send Packet");
			e.printStackTrace();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
