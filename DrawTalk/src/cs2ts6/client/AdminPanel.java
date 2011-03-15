package cs2ts6.client;

import java.awt.Image;

import javax.swing.JButton;
import javax.swing.JPanel;

public class AdminPanel extends JPanel{

	/**
	 * 
	 */
	private static final long serialVersionUID = -5253910251343064918L;
	private JButton startServer, freezeSession;
	
	public AdminPanel (Image bimg){
		startServer = new JButton("Start Server");
		
		freezeSession = new JButton ("Freeze Session");
		add(startServer);
		add(freezeSession);
	};

}
