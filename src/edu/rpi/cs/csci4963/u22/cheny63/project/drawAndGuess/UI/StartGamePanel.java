package edu.rpi.cs.csci4963.u22.cheny63.project.drawAndGuess.UI;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

import edu.rpi.cs.csci4963.u22.cheny63.project.drawAndGuess.tools.ImageUtility;

public class StartGamePanel extends JPanel{
	private static final long serialVersionUID = 1L;
	private Image backgroundImage;
	private Dimension curWindowSize;
	private double cursorTrackerY = 0;
	
	public StartGamePanel(String fileName, Dimension screenSize) throws IOException {
		super();
		this.setBackground(new Color(32, 130, 147));
		backgroundImage = ImageIO.read(new File(fileName));
		this.curWindowSize = new Dimension((int)screenSize.getWidth(), 
				 (int)(screenSize.getWidth())/16*9);
		this.addMouseMotionListener(new MouseMotionListener() {
	        @Override
	        public void mouseMoved(MouseEvent e) {
	        	cursorTrackerY =  e.getY();
	        }
	        @Override
	        public void mouseDragged(MouseEvent e) {
	        }
	    });
	}
	
	@Override
	protected void paintComponent(Graphics g) {
	    super.paintComponent(g);
	    // g.drawImage(ImageUtility.resizeIcon(backgroundImage, curWindowSize), 0, 0, null);
	    this.repaint();
	    this.revalidate();
	}
}
