package cs2ts6.client;

import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;

import cs2ts6.packets.ChatPacket;

/**
 * @author Ian, Vincent, Stephen
 * This panel is accessible to the teacher in order to 
 * start the embedded server on request.
 */
public class AdminPanel extends JPanel implements ActionListener{

	/**
	 * Automatically generated serial version ID.
	 */
	private static final long serialVersionUID = -5253910251343064918L;
	private JButton startServer, freezeSession;
	private ChatPanel cht;
	
	public AdminPanel (Image bimg, ChatPanel chatPanel){
		//This button starts the server
		startServer = new JButton("Start Server");
		freezeSession = new JButton ("Freeze Session");
		cht = chatPanel;
		add(startServer);
		add(freezeSession);
		freezeSession.setEnabled(false);
		startServer.setEnabled(false);
		startServer.addActionListener(this);
		freezeSession.addActionListener(this);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		int status;
		// If the button is the startServer button
		if(e.getSource() == startServer){
			System.out.println("HERE");
			status = cht.runServerCode();
			if(status != -1) {
				freezeSession.setEnabled(true);
			}
			startServer.setEnabled(false);
		}
		// If the button is the freezeSession button
		if(e.getSource() == freezeSession){
			if(MainWindow.frozen == false) {
				ChatPacket chtpkt = new ChatPacket("ADMIN", "Session Frozen");
				cht.client.sendMessage(chtpkt);
				freezeSession.setText("Un-Freeze Session");
			} else {
				ChatPacket chtpkt = new ChatPacket("ADMIN", "Session Unfrozen");
				cht.client.sendMessage(chtpkt);
				cht.client.clearCanvas();
				freezeSession.setText("Freeze Session");
			}
		}
	};
	
	public void setAdmin() {
		startServer.setEnabled(true);
	}
}
