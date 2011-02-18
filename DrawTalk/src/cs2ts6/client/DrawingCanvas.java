package cs2ts6.client;

import java.awt.BasicStroke;
import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.RenderingHints;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.ArrayList;

import cs2ts6.client.DrawingPanel.DrawType;
import cs2ts6.packets.PointPacket;

/** 
 * @author Ian Field, Stephen, Ali
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
	private int iflag = 0;
	
	private int size;
	long time;
	private Client client; //treated as a pointer
	
	private ArrayList<PointPacket> pktList = new ArrayList<PointPacket>();
	
	public DrawingCanvas(){
		setBackground(Color.white);
		colour = Color.black;
		this.addMouseMotionListener(this);
		this.addMouseListener(this);
		new RedrawThread(this).start();
	}
	
	public void set_client(Client client){
		this.client = client;
	}
	
	public void sendPoints(){
		// Check point have been initilized
		if (previousP == null || currentP == null)
			return;
		
		// Send point packet to server
		PointPacket pkt = new PointPacket(previousP.x, previousP.y, currentP.x, 
				currentP.y, colour, size, selectedOption);
		client.sendPoints(pkt);
		pktList.add(pkt);
		paint(getGraphics());
	}
	
	/**
	 * Draws the packet transmitted from the server on the canvas.
	 * @param pkt Packet containing start and end points, with draw
	 */
	public void drawPoints(PointPacket pkt){
		//Will be integrated but proof of concept
		if(pkt.get_drawType() == DrawType.FULL_CLEAR) {
			super.paint(getGraphics());
			pktList.clear();
			return;
		}
		else{
			pktList.add(pkt);
			paint(getGraphics());
		}
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
	
	public void set_brushSize(int s) {
		size = s;
	}
	
	public int get_brushSize() {
		return size;
	}
	
	@Override
	public void paint (Graphics g){
		((Graphics2D) g).setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		// Unused but required to override
		if(pktList.size() > 0){
			PointPacket pkt = pktList.get(pktList.size()-1);
			if(pkt.get_drawType() != DrawType.ERASE) {
				g.setColor(pkt.get_colour());
			} else {
				g.setColor(Color.WHITE);
			}
			Graphics2D gThick = (Graphics2D)g;
			gThick.setStroke(new BasicStroke(pkt.get_size())); // Sets brush size to packet size

			Point p0 = new Point(pkt.get_startX(), pkt.get_startY());
			Point p1 = new Point(pkt.get_finishX(), pkt.get_finishY());
			
			int size = pkt.get_size(); 
			double angle = Math.atan2(p1.y - p0.y, p1.x - p0.x);		
			double distance = p0.distance(p1);
			
			// If after the line is shortened by a brush size it is still smaller than the smallest possible line which can be drawn (a square)
			// then draw a polygon instead of using a line
			if ((distance - size) > size){
				
				// These 2 points basically shorten the drawn line by half the brush width at either end
				Point p2 = new Point((int)(p0.x + (size / 2) * Math.cos(angle)), (int)(p0.y + (size / 2) * Math.sin(angle)));
				Point p3 = new Point((int)(p1.x + (size / 2) * Math.cos(angle + Math.PI)), (int)(p1.y + (size / 2) * Math.sin(angle + Math.PI)));			
				gThick.drawLine(p2.x, p2.y, p3.x, p3.y);
				
			} else {
				
				// Possibly drawing a line may become deprecated, if we decide to represent everything with just polygons.
				
				// Calculate polygon points
				int[] xPoly = {
						(int)(p0.x + (size / 2) * Math.cos(angle + Math.PI / 2)), 
						(int)(p0.x + (size / 2) * Math.cos(angle - Math.PI / 2)),
						(int)(p1.x + (size / 2) * Math.cos(angle - Math.PI / 2)),
						(int)(p1.x + (size / 2) * Math.cos(angle + Math.PI / 2))
				};
				
				int[] yPoly = {
						(int)(p0.y + (size / 2) * Math.sin(angle + Math.PI / 2)),
						(int)(p0.y + (size / 2) * Math.sin(angle - Math.PI / 2)),
						(int)(p1.y + (size / 2) * Math.sin(angle - Math.PI / 2)),
						(int)(p1.y + (size / 2) * Math.sin(angle + Math.PI / 2))
				};
				
				g.fillPolygon(xPoly, yPoly, xPoly.length);
			}
						
			// Smooth of the edges with a circle
			g.fillOval(p1.x - (size / 2), p1.y - (size / 2), size, size); 
		}

	}
	
	/**
	 * Draws all previous points in the array list on screen. This is in place as the paint method is called
	 * by the system and would result in the wiping of the canvas. At least, all but the last draw call.
	 */
	public void redrawAction(){
		Graphics g = getGraphics();
		((Graphics2D) g).setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		for(int i = 0; i < pktList.size(); i++){ 
			PointPacket pkt = pktList.get(i);
			if(pkt.get_drawType() != DrawType.ERASE) {
				g.setColor(pkt.get_colour());
			} else {
				g.setColor(Color.WHITE);
			}
			Graphics2D gThick = (Graphics2D)g;
			gThick.setStroke(new BasicStroke(pkt.get_size()));
			Point p0 = new Point(pkt.get_startX(), pkt.get_startY());
			Point p1 = new Point(pkt.get_finishX(), pkt.get_finishY());
			
			int size = pkt.get_size(); 
			double angle = Math.atan2(p1.y - p0.y, p1.x - p0.x);		
			double distance = p0.distance(p1);
			
			// If after the line is shortened by a brush size it is still smaller than the smallest possible line which can be drawn (a square)
			// then draw a polygon instead of using a line
			if ((distance - size) > size){
				
				// These 2 points basically shorten the drawn line by half the brush width at either end
				Point p2 = new Point((int)(p0.x + (size / 2) * Math.cos(angle)), (int)(p0.y + (size / 2) * Math.sin(angle)));
				Point p3 = new Point((int)(p1.x + (size / 2) * Math.cos(angle + Math.PI)), (int)(p1.y + (size / 2) * Math.sin(angle + Math.PI)));			
				gThick.drawLine(p2.x, p2.y, p3.x, p3.y);
				
			} else {
				
				// Possibly drawing a line may become deprecated, if we decide to represent everything with just polygons.
				
				// Calculate polygon points
				int[] xPoly = {
						(int)(p0.x + (size / 2) * Math.cos(angle + Math.PI / 2)), 
						(int)(p0.x + (size / 2) * Math.cos(angle - Math.PI / 2)),
						(int)(p1.x + (size / 2) * Math.cos(angle - Math.PI / 2)),
						(int)(p1.x + (size / 2) * Math.cos(angle + Math.PI / 2))
				};
				
				int[] yPoly = {
						(int)(p0.y + (size / 2) * Math.sin(angle + Math.PI / 2)),
						(int)(p0.y + (size / 2) * Math.sin(angle - Math.PI / 2)),
						(int)(p1.y + (size / 2) * Math.sin(angle - Math.PI / 2)),
						(int)(p1.y + (size / 2) * Math.sin(angle + Math.PI / 2))
				};
				
				g.fillPolygon(xPoly, yPoly, xPoly.length);
			}
						
			// Smooth of the edges with a circle
			g.fillOval(p1.x - (size / 2), p1.y - (size / 2), size, size); 
		}
		//paint(g); // I believe this was causing issues in the repaint
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
		super.paint(getGraphics());
		pktList.clear(); //empty array list for redrawing
		//super.paint(getGraphics()); //super - default clears canvas
	}
	
	@Override
	public void mouseDragged(MouseEvent e) {
		// Get current point
		currentP = e.getPoint();
		
		//One packet in X
		if(iflag == 0 || iflag == 3){
			sendPoints();
			previousP = currentP;
			iflag = 1;
		} else {
			iflag++;
		}
		
	}

	@Override
	public void mouseMoved(MouseEvent e) {
	}

	@Override
	public void mouseClicked(MouseEvent e) {
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
	}

	
	private class RedrawThread extends Thread {
		DrawingCanvas canvas;
		RedrawThread(DrawingCanvas canv){
			super("RedrawThread");
			canvas = canv;
		}
		
		public void run(){
			while (true) {
				try{
					Thread.sleep(4000);
					//canvas.redrawAction();
				}
				catch (InterruptedException e){
					//Do nothing
				}
			}
		}
	}
}
