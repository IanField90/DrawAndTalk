package cs2ts6.packets;

/**
 * @author Ian Field
 * Used to transmit the data of the chat between the clients and server.
 */
public class ChatPacket extends Packet{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String sender; //The sending user
	private String message; //The message
	
	//Constructor
	public ChatPacket(String s, String m){
		sender = s;
		message = m;
	}
	
	public void set_sender(String sender) {
		this.sender = sender;
	}
	public String get_sender() {
		return sender;
	}
	
	public void set_message(String message) {
		this.message = message;
	}
	public String get_message() {
		return message;
	}
	
	public String toString() {
		return sender+": "+message;
	}
}
