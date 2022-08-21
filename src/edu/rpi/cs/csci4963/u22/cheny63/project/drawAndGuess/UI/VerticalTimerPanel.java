package edu.rpi.cs.csci4963.u22.cheny63.project.drawAndGuess.UI;

import java.awt.font.GlyphVector;
import java.awt.*;
import java.awt.geom.*;
import java.io.File;
import java.io.IOException;

import javax.swing.*;
<<<<<<< HEAD

/** 
 *  OVERVIEW: 
 * 	<b>VerticalTimerPanel</b> is an component in game start session
 * by generating text that is vertical(used as the vertical presentation)
 *
 * @author Yuetian Chen
 * @version <b>1.0</b> rev. 0
 */
=======
>>>>>>> b9e16d3490c342635bcd4bf34256f7739fcbe4d5
public class VerticalTimerPanel extends JPanel {
	private static final long serialVersionUID = 1L;
	private Font goreRegular;
	
	// Timer component
    public boolean end;
    private String timerInfo;
    
    /**
     * Constructor of the current vertical time panel 
     * setting the time information as the init state
	 * @throws FontFormatException when system is unable to find the correct format of font 
	 * @throws IOException when system is unable to find font
     */
    public VerticalTimerPanel() throws FontFormatException, IOException {
		goreRegular = Font.createFont(Font.TRUETYPE_FONT, new File("./res/gui/font/Gore Regular.otf"));
		this.setBackground(new Color(32, 130, 147));
        this.timerInfo = "HOLD";
    }

	/**
	 * Helper function: set the initial prefer size of current panel, override
	 */
    @Override
    public Dimension getPreferredSize() {
    	return new Dimension(220, 70);
    }
    
	/**
	 * overrided paintComponent func, use to render
	 * and change the button
	 * @param g  the graphic going to be paint
	 */
    @Override
    public void paintComponent(Graphics g) {
    	super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        goreRegular  = goreRegular.deriveFont(Font.PLAIN, 250);
        
        // Define rendering hint, font name, font style and font size
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setFont(goreRegular);
        g2.setColor(Color.WHITE);
        

        // ACTIVATE ANTIALIASING AND FRACTIONAL METRICS
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setRenderingHint(RenderingHints.KEY_FRACTIONALMETRICS, RenderingHints.VALUE_FRACTIONALMETRICS_ON);

        // CREATE GLYPHVECTOR FROM TEXT, CREATE PRELIMINARY SHAPE FOR COORDINATE CALCULATION, CALC COORDINATES
        final GlyphVector gv = this.goreRegular.createGlyphVector(g2.getFontRenderContext(), this.timerInfo);
        final Rectangle2D stringBoundsForPosition = gv.getOutline().getBounds2D();
        final double xForShapeCreation = (stringBoundsForPosition.getWidth()) / 2d;
        final double yForShapeCreation = (stringBoundsForPosition.getHeight()) / 2d;
        
        
        // Rotate 90 degree to make a vertical text
        g2.rotate(Math.toRadians(-90));
        g2.drawString(timerInfo, (int)(-xForShapeCreation*2 - 20), 
        						(int)(yForShapeCreation*2 + 20));
        this.repaint();
        this.revalidate();
    }
    
    /**
     *  Helper function update time
     * @param timeInterval the selected 
     */
    public void updateTime(int timeInterval) {
    	int min = timeInterval/60;
        int sec = timeInterval-(min*60);
        timerInfo = String.format("%s:%s",String.valueOf(min), String.valueOf(sec).length() == 1? "0"+String.valueOf(sec):String.valueOf(sec));
        this.repaint();
        this.revalidate();
    }
}