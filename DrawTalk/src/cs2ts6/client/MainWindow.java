package cs2ts6.client;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

/**
 * @author Ian Field, Stephen
 * Client side window for the Draw & Talk application. This contains DrawingPanel and ChatPanel.
 * Entry point for collaboration.
 */
public class MainWindow extends JPanel implements WindowListener{

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
		JFrame frame = new JFrame("Draw & Talk - Team 11 - OFFLINE");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setResizable(false);// disables maximise button
		drawingPanel = new DrawingPanel(frame);
		chatPanel = new ChatPanel(username);
		client = new Client(drawingPanel.get_canvas(), chatPanel, frame); //Client needs canvas + chatpanel
		drawingPanel.get_canvas().set_client(client); // canvas needs client
		chatPanel.set_client(client);
		frame.setPreferredSize(new Dimension(980, 470));
		drawingPanel.setBackground(Color.lightGray);
		chatPanel.setBackground(Color.lightGray);
		frame.add("West", drawingPanel);
		frame.add(chatPanel);
		frame.addWindowListener(this);
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
				try {
					System.out.println(System.getProperty("os.name"));
					if(System.getProperty("os.name").contains("Linux")) {
						UIManager.setLookAndFeel("javax.swing.plaf.metal.MetalLookAndFeel");
					} else {
						UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
					}
			    } 
			    catch (UnsupportedLookAndFeelException e) {
			       // handle exception
			    }
			    catch (ClassNotFoundException e) {
			       // handle exception
			    }
			    catch (InstantiationException e) {
			       // handle exception
			    }
			    catch (IllegalAccessException e) {
			       // handle exception
			    }
				mainWindow.createAndShowGUI();
			}
		});
	}

	@Override
	public void windowActivated(WindowEvent arg0) {
	}

	@Override
	public void windowClosed(WindowEvent arg0) {
	}

	@Override
	public void windowClosing(WindowEvent arg0) {
	}

	@Override
	public void windowDeactivated(WindowEvent arg0) {
	}

	@Override
	public void windowDeiconified(WindowEvent arg0) {
		drawingPanel.get_canvas().redrawAction();
	}

	@Override
	public void windowIconified(WindowEvent arg0) {
	}

	@Override
	public void windowOpened(WindowEvent arg0) {
	}

}
