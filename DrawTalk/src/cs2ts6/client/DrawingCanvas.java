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
	private boolean flag = true;
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
	
	@Override
	public void paint (Graphics g){
		// Unused but required to override
		if(pktList.size() > 0){
			PointPacket pkt = pktList.get(pktList.size()-1);
			g.setColor(pkt.get_colour());
			Graphics2D gThick = (Graphics2D)g;
			switch(pkt.get_drawType()){
			case PEN:
		        gThick.setStroke(new BasicStroke(size));
				break;
			case BRUSH:
		        gThick.setStroke(new BasicStroke(size));
				break;
			case ERASE:
				gThick.setStroke(new BasicStroke(size));
				g.setColor(Color.WHITE);
			}
			gThick.drawLine(pkt.get_startX(), pkt.get_startY(), pkt.get_finishX(), pkt.get_finishY()); 
		}

	}
	
	/**
	 * Draws all previous points in the array list on screen. This is in place as the paint method is called
	 * by the system and would result in the wiping of the canvas. At least, all but the last draw call.
	 */
	public void redrawAction(){
		Graphics g = getGraphics();
		for(int i = 0; i < pktList.size(); i++){
			PointPacket pkt = pktList.get(i);
			g.setColor(pkt.get_colour());
			Graphics2D gThick = (Graphics2D)g;
			switch(pkt.get_drawType()){
			case PEN:
		        gThick.setStroke(new BasicStroke(pkt.get_size()));
				break;
			case BRUSH:
		        gThick.setStroke(new BasicStroke(pkt.get_size()));
				break;
			case ERASE:
				gThick.setStroke(new BasicStroke(pkt.get_size()));
				g.setColor(Color.WHITE);
			}
			gThick.drawLine(pkt.get_startX(), pkt.get_startY(), pkt.get_finishX(), pkt.get_finishY()); 
		}
		paint(g);
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
		
		//One packet in 2
		/*if(flag) {
			sendPoints();
			previousP = currentP;
			flag = false;
		} else {
			flag = true;
		}*/
		
		//One packet in X
		if(iflag == 0 || iflag == 3){
			sendPoints();
			previousP = currentP;
			flag = false;
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
					canvas.redrawAction();
				}
				catch (InterruptedException e){
					//Do nothing
				}
			}
		}
	}
}
