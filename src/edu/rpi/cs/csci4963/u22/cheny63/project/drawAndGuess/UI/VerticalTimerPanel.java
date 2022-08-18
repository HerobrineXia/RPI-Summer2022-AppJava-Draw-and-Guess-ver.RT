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

public class VerticalTimerPanel extends JLabel {

    private String pattern;
    private Timer timer;
    
    public VerticalTimerPanel() {
    	super();
    	this.setText("Text");
    }
    @Override
    public void paintComponent(Graphics g) {
    	// super();
    	
    }
}