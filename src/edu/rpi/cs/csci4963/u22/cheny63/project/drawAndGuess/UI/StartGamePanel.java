package edu.rpi.cs.csci4963.u22.cheny63.project.drawAndGuess.UI;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import javax.swing.JPanel;

public class StartGamePanel extends JPanel{
	private static final long serialVersionUID = 1L;
	private Dimension curWindowSize;
	protected double cursorTrackerY = 0;
	protected double cursorTrackerX = 0;
	
	public StartGamePanel(Dimension screenSize) {
		super();
		this.setBackground(new Color(32, 130, 147));
		this.curWindowSize = new Dimension((int)screenSize.getWidth(), 
				             (int)(screenSize.getWidth())/16*9);
		
		this.addMouseMotionListener(new MouseMotionListener() {
	        @Override
	        public void mouseMoved(MouseEvent e) {
	        	cursorTrackerY =  e.getLocationOnScreen().getY();
	        	cursorTrackerX =  e.getLocationOnScreen().getX();
	        }
	        @Override
	        public void mouseDragged(MouseEvent e) {
	        }
	    });
		
	}
	
	@Override
	protected void paintComponent(Graphics g) {
	    super.paintComponent(g);
	    Graphics2D g2 = (Graphics2D)g;
	    g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setRenderingHint(RenderingHints.KEY_FRACTIONALMETRICS, RenderingHints.VALUE_FRACTIONALMETRICS_ON);
	    g.setColor(new Color(84, 159, 172));
	    for (int j = 0; j < 40; j++) {
	    	for (int i = 0; i < 120; i++) {
		    	g.fillOval((int)(30+ i * 40 - 0.1 * cursorTrackerX) , 
		    			   (int)(this.curWindowSize.height * 0.6 - 0.1 * cursorTrackerY + j*40), 12, 12);
		    }
	    }
	    
	    this.repaint();
	    this.revalidate();
	}
}
