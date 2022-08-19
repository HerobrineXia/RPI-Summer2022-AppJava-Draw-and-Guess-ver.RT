package edu.rpi.cs.csci4963.u22.cheny63.project.drawAndGuess.UI;

import java.awt.font.GlyphVector;
import java.text.SimpleDateFormat;
import java.awt.*;
import java.awt.geom.*;
import java.io.File;
import java.io.IOException;

import javax.swing.*;

public class VerticalTimerPanel extends JPanel {
	private static final long serialVersionUID = 1L;
	private Font goreRegular;
	
	// scale component
    GraphicsConfiguration scaleSys = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice().getDefaultConfiguration();
    double scaleX = scaleSys.getDefaultTransform().getScaleX();
    
	// Timer component
    public boolean end;
    private String timerInfo;
    private int fontSize;
    
    public VerticalTimerPanel() throws FontFormatException, IOException {
    	fontSize = (int) (260 / this.scaleX);
		goreRegular = Font.createFont(Font.TRUETYPE_FONT, new File("./res/gui/font/Gore Regular.otf"));
		this.setBackground(new Color(32, 130, 147));
        this.timerInfo = "HOLD";
    }

    @Override
    public Dimension getPreferredSize() {
    	return new Dimension((int)(220 / this.scaleX), (int)(70 / this.scaleX));
    }
    @Override
    public void paintComponent(Graphics g) {
    	super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        goreRegular  = goreRegular.deriveFont(Font.PLAIN, fontSize);
        
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
    	SimpleDateFormat df = new SimpleDateFormat("m:ss");
        timerInfo = df.format(timeInterval);
        this.repaint();
        this.revalidate();
    }
}