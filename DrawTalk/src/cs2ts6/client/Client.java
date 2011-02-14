package cs2ts6.client;

import cs2ts6.packets.*;

public class Client {
	
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
	}
	/**
	 * Send point to server, will auto return message back in mock client
	 * @param pnt
	 */
	public void sendPoints(PointPacket pnt) {
		canvas.drawPoints(pnt);
	}
	/**
	 * Send message t server, will auto return to chat window for mock client
	 * @param cht
	 */
	public void sendMessage(ChatPacket cht) {
		chat.drawMessage(cht);
	}
	
	/**
	 * Testing main
	 */
	public static void main(String[] args) {
		new Client2Thread(null, null).start();
	}
}
