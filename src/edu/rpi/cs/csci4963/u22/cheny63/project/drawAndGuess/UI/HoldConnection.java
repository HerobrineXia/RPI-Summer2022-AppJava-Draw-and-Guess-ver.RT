package edu.rpi.cs.csci4963.u22.cheny63.project.drawAndGuess.UI;

import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

/** 
 *  OVERVIEW: 
 * 	<b>HoldConnection</b> generate a simple notification frame serving as the transition
 *
 * @author Yuetian Chen
 * @version <b>1.0</b> rev. 0
 */
public class HoldConnection{
	JFrame mainFrameWaiter;
	/**
	 * Constructor: generate a simple notification frame serving as the transition
	 */
	public HoldConnection() {
		mainFrameWaiter = new JFrame("Please wait..");
		JLabel text = new JLabel("Connecting to your peer...");
		JPanel mainPanel = new JPanel();
		mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.PAGE_AXIS));
		mainPanel.add(text);
		mainFrameWaiter.add(mainPanel);
		
		//Display the window.       
		mainFrameWaiter.setSize(350, 150);
		mainFrameWaiter.setLocationRelativeTo(null); // set window centre
		mainFrameWaiter.setResizable(false);
		mainFrameWaiter.setAlwaysOnTop(true);
		mainFrameWaiter.setVisible(true);
	}
	
	/**
	 * Helper function for calling externally to close the current window
	 */
	public void close() {
		if (this.mainFrameWaiter != null)
			this.mainFrameWaiter.dispose();
	}
}
