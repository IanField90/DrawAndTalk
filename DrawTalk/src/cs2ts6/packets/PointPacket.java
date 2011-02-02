package cs2ts6.packets;

import java.awt.Point;

public class PointPacket {
	//TODO Extend packet
	private Point p;
	private int size;
	//private Colour colour;
	
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
}
