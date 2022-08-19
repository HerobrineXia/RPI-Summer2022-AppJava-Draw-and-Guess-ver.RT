package edu.rpi.cs.csci4963.u22.cheny63.project.drawAndGuess.UI;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.font.GlyphVector;
import java.text.SimpleDateFormat;
import javax.swing.JFrame;
import javax.swing.Timer;
import java.awt.*;
import java.awt.geom.*;
import java.io.File;
import java.io.IOException;

import javax.swing.*;

public class VerticalTimerPanel extends JPanel {
	private Font goreRegular;
	private FontMetrics metrics;
	private java.awt.Toolkit toolkit = java.awt.Toolkit.getDefaultToolkit();
	
	// Timer component
	private Timer timer;
    private long startTime = -1;
    private long duration = 90;
    public boolean end;
    private String timerInfo;
    
    public VerticalTimerPanel() throws FontFormatException, IOException {
		goreRegular = Font.createFont(Font.TRUETYPE_FONT, new File("./res/gui/font/Gore Regular.otf"));
		metrics = getFontMetrics(goreRegular); 
		this.setBackground(new Color(32, 130, 147));
		this.duration = duration * 1000; // convert to milsec
        this.setLayout(new GridBagLayout());
        timer = new Timer(10, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (startTime < 0) {
                    startTime = System.currentTimeMillis();
                }
                long now = System.currentTimeMillis();
                long clockTime = now - startTime;
                if (clockTime >= VerticalTimerPanel.this.duration) {
                    clockTime = VerticalTimerPanel.this.duration;
                    timer.stop();
                }
                SimpleDateFormat df = new SimpleDateFormat("m:ss");
                timerInfo = df.format(VerticalTimerPanel.this.duration - clockTime);
                VerticalTimerPanel.this.repaint();
                VerticalTimerPanel.this.revalidate();
            }
        });
        timer.setInitialDelay(0);
        this.timerInfo = "HOLD";
    }

    @Override
    public Dimension getPreferredSize() {
    	return new Dimension(220, 70);
    }
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
        
        System.out.println(xForShapeCreation);
        System.out.println(g2.getFontMetrics(this.goreRegular).getAscent());
        
        // Rotate 90 degree to make a vertical text
        g2.rotate(Math.toRadians(-90));
        g2.drawString(timerInfo, (int)(-xForShapeCreation*2 - 20), 
        						(int)(yForShapeCreation*2 + 20));
        this.repaint();
        this.revalidate();
    }
    
    public static void main(String[] args) throws FontFormatException, IOException {
        JFrame frame = new JFrame();
        frame.setBackground(Color.black);
        frame.setTitle("Draw Vertical Text Demo");
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        VerticalTimerPanel test = new VerticalTimerPanel();
        frame.add(test);
        frame.pack();
        test.start();
        frame.setSize(420, 350);
        frame.setVisible(true);
    }
    
    /**
     * Observer: check if timer stopped
     * @return true if stopped
     */
    public boolean isStop() {
    	return !this.timer.isRunning();
    }
    
    /**
     * Observer: get the timer setting
     * @return the timer element
     */
    public Timer getTimer() {
    	return this.timer;
    }
    
    /**
     * Helper function: start the time
     */
    public void start() {
    	if (!timer.isRunning()) {
            startTime = -1;
            timer.start();
        }
    }
    
    /**
     * Helper function: reset timer
     */
    public void reset() {
        startTime = -1;
        timer.start();
    }
    
    /**
     * Helper function: pause the timer and set HOLD in timer
     */
    public void pause() {
    	timer.stop();
        this.timerInfo = "HOLD";
        this.repaint();
        this.revalidate();
    }
}