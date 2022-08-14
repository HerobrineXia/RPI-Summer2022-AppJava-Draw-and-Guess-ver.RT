package edu.rpi.cs.csci4963.u22.cheny63.project.drawAndGuess.UI;

import javax.swing.BoxLayout;
import javax.swing.JButton;
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
	private static final long serialVersionUID = 1L;
	JFrame mainFrameWaiter;
	/**
	 * Constructor: generate a simple notification frame serving as the transition
	 */
	public HoldConnection() {
		mainFrameWaiter = new JFrame("Please wait..");
		JLabel text = new JLabel("Connecting to your peer...");
		JPanel mainPanel = new JPanel();
		JButton cancel = new JButton("Cancel");
		mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
		mainPanel.add(text);
		mainPanel.add(cancel);
		mainFrameWaiter.add(mainPanel);
		
		//Display the window.       
		mainFrameWaiter.setSize(350, 150);
		mainFrameWaiter.setLocationRelativeTo(null); // set window centre
		mainFrameWaiter.setResizable(false);
		mainFrameWaiter.setVisible(true);
	}
	
	public void close() {
		if (this.mainFrameWaiter != null)
			this.mainFrameWaiter.dispose();
	}
}
