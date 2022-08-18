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

public class VerticalTimerPanel extends JPanel {
    public static void main(String[] args) {
        JFrame frame = new JFrame();
        frame.setTitle("Draw Vertical Text Demo");
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.add(new VerticalTimerPanel());
        frame.pack();
        frame.setSize(420, 350);
        frame.setVisible(true);
    }

    @Override
    public void paint(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;

        // Define rendering hint, font name, font style and font size
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setFont(new Font("Segoe Script", Font.BOLD, 22));
        g2.setColor(Color.RED);

        // Rotate 90 degree to make a vertical text
        AffineTransform at = new AffineTransform();
        at.setToRotation(Math.toRadians(90), 80, 100);
        g2.setTransform(at);
        g2.drawString("This is a vertical text", 10, 10);
    }
}