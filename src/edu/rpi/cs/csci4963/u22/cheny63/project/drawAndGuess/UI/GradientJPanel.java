package edu.rpi.cs.csci4963.u22.cheny63.project.drawAndGuess.UI;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JPanel;

public class GradientJPanel extends JPanel{
	private static final long serialVersionUID = 1L;

	@Override
    public void paintComponent(Graphics g) {
        // super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        int w = getWidth(), h = getHeight();
        g2d.setPaint(new Color(0, 0, 0, 0));
        g2d.fillRect(0, 0, w, h);
    }
}
