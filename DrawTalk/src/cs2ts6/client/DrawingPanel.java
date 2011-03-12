package cs2ts6.client;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.net.URL;

import javax.swing.Action;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.JToolBar;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

/**
 * 
 * @author Ian Field, Stephen, Curtis (GUI)
 * Creates the Canvas panel. This is a 640px x 480px DrawingCanvas and a toolbar.
 * Toolbar sets private members in DrawingCanvas.
 */
public class DrawingPanel extends JPanel implements ActionListener, ChangeListener{

	/**
	 * Automatically generated number
	 */
	private static final long serialVersionUID = 7531407885742074028L;
	private JButton pen, brush, clear, brushSize, erase, brushColour;
	private JSlider sizeSlider;
	private DrawingCanvas canvas; //Canvas where drawing is handled
	private Color colour; //Holds drawing colour - for GUI/feedback
	private ColourPalette cp;
	private JFrame masterFrame; // used for co-ord tracking
	private final static String ICON_PATH = "src/icons" + File.separator;
	private boolean firstRun = true;
	private Image bimg;	
	
	// Use enums for types
	public static enum DrawType { PEN, BRUSH, ERASE, SQUARE, CIRCLE , FULL_CLEAR};
	
	public DrawingPanel(JFrame f, Image bimg){
		masterFrame = f;
		this.bimg = bimg;
		JPanel panel = new JPanel(); //Panel with tooblar + canvas
		panel.setLayout(new BorderLayout());
		colour = Color.BLACK;
		canvas = new DrawingCanvas();
		canvas.setPreferredSize(new Dimension(640, 366));
		canvas.set_colour(colour); //Sets drawing colour inside canvas
		canvas.set_selectedOption(DrawType.PEN);
		cp = new ColourPalette(canvas);
		//ToolBar
		ImageIcon imgicon = new ImageIcon("src/icons/Toolbar.png");
		JToolBar toolBar = new ToolBarWithImage("test", 0, imgicon);
		// WHY WONT THIS WORK FFS!!!
        toolBar.setRollover(true);
		        
		brush = new JButton(new ImageIcon(ICON_PATH + "brush.png"));
		brush.setToolTipText("Brush");
		brush.addActionListener(this);
		brush.setOpaque(false);
				
		pen = new JButton(new ImageIcon(ICON_PATH + "pen.png"));
		pen.setToolTipText("Pen");
		pen.addActionListener(this);
		pen.setBackground(Color.DARK_GRAY);
		
		clear = new JButton(new ImageIcon(ICON_PATH + "new.png"));
		clear.setToolTipText("Clear canvas");
		//clear.setEnabled(false);//TODO Support teacher admin (sprint 2)
		clear.addActionListener(this);
		clear.setOpaque(false);
		
		brushSize = new JButton(new ImageIcon(ICON_PATH + "size.png"));
		brushSize.setToolTipText("Brush size");
		brushSize.addActionListener(this);
		brushSize.setOpaque(false);
		
		sizeSlider = new JSlider(JSlider.HORIZONTAL, 0, 20, 5);
		sizeSlider.setVisible(false);
		sizeSlider.setOpaque(false);
		sizeSlider.setMajorTickSpacing(5);
		sizeSlider.setMinorTickSpacing(1);
		sizeSlider.setPaintTicks(true);
		sizeSlider.setPaintLabels(true);
		sizeSlider.addChangeListener(this);
		//Changing this to light grey makes minor ticks not visible
		//sizeSlider.setBackground(Color.lightGray); 
		
		erase = new JButton(new ImageIcon(ICON_PATH + "eraser.png"));
		erase.setToolTipText("Erase by drawing with the 'rubber'");
		erase.addActionListener(this);
		erase.setOpaque(false);
		
		brushColour = new JButton(new ImageIcon(ICON_PATH + "palette.png"));
		brushColour.setSize(brushColour.getPreferredSize());
		brushColour.setToolTipText("Colour Palette");
		brushColour.addActionListener(this);
		brushColour.setOpaque(false);
		
		toolBar.setOrientation(0);//Make toolbar appear horizontal
		toolBar.add(pen);
		toolBar.addSeparator(  );
		toolBar.add(brush);
		toolBar.addSeparator(  );
		toolBar.add(brushSize);
		toolBar.add(sizeSlider);
		toolBar.addSeparator(  );
		toolBar.add(brushColour);
		toolBar.addSeparator(  );
		toolBar.add(erase);
		toolBar.addSeparator(  );
		toolBar.add(clear);
		toolBar.setFloatable(false); //Disables dragging of toolbar
		
		panel.add(toolBar, BorderLayout.NORTH);
		panel.add(canvas, BorderLayout.CENTER);
		add(panel);
	}
	
