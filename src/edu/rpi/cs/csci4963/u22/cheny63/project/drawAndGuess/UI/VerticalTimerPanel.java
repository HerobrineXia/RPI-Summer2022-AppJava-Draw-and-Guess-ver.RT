package edu.rpi.cs.csci4963.u22.cheny63.project.drawAndGuess.UI;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.swing.JLabel;
import javax.swing.Timer;
import java.awt.*;
import java.awt.geom.*;

public class VerticalTimerPanel extends JLabel {

    private String pattern;
    private Timer timer;

    public VerticalTimerPanel ()
    {
        super();
        pattern = "hh:mm:ss a";
        createTimer();
        timer.start();
    }

    public VerticalTimerPanel(String pattern)
    {
        super (pattern);
        this.pattern = pattern;
        createTimer();
        timer.start();
    }

    private void createTimer(){
        timer = new Timer(0, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent arg0) {
                setText(new SimpleDateFormat(pattern).format(new Date()));
            }
        });
    }

    public void paint (Graphics g)
    {
        if (g instanceof Graphics2D) {
            Graphics2D g2 = (Graphics2D) g;
            AffineTransform flipTrans = new AffineTransform();
            double widthD = (double) getWidth();
            flipTrans.setToRotation(-Math.PI / 2.0, getWidth() / 2.0, getHeight() / 2.0);
            g2.setTransform(flipTrans);
            super.paint(g);
        } else {
            super.paint(g);
        }
    }
}