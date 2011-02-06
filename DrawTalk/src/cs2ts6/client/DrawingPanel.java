package cs2ts6.client;


import java.awt.BasicStroke;
import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;

import java.awt.Color;
import java.awt.Dimension;
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

	private ToolbarHandler toolbar;
	
	//TODO Draw point using pointpacket
	public DrawingPanel(){
		
		// Initiate toolbar
		toolbar = new ToolbarHandler();
		
		JPanel panel = new JPanel(); //Panel with tooblar + canvas
		canvas = new Canvas(); //White area
		canvas.setPreferredSize(new Dimension(640, 480));
		//canvas.setColor(color);
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
	 * Sends PointPacket for live authoring
	 */
	@Override
	public void mouseDragged(MouseEvent e) {
		// Send info for the toolbar to handle
		toolbar.mouseDragged(e);
		
		//TODO check type of drawing Sprint 2
		// TODO Send coordinate data + colour on move
		
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
			toolbar.selectOption(1);
		}
		
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
		// Send info for the toolbar to handle
		toolbar.mousePressed(e);
		
		// TODO Auto-generated method stub
		Point drawLoc = e.getPoint();
	}

	/**
	 * Used to manage the various different functions the toolbar has
	 */
	private class ToolbarHandler {
		
		// Always the point that was in use before the current point
		private Point previousP;
		
		// Always the point where the mouse press begins
		private Point startP;
		
		// The currently selected tool
		private int selectedOption = 0;
		
		// The currently selected colour
		private Color selectedColour = Color.BLACK;
		
		// The options, mainly for programmer reference at the moment
		private String[] options =  { 
			"Pencil", 
			"Brush", 
			"Draw Rectangle" 
		};
		
		public ToolbarHandler(){
			
		}
		
		/**
		 * Set the currently selected tool in the toolbox
		 * @param index
		 */
		public void selectOption(int index){
			selectedOption = index;
		}
		
		/**
		 * Set the colour to draw with
		 */
		public void selectColour(Color c){
			selectedColour = c;
		}
		
		/**
		 * The main mouse press handler for the tool bar, handles anything 
		 * generic for all tools and calls individual tool handlers
		 * @param e
		 */
		public void mousePressed(MouseEvent e){
			startP = e.getPoint();
			previousP = startP;
			
			// TODO: add some handlers to start off the pencil and brush strokes, else lines are only 
			// drawn on mouse move, they dont begin on click
		}
		
		/**
		 * The main mouse drag handler for the tool bar, handles anything 
		 * generic for all tools and calls individual tool handlers
		 * @param e
		 */
		public void mouseDragged(MouseEvent e) {
			// Get current point
			Point current = e.getPoint();
			
			// Perform various different functions for currently selected tool
			switch (selectedOption) {
            	case 0: pencilDragged(current); break;      
            	case 1: brushDragged(current); break;
            	case 2: rectangleDragged(current); break;
			}
			
			
			// Reset the previous point for next use.
			previousP = current;
		}
		
		/**
		 * Handles what to do when dragging with the pencil tool selected
		 * @param p
		 */
		private void pencilDragged(Point p){

			Graphics g = canvas.getGraphics();    
	        g.setColor(selectedColour);
	        g.drawLine(previousP.x, previousP.y, p.x, p.y);
	        repaint();
		}
		
		/**
		 * Handles what to do when dragging with the brush tool selected
		 * @param p
		 */
		private void brushDragged(Point p){
			Graphics g = canvas.getGraphics();    
	        g.setColor(selectedColour);       
	        Graphics2D gThick = (Graphics2D) g;
	        gThick.setStroke(new BasicStroke(5));
	        gThick.drawLine(previousP.x, previousP.y, p.x, p.y);        
	        repaint();
	        
	        // TODO: add some circles to give brush strokes nice round edges
		}
		
		/**
		 * Handles what to do when dragging with the pencil tool selected
		 * @param p
		 */
		private void rectangleDragged(Point p){

			// TODO: draw rectangle here.
		}
		
		
	}

	@Override
	public void mouseReleased(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}
	
}
