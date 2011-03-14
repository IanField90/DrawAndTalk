package cs2ts6.client;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

/**
 * @author Ian Field, Stephen, Curtis
 * Client side window for the Draw & Talk application. This contains DrawingPanel and ChatPanel.
 * Entry point for collaboration.
 */
public class MainWindow extends JPanel implements WindowListener, ComponentListener{

	/**
	 * 
	 */
	private static final long serialVersionUID = 983051938169997622L;
	private DrawingPanel drawingPanel;
	private ChatPanel chatPanel;
	private Client client;
	private String username;
	private ColourPalette cpal; // Used for palette location
	private JFrame frame;
	
	/**
	 * Creates the GUI for the client side of the Draw & Talk application.
	 * Defaulted to exit on close of the window.
	 */
	private void createAndShowGUI(){
		username = JOptionPane.showInputDialog("Please enter your username:");
		frame = new JFrame("Draw & Talk (T11) - "+username+" - **OFFLINE**");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setResizable(false);// disables maximise button
		drawingPanel = new DrawingPanel(frame, new ImageIcon("src/icons/Canvas_Panel.png").getImage());
		chatPanel = new ChatPanel(username, new ImageIcon("src/icons/Chat_Panel.png").getImage());
		client = new Client(drawingPanel.get_canvas(), chatPanel, frame, username); //Client needs canvas + chatpanel
		drawingPanel.get_canvas().set_client(client); // canvas needs client
		chatPanel.set_client(client);
		frame.setPreferredSize(new Dimension(980, 470));
		cpal = drawingPanel.get_cpalette();
		frame.add("West", drawingPanel);
		frame.add(chatPanel);
		frame.addWindowListener(this);
		frame.addComponentListener(this);
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
		drawingPanel.get_canvas().redrawAction(); // Should redraw on becoming focused window
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

	@Override
	public void componentHidden(ComponentEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void componentMoved(ComponentEvent e) {
		drawingPanel.get_canvas().redrawAction();
		cpal.setVisible(frame.getX(), frame.getY(),true);
	}

	@Override
	public void componentResized(ComponentEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void componentShown(ComponentEvent e) {
		// TODO Auto-generated method stub
		
	}

}
