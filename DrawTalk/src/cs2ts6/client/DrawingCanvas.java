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

import cs2ts6.client.DrawingPanel.DrawType;
import cs2ts6.packets.PointPacket;

/** 
 * @author Ian Field
 * A custom implementation of canvas to implement drawing for lines and brush strokes (Sprint 1)
 */
public class DrawingCanvas extends Canvas implements MouseMotionListener, MouseListener {

	/**
	 * Automatically generated, removes warning
	 */
	private static final long serialVersionUID = 5995159706684610807L;
	// Colour currently being drawn
	private Color colour;
	// Always the point that was in use before the current point
	private Point previousP;
	// Current point in use
	private Point currentP;
	// The currently selected tool
	private DrawType selectedOption;
	long time;
	private Client client; //treated as a pointer
	
	private Graphics graphic;
	
	public DrawingCanvas(){
		//this.client = client;
		setBackground(Color.white);
		colour = Color.black;
		this.addMouseMotionListener(this);
		this.addMouseListener(this);
	}
	
	public void set_client(Client client){
		this.client = client;
	}
	
	public void sendPoints(){
		// Send point packet to server
		PointPacket pkt = new PointPacket(previousP.x, previousP.y, currentP.x, 
				currentP.y, colour, 1, selectedOption);
		client.sendPoints(pkt);
	}
	
	/**
	 * Draws the packet transmitted from the server on the canvas.
	 * @param pkt Packet containing start and end points, with draw
	 */
	public void drawPoints(PointPacket pkt){
		//Will be integrated but proof of concept
		if(pkt.get_drawType() == DrawType.FULL_CLEAR) {
			super.paint(getGraphics());
		}
		
		switch (pkt.get_drawType()){
		case PEN:
			Graphics g = getGraphics();    
	        g.setColor(pkt.get_colour());       
	        Graphics2D gThick = (Graphics2D) g;
	        gThick.setStroke(new BasicStroke(1));
	        gThick.drawLine(pkt.get_startX(), pkt.get_startY(), 
	        		pkt.get_finishX(), pkt.get_finishY());   
	        this.paint(gThick);
			break;
		case BRUSH:
			Graphics g2 = getGraphics();    
	        g2.setColor(pkt.get_colour());       
	        Graphics2D gThick2 = (Graphics2D) g2;
	        gThick2.setStroke(new BasicStroke(5));
	        gThick2.drawLine(pkt.get_startX(), pkt.get_startY(), 
	        		pkt.get_finishX(), pkt.get_finishY());        
	        this.paint(gThick2);
			break;
		}
		
		// TODO Ali - Implement
	}
	
	/**
	 * Sets the colour to be drawn on the canvas
	 * @param color Colour to be used when drawing.
	 */
	public void set_colour(Color color){
		this.colour = color;
	}
	
	public void set_selectedOption(DrawType option){
		selectedOption = option;
	}
	
	@Override
	public void paint (Graphics g){
		// Unused but required to override
		
	}
	
	/**
	 * Clears the canvas
	 */
	public void clear(){
		DrawType prev = selectedOption;
		set_selectedOption(DrawType.FULL_CLEAR);
		sendPoints();
		sendPoints(); // Ensure 100% on UDP
		selectedOption = prev;
		//super.paint(getGraphics()); //super - default clears canvas
	}
	
	@Override
	public void mouseDragged(MouseEvent e) {
		//System.out.println(System.nanoTime()-time);
		//time = System.nanoTime();
		// Get current point
		currentP = e.getPoint();
		sendPoints();
		previousP = currentP;
		
	}

	@Override
	public void mouseMoved(MouseEvent e) {
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
	}

	@Override
	public void mouseEntered(MouseEvent e) {
	}

	@Override
	public void mouseExited(MouseEvent e) {
	}

	@Override
	public void mousePressed(MouseEvent e) {
		previousP = e.getPoint();
		currentP = e.getPoint();
		sendPoints();
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
	}

}
