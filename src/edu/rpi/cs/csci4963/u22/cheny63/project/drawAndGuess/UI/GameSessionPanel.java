package edu.rpi.cs.csci4963.u22.cheny63.project.drawAndGuess.UI;

import java.awt.Color;
import java.awt.FontFormatException;
import java.awt.Graphics;
import java.io.IOException;
import java.time.Duration;
import java.time.Instant;

import javax.swing.JPanel;

/** 
 *  OVERVIEW: 
 * 	<b>GameSessionPanel</b> is an helper class for GameSessionFrame
 *  used to render custom pattern as the background 
 *
 * @author Yuetian Chen
 * @version <b>1.0</b> rev. 0
 */
public class GameSessionPanel extends JPanel{
	private static final long serialVersionUID = 1L;
	private Instant start = Instant.now();
	
	/**
	 * Constructor for building the GameSessionPanel by setting a custom color
	 * @throws FontFormatException when system is unable to find the correct format of font 
	 * @throws IOException when system is unable to find font
	 */
	public GameSessionPanel() throws FontFormatException, IOException {
		super();
		this.setBackground(new Color(32, 130, 147));
	}
	
	/**
	 * overrided paintComponent func, use to render
	 * and change the panel
	 * @param g  the graphic going to be paint
	 */
	@Override
	protected void paintComponent(Graphics g) {
	    super.paintComponent(g);
	    Instant end = Instant.now();
	    g.setColor(new Color(84, 159, 172));
	    for (int j = 0; j < 120; j++) {
	    	for (int i = 0; i < 30; i++) {
		    	g.fillOval((int)(i * 40 - 660 + Math.sin(Duration.between(start, end).toSeconds())) , 
		    			  (int)(j * 40), 12, 12);
		    }
	    }       
        
	    this.repaint();
	    this.revalidate();
	}

//	public static void main(String[] args) throws FontFormatException, IOException {
//		JFrame testframe = new JFrame();
//		// avoid image displace case,  not necessary
//		testframe.add(new GameSessionPanel());
//		testframe.setSize(620, 640);
//		testframe.setLocationRelativeTo(null); // set window centre
//		testframe.setAlwaysOnTop(true); // since it is important, let it top
//		testframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//		testframe.setVisible(true);
//	}
}