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
	private JButton pen, brush, clear, brushSize;
	private DrawingCanvas canvas; //Canvas where drawing is handled
	private Color colour; //Holds drawing colour - for GUI/feedback
	private Client client; // treated like a pointer
	
	// Use enums for types
	public static enum DrawType { PEN, BRUSH, ERASE, SQUARE, CIRCLE };
	
	public DrawingPanel(Client client){
		this.client = client;
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
		clear.setEnabled(false);//TODO Support teacher admin (sprint 2)
		clear.addActionListener(this);
		
		brushSize = new JButton("Brush Size");
		brushSize.setToolTipText("Brush size");
		brushSize.addActionListener(this);
		
		toolbar.setOrientation(1);//Make toolbar appear vertically
		toolbar.add(pen);
		toolbar.add(brush);
		toolbar.add(brushSize);
		toolbar.add(clear);
		toolbar.setFloatable(false); //Disables dragging of toolbar
		
		panel.add("North", toolbar); //TODO align with top
		panel.add(canvas);
		add(panel);
	}
	
	public DrawingCanvas get_canvas(){
		return canvas;
	}

	//TODO possibly move into DrawingCanvas
	public void sendDrawPacket(PointPacket pointPacket){
		//PointPacket pointPacket = new PointPacket(); 
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
			canvas = new DrawingCanvas(); //Clear the canvas
			canvas.set_client(client); // canvas needs client
		}
		
		//TODO Colours
		//TODO Save

	}

}
