package cs2ts6.client;

import java.awt.BasicStroke;
import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
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
	// Always the point that was in use before the current point
	private Point previousP;
	// Always the point where the mouse press begins
	//private Point startP;
	// The currently selected tool
	private int selectedOption;
	
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
	public void set_color(Color color){
		this.color = color;
	}
	
	public void set_selectedOption(int option){
		selectedOption = option;
	}
	
	@Override
	public void paint (Graphics g){
		//TODO Unused but seeminly required
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
		doDrawPoint(e.getPoint(), this.color, 1); //Ian's original (temp)
		//TODO use pencilDragged, brushDragged instead
		
		//TODO initialise 'previousP'
		/*#### DOES NOT WORK With uninitialised variables
		// Get current point
		Point current = e.getPoint();
		
		// Perform various different functions for currently selected tool
		switch (selectedOption) {
        	case 0: pencilDragged(current); break;      
        	case 1: brushDragged(current); break;
		}
		
		
		
		// Reset the previous point for next use.
		//previousP = current;
		 */
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
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
	
	/**
	 * Handles what to do when dragging with the pencil tool selected
	 * @param p
	 */
	private void pencilDragged(Point p){
		Graphics g = getGraphics();    
        g.setColor(color);
        g.drawLine(previousP.x, previousP.y, p.x, p.y);
        //repaint();
        this.paint(g);
	}
	
	/**
	 * Handles what to do when dragging with the brush tool selected
	 * @param p
	 */
	private void brushDragged(Point p){
		Graphics g = getGraphics();    
        g.setColor(color);       
        Graphics2D gThick = (Graphics2D) g;
        gThick.setStroke(new BasicStroke(5));
        gThick.drawLine(previousP.x, previousP.y, p.x, p.y);        
        //repaint();
        this.paint(g);
        // TODO: add some circles to give brush strokes nice round edges
	}
}
