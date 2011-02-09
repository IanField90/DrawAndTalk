package cs2ts6.client;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JToolBar;

import cs2ts6.packets.PointPacket;

/**
 * 
 * @author Ian Field
 * Creates the Canvas panel. This is a 640px x 480px DrawingCanvas and a toolbar.
 * Toolbar sets private members in DrawingCanvas.
 */
public class DrawingPanel extends JPanel implements ActionListener{

	/**
	 * Automatically generated number
	 */
	private static final long serialVersionUID = 7531407885742074028L;
	private JButton brush, clear, brushSize; //TODO Pen button
	private DrawingCanvas canvas; //Canvas where drawing is handled
	private Color colour; //Holds drawing colour - for GUI/feedback
	
	//TODO Use enums for types
	public static enum drawType { PEN, BRUSH, ERASE, SQUARE, CIRCLE };
	//TODO Draw point using pointpacket
	
	public DrawingPanel(){
		JPanel panel = new JPanel(); //Panel with tooblar + canvas
		colour = Color.BLACK;
		canvas = new DrawingCanvas();
		canvas.setPreferredSize(new Dimension(640, 480));
		canvas.set_colour(colour); //Sets drawing colour inside canvas
		canvas.set_selectedOption(drawType.PEN);
		JToolBar toolbar = new JToolBar();
		
		brush = new JButton("Brush");//TODO Add reflection for image
		brush.setToolTipText("Brush");
		brush.addActionListener(this);
		
		clear = new JButton("Clear");//TODO Add reflection for image
		clear.setToolTipText("Clear canvas");
		clear.setEnabled(false);//TODO Support teacher admin
		clear.addActionListener(this);
		
		brushSize = new JButton("Brush Size");//TODO Add reflection for image
		brushSize.setToolTipText("Brush size");
		brushSize.addActionListener(this);
		
		toolbar.setOrientation(1);//TODO Check vertical
		toolbar.add(brush);
		toolbar.add(brushSize);
		toolbar.add(clear);
		toolbar.setFloatable(false); //Disables dragging of toolbar
		
		panel.add("North", toolbar); //TODO align with top
		panel.add(canvas);
		add(panel);
	}
	
	public void sendDrawPacket(PointPacket pointPacket){
		//PointPacket pointPacket = new PointPacket(); //TODO possibly move into DrawingCanvas
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		//If the button 'brush' is clicked
		if(e.getSource() == brush){
			canvas.set_selectedOption(drawType.BRUSH); //TODO use enum
		}
	}

	
}
