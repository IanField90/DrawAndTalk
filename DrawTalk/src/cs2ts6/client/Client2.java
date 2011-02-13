package cs2ts6.client;

import cs2ts6.packets.PointPacket;

/**
 * 
 * @author stephen
 *
 */
public class Client2 {
	/**
	 * Internal reference to Canvas for draw packets
	 */
	private DrawingCanvas canvas;
	/**
	 * Internal reference to ChatPanel for chat packets
	 */
	private ChatPanel chat;
	/**
	 * Constructor linking client to windows
	 * @param canv DrawingCanvas reference
	 * @param cht ChatPanel reference
	 */
	public Client2(DrawingCanvas canv, ChatPanel cht) {
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
		canvas.drawPoints(pnt);
	}
	/**
	 * For sending chats to the server
	 * PUBLIC method!
	 */
}
