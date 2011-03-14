package cs2ts6.client;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Panel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.File;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;

import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import cs2ts6.client.DrawingPanel.DrawType;
import cs2ts6.packets.ChatPacket;
import cs2ts6.packets.PointPacket;

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
	private Calendar calendar;
	private AdminPanel admin;
	private final static String ICON_PATH = "src/icons" + File.separator;
	
	private Image bimg;	
	public ChatPanel(String uname, Image bimg){
		this.bimg = bimg;
		username = uname;
		JTabbedPane jtpChat = new JTabbedPane();
		jtpChat.setOpaque(false);
		admin = new AdminPanel();
		globalChat = new JPanel();
		globalChat.setPreferredSize(new Dimension(300,338));
		globalChat.setOpaque(false);
		chatBox = new JTextArea();
		JScrollPane areaScrollPane = new JScrollPane(chatBox);
		areaScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		areaScrollPane.setPreferredSize(new Dimension(220, 370));
		chatBox.setEnabled(false);
		chatBox.setLineWrap(true);
		chatBox.setWrapStyleWord(true);
		txtField = new JTextField(26);
		btnSend = new JButton("Send");
		
		globalChat.setLayout(new BoxLayout(globalChat, BoxLayout.PAGE_AXIS));

		globalChat.add(areaScrollPane);
		
		Panel p = new Panel();
		p.setLayout(new FlowLayout());
		p.add(txtField);
		p.add(btnSend);
		
		globalChat.add(p);
		jtpChat.addTab("Chat", globalChat);
		jtpChat.addTab("Admin", admin);
		txtField.addKeyListener(this);
		btnSend.addActionListener(this);

		File theImage = new File(ICON_PATH + "space.png");
		ImageIcon img = new ImageIcon(theImage.getAbsolutePath());
		
		JLabel label = new JLabel(img);
		add(label);
		add(jtpChat);
	}
		 
	public void paintComponent(Graphics g) {
	  g.drawImage(bimg, 0, 0, null);
	}

	public void set_client(Client cli) {
		client = cli;
	}
	
	public void sendMessage(){
		String msg = null;
		msg = txtField.getText();
		ChatPacket chtpkt = new ChatPacket(username, msg);
		if(client.onServerGet()) {
			client.sendMessage(chtpkt);
			return;
		} else { //Not on server
			drawMessage(chtpkt);
		}
	}
	
	public void drawMessage(ChatPacket pkt){
		calendar = new GregorianCalendar();
		DateFormat dfm = new SimpleDateFormat("HH:mm:ss");
		String time = dfm.format(calendar.getTime());
		if(!pkt.get_sender().equals(username)) {
			chatBox.append(pkt.get_sender() + ":  " +time+"\n   "+pkt.get_message()+"\n");
		} else {
			chatBox.append("You:  "+time+"\n   "+pkt.get_message()+"\n");
		}
		chatBox.selectAll(); // Forces chatbox to autoscroll to bottom
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
	
	
	
	//Embedded Server - Stephen
	private void runServerCode() {
		if(!client.onServerGet()) { //If not connected, embedded server can activate
			new cs2ts6.server.ServerThread(this).start();
			chatBox.setText("");
			client.setHostTitle(); //Set the title of the window to the 'embedded server message'
		} else { // IF connected - do not activate
			chatBox.append("SERVER:\n   You are already attached to a server!\n");
		}
		txtField.setText("");
	}
}
