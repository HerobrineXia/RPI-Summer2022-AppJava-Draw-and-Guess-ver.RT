package edu.rpi.cs.csci4963.u22.cheny63.project.drawAndGuess.UI;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.Graphics;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.Image;
import java.awt.Toolkit;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import edu.rpi.cs.csci4963.u22.cheny63.project.drawAndGuess.tools.ImageUtility;


class startGameBackground extends JPanel {
	private static final long serialVersionUID = 1L;
	private Image backgroundImage;
	private Dimension curWindowSize;

	  public startGameBackground(String fileName, Dimension screenSize) throws IOException {
		this.curWindowSize = new Dimension((int)screenSize.getWidth(), 
							 (int)(screenSize.getWidth())/16*9);
		this.setBackground(new Color(32, 130, 147));
		backgroundImage = ImageIO.read(new File(fileName));
	  }

	  public void paintComponent(Graphics g) {
	    super.paintComponent(g);
	    // Draw the background image.
	    g.drawImage(ImageUtility.resizeIcon(backgroundImage, curWindowSize), 0, 0, this);
	  }
}

public class StartGameFrame extends JFrame{
	private static final long serialVersionUID = 1L;
	// param for setting maximum size
	GraphicsEnvironment graphics = GraphicsEnvironment.getLocalGraphicsEnvironment();
	GraphicsDevice device = graphics.getDefaultScreenDevice();
	
	/**
	 * main GUI generation function
	 * @throws IOException 
	 * @throws FontFormatException 
	 */
	private void generateGUI() throws FontFormatException, IOException {
		// get current window size and basic background
		this.getContentPane().add(new startGameBackground("./res/gui/startGameScreen/bg.png", 
				                  Toolkit.getDefaultToolkit().getScreenSize()));
		this.setBackground(new Color(32, 130, 147));
		

		
		
        //Display the window.       
		device.setFullScreenWindow(this);
		this.setLocationRelativeTo(null); // set window centre
		this.setResizable(true);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setVisible(true);
	}
	
	
	/**
	 * constructor: will generate a init setting panel
	 *  for users to select modes
	 */
	public StartGameFrame() {
		super("Start a game... - Battleship Generator");
		
		try {
			generateGUI();
		} catch (Exception e) { // case: cannot get resource
			JOptionPane.showMessageDialog(this, 
			"Fail to load game resource: please check resource", 
			"Oops...", JOptionPane.ERROR_MESSAGE);
		}
	}
}
