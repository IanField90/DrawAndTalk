package cs2ts6.client;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.GridLayout;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JWindow;
/**
 * Window for colour palette for brushes
 * @author Curtis, Stephen
 *
 */
public class ColourPalette{

	JWindow cPalette = new JWindow();
	private JTextField red, green, blue, black, yellow;
	private DrawingCanvas canvas;
	
	ColourPalette(DrawingCanvas canv){
		canvas = canv;
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
	
	public void setVisible(int X, int Y, boolean move) {
		int modY=80, modX=160;
		if(move) {
			cPalette.setLocation(X+modX,Y+modY);
			return;
		} else {
			if(cPalette.isVisible()) {
				cPalette.setVisible(false);
				return;
			}
			cPalette.setVisible(false);
			cPalette.setLocation(X+modX,Y+modY);
			cPalette.setVisible(true);
		}
	}
	
	MouseListener hover = new MouseListener() {
		@Override
		public void mouseClicked(MouseEvent arg0) {
			JTextField src = (JTextField)arg0.getSource();
			canvas.set_colour(src.getBackground());
			cPalette.setVisible(false);
			canvas.redrawAction();
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
			}
		});
	}
}
