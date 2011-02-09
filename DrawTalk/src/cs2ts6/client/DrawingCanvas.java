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

import cs2ts6.client.DrawingPanel.drawType;
import cs2ts6.packets.PointPacket;

/**
 * 
 * @author Ian Field
 * A custom implementation of canvas to implement drawing for lines and brush strokes (Sprint 1)
 */
public class DrawingCanvas extends Canvas implements MouseMotionListener, MouseListener {
	// TODO Packets - Bitmap [print()] or coordinates?

	/**
	 * Automatically generated, removes warning
	 */
	private static final long serialVersionUID = 5995159706684610807L;
	
	private Color colour;
	// Always the point that was in use before the current point
	private Point previousP;
	// Always the point where the mouse press begins
	// The currently selected tool
	//private int selectedOption;
	private drawType selectedOption;
	
	public DrawingCanvas(){
		setBackground(Color.white);
		colour = Color.black;
		this.addMouseMotionListener(this);
		this.addMouseListener(this);
	}
	
	public void sendPoints(){
		// TODO Send point packet to server
		int sX, sY, fX, fY;
		//client.sendPoints()
	}
	
	public void drawPoints(PointPacket pkt){
		// TODO sort
	}
	
	/**
	 * Sets the colour to be drawn on the canvas
	 * @param color Colour to be used when drawing.
	 */
	public void set_colour(Color color){
		this.colour = color;
	}
	
	public void set_selectedOption(drawType option){
		selectedOption = option;
	}
	
	@Override
	public void paint (Graphics g){
		//TODO Unused but seeminly required
	}
	
	@Override
	public void mouseDragged(MouseEvent e) {
		// Get current point
		Point current = e.getPoint();
		// Perform various different functions for currently selected tool
		switch (selectedOption) {
        	case PEN: pencilDragged(current); break;      
        	case BRUSH: brushDragged(current); break;
		}
		// Reset the previous point for next use.
		previousP = current;
		
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		// TODO Auto-generated method stub
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
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
		previousP = e.getPoint(); //Maybe?
		Point current = e.getPoint();
		switch (selectedOption) {
    		case PEN: pencilDragged(current); break;      
    		case BRUSH: brushDragged(current); break;
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
        //repaint();
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
        //repaint();
        this.paint(g);
        // TODO: add some circles to give brush strokes nice round edges
	}
}
