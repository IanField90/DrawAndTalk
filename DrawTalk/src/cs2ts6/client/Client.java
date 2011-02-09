package cs2ts6.client;

import cs2ts6.packets.*;

public class Client {
	
	private DrawingCanvas canvas;
	private ChatPanel chat;
	
	public Client(DrawingCanvas cnv, ChatPanel cht) {
		canvas = cnv;
		chat = cht;
	}
	
	public void sendPoints(PointPacket pnt) {
		canvas.drawPoints(pnt);
	}
	
	public void sendMessage(ChatPacket cht) {
		chat.drawMessage(cht);
	}
}
