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
import javax.swing.JTextPane;
import javax.swing.text.BadLocationException;
import javax.swing.text.StyledDocument;

import cs2ts6.packets.ChatPacket;

/**
 * 
 * @author Vince, Curtis, Stephen (Logic Layer)
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
	StyledDocument doc;
	
	public ChatPanel(String uname){
		username = uname;
		JTabbedPane jtpChat = new JTabbedPane();
		globalChat = new JPanel();
		globalChat.setPreferredSize(new Dimension(220,400));
		chatBox = new JTextArea();
		JScrollPane areaScrollPane = new JScrollPane(chatBox);
		areaScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		areaScrollPane.setPreferredSize(new Dimension(220, 350));
		chatBox.setEnabled(false);
		chatBox.setLineWrap(true);
		chatBox.setWrapStyleWord(true);
		txtField = new JTextField(12);
		btnSend = new JButton("Send");
		
		
		globalChat.setLayout(new BoxLayout(globalChat, BoxLayout.PAGE_AXIS));

		globalChat.add(areaScrollPane);
		
		Panel p = new Panel();
		p.setLayout(new FlowLayout());
		p.add(txtField);
		p.add(btnSend);
		
		globalChat.add(p);
		
		jtpChat.add("Global", globalChat);
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
			chatBox.append(chatBox.getText()+pkt.get_sender() + ": " + pkt.get_message()+"\n");
		} else {
			chatBox.setText(chatBox.getText()+"You: "+pkt.get_message());
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		//enter pressed or button pressed to send
		sendMessage();
	}


	@Override
	public void keyPressed(KeyEvent e) {
		// TODO Auto-generated method stub
		if (e.getKeyCode() == KeyEvent.VK_ENTER) {
			sendMessage();
			txtField.setText(""); // Blank text
		}
	}


	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
	}
}
