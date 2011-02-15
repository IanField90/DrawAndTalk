package cs2ts6.client;

import java.util.ArrayList;

import cs2ts6.packets.*;

public class Client {
	private ArrayList<Packet> packets;
	private DrawingCanvas canvas;
	private ChatPanel chat;
	/**
	 * Construct the client class with refernces to canvas and chat windows
	 * @param cnv Canvas reference
	 * @param cht Chat window reference
	 */
	public Client(DrawingCanvas cnv, ChatPanel cht) {
		canvas = cnv;
		chat = cht;
		packets = new ArrayList<Packet>();
		// Start UDP Listening Thread
		new Client2Thread(canvas, chat, this).start();
	}
	/**
	 * Send point to server, will auto return message back in mock client
	 * @param pnt
	 */
	public void sendPoints(PointPacket pnt) {
		//canvas.drawPoints(pnt);
		packets.add(pnt);
	}
	/**
	 * Send message t server, will auto return to chat window for mock client
	 * @param cht
	 */
	public void sendMessage(ChatPacket cht) {
		chat.drawMessage(cht);
		//packets.add(cht);
	}
	/**
	 * Used by CleintSendThread for communicating a packet from Client->Server
	 * @return The packet to be transmitted
	 */
	public Packet getPacketToSend() {
		//System.out.println(packets.size());
		while (packets.size() == 0) {
			try {
				Thread.sleep(10);
			} catch (InterruptedException e) {}
		}
		//Then return packet when one exists
		Packet pkt = packets.get(0);
		packets.remove(0);
		return pkt;
	}
	
	
	
	/**
	 * Testing main
	 */
	/*public static void main(String[] args) {
		new Client2Thread(null, null, ).start();
		//new ClientSendThread().start();
	}*/
}
