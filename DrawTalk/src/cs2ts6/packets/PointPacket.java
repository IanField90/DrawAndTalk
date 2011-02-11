package cs2ts6.packets;

import java.awt.Color;
import java.awt.Point;

public class PointPacket extends Packet{
	//TODO Extend packet
	private Point p;
	private int size;
	private Color colour;
	
	public PointPacket(Point p, Color colour, int size){
		this.p = p;
		this.set_colour(colour);
		this.size = size;
	}
	
	public void set_p(Point p) {
		this.p = p;
	}
	public Point get_p() {
		return p;
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
	
}
