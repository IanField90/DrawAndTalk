package cs2ts6.client;

import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;

import javax.swing.JPanel;

//TODO Dimensions 640x480
public class Canvas extends JPanel implements MouseMotionListener{

	//TODO Construct jpanel, white - fixed size
	//TODO Draw point using pointpacket
	//TODO Add toolbar
	
	/**
	 * Sends PointPacket
	 */
	@Override
	public void mouseDragged(MouseEvent e) {
		// TODO Send coordinate data + colour on move
		Point drawLoc = e.getPoint();
		//TODO Get colour
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		// TODO Auto-generated method stub
	}

}
