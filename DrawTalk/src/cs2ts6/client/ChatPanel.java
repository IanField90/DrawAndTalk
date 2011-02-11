package cs2ts6.client;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Panel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

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
	private JTextArea chatBox;
	private JTextField txtField;
	private JButton btnSend;
	
	public ChatPanel(){
		JTabbedPane jtpChat = new JTabbedPane();
		globalChat = new JPanel();
		globalChat.setPreferredSize(new Dimension(220,400));
		chatBox = new JTextArea();
		chatBox.setEnabled(false);
		txtField = new JTextField(12);
		btnSend = new JButton("Send");
		
		
		globalChat.setLayout(new BoxLayout(globalChat, BoxLayout.PAGE_AXIS));

		globalChat.add(chatBox);
		
		Panel p = new Panel();
		p.setLayout(new FlowLayout());
		p.add(txtField);
		p.add(btnSend);
		
		globalChat.add(p);
		
		jtpChat.add("Global", globalChat);
		addKeyListener(this);
		//jtpChat.add()
		add(jtpChat);
		
	}
	

	
	
	public void sendMessage(){
		String msg = null;
		msg = txtField.getText();
		ChatPacket chtpkt = new ChatPacket("sender", msg);
	}
	
	public void drawMessage(ChatPacket pkt){
		chatBox.append(pkt.get_sender() + ": " + pkt.get_message());
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
