package cs2ts6.client;

import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;

/**
 * @author Ian, Vincent,
 * This panel is accessible to the teacher in order to 
 * start the embedded server on request.
 */
public class AdminPanel extends JPanel implements ActionListener{

	/**
	 * Automatically generated serial version ID.
	 */
	private static final long serialVersionUID = -5253910251343064918L;
	private JButton startServer, freezeSession;
	
	public AdminPanel (Image bimg){
		//This button starts the server
		startServer = new JButton("Start Server");
		freezeSession = new JButton ("Freeze Session");
		add(startServer);
		add(freezeSession);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// If the button is the startServer button
		if(e.getSource() == startServer){
			//TODO STEPHEN: start server method call
			//getChatPanel().runServerCode(); ??
		}
		// If the button is the freezeSession button
		if(e.getSource() == freezeSession){
			//TODO STEPHEN: freeze session call. If not possible remove references
			//Javadoc class comment required if implemented
		}
	};

}
