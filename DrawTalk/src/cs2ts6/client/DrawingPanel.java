package cs2ts6.client;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JToolBar;

/**
 * 
 * @author Ian Field, Stephen (Minor)
 * Creates the Canvas panel. This is a 640px x 480px DrawingCanvas and a toolbar.
 * Toolbar sets private members in DrawingCanvas.
 */
public class DrawingPanel extends JPanel implements ActionListener{

	/**
	 * Automatically generated number
	 */
	private static final long serialVersionUID = 7531407885742074028L;
	private JButton pen, brush, clear, brushSize, erase, brushcolour;
	private DrawingCanvas canvas; //Canvas where drawing is handled
	private Color colour; //Holds drawing colour - for GUI/feedback
	
	// Use enums for types
	public static enum DrawType { PEN, BRUSH, ERASE, SQUARE, CIRCLE , FULL_CLEAR};
	
	public DrawingPanel(){
		JPanel panel = new JPanel(); //Panel with tooblar + canvas
		colour = Color.BLACK;
		canvas = new DrawingCanvas();
		canvas.setPreferredSize(new Dimension(640, 480));
		canvas.set_colour(colour); //Sets drawing colour inside canvas
		canvas.set_selectedOption(DrawType.PEN);
		JToolBar toolbar = new JToolBar();
		
		brush = new JButton("Brush");
		brush.setToolTipText("Brush");
		brush.addActionListener(this);
		
		pen = new JButton("Pen");
		pen.setToolTipText("Pen");
		pen.addActionListener(this);
		
		clear = new JButton("Clear");
		clear.setToolTipText("Clear canvas");
		//clear.setEnabled(false);//TODO Support teacher admin (sprint 2)
		clear.addActionListener(this);
		
		brushSize = new JButton("Brush Size");
		brushSize.setToolTipText("Brush size");
		brushSize.setEnabled(false);
		brushSize.addActionListener(this);
		
		erase = new JButton("Erase");
		erase.setToolTipText("Erase by drawing with the 'rubber'");
		erase.addActionListener(this);
		
		brushcolour = new JButton("Colours");
		brushcolour.setToolTipText("Colour Palette");
		brushcolour.addActionListener(this);
		
		toolbar.setOrientation(1);//Make toolbar appear vertically
		toolbar.add(pen);
		toolbar.add(brush);
		toolbar.add(brushSize);
		toolbar.add(brushcolour);
		toolbar.add(erase);
		toolbar.add(clear);
		toolbar.setFloatable(false); //Disables dragging of toolbar
		
		panel.add("North", toolbar); //TODO align with top
		panel.add(canvas);
		add(panel);
	}
	
	public DrawingCanvas get_canvas(){
		return canvas;
	}


	@Override
	public void actionPerformed(ActionEvent e) {
		//TODO Emphasise selected drawType
		//Sort drawing type
		if(e.getSource() == brush){
			canvas.set_selectedOption(DrawType.BRUSH);
		}
		if(e.getSource() == pen){
			canvas.set_selectedOption(DrawType.PEN);
		}
		if(e.getSource() == brushSize){
			//TODO Brush size
		}
		if(e.getSource() == clear){
			//canvas = new DrawingCanvas(); //Clear the canvas
			canvas.clear(); //clear canvas, does not change reference
			//canvas.set_client(client); // canvas needs client
		}
		if(e.getSource() == erase) {
			canvas.set_selectedOption(DrawType.ERASE);
		}
		if(e.getSource() == brushcolour) {
			String[] choices = { "Red", "Green", "Blue", "Black", "Yellow" };
			int choice = JOptionPane.showOptionDialog(null, "Choose a colour", "Colour Palette", 
					JOptionPane.YES_NO_OPTION, JOptionPane.PLAIN_MESSAGE, null, choices, "Black");
			
			switch(choice) {
			case 0: canvas.set_colour(Color.RED); break;
			case 1: canvas.set_colour(Color.GREEN); break;
			case 2: canvas.set_colour(Color.BLUE); break;
			case 3: canvas.set_colour(Color.BLACK); break;
			case 4: canvas.set_colour(Color.YELLOW); break;
			}
		}
		//TODO Save

	}

}
