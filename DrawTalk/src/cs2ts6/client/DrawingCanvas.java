package cs2ts6.client;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

/**
 * 
 * @author Ian Field
 * A custom implementation of canvas to implement drawPoint method (Sprint 1)
 */
public class DrawingCanvas extends Canvas implements MouseMotionListener, MouseListener {

	/**
	 * Automatically generated, removes warning
	 */
	private static final long serialVersionUID = 5995159706684610807L;
	
	private Color color;
	
	public DrawingCanvas(){
		setBackground(Color.white);
		color = Color.black;
		this.addMouseMotionListener(this);
		this.addMouseListener(this);
	}
	
	/**
	 * Sets the colour to be drawn on the canvas
	 * @param color Colour to be used when drawing.
	 */
	public void setColor(Color color){
		this.color = color;
	}
	
	@Override
	public void paint (Graphics g){
		//Graphics2D g2;
		//g2 = (Graphics2D) g;
		//g2.drawRect(0, 0, 44, 55);
	}
	
	/**
	 * Draws a point on the Drawing canvas
	 * @param x The x-coordinate of the point to draw
	 * @param y The y-coordinate of the point to draw
	 * @param colour The colour of the point to draw
	 */
	public void doDrawPoint(Point p, Color colour, int size){
		
		Graphics g = getGraphics();
		g.setColor(colour);
		g.drawRect(p.x, p.y, 1, 1);
		this.paint(g);
		
		//Try to send packet
		//sendDrawPacket(new PointPacket(p, colour, size));
		//TODO if successful packet should be recieved + drawn
	}
	
	@Override
	public void mouseDragged(MouseEvent e) {
		doDrawPoint(e.getPoint(), this.color, 1);
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		// TODO Auto-generated method stub
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		Point drawLoc = e.getPoint();
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
