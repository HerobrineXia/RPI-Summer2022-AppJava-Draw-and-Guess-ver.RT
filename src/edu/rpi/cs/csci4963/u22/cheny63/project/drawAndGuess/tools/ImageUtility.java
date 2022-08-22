package edu.rpi.cs.csci4963.u22.cheny63.project.drawAndGuess.tools;

import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;

import javax.swing.ImageIcon;

/**
 * ImageUtility is the utility for generating a image scale when input as the image icon
 * 
 * @author Yuetian Chen
 * @version <b>1.0</b> rev. 0
 */
public class ImageUtility {
	
    /**
     * Helper function: change the size of input icon for rendering
     * @param imageIcon the input icon
     * @param newSize the scale ratio
     * @return Image, the scaled input imageIcon icon
     */
    public static Image resizeIcon(Image imageIcon, Dimension newSize) {
    	// System.out.println("current: " + imageIcon.getIconHeight()*scale + " " + imageIcon.getIconWidth()*scale + " " + scale);
    	return imageIcon.getScaledInstance((int) newSize.getWidth(),
    			                           (int) newSize.getHeight(), 
    			                           java.awt.Image.SCALE_SMOOTH);
    }
    
    /**
     * Helper function: change the size of input icon for rendering
     * @param imageIcon the input icon
     * @param scale the scale ratio, should smaller than 1
     * @return ImageIcon, the scaled input imageIcon icon
     */
    public static ImageIcon resizeIcon(ImageIcon imageIcon, double scale) {
    	// System.out.println("current: " + imageIcon.getIconHeight()*scale + " " + imageIcon.getIconWidth()*scale + " " + scale);
    	if (imageIcon.getIconWidth() != -1)
    		return new ImageIcon(imageIcon.getImage().getScaledInstance((int)(imageIcon.getIconWidth()*scale), 
					(int)(imageIcon.getIconHeight()*scale), java.awt.Image.SCALE_SMOOTH));
    	else
    		return new ImageIcon();
    }
    
    /**
     * Converts a given Image into a BufferedImage
     *
     * @param importedImage The Image to be converted
     * @return The converted BufferedImage
     */
    public static BufferedImage toBufferedImage(Image importedImage) {
        if (importedImage instanceof BufferedImage)
            return (BufferedImage) importedImage;

        // Create a buffered image with transparency
        BufferedImage resultBuffered = new BufferedImage(importedImage.getWidth(null), 
        												 importedImage.getHeight(null), 
        												 BufferedImage.TYPE_INT_ARGB);

        // Draw the image on to the buffered image
        Graphics2D backgroundCtration = resultBuffered.createGraphics();
        backgroundCtration.drawImage(importedImage, 0, 0, null);
        backgroundCtration.dispose();

        // Return the buffered image
        return resultBuffered;
    }
}
