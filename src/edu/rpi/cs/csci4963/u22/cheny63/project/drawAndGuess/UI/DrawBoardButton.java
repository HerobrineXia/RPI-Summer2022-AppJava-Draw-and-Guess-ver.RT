package edu.rpi.cs.csci4963.u22.cheny63.project.drawAndGuess.UI;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ButtonModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import edu.rpi.cs.csci4963.u22.cheny63.project.drawAndGuess.tools.ImageUtility;


class ImageBrightener extends JPanel{
	private static final long serialVersionUID = 1L;
	BufferedImage iamgeIcon;
    float brightPercent = 0.0f;

    public ImageBrightener(Image image){
        iamgeIcon = ImageUtility.toBufferedImage(image);
        this.setOpaque(true);
    }
    
    public Dimension getPreferredSize(){
        return new Dimension(iamgeIcon.getWidth(), iamgeIcon.getHeight());
    }

    public void paintComponent(Graphics g){
        // super.paintComponent(g);
        g.drawImage(iamgeIcon, 0, 0, this);
        int brightness = (int)(255 * brightPercent);
        g.setColor(new Color(255, 255, 255, brightness));
        g.fillRect(0, 0, getWidth(), getHeight());
    }
    
    @Override
    public int getWidth() {
    	return this.iamgeIcon.getWidth();
    }
    
    @Override
    public int getHeight() {
    	return this.iamgeIcon.getHeight();
    }
    
    public void brighter() {
    	this.brightPercent = 0.7f;
    	this.repaint();
    	this.revalidate();
    }
    
    public void restore() {
    	this.brightPercent = 0.f;
    	this.repaint();
    	this.revalidate();
    }
    
    public void darker() {
    	this.brightPercent = 0.f;
    	this.repaint();
    	this.revalidate();
    }

//    public static void main(String[] args){
//        final JFrame frame = new JFrame();
//        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//        frame.add(new ImageBrightener(new File("./res/gui/gameSession/pencil.png")));
//        frame.pack();
//        frame.setLocationRelativeTo(null);
//        frame.setVisible(true);
//    }
}

public class DrawBoardButton extends JButton{
	private static final long serialVersionUID = 1L;
	private ImageBrightener buttonContent;
	
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
                	DrawBoardButton.this.buttonContent.restore();
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
