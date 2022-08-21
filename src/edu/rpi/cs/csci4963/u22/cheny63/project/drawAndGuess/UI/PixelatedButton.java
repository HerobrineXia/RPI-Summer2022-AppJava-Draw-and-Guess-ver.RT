package edu.rpi.cs.csci4963.u22.cheny63.project.drawAndGuess.UI;

import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.IOException;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

/** 
 *  OVERVIEW: 
 * 	<b>PixelatedButton</b> is an custon button class used for pixel button GUI design
 *
 * @author Yuetian Chen
 * @version <b>1.0</b> rev. 0
 */
public class PixelatedButton extends JButton{
	private static final long serialVersionUID = 1L;
	private Font goreRegular = Font.createFont(Font.TRUETYPE_FONT, new File("./res/gui/font/Gore Regular.otf"));
	private String buttonText;
	
	/**
	 *  Constructor for this button by setting everything ready and assign
	 *  the basic behavior to this button
	 * @param text the text content to the button
	 * @param actionClicked the action assigned to this button
	 * @param parent a StartGamePanel storing all info passed from above
	 * @throws FontFormatException when system is unable to find the correct format of font 
	 * @throws IOException when system is unable to find font
	 */
    public PixelatedButton(String text, Action actionClicked, StartGamePanel parent) throws FontFormatException, IOException {
    	// set font
    	this.goreRegular  = goreRegular.deriveFont(Font.PLAIN, 70);
    	this.buttonText = text;
    	// basic info filling
        this.setFont(goreRegular);
        this.setText(text);
        this.setForeground(new Color(253, 253, 253));
        PixelatedButton.this.setContentAreaFilled(false);
        this.setPreferredSize(new Dimension(900, 80));
        
        // activate action
        this.addActionListener(new java.awt.event.ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	actionClicked.actionPerformed(e);
            }
        });
        
        this.addMouseMotionListener(new MouseMotionListener() {
	        @Override
	        public void mouseMoved(MouseEvent e) {
	        	parent.cursorTrackerY =  e.getLocationOnScreen().getY();
	        	parent.cursorTrackerX =  e.getLocationOnScreen().getX();
	        }
	        @Override
	        public void mouseDragged(MouseEvent e) {
	        }
	    });
        
        this.setBorderPainted(false);
        this.setFocusPainted(true);
        
        this.setHorizontalTextPosition(SwingConstants.CENTER);
        this.setVerticalTextPosition(SwingConstants.BOTTOM);

        this.getModel().addChangeListener(new ChangeListener() { // set custom behavior
            @Override
            public void stateChanged(ChangeEvent e) {
                ButtonModel model = (ButtonModel) e.getSource();
                if (model.isRollover()) {
                	PixelatedButton.this.setContentAreaFilled( true );
                	PixelatedButton.this.setForeground(new Color(32, 130, 147));
                	PixelatedButton.this.setText(">" + PixelatedButton.this.buttonText + "<");
                	PixelatedButton.this.setBackground(Color.WHITE);
                } else {
                	PixelatedButton.this.setForeground(Color.WHITE);
                	PixelatedButton.this.setText(PixelatedButton.this.buttonText);
                	PixelatedButton.this.setContentAreaFilled(false);
                }
                if (model.isPressed()) {
                	PixelatedButton.this.setContentAreaFilled(false);
                	PixelatedButton.this.setText(PixelatedButton.this.buttonText);
                	PixelatedButton.this.setForeground(Color.GRAY);
                }
            }
        });
    }

//    public static void main(String args[]) {
//        JFrame test = new JFrame();
//        JPanel panel = new JPanel();
//        try {
//        	panel.add(new PixelatedButton("Text"));
//		} catch (FontFormatException | IOException e) {
//			
//			e.printStackTrace();
//		}
//        test.add(panel);
//        test.setBackground(new Color(32, 130, 147));
//        test.setSize(500, 250);
//        test.setLocationRelativeTo(null); // set window centre
//        test.setVisible(true);
//    }
}