	public void paintComponent(Graphics g) {
		  g.drawImage(bimg, 0, 0, null);
		}
	
	/**
	 * Return a ImageIcon with the given icon or generate an error message.
	 * @return the icon as an image 
	 */
	static ImageIcon getImageIcon(String iconFilename) {
		ImageIcon theIcon = null;
		// Directory of the image
		//File theImage = new File(ICON_PATH + iconFilename);
		java.net.URL imgURL = DrawingPanel.class.getResource("/src/icons/"+iconFilename);
		if(imgURL != null) {
			Image theImage = Toolkit.getDefaultToolkit().getImage(imgURL);
			theIcon = new ImageIcon(theImage);
		} else {
			JOptionPane.showMessageDialog(null, "Error - file not found: "+ iconFilename);
		}
		/*if (theImage.isFile()) {
			theIcon = new ImageIcon(theImage.getAbsolutePath());
		} else {
			// display an error if the image can't be found
			theIcon = null;
			JOptionPane.showMessageDialog(null, "Error - file not found: "
					+ iconFilename);
		}*/
		return (theIcon);
	}
	
	public DrawingCanvas get_canvas(){
		return canvas;
	}
	
	public ColourPalette get_cpalette() {
		return cp;
	}


	@Override
	public void actionPerformed(ActionEvent e) {
		int size = 1;
		boolean flag = false;
		if(e.getSource() == brush){
			canvas.set_selectedOption(DrawType.BRUSH);
			sizeSlider.setMaximum(30);
			size = 10;
			flag = true;
			((JButton)e.getSource()).setBackground(Color.BLACK);
		}
		if(e.getSource() == pen){
			canvas.set_selectedOption(DrawType.PEN);
			sizeSlider.setMaximum(10);
			size = 1;
			flag = true;
		}
		/*if(e.getSource() == clear){
			size = canvas.get_brushSize();
			canvas.clear(); //clear canvas, does not change reference
			flag = true;
		}*/
		if(e.getSource() == erase) {
			canvas.set_selectedOption(DrawType.ERASE);
			sizeSlider.setMaximum(40);
			size = 30;
			flag = true;
		}
		if(flag) { // If one of the above
			//Generic to all
			canvas.set_brushSize(size);
			sizeSlider.setVisible(false);
			brushSize.setVisible(true);
			sizeSlider.setValue(size);
			//Set all to 'off'
			pen.setOpaque(false);
			pen.repaint();
			brush.setOpaque(false);
			brush.repaint();
			erase.setOpaque(false);
			erase.repaint();
			//Set selected to 'on'
			((JButton)e.getSource()).setOpaque(true);
			((JButton)e.getSource()).setBackground(Color.darkGray);
			//If clear, just turn to 'off'
			clear.setOpaque(false);
			clear.repaint();
		}
		
		if(e.getSource() == clear){
			canvas.clear(); //clear canvas, does not change reference
		}

		if(e.getSource() == brushSize){
			brushSize.setVisible(false);
			sizeSlider.setVisible(true);
		}
		if(e.getSource() == brushColour) {
			if(firstRun) {
				cp.createAndShowGUI();
				firstRun = false;
			}
			cp.setVisible(masterFrame.getX(),masterFrame.getY(), false);
		}
		canvas.redrawAction();

	}

	@Override
	public void stateChanged(ChangeEvent arg0) {
		if(arg0.getSource() == sizeSlider) {
			canvas.set_brushSize(sizeSlider.getValue());
		}
		
	}
	
	/**
	 * Class Overrides JToolBar to enable an image to be used as a background
	 * Class will stretch supplied image to fit the space available
	 * @author Curtis
	 *
	 */
	   class ToolBarWithImage extends JToolBar {
	 
	      private ImageIcon bgImage;
	 
	      ToolBarWithImage(String name, int orientation, ImageIcon imgicon) {
	         super(name, orientation);
	         this.bgImage = imgicon;
	         setOpaque(true);
	      }
	 
	      public void paintComponent(Graphics g) {
	         super.paintComponent(g);
	         if (bgImage != null) {
	            Dimension size = this.getSize();
	            g.drawImage(bgImage.getImage(), 0,0, size.width, size.height, this);
	         }
	      }
	   }

}
