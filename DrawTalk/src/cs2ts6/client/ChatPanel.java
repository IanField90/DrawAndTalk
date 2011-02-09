package cs2ts6.client;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JPanel;
import javax.swing.JTabbedPane;

import cs2ts6.packets.ChatPacket;

/**
 * 
 * @author Vince, Curtis
 *
 */
public class ChatPanel extends JPanel implements ActionListener, KeyListener{

	/**
	 * Automatically generated
	 */
	private static final long serialVersionUID = -2697225417654336509L;
	private JPanel globalChat;
	
	public ChatPanel(){
		JTabbedPane jtpChat = new JTabbedPane();
		globalChat = new JPanel();
		jtpChat.add("Global", globalChat);
		addKeyListener(this);
		//jtpChat.add()
		add(jtpChat);
		
	}
	
	
	public void sendMessage(){
		//TODO
	}
	
	public void drawMessage(ChatPacket pkt){
		
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		//enter pressed or button pressed to send
	}


	@Override
	public void keyPressed(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		System.out.println(e);
		//if (e.getSource() == Key
	}
}
