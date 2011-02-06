package cs2ts6.client;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JPanel;
import javax.swing.JTabbedPane;

public class ChatPanel extends JPanel implements ActionListener{

	/**
	 * Automatically generated
	 */
	private static final long serialVersionUID = -2697225417654336509L;
	private JPanel globalChat;
	
	public ChatPanel(){
		JTabbedPane jtpChat = new JTabbedPane();
		globalChat = new JPanel();
		jtpChat.add("Global", globalChat);
		//jtpChat.add()
		add(jtpChat);
		
	}
	
	@Override
	public void actionPerformed(ActionEvent arg0) {
		// TODO Auto-generated method stub
		
	}
}
