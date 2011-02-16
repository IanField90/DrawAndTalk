package cs2ts6.client;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

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
	
	// Use enums for types
	public static enum DrawType { PEN, BRUSH, ERASE, SQUARE, CIRCLE , FULL_CLEAR};
	
	public DrawingPanel(JFrame f){
		masterFrame = f;
		JPanel panel = new JPanel(); //Panel with tooblar + canvas
		panel.setLayout(new BorderLayout());
		colour = Color.BLACK;
		canvas = new DrawingCanvas();
		canvas.setPreferredSize(new Dimension(640, 390));
		canvas.set_colour(colour); //Sets drawing colour inside canvas
		canvas.set_selectedOption(DrawType.PEN);
		
		//ToolBar
		JToolBar toolBar = new JToolBar();
        toolBar.setRollover(true);
		
		brush = new JButton(getImageIcon("brush.jpg"));
		brush.setToolTipText("Brush");
		brush.addActionListener(this);
		brush.setBackground(Color.lightGray);
		
		pen = new JButton(getImageIcon("pen.jpg"));
		pen.setToolTipText("Pen");
		pen.addActionListener(this);
		pen.setBackground(Color.lightGray);
		
		clear = new JButton(getImageIcon("new.jpg"));
		clear.setToolTipText("Clear canvas");
		//clear.setEnabled(false);//TODO Support teacher admin (sprint 2)
		clear.addActionListener(this);
		clear.setBackground(Color.lightGray);
		
		brushSize = new JButton(getImageIcon("size.jpg"));
		brushSize.setToolTipText("Brush size");
		brushSize.addActionListener(this);
		brushSize.setBackground(Color.lightGray);
		
		sizeSlider = new JSlider(JSlider.HORIZONTAL, 0, 20, 5);
		sizeSlider.setVisible(false);
		sizeSlider.setMajorTickSpacing(5);
		sizeSlider.setMinorTickSpacing(1);
		sizeSlider.setPaintTicks(true);
		sizeSlider.setPaintLabels(true);
		sizeSlider.addChangeListener(this);
		//Changing this to light grey makes minor ticks not visible
		//sizeSlider.setBackground(Color.lightGray); 
		
		erase = new JButton(getImageIcon("eraser.jpg"));
		erase.setToolTipText("Erase by drawing with the 'rubber'");
		erase.addActionListener(this);
		erase.setBackground(Color.lightGray);
		
		brushColour = new JButton(getImageIcon("palette.jpg"));
		brushColour.setSize(brushColour.getPreferredSize());
		brushColour.setToolTipText("Colour Palette");
		brushColour.addActionListener(this);
		brushColour.setBackground(Color.lightGray);
		
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
		toolBar.setBackground(Color.lightGray);
		toolBar.setFloatable(false); //Disables dragging of toolbar
		
		panel.add(toolBar, BorderLayout.NORTH);
		panel.add(canvas, BorderLayout.CENTER);
		add(panel);
	}
	
	/**
	 * Return a ImageIcon with the given icon or generate an error message.
	 * @return the icon as an image 
	 */
	static ImageIcon getImageIcon(String iconFilename) {
		ImageIcon theIcon;
		// Directory of the image
		File theImage = new File(ICON_PATH + iconFilename);
		if (theImage.isFile()) {
			theIcon = new ImageIcon(theImage.getAbsolutePath());
		} else {
			// display an error if the image can't be found
			theIcon = null;
			JOptionPane.showMessageDialog(null, "Error - file not found: "
					+ iconFilename);
		}
		return (theIcon);
	}
	
	public DrawingCanvas get_canvas(){
		return canvas;
	}


	@Override
	public void actionPerformed(ActionEvent e) {
		//TODO Emphasise selected drawType
		//Sort drawing type
		//TODO Fix redraw issue
		int size = 1;
		boolean flag = false;
		if(e.getSource() == brush){
			canvas.set_selectedOption(DrawType.BRUSH);
			sizeSlider.setMaximum(20);
			size = 5;
			flag = true;
		}
		if(e.getSource() == pen){
			canvas.set_selectedOption(DrawType.PEN);
			sizeSlider.setMaximum(10);
			size = 1;
			flag = true;
		}
		if(e.getSource() == clear){
			canvas.clear(); //clear canvas, does not change reference
			flag = true;
		}
		if(e.getSource() == erase) {
			canvas.set_selectedOption(DrawType.ERASE);
			sizeSlider.setMaximum(40);
			size = 30;
			flag = true;
		}
		if(flag) {
			//Generic to all
			canvas.set_brushSize(size);
			sizeSlider.setVisible(false);
			brushSize.setVisible(true);
			sizeSlider.setValue(size);
		}

		if(e.getSource() == brushSize){
			brushSize.setVisible(false);
			sizeSlider.setVisible(true);
		}
		if(e.getSource() == brushColour) {
			if(firstRun) {
				cp = new ColourPalette(canvas);
				cp.createAndShowGUI();
				firstRun = false;
			}
			cp.setVisible(masterFrame.getX(),masterFrame.getY()+masterFrame.getHeight());
		}
		canvas.redrawAction();

	}

	@Override
	public void stateChanged(ChangeEvent arg0) {
		if(arg0.getSource() == sizeSlider) {
			canvas.set_brushSize(sizeSlider.getValue());
		}
		
	}

}
