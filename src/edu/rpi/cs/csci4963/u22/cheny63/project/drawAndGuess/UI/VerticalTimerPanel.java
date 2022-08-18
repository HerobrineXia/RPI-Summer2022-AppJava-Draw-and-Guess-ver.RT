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

    public VerticalTimerPanel () throws FontFormatException, IOException {
        super("" ,JLabel.LEFT);
        Font goreRegular = Font.createFont(Font.TRUETYPE_FONT, new File("./res/gui/font/Gore Regular.otf"));
        goreRegular  = goreRegular.deriveFont(Font.PLAIN, 140);
        this.setFont(goreRegular);
        this.setForeground(Color.WHITE);
        pattern = "mm:ss";
        createTimer();
        timer.start();
    }

    public VerticalTimerPanel(String pattern) {
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

    public void paint (Graphics g){
        if (g instanceof Graphics2D) {
            Graphics2D g2 = (Graphics2D) g;
            AffineTransform flipTrans = new AffineTransform();
            double widthD = (double) getWidth();
            flipTrans.setToRotation(-Math.PI / 2.0, 500, 500);
            g2.setTransform(flipTrans);
            super.paint(g);
        } else {
            super.paint(g);
        }
    }
    
	public static void main(String[] args) throws FontFormatException, IOException {
		JFrame testframe = new JFrame();
		// avoid image displace case,  not necessary
		testframe.add(new VerticalTimerPanel());
		testframe.setSize(620, 640);
		testframe.setLocationRelativeTo(null); // set window centre
		testframe.setAlwaysOnTop(true); // since it is important, let it top
		testframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		testframe.setVisible(true);
	}
}