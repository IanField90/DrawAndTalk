package cs2ts6.client;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Panel;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JWindow;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
/**
 * Window for colour palette for brushes
 * @author Curtis
 *
 */
public class ColourPalette{

	JWindow cPalette = new JWindow();
	private JTextField red, green, blue, black, yellow;
	
	ColourPalette(){
		
		red = new JTextField ();
		red.setBackground(Color.red);
		red.setEnabled(false);
		red.addMouseListener(hover);
		green = new JTextField ();
		green.setBackground(Color.green);
		green.setEnabled(false);
		green.addMouseListener(hover);
		blue = new JTextField ();
		blue.setBackground(Color.blue);
		blue.setEnabled(false);
		blue.addMouseListener(hover);
		black = new JTextField ();
		black.setBackground(Color.black);
		black.setEnabled(false);
		black.addMouseListener(hover);
		yellow = new JTextField ();
		yellow.setBackground(Color.yellow);
		yellow.setEnabled(false);
		yellow.addMouseListener(hover);
		JPanel p = new JPanel();
		p.setLayout(new GridLayout(2,3));
		p.add(red);
		p.add(green);
		p.add(blue);
		p.add(black);
		p.add(yellow);
		
		cPalette.add(p);
		
	}
	
	MouseListener hover = new MouseListener() {
		@Override
		public void mouseClicked(MouseEvent arg0) {
			//TODO When clicked the colours change
		}
		public void mouseEntered(MouseEvent e) {
			cPalette.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		}
		public void mouseExited(MouseEvent e) {
			cPalette.setCursor(null);
		}
		public void mousePressed(MouseEvent e) {}
		public void mouseReleased(MouseEvent e) {}
	};
	
	/**
	 * Method, creates and shows the GUI
	 */
	public void createAndShowGUI(){
		// Schedule a job for the event-dispatching thread:
		// creating and showing this application's GUI.
		javax.swing.SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				cPalette.setSize(130, 80);
				cPalette.setLocation(150, 80);
				cPalette.setVisible(true);
				
			}
		});
	}
}
