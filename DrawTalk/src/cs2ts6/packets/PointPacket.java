package cs2ts6.packets;

import java.awt.Color;

import cs2ts6.client.DrawingPanel.DrawType;

/**
 * @author Ian Field
 * Used to transmit the data of the drawing between the clients and server.
 */
public class PointPacket extends Packet{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int startX, startY, finishX, finishY; //Co-ordinates for drawing
	private int size; // Brush size
	private Color colour; // Tool colour
	private DrawType drawType; //for future use (shapes)
	
	public PointPacket(int startX, int startY, int finishX, int finishY, Color colour, int size, DrawType drawType){
		this.set_startX(startX);
		this.set_startY(startY);
		this.set_finishX(finishX);
		this.set_finishY(finishY);
		this.set_drawType(drawType);
		this.set_colour(colour);
		this.size = size;
	}
	
	
	public void set_size(int size) {
		this.size = size;
	}
	public int get_size() {
		return size;
	}

	public void set_colour(Color colour) {
		this.colour = colour;
	}
	public Color get_colour() {
		return colour;
	}

	public void set_startX(int startX) {
		this.startX = startX;
	}
	public int get_startX() {
		return startX;
	}

	public void set_startY(int startY) {
		this.startY = startY;
	}
	public int get_startY() {
		return startY;
	}

	public void set_finishX(int finishX) {
		this.finishX = finishX;
	}
	public int get_finishX() {
		return finishX;
	}

	public void set_finishY(int finishY) {
		this.finishY = finishY;
	}
	public int get_finishY() {
		return finishY;
	}

	public void set_drawType(DrawType drawType) {
		this.drawType = drawType;
	}
	public DrawType get_drawType() {
		return drawType;
	}
	
	public String toString() {
		String str = "";
		str = str+startX+" "+finishX+" "+startY+" "+finishY+" "+size;
		return str;
	}
}
