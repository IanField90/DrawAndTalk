package cs2ts6.client;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.JToolBar;

/**
 * 
 * @author Ian Field, Stephen (Minor), Curtis (GUI)
 * Creates the Canvas panel. This is a 640px x 480px DrawingCanvas and a toolbar.
 * Toolbar sets private members in DrawingCanvas.
 */
public class DrawingPanel extends JPanel implements ActionListener{

	/**
	 * Automatically generated number
	 */
	private static final long serialVersionUID = 7531407885742074028L;
	private JButton pen, brush, clear, brushSize, erase, brushColour;
	private JSlider sizeSlider;
	private DrawingCanvas canvas; //Canvas where drawing is handled
	private Color colour; //Holds drawing colour - for GUI/feedback
	private final static String ICON_PATH = "src/icons" + File.separator;
	
	// Use enums for types
	public static enum DrawType { PEN, BRUSH, ERASE, SQUARE, CIRCLE , FULL_CLEAR};
	
	public DrawingPanel(){
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
		
		pen = new JButton(getImageIcon("pen.jpg"));
		pen.setToolTipText("Pen");
		pen.addActionListener(this);
		
		clear = new JButton(getImageIcon("new.jpg"));
		clear.setToolTipText("Clear canvas");
		//clear.setEnabled(false);//TODO Support teacher admin (sprint 2)
		clear.addActionListener(this);
		
		brushSize = new JButton(getImageIcon("size.jpg"));
		brushSize.setToolTipText("Brush size");
		brushSize.addActionListener(this);
		
		sizeSlider = new JSlider(JSlider.HORIZONTAL, 2, 32, 5);
		sizeSlider.setVisible(false);
		sizeSlider.setMajorTickSpacing(5);
		sizeSlider.setMinorTickSpacing(1);
		sizeSlider.setPaintTicks(true);
		sizeSlider.setPaintLabels(true);
		
		erase = new JButton(getImageIcon("eraser.jpg"));
		erase.setToolTipText("Erase by drawing with the 'rubber'");
		erase.addActionListener(this);
		
		brushColour = new JButton(getImageIcon("palette.jpg"));
		brushColour.setSize(brushColour.getPreferredSize());
		brushColour.setToolTipText("Colour Palette");
		brushColour.addActionListener(this);
		
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
		if(e.getSource() == brush){
			canvas.set_selectedOption(DrawType.BRUSH);
		}
		if(e.getSource() == pen){
			canvas.set_selectedOption(DrawType.PEN);
		}
		if(e.getSource() == brushSize){
			brushSize.setVisible(false);
			sizeSlider.setVisible(true);
		}
		if(e.getSource() == clear){
			//canvas = new DrawingCanvas(); //Clear the canvas
			canvas.clear(); //clear canvas, does not change reference
			//canvas.set_client(client); // canvas needs client
		}
		if(e.getSource() == erase) {
			canvas.set_selectedOption(DrawType.ERASE);
		}
		if(e.getSource() == brushColour) {
			/*String[] choices = { "Red", "Green", "Blue", "Black", "Yellow" };
			int choice = JOptionPane.showOptionDialog(null, "Choose a colour", "Colour Palette", 
					JOptionPane.YES_NO_OPTION, JOptionPane.PLAIN_MESSAGE, null, choices, "Black");*/
			
			ColourPalette cp = new ColourPalette();
			cp.createAndShowGUI();
			/*switch(choice) {
			case 0: canvas.set_colour(Color.RED); break;
			case 1: canvas.set_colour(Color.GREEN); break;
			case 2: canvas.set_colour(Color.BLUE); break;
			case 3: canvas.set_colour(Color.BLACK); break;
			case 4: canvas.set_colour(Color.YELLOW); break;
			}*/
		}
		//TODO Colours
		//TODO Save

	}

}
