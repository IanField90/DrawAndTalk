package cs2ts6.client;

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
import javax.swing.JOptionPane;
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
	//Set up private member variables and objects.
	private JPanel globalChat;
	private JTextArea chatBox;
	private JTextField txtField;
	private JButton btnSend;
	public Client client;
	private String username;
	private String lastMessage;
	private Calendar calendar;
	private AdminPanel admin;
	//Create path information: STEPHEN - maybe sort out
	private final static String ICON_PATH = "src/icons" + File.separator;
	
	private Image bimg;
	
	//Constructor
	public ChatPanel(String uname, Image bimg){
		this.bimg = bimg; //set the background image?
		username = uname; // Username entered on application startup
		JTabbedPane jtpChat = new JTabbedPane(); //Tabbed pain for chat and admin panel
		jtpChat.setOpaque(false);
		admin = new AdminPanel(bimg, this);
		if(username.equalsIgnoreCase("admin")) {
			System.out.println(username);
			admin.setAdmin();
		}
		globalChat = new JPanel();
		globalChat.setPreferredSize(new Dimension(300,338)); //Set the size of the chat panel
		globalChat.setOpaque(false);
		chatBox = new JTextArea();
		JScrollPane areaScrollPane = new JScrollPane(chatBox);
		
		 //Force chatbox to always display scrollbar
		areaScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		areaScrollPane.setPreferredSize(new Dimension(220, 370));
		//Prevent users from accidentally editing the live chat window
		chatBox.setEnabled(false);
		chatBox.setLineWrap(true);
		chatBox.setWrapStyleWord(true);
		txtField = new JTextField(26);
		//If the user does not press enter, a button can be used to send message
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

		/*File theImage = new File(ICON_PATH + "space.png");
		ImageIcon img = new ImageIcon(theImage.getAbsolutePath());*/
		
		JLabel label = new JLabel(DrawingPanel.getImageIcon("space.png"));
		add(label);
		add(jtpChat);
	}
	
	/**
	 * Draws the background image for target audience
	 */
	public void paintComponent(Graphics g) {
	  g.drawImage(bimg, 0, 0, null);
	}

	/**
	 * Create the client link for communication
	 * @param cli Client to be used for communication
	 */
	public void set_client(Client cli) {
		client = cli;
	}
	
	/**
	 * Sends the message in the text field within the chat panel.
	 */
	public void sendMessage(){
		String msg = null;
		msg = txtField.getText();
		//Create a chat packet for data holding for communication
		ChatPacket chtpkt = new ChatPacket(username, msg);
		if(client.onServerGet()) {
			client.sendMessage(chtpkt);
			return;
		} else { //Not on server
			drawMessage(chtpkt);
		}
	}
	
	/**
	 * Draws the message if one is received from the server.
	 * Username and timestamp included.
	 * @param pkt Packet received
	 */
	public void drawMessage(ChatPacket pkt){
		calendar = new GregorianCalendar();
		DateFormat dfm = new SimpleDateFormat("HH:mm:ss");
		String time = dfm.format(calendar.getTime());
		System.out.println(pkt.get_sender()+" "+pkt.get_message());
		if (pkt.get_sender().equals("ADMIN") && pkt.get_message().equals("Session Frozen")) {
			MainWindow.frozen = true;
			JOptionPane.showMessageDialog(this, "The session has been frozen, you are only drawing locally");
		} else if (pkt.get_sender().equals("ADMIN") && pkt.get_message().equals("Session Unfrozen")) {
			MainWindow.frozen = false;
			JOptionPane.showMessageDialog(this, "The session has been unfrozen, you are drawing with peers");
		} else if(!pkt.get_sender().equals(username)) { //If the sender is not you, display username.
			chatBox.append(pkt.get_sender() + ":  " +time+"\n   "+pkt.get_message()+"\n");
		} else {
			//If the sender is you then 'You' is displayed rather than username
			chatBox.append("You:  "+time+"\n   "+pkt.get_message()+"\n");
		}
		chatBox.selectAll(); // Forces chatbox to autoscroll to bottom
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		//enter pressed or button pressed to send
		//If text is null then do not waste communication
		if(!txtField.getText().equals("")) {
			send();
		}
	}


	@Override
	public void keyPressed(KeyEvent e) {
		//Implement send on enter press
		if (e.getKeyCode() == KeyEvent.VK_ENTER && !txtField.getText().equals("")) {
			send();
		}
		//Implement message last sent text field population
		//can be used to re-send last message with a spelling correction
		if (e.getKeyCode() == KeyEvent.VK_UP) {
			txtField.setText(lastMessage);
		}
	}
	
	/**
	 * Sends the chat message to the server, which is then distributed to
	 * connected clients
	 */
	public void send() {
		//Embedded server implementation for quickstart
		if(txtField.getText().equals("#startserver")) {
			runServerCode();
			return;
		}
		sendMessage(); // actually transmit message
		lastMessage = txtField.getText(); //Populate lastMessage for up key press
		txtField.setText(""); // Clear the text field
	}

	@Override
	public void keyReleased(KeyEvent arg0) {
		//Not used
	}

	@Override
	public void keyTyped(KeyEvent arg0) {
		//Not used
	}
	
	
	
	//Embedded Server - Stephen
	public int runServerCode() {
		if(!client.onServerGet()) { //If not connected, embedded server can activate
			new cs2ts6.server.ServerThread(this).start();
			chatBox.setText("");
			client.setHostTitle(); //Set the title of the window to the 'embedded server message'
		} else { // IF connected - do not activate
			chatBox.append("SERVER:\n   You are already attached to a server!\n");
			return -1;
		}
		txtField.setText("");
		return 0;
	}
}
