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
	 * 
	 */
	private static final long serialVersionUID = 7531407885742074028L;
	private JButton brush, clear, brushSize;
	private DrawingCanvas canvas; //TODO Create custom canvas with 'draw' for points (+shapes sprint 2)
	private Color color;
	
	//TODO Use enums for types
	//public enum drawType { PEN, BRUSH, SHAPE };
	
	//TODO Draw point using pointpacket
	public DrawingPanel(){
		JPanel panel = new JPanel(); //Panel with tooblar + canvas

		color = Color.BLACK; //default colour
		canvas = new DrawingCanvas(); //White area
		canvas.setPreferredSize(new Dimension(640, 480));
		canvas.set_color(color);
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
		toolbar.setFloatable(false);
		
		canvas.setBackground(Color.WHITE);
		
		panel.add("North", toolbar);
		panel.add(canvas);
		add(panel);
	}
	public void sendDrawPacket(PointPacket pointPacket){
		//PointPacket pointPacket = new PointPacket();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if(e.getSource() == brush){
			canvas.set_selectedOption(1); //TODO use enum
		}
	}

	
}
