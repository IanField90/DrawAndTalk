package cs2ts6.client;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JToolBar;

import cs2ts6.packets.PointPacket;

/**
 * 
 * @author Ian Field
 * Creates the Canvas panel. This is a 640px x 480px white-background panel.
 * It includes a toolbar for drawing.
 */
public class DrawingPanel extends JPanel implements MouseMotionListener, MouseListener, ActionListener{

	/**
	 * 
	 */
	private static final long serialVersionUID = 7531407885742074028L;
	private JButton brush, clear, brushSize;
	private Canvas canvas; //TODO Create custom canvas with 'draw' for points (+shapes sprint 2)
	private Color color;
	
	//TODO Draw point using pointpacket
	public DrawingPanel(){
		JPanel panel = new JPanel(); //Panel with tooblar + canvas
		canvas = new Canvas(); //White area
		canvas.setPreferredSize(new Dimension(640, 480));
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
		canvas.addMouseMotionListener(this);
		canvas.addMouseListener(this);
		panel.add("North", toolbar);
		panel.add(canvas);
		add(panel);
		color = Color.BLACK; //default colour
	}
	
	public void sendDrawPacket(PointPacket pointPacket){
		//PointPacket pointPacket = new PointPacket();
	}
	
	/**
	 * Draws a point on the Drawing canvas
	 * @param x The x-coordinate of the point to draw
	 * @param y The y-coordinate of the point to draw
	 * @param colour The colour of the point to draw
	 */
	public void doDrawPoint(Point p, Color colour, int size){
		
		// Override just for testing
		//size = 10;
		//colour = Color.RED;
		
		// Increment size by 1, this is because not bothering to draw a border.
		// Therefore a size of 1 would not be visible.
		size++;
		
		Graphics g = canvas.getGraphics();    
        g.setColor(colour);
        g.fillOval(p.x, p.y, size, size);
        repaint();
		
		//Try to send packet
		sendDrawPacket(new PointPacket(p, colour, size));
		//TODO if successful packet should be recieved + drawn
	}
	
	/**
	 * Sends PointPacket for live authoring
	 */
	@Override
	public void mouseDragged(MouseEvent e) {
		//TODO check type of drawing Sprint 2
		// TODO Send coordinate data + colour on move
		doDrawPoint(e.getPoint(), this.color, 1);
		
		Point drawLoc = e.getPoint();
		System.out.println("Dragged: ("+ drawLoc.x + ", " + drawLoc.y + ")");
		//TODO Get colour
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		// TODO Auto-generated method stub
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if(e.getSource() == brush){
			//do something
		}
		
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		Point drawLoc = e.getPoint();
		
		doDrawPoint(e.getPoint(), this.color, 1);
		
		System.out.println("Clicked: ("+ drawLoc.x + ", " + drawLoc.y + ")");
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mousePressed(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseReleased(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

}
