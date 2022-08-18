package edu.rpi.cs.csci4963.u22.cheny63.project.drawAndGuess.UI;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.Timer;
import java.awt.*;
import java.awt.geom.*;
import java.io.File;
import java.io.IOException;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.AffineTransform;

public class VerticalTimerPanel extends OpaqueJPanel {
	private Font goreRegular;
	private FontMetrics metrics;
	private java.awt.Toolkit toolkit = java.awt.Toolkit.getDefaultToolkit();
    
    public VerticalTimerPanel() throws FontFormatException, IOException {
		goreRegular = Font.createFont(Font.TRUETYPE_FONT, new File("./res/gui/font/Gore Regular.otf"));
		metrics = getFontMetrics(goreRegular); 
    }

    @Override
    public Dimension getPreferredSize() {
    	return new Dimension(220, 70);
    }
    @Override
    public void paint(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;		
        goreRegular  = goreRegular.deriveFont(Font.PLAIN, 250);
        
        // Define rendering hint, font name, font style and font size
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setFont(goreRegular);
        g2.setColor(Color.WHITE);
        

        // Rotate 90 degree to make a vertical text
        g2.rotate(Math.toRadians(-90));
        g2.drawString("1:30", -590, 200);
    }
    
    public static void main(String[] args) throws FontFormatException, IOException {
        JFrame frame = new JFrame();
        frame.setBackground(Color.black);
        frame.setTitle("Draw Vertical Text Demo");
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.add(new VerticalTimerPanel());
        frame.pack();
        frame.setSize(420, 350);
        frame.setVisible(true);
        // control remain time ==> get seconds
    }
}