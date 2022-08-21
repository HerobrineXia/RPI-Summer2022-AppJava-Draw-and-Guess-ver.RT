package edu.rpi.cs.csci4963.u22.cheny63.project.drawAndGuess.UI;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import javax.swing.ButtonModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import edu.rpi.cs.csci4963.u22.cheny63.project.drawAndGuess.tools.ImageUtility;

/** 
 *  OVERVIEW: 
 * 	<b>ImageBrightener</b> is an helper class that make an image 
 *  brighter or darker when selected
 *
 * @author Yuetian Chen
 * @version <b>1.0</b> rev. 0
 */
class ImageBrightener extends JPanel{
	private static final long serialVersionUID = 1L;
	BufferedImage iamgeIcon;
    float brightPercent = 0.0f;

    /**
     * Constructor to set the corresponding image ready
     * @param image the input pre-processed image
     */
    public ImageBrightener(Image image){
        iamgeIcon = ImageUtility.toBufferedImage(image);
        this.setOpaque(true);
    }
    
	/**
	 * Helper function: set the initial prefer size of current panel, override
	 */
    @Override
    public Dimension getPreferredSize(){
        return new Dimension(iamgeIcon.getWidth(), iamgeIcon.getHeight());
    }
    

	/**
	 * overrided paintComponent func, use to render
	 * and change the button
	 * @param g  the graphic going to be paint
	 */
    public void paintComponent(Graphics g){
        // super.paintComponent(g);
        g.drawImage(iamgeIcon, 0, 0, this);
        if (this.brightPercent == -1) {
        	int brightness = (int)(255 * 0.7);
            g.setColor(new Color(0, 0, 0, brightness));
        }else {
        	int brightness = (int)(255 * brightPercent);
            g.setColor(new Color(255, 255, 255, brightness));
        }
        
        g.fillRect(0, 0, getWidth(), getHeight());
    }
    
    /**
     * get the width of the button, used for constructing in a JPanel
     */
    @Override
    public int getWidth() {
    	return this.iamgeIcon.getWidth();
    }
    
    /**
     * get the height of the button, used for constructing in a JPanel
     */
    @Override
    public int getHeight() {
    	return this.iamgeIcon.getHeight();
    }
    
    /**
     * helper function for making the image brighter in rendering
     */
    public void brighter() {
    	this.brightPercent = 0.7f;
    	this.repaint();
    	this.revalidate();
    }
    
    /**
     * helper function for making the image restore its luminous setting in rendering
     */
    public void restore() {
    	this.brightPercent = 0.f;
    	this.repaint();
    	this.revalidate();
    }
    
    /**
     * helper function for making the image darker in rendering
     */
    public void darker() {
    	this.brightPercent = -1;
    	this.repaint();
    	this.revalidate();
    }
}

/** 
 *  OVERVIEW: 
 * 	<b>DrawBoardButton</b> is an custon button class used for drawboard GUI design
 *
 * @author Yuetian Chen
 * @version <b>1.0</b> rev. 0
 */
public class DrawBoardButton extends JButton{
	private static final long serialVersionUID = 1L;
	private ImageBrightener buttonContent;
	
	/**
	 * Constructor for the current button, set every variable ready
	 * @param image the  input image as the main component of the button
	 * @param needContentBrighter true if we need image in button brighter when selected
	 */
	public DrawBoardButton(ImageIcon image, boolean needContentBrighter) {
		this.setBorderPainted(false);
		try {
			this.buttonContent = new ImageBrightener(image.getImage());
			this.setContentAreaFilled(false);
			this.add(buttonContent);
			this.setFocusPainted(false);
			// this.setPreferredSize(new Dimension(buttonContent.getWidth(), buttonContent.getHeight()));
		} catch (Exception ex) {
			ex.printStackTrace();
			JOptionPane.showMessageDialog(null, "Failed to load icon resource", "Oops...", JOptionPane.ERROR_MESSAGE);
		}
		
		// custom of the button behavior
        this.getModel().addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                ButtonModel model = (ButtonModel) e.getSource();
                if (model.isRollover()) {
                	DrawBoardButton.this.setContentAreaFilled( true );
                	DrawBoardButton.this.setForeground(new Color(32, 130, 147));
                	DrawBoardButton.this.buttonContent.brighter();
                	DrawBoardButton.this.setContentAreaFilled(false);
                } else {
                	DrawBoardButton.this.setForeground(new Color(251, 251, 251));
                	if(DrawBoardButton.this.isEnabled())
                		DrawBoardButton.this.buttonContent.restore();
                	else
                		DrawBoardButton.this.buttonContent.darker();
                	DrawBoardButton.this.setContentAreaFilled(false);
                }
                if (model.isPressed()) {
                	DrawBoardButton.this.setContentAreaFilled(false);
                	DrawBoardButton.this.buttonContent.darker();
                	DrawBoardButton.this.setForeground(Color.GRAY);
                }
            }
        });
	}
	
//	public static void main(String args[ ]){
//		JFrame frame = new JFrame("Test");
//		JPanel panel = new JPanel();
//		panel.add(new DrawBoardButton(ImageUtility.resizeIcon(new ImageIcon("./res/gui/gameSession/pencil.png"), 0.5), true));
//		frame.add(panel);
//		panel.setBackground(Color.BLUE);
//		frame.setSize(800, 640);
//		frame.setVisible(true);
//	} 
}
