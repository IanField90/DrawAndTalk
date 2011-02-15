package cs2ts6.client;

import java.awt.Dimension;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

/**
 * @author Ian Field, Stephen
 * Client side window for the Draw & Talk application. This contains DrawingPanel and ChatPanel.
 * Entry point for collaboration.
 */
public class MainWindow extends JPanel{

	/**
	 * 
	 */
	private static final long serialVersionUID = 983051938169997622L;
	private DrawingPanel drawingPanel;
	private ChatPanel chatPanel;
	private Client client;
	private String username;
	
	/**
	 * Creates the GUI for the client side of the Draw & Talk application.
	 * Defaulted to exit on close of the window.
	 */
	private void createAndShowGUI(){
		username = JOptionPane.showInputDialog("Please enter your username:");
		JFrame frame = new JFrame("Draw & Talk");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		drawingPanel = new DrawingPanel();
		chatPanel = new ChatPanel(username);
		client = new Client(drawingPanel.get_canvas(), chatPanel); //Client needs canvas + chatpanel
		drawingPanel.get_canvas().set_client(client); // canvas needs client
		chatPanel.set_client(client);
		frame.setPreferredSize(new Dimension(1000, 480));
		frame.add("West", drawingPanel);
		frame.add(chatPanel);
		frame.pack();
		frame.setVisible(true);
	}
	
	public Client get_client(){
		return client;
	}
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		final MainWindow mainWindow = new MainWindow();
		SwingUtilities.invokeLater(new Runnable(){
			public void run(){
				mainWindow.createAndShowGUI();
			}
		});
	}

}
