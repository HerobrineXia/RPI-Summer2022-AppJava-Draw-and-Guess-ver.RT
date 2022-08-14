package edu.rpi.cs.csci4963.u22.cheny63.project.drawAndGuess.tools;

import java.awt.Dimension;
import java.awt.Image;

public class ImageUtility {
	
    /**
     * Helper function: change the size of input icon for rendering
     * @param imageIcon the input icon
     * @param scale the scale ratio, should smaller than 1
     * @return Image, the scaled input imageIcon icon
     */
    public static Image resizeIcon(Image imageIcon, Dimension newSize) {
    	// System.out.println("current: " + imageIcon.getIconHeight()*scale + " " + imageIcon.getIconWidth()*scale + " " + scale);
    	return imageIcon.getScaledInstance((int) newSize.getWidth(),
    			                           (int) newSize.getHeight(), 
    			                           java.awt.Image.SCALE_SMOOTH);
    }
}
