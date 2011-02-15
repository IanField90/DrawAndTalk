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
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import cs2ts6.packets.ChatPacket;

/**
 * 
 * @author Vince, Curtis, Stephen (Logic Layer and Some GUI)
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
	private Client client;
	private String username;
	private String lastMessage;
	
	public ChatPanel(String uname){
		username = uname;
		JTabbedPane jtpChat = new JTabbedPane();
		globalChat = new JPanel();
		globalChat.setPreferredSize(new Dimension(300,400));
		globalChat.setBackground(Color.lightGray);
		chatBox = new JTextArea();
		JScrollPane areaScrollPane = new JScrollPane(chatBox);
		areaScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		areaScrollPane.setPreferredSize(new Dimension(220, 370));
		chatBox.setEnabled(false);
		chatBox.setLineWrap(true);
		chatBox.setWrapStyleWord(true);
		txtField = new JTextField(28);
		btnSend = new JButton("Send");
		btnSend.setBackground(Color.lightGray);
		
		
		globalChat.setLayout(new BoxLayout(globalChat, BoxLayout.PAGE_AXIS));

		globalChat.add(areaScrollPane);
		
		Panel p = new Panel();
		p.setLayout(new FlowLayout());
		p.add(txtField);
		p.add(btnSend);
		
		globalChat.add(p);
		jtpChat.add("Global Chat", globalChat);
		txtField.addKeyListener(this);
		btnSend.addActionListener(this);
		//jtpChat.add()
		add(jtpChat);
		
	}
	
	public void set_client(Client cli) {
		client = cli;
	}
	
	public void sendMessage(){
		String msg = null;
		msg = txtField.getText();
		ChatPacket chtpkt = new ChatPacket(username, msg);
		client.sendMessage(chtpkt);
	}
	
	public void drawMessage(ChatPacket pkt){
		if(!pkt.get_sender().equals(username)) {
			chatBox.append(pkt.get_sender() + ": " + pkt.get_message()+"\n");
		} else {
			chatBox.setText(chatBox.getText()+"You: "+pkt.get_message()+"\n");
		}
		chatBox.selectAll(); // Forces chatbox to autoscroll to bottom
	}
	
	//Embedded Server - Stephen
	private void runServerCode() {
		if(!client.onServerGet()) { //If not connected, embedded server can activate
			new cs2ts6.server.ServerThread().start();
			chatBox.append(chatBox.getText()+"SERVER: IM ALIVE!\n");
		} else { // IF connected - do not activate
			chatBox.append("SERVER: You are already attached to a server!\n");
		}
		txtField.setText("");
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		//enter pressed or button pressed to send
		if(!txtField.getText().equals("")) {
			send();
		}
	}


	@Override
	public void keyPressed(KeyEvent e) {
		// TODO Auto-generated method stub
		if (e.getKeyCode() == KeyEvent.VK_ENTER && !txtField.getText().equals("")) {
			send();
		}
		if (e.getKeyCode() == KeyEvent.VK_UP) {
			txtField.setText(lastMessage);
		}
	}
	
	public void send() {
		if(txtField.getText().equals("#startserver")) {
			runServerCode();
			return;
		}
		sendMessage();
		lastMessage = txtField.getText();
		txtField.setText(""); // Blank text
	}

	@Override
	public void keyReleased(KeyEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyTyped(KeyEvent arg0) {
		// TODO Auto-generated method stub
		
	}
}
