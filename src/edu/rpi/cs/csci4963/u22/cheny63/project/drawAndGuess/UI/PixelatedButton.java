package edu.rpi.cs.csci4963.u22.cheny63.project.drawAndGuess.UI;

import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.IOException;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class PixelatedButton extends JButton{
	private static final long serialVersionUID = 1L;
	private Font goreRegular = Font.createFont(Font.TRUETYPE_FONT, new File("./res/gui/font/Gore Regular.otf"));
	private String buttonText;
	
    public PixelatedButton(String text, Action actionClicked) throws FontFormatException, IOException {
    	// set font
    	this.goreRegular  = goreRegular.deriveFont(Font.PLAIN, 70);
    	this.buttonText = text;
    	// basic info filling
        this.setFont(goreRegular);
        this.setText(text);
        this.setForeground(Color.WHITE);
        PixelatedButton.this.setContentAreaFilled(false);
        this.setPreferredSize(new Dimension(900, 80));
        
        // activate action
        this.addActionListener(new java.awt.event.ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	actionClicked.actionPerformed(e);
            }
        });
        
        this.setBorderPainted(false);
        this.setFocusPainted(false);
        this.setHorizontalTextPosition(SwingConstants.CENTER);
        this.setVerticalTextPosition(SwingConstants.BOTTOM);

        this.getModel().addChangeListener(new ChangeListener() {
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