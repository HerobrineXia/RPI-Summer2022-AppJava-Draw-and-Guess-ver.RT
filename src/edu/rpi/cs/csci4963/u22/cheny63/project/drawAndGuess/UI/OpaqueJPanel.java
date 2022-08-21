package edu.rpi.cs.csci4963.u22.cheny63.project.drawAndGuess.UI;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JPanel;
/** 
 *  OVERVIEW: 
 * 	<b>OpaqueJPanel</b> generate a custom JPanel by setting the bakground as none
 * by default
 *
 * @author Yuetian Chen
 * @version <b>1.0</b> rev. 0
 */
public class OpaqueJPanel extends JPanel{
	private static final long serialVersionUID = 1L;

	/**
	 * overrided paintComponent func, use to render
	 * and change the panel
	 * @param g  the graphic going to be paint
	 */
	@Override
    public void paintComponent(Graphics g) {
        // super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        int w = getWidth(), h = getHeight();
        g2d.setPaint(new Color(0, 0, 0, 0));
        g2d.fillRect(0, 0, w, h);
    }
}
