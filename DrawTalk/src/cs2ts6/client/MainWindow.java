package cs2ts6.client;

import java.awt.Dimension;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

/**
 * @author Ian Field
 * Client side window for the Draw & Talk application. This contains DrawingPanel and ChatPanel.
 * Entry point for collaboration.
 */
public class MainWindow extends JPanel{

	/**
	 * 
	 */
	private static final long serialVersionUID = 983051938169997622L;
	//TODO Create client
	//TODO Pass client references to this.getDrawingPanel().getDrawingCanvas(); etc
	private DrawingPanel drawingPanel;
	private ChatPanel chatPanel;
	
	/**
	 * Creates the GUI for the client side of the Draw & Talk application.
	 * Defaulted to exit on close of the window.
	 */
	private void createAndShowGUI(){
		JFrame frame = new JFrame("Draw & Talk");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		drawingPanel = new DrawingPanel();
		chatPanel = new ChatPanel();
		frame.setPreferredSize(new Dimension(1000, 480));
		frame.add("West", drawingPanel);
		frame.add(chatPanel);
		frame.pack();
		frame.setVisible(true);
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
