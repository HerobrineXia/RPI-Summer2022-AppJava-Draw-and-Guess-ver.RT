package edu.rpi.cs.csci4963.u22.cheny63.project.drawAndGuess.UI;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.Rectangle2D;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.Timer;

/** 
 *  OVERVIEW: 
 * 	<b>VerticalTimerPanel</b> will generate a on-going and controllable
 *  timer for counting down
 *
 * @author Yuetian Chen
 * @version <b>1.0</b> rev. 0
 */
public class VerticalTimerPanel extends JPanel {
	private static final long serialVersionUID = 1L;
	private Font goreRegular;
	private Timer timer;
    private long startTime = -1;
    private long duration;
    public boolean end;
    
    // GUI setting
    private RotateLabel contentHolder;

    /**
     * constructor: will generate a on-going and controllable
     *  timer for counting down
     * @param duration the given duration is seconds
     * @throws IOException 
     * @throws FontFormatException 
     */
    public VerticalTimerPanel(long duration) throws FontFormatException, IOException {
    	
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
                contentHolder.setBackground(clockTime < 1000? Color.RED : Color.BLACK);
                if (clockTime >= VerticalTimerPanel.this.duration) {
                    clockTime = VerticalTimerPanel.this.duration;
                    timer.stop();
                }
                SimpleDateFormat df = new SimpleDateFormat("mm:ss");
                contentHolder.setText(df.format(VerticalTimerPanel.this.duration - clockTime));
            }
        });
        timer.setInitialDelay(0);
        contentHolder = new RotateLabel("HOLD");
        contentHolder.setFont(this.goreRegular);
        this.add(contentHolder);
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
        contentHolder.setText("HOLD");
    }
    
	public static void main(String[] args) throws FontFormatException, IOException {
		JFrame testframe = new JFrame();
		// avoid image displace case,  not necessary
		VerticalTimerPanel TIMER = new VerticalTimerPanel(17);
		testframe.add(TIMER);
		TIMER.start();
		testframe.setSize(620, 640);
		testframe.setLocationRelativeTo(null); // set window centre
		testframe.setAlwaysOnTop(true); // since it is important, let it top
		testframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		testframe.setVisible(true);
	}
}

class RotateLabel extends JLabel {
    public RotateLabel(String text) throws FontFormatException, IOException {
       super(text);
       Font goreRegular = Font.createFont(Font.TRUETYPE_FONT, new File("./res/gui/font/Gore Regular.otf"));
	   goreRegular  = goreRegular.deriveFont(Font.PLAIN, 140);
       FontMetrics metrics = new FontMetrics(goreRegular){};
       Rectangle2D bounds = metrics.getStringBounds(text, null);
       setBounds(0, 0, (int) bounds.getWidth(), (int) bounds.getHeight());
    }
    @Override
    public void paintComponent(Graphics g) {
       Graphics2D gx = (Graphics2D) g;
       gx.rotate(0.6, 0, 0);
       super.paintComponent(g);
    }
 }
   
