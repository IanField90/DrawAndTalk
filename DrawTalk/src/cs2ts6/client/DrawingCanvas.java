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
	
	public DrawingCanvas(){
		setBackground(Color.white);
		colour = Color.black;
		this.addMouseMotionListener(this);
		this.addMouseListener(this);
	}
	
	public void sendPoints(){
		// TODO Send point packet to server
		PointPacket pkt = new PointPacket(previousP.x, previousP.y, currentP.x, 
				currentP.y, colour, 1, selectedOption);
		//../drawingPanel/mainWindow/client??
		//this.getParent().getParent().getClient().sendPoints(pkt);
	}
	
	public void drawPoints(PointPacket pkt){
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
	
	@Override
	public void mouseDragged(MouseEvent e) {
		// Get current point
		currentP = e.getPoint();
		// Perform various different functions for currently selected tool
		switch (selectedOption) {
        	case PEN: pencilDragged(currentP); break;      
        	case BRUSH: brushDragged(currentP); break;
		}
		// Reset the previous point for next use.
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
		switch (selectedOption) {
    		case PEN: pencilDragged(currentP); break;      
    		case BRUSH: brushDragged(currentP); break;
		}
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
	}
	
	/**
	 * Handles what to do when dragging with the pencil tool selected
	 * @param p Point to draw to from previous point
	 */
	private void pencilDragged(Point p){
		Graphics g = getGraphics();    
        g.setColor(colour);
        g.drawLine(previousP.x, previousP.y, p.x, p.y);
        this.paint(g);
	}
	
	/**
	 * Handles what to do when dragging with the brush tool selected
	 * @param p Point to draw to from previous point
	 */
	private void brushDragged(Point p){
		Graphics g = getGraphics();    
        g.setColor(colour);       
        Graphics2D gThick = (Graphics2D) g;
        gThick.setStroke(new BasicStroke(5));
        gThick.drawLine(previousP.x, previousP.y, p.x, p.y);        
        this.paint(g);
        // TODO: add some circles to give brush strokes nice round edges
	}
}
